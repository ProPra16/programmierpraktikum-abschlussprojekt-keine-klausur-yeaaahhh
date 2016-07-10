package de.hhu.propra16.tddt.exerciseTest;

import de.hhu.propra16.tddt.exercise.ExerciseLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ExerciseLoaderTester {
    private int n;
    private InputSource in;

    public ExerciseLoaderTester(int n, String input){
        this.n = n;
        in = new InputSource(new StringReader(input));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        String test1 = "<exercises><exercise name=\"test1\"></exercise></exercises>";
        String test2 = "<exercise name=\"test2\"></exercise>";
        String test3 = "<exercises><exercise></exercises>";
        String test4 = "<EXERcises><EXERCISe></EXERCISe></EXERcises>";
        Object[][] data = new Object[][] {{1, test1}, {0, test2}, {0, test3}, {1, test4}};
        return Arrays.asList(data);
    }

    @Test
    public void test(){
        assertEquals(n , ExerciseLoader.loadFile(in).size());
    }
}
