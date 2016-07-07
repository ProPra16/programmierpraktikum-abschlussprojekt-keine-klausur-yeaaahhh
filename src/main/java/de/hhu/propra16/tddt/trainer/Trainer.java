package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.userinterface.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages the different Phases
 */
public class Trainer{
    private static Exercise exercise;
    private Phase phase;
    private CheckCompile checker;
    private Editor editor;
    private SourceCode current;
    private SourceCode previous;
    private ErrorDisplay errorDisplay;
    private MessageDisplay messageDisplay;
    private PhaseDisplay phaseDisplay;

    /**
     * Builder Class for building a Trainer instance;
     */
    public static class Builder {
        private static Exercise exercise;
        private static Editor editor;
        private static ErrorDisplay errorDisplay;
        private static MessageDisplay messageDisplay;
        private static PhaseDisplay phaseDisplay;

        public Builder(Exercise exercise) {
            if (exercise == null) throw new NullPointerException("There has to be an Exercise to work with!");
            Builder.exercise = exercise;
        }

        public Builder editor(Editor editor) {
            if (exercise == null) throw new NullPointerException("There has to be an Instance of Editor Class");
            Builder.editor = editor;
            return this;
        }

        public Builder displayerGroup(DisplayerGroup group) {
            if (group == null) throw new NullPointerException("There has to be an Instance of DisplayerGroup Class");
            errorDisplay = group.errorDisplay();
            messageDisplay = group.messageDisplay();
            phaseDisplay = group.phaseDisplay();
            return this;
        }

        public Trainer build() {
            return new Trainer(this);
        }
    }

    /**
     * Constructor for Trainer instance. Access is private.
     * Trainer instance has to be made by using the Builder
     *
     * @param builder contains the necessary Informations.
     */
    private Trainer(Builder builder) {
        exercise = Builder.exercise;
        editor = Builder.editor;

        messageDisplay = Builder.messageDisplay;
        errorDisplay = Builder.errorDisplay;
        phaseDisplay = Builder.phaseDisplay;

        current = exercise.getSources();
        previous = current;
        phase = Phase.RED;

        checker = new ConditionChecker();
    }

    /**
     * Checks if the actual Code runs, if so it'll let the user go to the next Phase
     * It shows in both cases the compilation result.
     */
    public void checkPhaseStatus() {
        current = editor.get();
        String compilationMessage = "";
        boolean check = checker.check(current, phase);
        phaseOkay.setValue(check);
        if (check) {
            phaseDisplay.showNext(phase);
            errorDisplay.show(compilationMessage);
        } else errorDisplay.show(compilationMessage);
    }


    /**
     * Manages Phase to go to the Next Phase, so phase goes a step further
     * and the actual Sourcecode becomes the previous one.
     */
    public void nextPhase() {
        cycle(true);
        previous = current;
        current = editor.get();
        editor.show(current,
                    phase == Phase.GREEN || phase == Phase.BLACK,
                    phase == Phase.RED || phase == Phase.BLACK);
    }

    /**
     * Manages Phase to go to the previous Phase, so phase goes a step back
     * and the previous Sourcecode becomes the actual one.
     */
    public void previousPhase() {
        cycle(false);
        current = previous;
        editor.show(current,
                phase == Phase.GREEN || phase == Phase.BLACK,
                phase == Phase.RED || phase == Phase.BLACK);
    }

    /**
     * Changes Phase (counter) clockwise
     *
     * @param forward determines the direction in which the Phase cycles
     * @value true to go clockwise
     * @value false to go counterclockwise
     */

    private void cycle(boolean forward) {
        if (forward) {
            if (phase == Phase.RED) phase = Phase.GREEN;
            if (phase == Phase.GREEN) phase = Phase.BLACK;
            if (phase == Phase.BLACK) phase = Phase.RED;
        } else {
            if (phase == Phase.GREEN) phase = Phase.RED;
        }
    }

    private BooleanProperty phaseOkay = new SimpleBooleanProperty(this, "Ability to move to next phase", false);
    public BooleanProperty phaseAcceptedProperty() {
        return phaseOkay;
    }

    private StringProperty time = new SimpleStringProperty(this, "");
    public StringProperty timeProperty() {
        return time;
    }

    private void setTimeLeft(Duration duration) {
        time.setValue("Time left: " + duration.toMillis()/1000);
    }

    // TODO Resets phase if time is up
    private synchronized void reset() {

    }

    /**
     * Set up a Timer with the Time corresponding to the Exercise and do some work.
     */
    public void babyStepTimer() {
        if (!exercise.getOptions().getBabySteps()) return;
        Duration duration = exercise.getOptions().getTime();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                reset();
            }
        }, duration.toMillis());

        Instant finishTime = Instant.now().plus(duration);
        Timer timerDisplay = new Timer();
        timerDisplay.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                setTimeLeft(Duration.between(Instant.now(), finishTime));
            }
        }, 0, 1000);
    }
}
