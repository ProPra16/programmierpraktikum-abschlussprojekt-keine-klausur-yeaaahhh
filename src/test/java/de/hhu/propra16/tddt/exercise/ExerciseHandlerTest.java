package de.hhu.propra16.tddt.exercise;

import org.junit.Before;
import org.junit.Test;
import org.xml.sax.Attributes;

import static org.mockito.Mockito.*;

public class ExerciseHandlerTest {
    private ExerciseBuilder builder;
    private ExerciseHandler handler;

    @Before
    public void setupTest1() {
        builder = mock(ExerciseBuilder.class);
        handler = new ExerciseHandler(() -> builder);
    }

    @Test
    public void test_startElement_withExercises() {
        handler.startElement("", "", "exercises", null);
        verify(builder, times(0)).setName("Name");
    }

    @Before
    public void setupTest2() {
        builder = mock(ExerciseBuilder.class);
        handler = new ExerciseHandler(() -> builder);
    }

    @Test
    public void test_startElement_withExercise() {
        Attributes atts = mock(Attributes.class);
        when(atts.getLocalName(0)).thenReturn("name");
        when(atts.getValue(0)).thenReturn("Test");
        when(atts.getLength()).thenReturn(1);

        handler.startElement("", "", "exercises", null);
        handler.startElement("", "", "exercise", atts);
        verify(builder, times(1)).setName("Test");
    }
}