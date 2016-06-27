package de.hhu.propra16.tddt.exercise;

import de.hhu.propra16.tddt.sourcecode.SourceCodeBuilder;
import vk.core.api.CompilationUnit;

public class ExerciseBuilder {

    private final String name;
    private String description = "No description available";
    private String className;
    private String testName;
    private boolean babySteps = false;
    private boolean tracking = false;
    private final SourceCodeBuilder sourceBuilder = new SourceCodeBuilder();

    public ExerciseBuilder setDescription(String description) {
        if (description == null) throw new NullPointerException("Desctipion must not be null.");
        this.description = description;
        return this;
    }

    public ExerciseBuilder setTestName(String testName) {
        if (testName == null) throw new NullPointerException("Test name must not be null.");
        this.testName = testName;
        return this;
    }

    public ExerciseBuilder setClassName(String className) {
        if (className == null) throw new NullPointerException("Class name must not be null.");
        this.className = className;
        return this;
    }

    public ExerciseBuilder setClassCode(String classCode) {
        if (className == null) throw new IllegalStateException("Class name is not set.");
        if (classCode == null) throw new NullPointerException("Class code must no be null.");
        CompilationUnit unit = new CompilationUnit(className, classCode, false);
        sourceBuilder.add(unit);
        className = null;
        return this;
    }

    public ExerciseBuilder setTestCode(String testCode) {
        if (testName == null) throw new IllegalStateException("Test name is not set.");
        if (testCode == null) throw new NullPointerException("Test code must no be null.");
        CompilationUnit unit = new CompilationUnit(testName, testCode, true);
        sourceBuilder.add(unit);
        testName = null;
        return this;
    }

    public ExerciseBuilder setBabySteps(boolean babySteps) {
        this.babySteps = babySteps;
        return this;
    }

    public ExerciseBuilder setTracking(boolean tracking) {
        this.tracking = tracking;
        return this;
    }

    public Exercise build() {
        return new Exercise(name, description, sourceBuilder.build(), new Options(tracking, babySteps));
    }


    public ExerciseBuilder(String name) {
        this.name = name;
    }

}
