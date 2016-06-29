package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.function.BiConsumer;

@RunWith(Theories.class)
public class ExerciseBuilderTest {

    private ExerciseBuilder builder = new ExerciseBuilder("Name");

    @Before
    public void setUp() {
        builder = new ExerciseBuilder("Name");
    }

    @Theory
    @Test(expected = NullPointerException.class)
    public void setClassNameNullFail(BiConsumer setter) {
        setter.accept(builder, null);
    }

}