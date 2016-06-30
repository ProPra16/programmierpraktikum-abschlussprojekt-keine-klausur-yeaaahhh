package de.hhu.propra16.tddt.trainer;

import com.sun.jmx.snmp.tasks.Task;
import de.hhu.propra16.tddt.exercise.Exercise;
import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.userinterface.Editor;


import java.util.Timer;
import java.util.TimerTask;

import static de.hhu.propra16.tddt.userinterface.Editor.*;


/**
 * Created by tim on 30.06.2016.
 */
public class Trainer{
    // bekomme Sourcecode von Interface
    private Boolean nextPhaseaccepted;
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
    public Boolean checkPhaseRed(){
        nextPhaseaccepted = false;
        if (checkcompile.check(editor.get(), Phase.RED) == false){
            previousSorcecode = editor.get();
            nextPhaseaccepted = true;
        }
        return nextPhaseaccepted;
    }

    public Boolean checkPhaseGreen(){
        nextPhaseaccepted = false;
        if (checkcompile.check(editor.get(), Phase.GREEN) == true){
            previousSorcecode = editor.get();
            nextPhaseaccepted = true;
        }
        return nextPhaseaccepted;
    }

    public Boolean checkPhaseRefactor(){
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

}
