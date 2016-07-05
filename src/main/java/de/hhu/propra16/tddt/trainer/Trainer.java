package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.userinterface.*;

public class Trainer{
    private Exercise exercise;
    private Phase phase;
    private CheckCompile checker;
    private Editor editor;
    private SourceCode current;
    private SourceCode previous;
    private ErrorDisplay errorDisplay;
    private MessageDisplay messageDisplay;
    private PhaseDisplay phaseDisplay;
    private TimeDisplay timeDisplay;

    public Trainer(Exercise exercise, Editor editor, DisplayerGroup display) {
        this.exercise = exercise;
        this.editor = editor;
        current = exercise.getSources();
        previous = current;
        phase = Phase.RED;
        checker = new ConditionChecker();

        errorDisplay = display.errorDisplay();
        messageDisplay = display.messageDisplay();
        phaseDisplay = display.phaseDisplay();
        timeDisplay = display.timeDisplay();

    }

    public void checkPhaseStatus() {
        current = editor.get();
        String compilationMessage = "";
        if (checker.check(current, phase)) {
            phaseDisplay.showNext(phase);
            errorDisplay.show(compilationMessage);
        } else errorDisplay.show(compilationMessage);
    }

    public void nextPhase() {
        cycle(true);
        previous = current;
        current = editor.get();
        editor.show(current,
                    phase == Phase.GREEN || phase == Phase.BLACK,
                    phase == Phase.RED || phase == Phase.BLACK);
    }

    public void previousPhase() {
        cycle(false);
        current = previous;
        editor.show(current,
                phase == Phase.GREEN || phase == Phase.BLACK,
                phase == Phase.RED || phase == Phase.BLACK);
    }


    private void cycle(boolean forward) {
        if (forward) {
            if (phase == Phase.RED) phase = Phase.GREEN;
            if (phase == Phase.GREEN) phase = Phase.BLACK;
            if (phase == Phase.BLACK) phase = Phase.RED;
        } else {
            if (phase == Phase.GREEN) phase = Phase.RED;
        }
    }
}
