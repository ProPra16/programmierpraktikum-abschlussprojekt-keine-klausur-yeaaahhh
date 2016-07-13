package de.hhu.propra16.tddt.trainer;

import javax.xml.crypto.Data;
import java.time.Duration;

public class DataPoint {

    private final Phase phase;
    private final Duration timeUsed;
    private final int checksWithFailedTests;
    private final int checksWithCompilationError;
    private final int newLinesOfCode;
    private final int newLinesOfTest;
    private final int linesChanged;

    public static class Builder {
        public void setPhase(Phase phase) {
            this.phase = phase;
        }

        public void setTimeUsed(Duration timeUsed) {
            this.timeUsed = timeUsed;
        }

        public void setChecksWithFailedTests(int checksWithFailedTests) {
            this.checksWithFailedTests = checksWithFailedTests;
        }

        public void setChecksWithCompilationError(int checksWithCompilationError) {
            this.checksWithCompilationError = checksWithCompilationError;
        }

        public void setNewLinesOfCode(int newLinesOfCode) {
            this.newLinesOfCode = newLinesOfCode;
        }

        public void setNewLinesOfTest(int newLinesOfTest) {
            this.newLinesOfTest = newLinesOfTest;
        }

        public void setLinesChanged(int linesChanged) {
            this.linesChanged = linesChanged;
        }

        private Phase phase = Phase.RED;
        private Duration timeUsed = Duration.ZERO;
        private int checksWithFailedTests = 0;
        private int checksWithCompilationError = 0;
        private int newLinesOfCode = 0;
        private int newLinesOfTest = 0;
        private int linesChanged = 0;

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
        this.linesChanged = builder.linesChanged;
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

    public int getLinesChanged() {
        return linesChanged;
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
                .append(getLinesChanged()).append('\t')
                .toString();
    }
}
