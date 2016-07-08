package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.userinterface.*;
import javafx.beans.property.*;

import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Manages the different Phases
 */
public class Trainer{
    private static Exercise exercise;
    private CheckCompile checker;
    private Editor editor;
    private SourceCode current;
    private SourceCode previous;
    private MessageDisplay messageDisplay;

    /**
     * Builder Class for building a Trainer instance;
     */
    public static class Builder {
        private static Exercise exercise;
        private static Editor editor;
        private static MessageDisplay messageDisplay;

        public Builder(Exercise exercise) {
            if (exercise == null) throw new NullPointerException("There has to be an Exercise to work with!");
            Builder.exercise = exercise;
        }

        public Builder editor(Editor editor) {
            if (exercise == null) throw new NullPointerException("There has to be an Instance of Editor Class");
            Builder.editor = editor;
            return this;
        }

        public Builder messageDisplay(MessageDisplay message) {
            if (message == null) throw new NullPointerException("There has to be an Instance of MessageDisplay Class");
            messageDisplay = message;
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

        current = exercise.getSources();
        previous = current;
        setPhase(Phase.RED);

        checker = new ConditionChecker();
    }

    /**
     * Checks if the actual Code runs, if so it'll let the user go to the next Phase
     * It shows in both cases the compilation result.
     */
    public void checkPhaseStatus() {
        current = editor.get();
        // TODO Replace with actual SourceCode message, once the SourceCode branch is finished and merged
        String compilationMessage = "";
        boolean check = checker.check(current, getPhase());
        phaseOkay.setValue(check);
        errorField.setValue(compilationMessage);
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
                    getPhase() == Phase.GREEN || getPhase() == Phase.BLACK,
                    getPhase() == Phase.RED || getPhase() == Phase.BLACK);
        if (!(getPhase() == Phase.BLACK)) babyStepTimer();
    }

    /**
     * Manages Phase to go to the previous Phase, so phase goes a step back
     * and the previous Sourcecode becomes the actual one.
     */
    public void previousPhase() {
        cycle(false);
        current = previous;
        editor.show(current,
                getPhase() == Phase.GREEN || getPhase() == Phase.BLACK,
                getPhase() == Phase.RED || getPhase() == Phase.BLACK);
        if (!(getPhase() == Phase.BLACK)) babyStepTimer();
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
            if (getPhase() == Phase.RED) setPhase(Phase.GREEN);
            if (getPhase() == Phase.GREEN) setPhase(Phase.BLACK);
            if (getPhase() == Phase.BLACK) setPhase(Phase.RED);
        } else {
            if (getPhase() == Phase.GREEN) setPhase(Phase.RED);
            if (getPhase() == Phase.RED) setPhase(Phase.BLACK);
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

    private StringProperty errorField = new SimpleStringProperty(this, "Content of the error field", "");
    public StringProperty errorMessageProperty() {
        return errorField;
    }

    private ObjectProperty<Phase> phaseProperty = new SimpleObjectProperty<>(this, "Phase of the trainer");
    public ObjectProperty<Phase> phaseProperty() {
        return phaseProperty;
    }

    private void setPhase(Phase phase) {
        phaseProperty.setValue(phase);
    }

    private Phase getPhase() {
        return phaseProperty.getValue();
    }

    private void setTimeLeft(Duration duration) {
        time.setValue("Time left: " + duration.toMillis() / 1000);
    }

    private synchronized void reset() {
        previousPhase();
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
