package de.hhu.propra16.tddt.exercise;

import java.time.Duration;

public class Options {

    private static final Duration DEFAULT_TIME = Duration.ZERO.plusMinutes(2);

    private final boolean babySteps;
    private final boolean tracking;
    private final Duration time;

    public Options(boolean tracking, boolean babySteps, Duration time) {
        this.babySteps = babySteps;
        this.tracking = tracking;
        this.time = time;
    }

    public Options(boolean tracking, boolean babySteps) {
        this(tracking, babySteps, DEFAULT_TIME);
    }
}
