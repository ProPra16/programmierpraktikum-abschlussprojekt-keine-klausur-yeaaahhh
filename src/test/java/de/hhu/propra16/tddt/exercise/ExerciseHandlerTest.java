package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;

import java.time.Duration;

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
    public void start_withExercises() {
        verify(builder, times(0)).setName("Test");
    }

    @Test
    public void start_withExercise_withAttsGreater0() {
        handler.startElement("", "", "exercise", exerciseAtts);
        verify(builder, times(1)).setName("Test");
    }

    @Test
    public void start_withExercise_withAttsEquals0() {
        exerciseAtts = mock(Attributes.class);
        handler.startElement("", "", "exercise", exerciseAtts);
        verify(builder, times(1)).setName(null);
    }

    @Test
    public void start_withExercise_withClassName() {
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.startElement("", "", "class", exerciseAtts);
        verify(builder, times(1)).setClassName("Test");
    }

    @Test
    public void start_withExercise_withTestName() {
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.startElement("", "", "test", exerciseAtts);
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
    public void characters_regular() {
        char[] sequence = {'H', 'a', 'l', 'l', 'o'};
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.characters(sequence, 0, 5);
        handler.endElement("", "", "description");
        verify(builder, times(1)).setDescription("Hallo");
    }

    @Test
    public void characters_withSpaces() {
        char[] sequence = {' ', ' ', 'l', ' ', 'o'};
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.characters(sequence, 0, 5);
        handler.endElement("", "", "description");
        verify(builder, times(1)).setDescription("l o");
    }

    @Test
    public void characters_onlyWhitespace() {
        char[] sequence = {' ', '\n', ' ', '\n', '\n'};
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.characters(sequence, 0, 5);
        handler.endElement("", "", "description");
        verify(builder, times(1)).setDescription("");
    }

    @Test
    public void characters_withSpacesAndNewLines() {
        char[] sequence = {'\n', ' ', ' ', '\n', 'o'};
        handler.startElement("", "", "exercise", exerciseAtts);
        handler.characters(sequence, 0, 5);
        handler.endElement("", "", "description");
        verify(builder, times(1)).setDescription("o");
    }
}