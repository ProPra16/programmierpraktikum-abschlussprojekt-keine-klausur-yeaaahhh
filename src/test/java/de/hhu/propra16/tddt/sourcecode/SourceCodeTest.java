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
    SourceCode SC1;
    SourceCode SC2;
    @Before
    public void setUp() throws Exception {
        CompilationUnit a = new CompilationUnit("Test1", "import org.junit.*; public class Test1{ @Test public void dat(){bla.doSomething();}}", true);
        CompilationUnit b = new CompilationUnit("bla", "public class bla{}", false);
        CompilationUnit c = new CompilationUnit("Test3", "import org.junit.*; public Test22 {@Test public void dot(){}}", true);
        CompilationUnit d = new CompilationUnit("blubb", "public class blubb{int i =1}", false);
        List<CompilationUnit> liste1 = new ArrayList<>();
        List<CompilationUnit> liste2 = new ArrayList<>();
        liste1.add(a);
        liste1.add(b);
        liste2.add(c);
        liste2.add(d);
        SC1 = new SourceCode(liste1);
        SC2 = new SourceCode(liste2);
    }
    @Test
    public void testgetStringCode() {
        assertEquals("public class bla{}", SC1.getStringCode("bla"));
    }

    @Test
    public void testgetNameTest() {
        assertEquals("Test1", SC1.getNameTest().get(0));
    }

    @Test
    public void testgetNameCode() {
        assertEquals("blubb", SC2.getNameCode().get(0));
    }

    @Test
    public void FailedTest() {
        System.out.println(SC1.getResult());
//        assertEquals(1,SC1.numberOfFailedTests());
    }

    @Test
    public void CodeError() {
        System.out.println(SC2.getResult());

    }
}