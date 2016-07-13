package de.hhu.propra16.tddt.trainer;

import java.time.Duration;

public class DataPoint {

    private final Phase phase;
    private final Duration timeUsed;
    private final int checksWithFailedTests;
    private final int checksWithCompilationError;
    private final int newLinesOfCode;
    private final int newLinesOfTest;
    private final int linesCodeChanged;
    private final int linesTestChanged;

    public static class Builder {
        public Builder setPhase(Phase phase) {
            this.phase = phase;
            return this;
        }

        public Builder setTimeUsed(Duration timeUsed) {
            this.timeUsed = timeUsed;
            return this;
        }

        public Builder setChecksWithFailedTests(int checksWithFailedTests) {
            this.checksWithFailedTests = checksWithFailedTests;
            return this;
        }

        public Builder setChecksWithCompilationError(int checksWithCompilationError) {
            this.checksWithCompilationError = checksWithCompilationError;
            return this;
        }

        public Builder setNewLinesOfCode(int newLinesOfCode) {
            this.newLinesOfCode = newLinesOfCode;
            return this;
        }

        public Builder setNewLinesOfTest(int newLinesOfTest) {
            this.newLinesOfTest = newLinesOfTest;
            return this;
        }

        public Builder setLinesCodeChanged(int linesCodeChanged) {
            this.linesCodeChanged = linesCodeChanged;
            return this;
        }

        public Builder setLinesTestChanged(int linesTestChanged) {
            this.linesTestChanged = linesTestChanged;
            return this;
        }

        private Phase phase = Phase.RED;
        private Duration timeUsed = Duration.ZERO;
        private int checksWithFailedTests = 0;
        private int checksWithCompilationError = 0;
        private int newLinesOfCode = 0;
        private int newLinesOfTest = 0;
        private int linesCodeChanged = 0;
        private int linesTestChanged = 0;

        public DataPoint build() {
            return new DataPoint(this);
        }
    }

    private DataPoint(Builder builder) {
        this.phase = builder.phase;
        this.timeUsed = builder.timeUsed;
        this.checksWithFailedTests = builder.checksWithFailedTests;
        this.checksWithCompilationError = builder.checksWithCompilationError;
        this.newLinesOfCode = builder.newLinesOfCode;
        this.newLinesOfTest = builder.newLinesOfTest;
        this.linesCodeChanged = builder.linesCodeChanged;
        this.linesTestChanged = builder.linesTestChanged;
    }

    public Phase getPhase() {
        return phase;
    }

    public Duration getTimeUsed() {
        return timeUsed;
    }

    public int getChecksWithFailedTests() {
        return checksWithFailedTests;
    }

    public int getChecksWithCompilationError() {
        return checksWithCompilationError;
    }

    public int getNewLinesOfCode() {
        return newLinesOfCode;
    }

    public int getNewLinesOfTest() {
        return newLinesOfTest;
    }

    public int getLinesCodeChanged() {
        return linesCodeChanged;
    }

    public int getLinesTestChanged() {
        return linesTestChanged;
    }

    /**
     * String representation of this datapoint
     *
     * @return String containing information of this datapoint
     * if tabular form
     */
    @Override
    public String toString() {
        return new StringBuilder()
                .append(getPhase().name()).append('\t')
                .append(getTimeUsed().getSeconds()).append('\t')
                .append(getChecksWithFailedTests()).append('\t')
                .append(getChecksWithCompilationError()).append('\t')
                .append(getNewLinesOfCode()).append('\t')
                .append(getNewLinesOfTest()).append('\t')
                .append(getLinesCodeChanged()).append('\t')
                .append(getLinesTestChanged()).append('\t')
                .toString();
    }
}
