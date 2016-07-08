package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

    // I guess the following tests are more like integration tests

    @Test
    public void noShow() {
        code = editor.get();
        assertTrue(code.getNameCode().isEmpty() && code.getNameTest().isEmpty());
    }

    @Test
    public void getNoChange() {
        String fooClass = "public class Foo { }";
        String barClass = "public class Bar { }";
        String bazClass = "public class Baz { @Test dosomehting() {} }";
        String quxClass = "public class qux { @Test another() {} }";

        when(code.getNameCode()).thenReturn(Arrays.asList("Foo", "Bar"));
        when(code.getNameTest()).thenReturn(Arrays.asList("Qux", "Baz"));

        // What the hell is this mess, I'm sorry
        when(sourceField.getTextOf("Foo")).thenReturn(fooClass);
        when(sourceField.getTextOf("Bar")).thenReturn(barClass);
        when(testField.getTextOf("Baz")).thenReturn(bazClass);
        when(testField.getTextOf("Qux")).thenReturn(quxClass);


        editor.show(code, false, false);
        code = editor.get();

        assertEquals(2, code.getNameCode().size());
        assertTrue(code.getNameCode().contains("Foo"));
        assertTrue(code.getNameCode().contains("Bar"));
        assertEquals(fooClass, code.getStringCode("Foo"));
        assertEquals(barClass, code.getStringCode("Bar"));

        assertEquals(2, code.getNameTest().size());
        assertTrue(code.getNameTest().contains("Baz"));
        assertTrue(code.getNameTest().contains("Qux"));
        assertEquals(bazClass, code.getStringCode("Baz"));
        assertEquals(quxClass, code.getStringCode("Qux"));

    }
}