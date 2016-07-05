package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;


/**
 * Created by tim on 30.06.2016.
 */
public class Trainer{
    /*// bekomme Sourcecode von Interface
    private boolean nextPhaseaccepted;
    Exercise excersise;
    CheckCompile checkcompile;
    Editor editor;
    Timer timer = new Timer();
    SourceCode previousSorcecode = excersise.getSources();

    public Trainer(Exercise excersise, CheckCompile checkcompile, Editor editor){
        this.excersise = excersise;
        this.checkcompile = checkcompile;
        this.editor = editor;
    }
    public boolean checkPhaseRed(){
        nextPhaseaccepted = false;
        if (checkcompile.check(editor.get(), Phase.RED) == false){
            previousSorcecode = editor.get();
            nextPhaseaccepted = true;
        }
        return nextPhaseaccepted;
    }

    public boolean checkPhaseGreen(){
        nextPhaseaccepted = false;
        if (checkcompile.check(editor.get(), Phase.GREEN) == true){
            previousSorcecode = editor.get();
            nextPhaseaccepted = true;
        }
        return nextPhaseaccepted;
    }

    public boolean checkPhaseRefactor(){
        nextPhaseaccepted = false;
        if (checkcompile.check(editor.get(), Phase.BLACK) == true){
            previousSorcecode = editor.get();
            nextPhaseaccepted = true;
        }
        return nextPhaseaccepted;
    }
    public void returntoPhaseRED(){
        editor.show(previousSorcecode);
    }
    */
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
        }
    }

    public void previousPhase() {
        cycle(false);
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
