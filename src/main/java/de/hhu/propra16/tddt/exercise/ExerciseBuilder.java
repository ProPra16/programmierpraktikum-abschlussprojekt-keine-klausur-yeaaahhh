package de.hhu.propra16.tddt.exercise;

import de.hhu.propra16.tddt.sourcecode.SourceCodeBuilder;
import vk.core.api.CompilationUnit;

import java.time.Duration;

public class ExerciseBuilder {

    private static final Duration DEFAULT_TIME = Duration.ZERO.plusMinutes(2);
    private static final String DEFAULT_DESCRIPTION = "No description available";
    private static final String DEFAULT_NAME = "Exercise";

    private String name = DEFAULT_NAME;
    private String description = DEFAULT_DESCRIPTION;
    private String className;
    private String testName;
    private boolean babySteps = false;
    private boolean tracking = false;
    private final SourceCodeBuilder sourceBuilder = new SourceCodeBuilder();
    private Duration time = DEFAULT_TIME;

    public ExerciseBuilder setDescription(String description) {
        if (description == null) throw new NullPointerException("Description must not be null.");
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
        return new Exercise(name, description, sourceBuilder.build(), new Options(tracking, babySteps, time));
    }

    public void setName(String name) {
        if (name == null) throw new NullPointerException("Name must not be null.");
        this.name = name;
    }

    public void setTime(Duration time) {
        if(time == null) throw new NullPointerException("Time must not be null.");
        this.time = time;
    }
}
