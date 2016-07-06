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
    SourceCode sc1;
    SourceCode sc2;

    @Before
    public void setUp() throws Exception {
        CompilationUnit a = new CompilationUnit("Test1", "import org.junit.*; public class Test1{ @Test public void dat(){bla.doSomething();}}", true);
        CompilationUnit b = new CompilationUnit("bla", "public class bla{}", false);
        CompilationUnit c = new CompilationUnit("Test3", "import org.junit.*; public Test22 {@Test public void dot(){}}", true);
        CompilationUnit d = new CompilationUnit("blubb", "public class blubb{int i =1}", false);

        sc1 = new SourceCodeBuilder().add(a).add(b).build();
        sc2 = new SourceCodeBuilder().add(c).add(d).build();
    }
    @Test
    public void testgetStringCode() {
        assertEquals("public class bla{}", sc1.getStringCode("bla"));
    }

    @Test
    public void testgetNameTest() {
        assertEquals("Test1", sc1.getNameTest().get(0));
    }

    @Test
    public void testgetNameCode() {
        assertEquals("blubb", sc2.getNameCode().get(0));
    }

    @Test
    public void FailedTest() {
        System.out.println(sc1.getResult());
//        assertEquals(1,sc1.numberOfFailedTests());
    }

    @Test
    public void CodeError() {
        System.out.println(sc2.getResult());

    }
}