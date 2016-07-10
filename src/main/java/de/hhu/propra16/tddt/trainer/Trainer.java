package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.sourcecode.SourceCodeBuilder;


/**
 * Created by tim on 30.06.2016.
 */
public class Trainer{
    private Exercise exercise;
    private Phase phase;
    private CheckCompile checker;
    SourceCode current;
    SourceCode previous;

    public Trainer(Exercise exercise) {
        this.exercise = exercise;
        phase = Phase.RED;
        checker = new ConditionChecker();
    }

    public void nextPhase() {
        if (checker.check(exercise.getSources(), phase)) {
            cycle(true);
            previous = current;
            SourceCodeBuilder scBuilder = new SourceCodeBuilder();
        }
    }

    public void previousPhase() {
        cycle(false);
        current = previous;
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
