package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.userinterface.DisplayerGroup;
import de.hhu.propra16.tddt.userinterface.Editor;
import de.hhu.propra16.tddt.userinterface.ErrorDisplay;

public class Trainer{
    private Exercise exercise;
    private Phase phase;
    private CheckCompile checker;
    private Editor editor;
    private SourceCode current;
    private SourceCode previous;
    private ErrorDisplay error;


    public Trainer(Exercise exercise, Editor editor, DisplayerGroup display) {
        this.exercise = exercise;
        this.editor = editor;
        error = display.getErrorDisplay();
        current = exercise.getSources();
        previous = current;
        phase = Phase.RED;
        checker = new ConditionChecker();
    }

    public void nextPhase() {
        current = editor.get();
        String compilationMessage = " ";
        if (checker.check(current, phase)) {
            previous = current;
            editor.show(current,
                    phase == Phase.GREEN || phase == Phase.BLACK,
                    phase == Phase.RED || phase == Phase.BLACK);
            error.show(compilationMessage);
        } else {
            error.show(compilationMessage);
        }
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
