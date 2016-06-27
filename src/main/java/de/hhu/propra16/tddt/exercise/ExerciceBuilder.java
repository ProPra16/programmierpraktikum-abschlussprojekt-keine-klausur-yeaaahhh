package de.hhu.propra16.tddt.exercise;

import de.hhu.propra16.tddt.sourcecode.SourceCodeBuilder;
import vk.core.api.CompilationUnit;

/**
 * Created by felix on 6/27/16.
 */
public class ExerciceBuilder {

    private final String name;
    private String description;
    private String className;
    private String testName;
    private final SourceCodeBuilder sourceBuilder = new SourceCodeBuilder();

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setClassCode(String classCode) {
        if (className == null) throw new IllegalArgumentException("Class name is not set.");
        CompilationUnit unit = new CompilationUnit(className, classCode, false);
        sourceBuilder.add(unit);
        className = null;
    }

    public void setTestCode(String testCode) {
        if (testName == null) throw new IllegalArgumentException("Test name is not set.");
        CompilationUnit unit = new CompilationUnit(className, testCode, true);
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
