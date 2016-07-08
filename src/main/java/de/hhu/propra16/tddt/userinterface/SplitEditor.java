package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import de.hhu.propra16.tddt.sourcecode.SourceCodeBuilder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import vk.core.api.CompilationUnit;

import java.util.ArrayList;
import java.util.List;

public class SplitEditor implements Editor {

    private final CodeField sourceField;
    private final CodeField testField;

    private List<String> sourceNames = new ArrayList<>();
    private List<String> testNames = new ArrayList<>();

    public SplitEditor(CodeField sourceField, CodeField testField) {
        this.sourceField = sourceField;
        this.testField = testField;

        sourceField.editNumber().addListener(e -> changedProperty.setValue(true));
        testField.editNumber().addListener(e -> changedProperty.setValue(true));
    }

    @Override
    public void show(SourceCode code, boolean sourceEditable, boolean testEditable) {
        if (code == null) throw new NullPointerException("code must not be null.");
        sourceField.setEditable(sourceEditable);
        testField.setEditable(testEditable);

        sourceNames = code.getNameCode();
        for (String sourceName : sourceNames) {
            sourceField.showText(sourceName, code.getStringCode(sourceName));
        }
        testNames = code.getNameTest();
        for (String testName : testNames) {
            testField.showText(testName, code.getStringCode(testName));
        }
    }

    @Override
    public SourceCode get() {
        changedProperty.setValue(false);
        SourceCodeBuilder builder = new SourceCodeBuilder();
        for (String sourceName : sourceNames) {
            String sourceCode = sourceField.getTextOf(sourceName);
            builder.add(new CompilationUnit(sourceName, sourceCode, false));
        }
        for (String testName : testNames) {
            String testCode = testField.getTextOf(testName);
            builder.add(new CompilationUnit(testName, testCode, true));
        }
        return builder.build();
    }

    private final BooleanProperty changedProperty = new SimpleBooleanProperty(this, "Change of code", false);
    @Override
    public BooleanProperty changed() {
        return changedProperty;
    }
}
