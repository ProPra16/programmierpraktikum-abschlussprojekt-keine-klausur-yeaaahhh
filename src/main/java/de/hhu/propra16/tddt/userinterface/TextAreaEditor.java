package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import javafx.scene.control.TextArea;

public class TextAreaEditor implements Editor {

    private final TextArea sourceField;
    private final TextArea testField;


    public TextAreaEditor(TextArea sourceField, TextArea testField) {
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
