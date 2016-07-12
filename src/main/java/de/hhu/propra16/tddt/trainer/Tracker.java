package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Tracker {

    private static class TrackedObject {
        private final SourceCode code;
        private final Duration timeUsed;
        private final Phase oldPhase;
        private final Phase newPhase;

        private TrackedObject(SourceCode code, Duration timeUsed, Phase oldPhase, Phase newPhase) {
            this.code = code;
            this.timeUsed = timeUsed;
            this.oldPhase = oldPhase;
            this.newPhase = newPhase;
        }
    }

    private final List<TrackedObject> datapoints = new ArrayList<>();

    public void push(SourceCode code, Duration timeUsed, Phase oldPhase, Phase newPhase) {
        datapoints.add(new TrackedObject(code, timeUsed, oldPhase, newPhase));
    }

    public Duration getTotalTime(Phase phase) {
        Duration total = Duration.ZERO;
        for (TrackedObject tracked : datapoints) {
            if (tracked.oldPhase == phase && tracked.oldPhase != tracked.newPhase) {
                total = total.plus(tracked.timeUsed);
            }
        }
        return total;
    }
}
