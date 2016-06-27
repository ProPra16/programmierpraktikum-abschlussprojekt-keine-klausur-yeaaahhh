package de.hhu.propra16.tddt.exercise;

import de.hhu.propra16.tddt.sourcecode.SourceCodeBuilder;
import vk.core.api.CompilationUnit;

/**
 * Created by felix on 6/27/16.
 */
public class ExerciceBuilder {

    private final String name;
    private String description = "No description available";
    private String className;
    private String testName;
    private final SourceCodeBuilder sourceBuilder = new SourceCodeBuilder();

    public void setDescription(String description) {
        if (description == null) throw new NullPointerException("Desctipion must not be null.");
        this.description = description;
    }

    public void setTestName(String testName) {
        if (testName == null) throw new NullPointerException("Test name must not be null.");
        this.testName = testName;
    }

    public void setClassName(String className) {
        if (className == null) throw new NullPointerException("Class name must not be null.");
        this.className = className;
    }

    public void setClassCode(String classCode) {
        if (className == null) throw new IllegalStateException("Class name is not set.");
        if (classCode == null) throw new NullPointerException("Class code must no be null.");
        CompilationUnit unit = new CompilationUnit(className, classCode, false);
        sourceBuilder.add(unit);
        className = null;
    }

    public void setTestCode(String testCode) {
        if (testName == null) throw new IllegalStateException("Test name is not set.");
        if (testCode == null) throw new NullPointerException("Test code must no be null.");
        CompilationUnit unit = new CompilationUnit(testName, testCode, true);
        sourceBuilder.add(unit);
        testName = null;
    }

    public Exercise build() {
        return new Exercise(sourceBuilder.build(), name, description);
    }


    public ExerciceBuilder(String name) {
        this.name = name;
    }

}
