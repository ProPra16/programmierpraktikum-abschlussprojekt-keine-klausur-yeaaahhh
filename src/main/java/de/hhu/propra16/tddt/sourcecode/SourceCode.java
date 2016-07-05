package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Contains test classes as well as normal classes
 * WIP
 */
public class SourceCode{

    private final List<CompilationUnit> units;
    private final List<CompilationUnit> tests = new ArrayList<>();
    private final List<CompilationUnit> code = new ArrayList<>();

    public SourceCode(List<CompilationUnit> units) {
        this.units = units;
        slit();
    }

    private void slit() {
        units.forEach((element) -> {
            if (element.isATest())
                tests.add(element);
            else
                code.add(element);
        });
    }

    // CompilerResult
    public CompilerResult compileCode() {
        synchronized (units) {
            CompilationUnit[] array = units.toArray(new CompilationUnit[units.size()]);
            JavaStringCompiler JSC = CompilerFactory.getCompiler(array);
            JSC.compileAndRunTests();
            return JSC.getCompilerResult();
        }
    }
    // TestResult
    public TestResult compileTest() {
        synchronized (units) {
            CompilationUnit[] array = units.toArray(new CompilationUnit[units.size()]);
            JavaStringCompiler JSC = CompilerFactory.getCompiler(array);
            JSC.compileAndRunTests();
            return JSC.getTestResult();
        }
    }

    public String ResultTests() {
        String Fehler = String.valueOf(this.compileTest().getNumberOfFailedTests());
        Collection<TestFailure> test = this.compileTest().getTestFailures();
        test.forEach((e)-> {
            String tmp = e.getMethodName() + " " + e.getMessage();
            Fehler.concat(tmp);
        });
        return Fehler;
    }

    //Methode bekommt einen Klassennamen und gibt den SourceCode als String zurück
    public String getStringCode(String Klassenname) {
        for (int i=0; i<units.size(); i++) {
            if (units.get(i).getClassName().equals(Klassenname))
                return units.get(i).getClassContent();
        }
       return "";
    }

    //Methode gibt Klassenname der Tests aus
    public List<String> getNameTest() {
        List<String> stringTest = new ArrayList<>();
        tests.forEach((t) -> {
            stringTest.add(t.getClassName());
        });
        return stringTest;
    }

    //Methode gibt Klassenname der Programme aus
    public List<String> getNameCode() {
        List<String> stringCode = new ArrayList<>();
        code.forEach((c) -> {
            stringCode.add(c.getClassName());
        });
        return stringCode;
    }

    public static void main(String[] args) {
        CompilationUnit a = new CompilationUnit("bla", "import org.junit.*; public class bla{@Test public void dot(){}}", true);
        CompilationUnit b = new CompilationUnit("blubb", "public class blubb{ } ", false);
        CompilationUnit c = new CompilationUnit("bsp", "import org.junit.*; public class bsp{} ", true);
        List<CompilationUnit> liste = new ArrayList<>();
        liste.add(a);
        liste.add(b);
        liste.add(c);
        SourceCode sc = new SourceCode(liste);
        System.out.println("Fehler?:" + sc.compileCode().hasCompileErrors());
        System.out.println("DurationCode: "+ sc.compileCode().getCompileDuration());
//        System.out.println("Duration:" + sc.compileTest().getTestDuration());
        Collection<CompileError> error = sc.compileCode().getCompilerErrorsForCompilationUnit(a);
        error.forEach((e)->{
            System.out.println(e.getMessage());
        });
        System.out.println("FailedTest: " + sc.compileTest().getNumberOfFailedTests());
        Collection<TestFailure> test = sc.compileTest().getTestFailures();
        test.forEach((e) -> {
            System.out.println(e.getMethodName()+" "+e.getTestClassName()+" "+e.getMessage());
        });
        System.out.println("##" + sc.ResultTests());

    }
}
