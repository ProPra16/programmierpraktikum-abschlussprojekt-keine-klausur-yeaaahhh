package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.userinterface.Editor;
import de.hhu.propra16.tddt.userinterface.MessageDisplay;
import javafx.beans.property.*;

import java.time.Duration;
import java.time.Instant;

/**
 * Manages the different Phases
 */
public class Trainer{
    private Exercise exercise;
    private CheckCompile checker;
    private Editor editor;
    private SourceCode current;
    private SourceCode previous;
    private MessageDisplay messageDisplay;
    private Tracker tracker;
    private Instant start;
    private BabySteps babysteps;

    /**
     * Constructs a Trainer instance.
     *
     * @param exercise       the exercise to use
     * @param editor         the editor to display code to, and get modified code from
     * @param messageDisplay used to show certain warnings/messages
     * @param checker        determines, when a phase condition is satisfied
     */
    public Trainer(Exercise exercise, Editor editor, MessageDisplay messageDisplay, CheckCompile checker) {
        this.exercise = exercise;
        this.editor = editor;
        this.checker = checker;
        // If the editor code changes, mark phase as not accepted
        editor.changed().addListener((observable, oldV, newV) -> {
            if (newV) setPhaseAccepted(false);
        });
        this.messageDisplay = messageDisplay;

        if (exercise.getOptions().getTracking()) {
            tracker = new Tracker(checker);
            phaseTime();
        }
        if (exercise.getOptions().getBabySteps()) {
            babysteps = new BabySteps(exercise.getOptions().getTime());
            babysteps.timer(this);
        }
        current = exercise.getSources();
        previous = current;
        setPhase(Phase.RED);
        showCurrentCode();
    }

    /**
     * Checks if the actual Code runs, if so it'll let the user go to the next Phase
     * It shows in both cases the compilation result.
     */
    public void checkPhaseStatus() {
        current = editor.get();
        String compilationMessage = current.getResult();
        boolean check = checker.check(current, getPhase());
        if (!check && exercise.getOptions().getTracking()) {
            tracker.push(current, usedTime(), getPhase(), getPhase());
        }
        setPhaseAccepted(check);
        setErrorField(compilationMessage);
    }


    /**
     * Manages Phase to go to the Next Phase, so phase goes a step further
     * and the actual Sourcecode becomes the previous one.
     */
    public void nextPhase() {
        cycle(true);
        previous = current;
        current = editor.get();
        showCurrentCode();
    }

    /**
     * Manages Phase to go to the previous Phase, so phase goes a step back
     * and the previous Sourcecode becomes the actual one.
     */
    public void previousPhase() {
        cycle(false);
        current = previous;
        showCurrentCode();
    }

    // Shows the current code. Nothing more.
    private void showCurrentCode() {
        editor.show(current,
                getPhase() == Phase.GREEN || getPhase() == Phase.BLACK,
                getPhase() == Phase.RED || getPhase() == Phase.BLACK);
    }

    private void phaseTime() {
        start = Instant.now();
    }

    private Duration usedTime() {
        return Duration.between(start, Instant.now());
    }
    /**
     * Changes Phase (counter) clockwise
     *
     * @param forward determines the direction in which the Phase cycles
     * @value true to go clockwise
     * @value false to go counterclockwise
     */
    private void cycle(boolean forward) {
        Phase prevPhase = getPhase(); // Save for tracking

        if (forward) setPhase(getPhase().next());
        else if (getPhase() != Phase.BLACK) setPhase(getPhase().previous());
        if (babysteps != null) babysteps.cancel();

        if (exercise.getOptions().getTracking()) {
            tracker.push(current, usedTime(), prevPhase, getPhase());
            phaseTime();
        }

        setTimeLeft(null);
        if ((getPhase() != Phase.BLACK) && exercise.getOptions().getBabySteps()) babysteps.timer(this);
    }

    private final BooleanProperty phaseOkay = new SimpleBooleanProperty(this, "Ability to move to next phase", false);
    public BooleanProperty phaseAcceptedProperty() {
        return phaseOkay;
    }

    private void setPhaseAccepted(boolean value) {
        phaseOkay.setValue(value);
    }

    private final ObjectProperty<Duration> time = new SimpleObjectProperty<>(this, "Duration of remaining time");

    public ObjectProperty<Duration> timeProperty() {
        return time;
    }

    private final StringProperty errorField = new SimpleStringProperty(this, "Content of the error field", "");
    public StringProperty errorMessageProperty() {
        return errorField;
    }

    private void setErrorField(String value) {
        errorField.setValue(value);
    }

    private final ObjectProperty<Phase> phaseProperty = new SimpleObjectProperty<>(this, "Phase of the trainer");
    public ObjectProperty<Phase> phaseProperty() {
        return phaseProperty;
    }

    private void setPhase(Phase phase) {
        phaseProperty.setValue(phase);
    }

    private Phase getPhase() {
        return phaseProperty.getValue();
    }

    void setTimeLeft(Duration duration) {
        time.setValue(duration);
    }

    synchronized void reset() {
        messageDisplay.show("Zeit abgelaufen!", "Du hast zu lange gebraucht. Zurück in die letzte Phase mit dir!");
        previousPhase();
    }

    public Tracker getTracker() {
        return tracker;
    }
}
