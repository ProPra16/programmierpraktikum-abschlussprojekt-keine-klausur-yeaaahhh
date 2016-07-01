package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;

import static org.mockito.Mockito.*;

public class ExerciseHandlerTest {
    private ExerciseBuilder builder;
    private ExerciseHandler handler;
    private Attributes exerciseAtts;

    @Before
    public void setup() {
        builder = mock(ExerciseBuilder.class);
        handler = new ExerciseHandler(() -> builder);

        exerciseAtts = mock(Attributes.class);
        when(exerciseAtts.getLocalName(0)).thenReturn("name");
        when(exerciseAtts.getValue(0)).thenReturn("Test");
        when(exerciseAtts.getLength()).thenReturn(1);

        handler.startElement("", "", "exercises", null);
    }

    @Test
    public void test_startElement_withExercises() {
        verify(builder, times(0)).setName("Name");
    }

    @Test
    public void test_startElement_withExercise_withAttsGreater0() {
        handler.startElement("", "", "exercise", exerciseAtts);
        verify(builder, times(1)).setName("Test");
    }

    @Test
    public void test_startElement_withExercise_withAttsEquals0() {
        exerciseAtts = mock(Attributes.class);
        handler.startElement("", "", "exercise", exerciseAtts);
        verify(builder, times(1)).setName(null);
    }

    @Test
    public void test_characters() {
        char[] sequence = {'H', 'a', 'l', 'l', 'o'};
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.characters(sequence, 0, 5);
        handler.endElement("", "", "description");
        verify(builder, times(1)).setDescription("Hallo");
    }
}