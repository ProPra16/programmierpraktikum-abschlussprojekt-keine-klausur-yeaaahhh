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

    private static class DataPoint {
        private final SourceCode code;
        private final Duration timeUsed;
        private final Phase phase;
        private final Phase newPhase;

        private DataPoint(SourceCode code, Duration timeUsed, Phase phase, Phase newPhase) {
            this.code = code;
            this.timeUsed = timeUsed;
            this.phase = phase;
            this.newPhase = newPhase;
        }
    }

    private final List<DataPoint> datapoints = new ArrayList<>();

    private List<DataPoint> phaseEnds() {
        return datapoints.stream().filter(data -> data.phase != data.newPhase).collect(Collectors.toList());
    }

    public void push(SourceCode code, Duration timeUsed, Phase oldPhase, Phase newPhase) {
        datapoints.add(new DataPoint(code, timeUsed, oldPhase, newPhase));
    }

    public Duration getTotalTime(Phase phase) {
        return phaseEnds().stream().filter(data -> data.phase == phase)
                .map(data -> data.timeUsed).reduce(Duration.ZERO, Duration::plus);
    }

    public long numberOfFailedChecks(Phase phase) {
        return datapoints.stream().filter(tr -> tr.phase == phase && tr.newPhase == phase)
                .filter(data -> !checker.check(data.code, phase)).count();
    }

    public long numberOfCycles() {
        return datapoints.stream().filter(data -> data.phase == Phase.BLACK && data.newPhase == Phase.RED)
                .count();
    }
}
