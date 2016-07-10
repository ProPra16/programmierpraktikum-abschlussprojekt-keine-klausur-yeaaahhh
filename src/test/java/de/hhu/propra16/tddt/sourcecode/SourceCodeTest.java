package de.hhu.propra16.tddt.sourcecode;

import org.junit.Before;
import org.junit.Test;
import vk.core.api.CompilationUnit;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by NatalieHHU on 30.06.16.
 */
public class SourceCodeTest {
    private SourceCode sc1;
    private SourceCode sc2;
    private SourceCode sc3;

    @Before
    public void setUp() throws Exception {
        CompilationUnit a = new CompilationUnit("Test1", "import org.junit.*; public class Test1{ @Test public void dat(){ Assert.assertTrue(false);}}", true);
        CompilationUnit b = new CompilationUnit("bla", "public class bla{}", false);
        CompilationUnit c = new CompilationUnit("Test3", "import org.junit.*; public class Test3 {@Test public void dot(){}}", true);
        CompilationUnit d = new CompilationUnit("blubb", "public class blubb{int i =1}", false);

        sc1 = new SourceCodeBuilder().add(a).add(b).build();
        sc2 = new SourceCodeBuilder().add(c).add(d).build();
        sc3 = new SourceCodeBuilder().add(b).add(c).build();
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
        assertFalse(sc1.hasCompileErrors());
        assertEquals(1,sc1.numberOfFailedTests());
    }

    @Test
    public void CodeError() {
        System.out.println(sc2.getResult());
        assertTrue(sc2.hasCompileErrors());
        assertEquals(0, sc2.numberOfFailedTests());
    }

    @Test
    public void everythingFine() {
        assertEquals(0, sc3.numberOfFailedTests());
        assertFalse(sc3.hasCompileErrors());
    }
}