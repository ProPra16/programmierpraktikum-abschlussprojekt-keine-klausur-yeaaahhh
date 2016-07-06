package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import javafx.scene.control.TextArea;

public class SplitEditor implements Editor {

    private final CodeField sourceField;
    private final CodeField testField;


    public SplitEditor(CodeField sourceField, CodeField testField) {
        this.sourceField = sourceField;
        this.testField = testField;
    }

    @Override
    public void show(SourceCode code, boolean sourceEditable, boolean testEditable) {
    }

    @Override
    public SourceCode get() {
        return null;
    }
}
