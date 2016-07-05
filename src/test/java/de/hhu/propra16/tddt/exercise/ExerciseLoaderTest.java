package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class ExerciseLoaderTest {
    private String[] input;
    private int[] expected;

    @Before
    public void setUp() throws Exception {
        input = new String[]{
                "<exercises>" +
                        "<exercise name=\"Test1\"></exercise>" +
                        "<exercise name=\"Test2\"></exercise>" +
                        "</exercises>",

                "<exercise name=\"Test3\"></exercise>",

                "<exercises>" +
                        "<exercise name=\"Test4\">" +
                        "</exercises>"
        };
        expected = new int[]{2, 0, 0};
    }

    @Test
    public void loadFile() throws Exception {

        for (int i = 0; i < input.length; i++) {
            String string = input[i];
            Reader reader = new StringReader(string);
            InputSource in = new InputSource(reader);
            assertEquals(expected[i], ExerciseLoader.loadFile(in).size());
        }
    }
}