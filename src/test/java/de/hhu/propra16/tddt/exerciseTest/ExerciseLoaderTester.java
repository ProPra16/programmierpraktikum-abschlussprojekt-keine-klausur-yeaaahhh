package de.hhu.propra16.tddt.exerciseTest;

import de.hhu.propra16.tddt.exercise.ExerciseLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.xml.sax.InputSource;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ExerciseLoaderTester {
    private int n;
    private String input;

    public ExerciseLoaderTester(int n, String input){
        this.n = n;
        this.input = input;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data(){
        String test1 = "<exercises><exercise name=\"test1\"></exercise></exercises>";
        String test2 = "<exercise name=\"test2\"></exercise>";
        String test3 = "<exercises></exercise></exercises>\";";
        Object[][] data = new Object[][] {{1, test1}, {0, test2}, {0, test3}};
        return Arrays.asList(data);
    }

    @Test
    public void test(){
        Reader r = new StringReader(input);
        InputSource in = new InputSource(r);
        assertEquals(n , ExerciseLoader.loadFile(in).size());
    }
}
