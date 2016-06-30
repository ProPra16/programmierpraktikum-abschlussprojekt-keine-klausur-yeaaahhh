package de.hhu.propra16.tddt.sourcecode;

import org.junit.Before;
import org.junit.Test;
import vk.core.api.CompilationUnit;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by NatalieHHU on 30.06.16.
 */
public class SourceCodeTest {
    SourceCode SC;
    @Before
    public void setUp() throws Exception {
        CompilationUnit a = new CompilationUnit("Test1", "public class Test1 {do some stuff}", true);
        CompilationUnit b = new CompilationUnit("bla", "public class bla{}", false);
        CompilationUnit c = new CompilationUnit("Test3", "public Test2 {do something}", true);
        CompilationUnit d = new CompilationUnit("blubb", "public class blubb{}", false);
        List<CompilationUnit> liste = new ArrayList<>();
        liste.add(a);
        liste.add(b);
        liste.add(c);
        liste.add(d);
        SC = new SourceCode(liste);
    }
    @Test
    public void testgetStringCode() {
        assertEquals("public class bla{}", SC.getStringCode("Test2"));
    }
    @Test
    public void testgetNameTest() {
        assertEquals("Test1", SC.getNameTest().get(0));
    }
    @Test
    public void testgetNameCode() {
        assertEquals("blubb", SC.getNameCode().get(1));
    }
}