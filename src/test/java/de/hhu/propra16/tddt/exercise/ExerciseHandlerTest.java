package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ExerciseHandlerTest {
    private ExerciseBuilder builder;
    private ExerciseHandler handler;
    private Attributes exerciseAtts;
    private char[][] input;
    private String[] expected;

    @Before
    public void setup() {
        builder = mock(ExerciseBuilder.class);
        handler = new ExerciseHandler(() -> builder);

        exerciseAtts = mock(Attributes.class);
        when(exerciseAtts.getLocalName(0)).thenReturn("name");
        when(exerciseAtts.getValue(0)).thenReturn("Test");
        when(exerciseAtts.getLength()).thenReturn(1);

        handler.startElement("", "", "exercises", exerciseAtts);

        input = new char[][]{{'H', 'a', 'l', 'l', 'o'},
                {' ', '\n', ' ', '\n', '\n'},
                {'\n', ' ', ' ', '\n', 'o'},
                {' ', ' ', 'l', ' ', 'o'}};
        expected = new String[]{"Hallo", "", "o", "l o"};
    }

    @Test
    public void start_withExercises() {
        verify(builder, times(0)).setName("Test");
    }

    @Test
    public void start_withExercise_withAttsEquals0() {
        exerciseAtts = mock(Attributes.class);
        handler.startElement("", "", "exercise", exerciseAtts);
        verify(builder, times(1)).setName(null);
    }

    @Test
    public void start_withExercise_withCode() {
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.startElement("", "", "class", exerciseAtts);
        handler.startElement("", "", "test", exerciseAtts);

        verify(builder, times(1)).setName("Test");
        verify(builder, times(1)).setClassName("Test");
        verify(builder, times(1)).setTestName("Test");
    }

    @Test
    public void start_withExercise_withBabysteps() {
        exerciseAtts = mock(Attributes.class);
        when(exerciseAtts.getLength()).thenReturn(2);
        when(exerciseAtts.getLocalName(0)).thenReturn("value");
        when(exerciseAtts.getLocalName(1)).thenReturn("time");
        when(exerciseAtts.getValue(0)).thenReturn("true");
        when(exerciseAtts.getValue(1)).thenReturn("5:00");

        handler.startElement("", "", "exercise", exerciseAtts);
        handler.startElement("", "", "babysteps", exerciseAtts);
        verify(builder, times(1)).setBabySteps(true);
        verify(builder, times(1)).setTime(Duration.parse("PT5M0S"));
    }


    @Test
    public void end_Code() {
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.endElement("", "", "class");
        handler.endElement("", "", "test");

        verify(builder, times(1)).setClassCode("");
        verify(builder, times(1)).setTestCode("");
    }
    @Test
    public void end_Exercise() {
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.endElement("", "", "exercise");
        assertEquals(1, handler.getExercises().size());
    }
    @Test
    public void end_Exercises() {
        handler.endElement("", "", "exercises");
        handler.startElement("", "", "exercise", exerciseAtts);
        verify(builder, times(0)).setName("Test");
    }

    @Test
    public void whitespaceHandling() {
        handler.startElement("", "", "exercise", exerciseAtts);

        for (int i = 0; i < input.length; i++) {
            handler.characters(input[i], 0, 5);
            handler.endElement("", "", "description");
            verify(builder, times(1)).setDescription(expected[i]);
        }
    }
}
