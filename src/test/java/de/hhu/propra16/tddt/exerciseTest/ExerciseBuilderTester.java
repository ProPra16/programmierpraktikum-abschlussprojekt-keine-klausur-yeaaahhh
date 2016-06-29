package de.hhu.propra16.tddt.exerciseTest;

import de.hhu.propra16.tddt.exercise.ExerciseBuilder;
import org.junit.Before;
import org.junit.Test;

public class ExerciseBuilderTester {
    private ExerciseBuilder exb;

    @Before
    public void setup(){
        exb = new ExerciseBuilder("Test");
    }

    @Test(expected=NullPointerException.class)
    public void TestDescriptionNullInput(){
        exb.setDescription(null);
    }

    @Test(expected=NullPointerException.class)
    public void TestTestNameNullInput(){
        exb.setTestName(null);
    }

    @Test(expected=NullPointerException.class)
    public void TestClassNameNullInput(){
        exb.setClassName(null);
    }
    @Test(expected=IllegalStateException.class)
    public void TestClassCodeNameNotSet(){
        exb.setClassCode(null);
    }
    @Test(expected=NullPointerException.class)
    public void TestClassCodeNullInput(){
        exb.setClassName("class");
        exb.setClassCode(null);
    }
    @Test(expected=IllegalStateException.class)
    public void TestTestCodeNameNotSet(){
        exb.setTestCode(null);
    }
    @Test(expected=NullPointerException.class)
    public void TestTestCodeNullInput(){
        exb.setClassName("test");
        exb.setClassCode(null);
    }
    @Test(expected=NullPointerException.class)
    public void TestTimeNullInput(){
        exb.setTime(null);
    }
}
