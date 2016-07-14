package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.sourcecode.SourceCodeComparator;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Tracker {

    private final CheckCompile checker;

    public Tracker(CheckCompile checker) {
        this.checker = checker;
    }

    private static class RawData {
        private final SourceCode code;
        private final Duration timeUsed;
        private final Phase phase;
        private final Phase newPhase;

        private RawData(SourceCode code, Duration timeUsed, Phase phase, Phase newPhase) {
            this.code = code;
            this.timeUsed = timeUsed;
            this.phase = phase;
            this.newPhase = newPhase;
        }
    }

    private final List<RawData> data = new ArrayList<>();

    private List<RawData> phaseEnds() {
        return data.stream().filter(data -> data.phase != data.newPhase).collect(Collectors.toList());
    }

    public void push(SourceCode code, Duration timeUsed, Phase oldPhase, Phase newPhase) {
        data.add(new RawData(code, timeUsed, oldPhase, newPhase));
        changed = true;
    }

    public Duration getTotalTime(Phase phase) {
        return phaseEnds().stream().filter(data -> data.phase == phase)
                .map(data -> data.timeUsed).reduce(Duration.ZERO, Duration::plus);
    }

    public long numberOfFailedChecks(Phase phase) {
        return data.stream().filter(data -> data.phase == phase && data.newPhase == phase)
                .filter(data -> !checker.check(data.code, phase)).count();
    }

    public long numberOfCycles() {
        return data.stream().filter(data -> data.phase == Phase.BLACK && data.newPhase == Phase.RED)
                .count();
    }

    public List<Duration> timeUsedData() {
        return phaseEnds().stream().map(data -> data.timeUsed).collect(Collectors.toList());
    }

    private int totalCodeChange(BiFunction<SourceCode, SourceCode, List<Integer>> measure) {
        if (data.size() <= 1) return 0;
        return codeChangeBetween(data.get(0), data.get(1), measure);
    }

    private int codeChangeBetween(RawData raw1, RawData raw2
            , BiFunction<SourceCode, SourceCode, List<Integer>> measure) {
        return measure.apply(raw1.code, raw2.code)
                .stream().mapToInt(Integer::valueOf).sum();
    }

    public int totalNewCodeLines() {
        return totalCodeChange(SourceCodeComparator::newLinesCode);
    }

    public int totalNewTestLines() {
        return totalCodeChange(SourceCodeComparator::newLinesTest);
    }

    public int totalChangedCodeLines() {
        return totalCodeChange(SourceCodeComparator::changedLinesCode);
    }

    public int totalChangedTestLines() {
        return totalCodeChange(SourceCodeComparator::changedLinesTest);
    }

    /**
     * Gets all interesting data
     *
     * @return a List of DataPoint in appropriate order
     */
    public List<DataPoint> getRawData() {
        if (changed || computedData == null) {
            computedData = computeRawData();
            changed = false;
        }
        return computedData;

    }

    private List<DataPoint> computedData;
    private boolean changed = false;

    private List<DataPoint> computeRawData() {
        // I am sorry for this method
        List<DataPoint> result = new ArrayList<>();

        int checksWithFailedTests = 0;
        int checksWithCompilationError = 0;
        RawData previousRaw = null;
        DataPoint.Builder builder = new DataPoint.Builder();
        boolean builderEmpty = true;
        for (RawData raw : data) {
            builderEmpty = false;
            if (raw.phase == raw.newPhase) {
                // An intermediate point
                if (raw.code.hasCompileErrors()) checksWithCompilationError++;
                if (raw.code.numberOfFailedTests() > 0) checksWithFailedTests++;
            } else {
                // A new phase point
                fillBuilder(raw, checksWithFailedTests, checksWithCompilationError, previousRaw, builder);
                previousRaw = raw;
                checksWithCompilationError = 0;
                checksWithFailedTests = 0;

                result.add(builder.build());
                builderEmpty = true;
                builder = new DataPoint.Builder();
            }

        }
        // Don't forget the last DataPoint...
        if (!builderEmpty) {
            fillBuilder(data.get(data.size() - 1), checksWithFailedTests, checksWithCompilationError, previousRaw, builder);
            result.add(builder.build());
        }
        return result;
    }

    private void fillBuilder(RawData raw, int checksWithFailedTests, int checksWithCompilationError, RawData previousRaw, DataPoint.Builder builder) {
        builder.setChecksWithCompilationError(checksWithCompilationError)
                .setChecksWithFailedTests(checksWithFailedTests)
                .setTimeUsed(raw.timeUsed)
                .setPhase(raw.phase);
        if (previousRaw != null) {
            builder.setLinesCodeChanged(codeChangeBetween(previousRaw, raw
                    , SourceCodeComparator::changedLinesCode));
            builder.setLinesTestChanged(codeChangeBetween(previousRaw, raw
                    , SourceCodeComparator::changedLinesTest));
            builder.setNewLinesOfCode(codeChangeBetween(previousRaw, raw
                    , SourceCodeComparator::newLinesCode));
            builder.setNewLinesOfTest(codeChangeBetween(previousRaw, raw
                    , SourceCodeComparator::newLinesTest));
        }
    }

    public void saveTo(Path path) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(DataPoint.dataHeader()).append(System.lineSeparator());
        for (DataPoint point : getRawData()) {
            builder.append(point.toString()).append(System.lineSeparator());
        }
        byte[] bytes = builder.toString().getBytes(StandardCharsets.UTF_8);
        Files.write(path, bytes, StandardOpenOption.CREATE);
    }
}
