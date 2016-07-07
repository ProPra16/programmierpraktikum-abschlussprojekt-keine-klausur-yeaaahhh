package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
                        "<exercise name=\"Test4\"></exercise>" +
                        "</exercises>"
        };
        expected = new int[]{2, 1, 1};
    }

    @Test
    public void loadFile() {

        for (int i = 2; i < 3; i++) {
            String string = input[i];
            ExerciseLoader loader = new ExerciseLoader(null);
            InputStream in = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
            assertEquals(expected[i], loader.load(in).size());
        }
    }
}