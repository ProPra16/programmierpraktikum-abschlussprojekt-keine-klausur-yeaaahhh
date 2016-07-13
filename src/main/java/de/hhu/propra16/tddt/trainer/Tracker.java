package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tracker {

    private final CheckCompile checker;

    public Tracker(CheckCompile checker) {
        this.checker = checker;
    }

    private static class rawData {
        private final SourceCode code;
        private final Duration timeUsed;
        private final Phase phase;
        private final Phase newPhase;

        private rawData(SourceCode code, Duration timeUsed, Phase phase, Phase newPhase) {
            this.code = code;
            this.timeUsed = timeUsed;
            this.phase = phase;
            this.newPhase = newPhase;
        }
    }

    private final List<rawData> data = new ArrayList<>();

    private List<rawData> phaseEnds() {
        return data.stream().filter(data -> data.phase != data.newPhase).collect(Collectors.toList());
    }

    public void push(SourceCode code, Duration timeUsed, Phase oldPhase, Phase newPhase) {
        data.add(new rawData(code, timeUsed, oldPhase, newPhase));
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
}
