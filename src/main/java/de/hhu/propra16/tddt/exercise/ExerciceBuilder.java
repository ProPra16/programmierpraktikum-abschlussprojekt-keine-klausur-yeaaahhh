package de.hhu.propra16.tddt.exercise;

/**
 * Created by felix on 6/27/16.
 */
public class ExerciceBuilder {

    private final String name;
    private String description;
    private String className;
    private String testName;
    private String classCode;
    private String testCode;

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
        this.classCode = classCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }


    public ExerciceBuilder(String name) {
        this.name = name;
    }

}
