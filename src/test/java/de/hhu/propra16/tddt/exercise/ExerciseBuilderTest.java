package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.time.Duration;
import java.util.function.BiConsumer;

@RunWith(Theories.class)
public class ExerciseBuilderTest {

    private ExerciseBuilder builder = new ExerciseBuilder("Name");

    @Before
    public void setUp() {
        builder = new ExerciseBuilder("Name");
    }

    @DataPoint
    public static BiConsumer<ExerciseBuilder, String> classNameSetter = ExerciseBuilder::setClassName;
    @DataPoint
    public static BiConsumer<ExerciseBuilder, String> testNameSetter = ExerciseBuilder::setTestName;
    @DataPoint
    public static BiConsumer<ExerciseBuilder, String> descriptionSetter = ExerciseBuilder::setDescription;
    @DataPoint
    public static BiConsumer<ExerciseBuilder, Duration> timeSetter = ExerciseBuilder::setTime;

    @Theory
    @Test(expected = NullPointerException.class)
    public void setterFailWithNull(BiConsumer setter) {
        setter.accept(builder, null);
    }

    @Test(expected = IllegalStateException.class)
    public void classCodeWihoutName() {
        builder.setClassCode("public class foo { }");
    }

    @Test(expected = IllegalStateException.class)
    public void testCodeWithoutName() {
        builder.setTestCode("public class fooTest { }");
    }

    @Test(expected = NullPointerException.class)
    public void setClassCodeNullFail() {
        builder.setClassName("Foo").setClassCode(null);
    }

    @Test(expected = NullPointerException.class)
    public void setTestCodeNullFail() {
        builder.setTestName("Foo").setTestCode(null);
    }


}