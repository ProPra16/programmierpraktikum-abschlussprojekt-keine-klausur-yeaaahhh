package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class SplitEditorTest {

    private CodeField sourceField;
    private CodeField testField;
    private SourceCode code;
    private Editor editor;

    @Before
    public void setUp() {
        sourceField = mock(CodeField.class);
        testField = mock(CodeField.class);
        code = mock(SourceCode.class);
        editor = new SplitEditor(sourceField, testField);
    }

    @Test(expected = NullPointerException.class)
    public void showDoesntTakeNull() {
        editor.show(null, true, true);
    }

    @Test
    public void sourceEditableTestNot() {
        editor.show(code, true, false);
        verify(sourceField).setEditable(true);
        verify(sourceField, never()).setEditable(false);
        verify(testField).setEditable(false);
        verify(testField, never()).setEditable(true);
    }

    @Test
    public void showTest() {
        String fooClass = "public class Foo { }";
        String barClass = "public class Bar { }";
        when(code.getNameTest()).thenReturn(Arrays.asList("Foo", "Bar"));
        when(code.getStringCode("Foo")).thenReturn(fooClass);
        when(code.getStringCode("Bar")).thenReturn(barClass);
        editor.show(code, true, true);
        verify(testField).showText("Foo", fooClass);
        verify(testField).showText("Bar", barClass);
    }

    @Test
    public void showSource() {
        String fooClass = "public class Foo { }";
        String barClass = "public class Bar { }";
        when(code.getNameCode()).thenReturn(Arrays.asList("Foo", "Bar"));
        when(code.getStringCode("Foo")).thenReturn(fooClass);
        when(code.getStringCode("Bar")).thenReturn(barClass);
        editor.show(code, false, false);
        verify(sourceField).showText("Foo", fooClass);
        verify(sourceField).showText("Bar", barClass);
    }
}