package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Contains test classes as well as normal classes
 * WIP
 */
public class SourceCode {

    private final List<CompilationUnit> units;
    private final List<CompilationUnit> tests = new ArrayList<>();
    private final List<CompilationUnit> code = new ArrayList<>();
    private boolean hasErrors;

    public SourceCode(List<CompilationUnit> units) {
        this.units = new ArrayList<>(units);
        split();
    }

    public boolean hasCompileErrors() {
        return this.compileCode().hasCompileErrors();
    }

    public int numberOfFailedTests() {
        if (compileTest() == null) {
            return 0;
        }
        return this.compileTest().getNumberOfFailedTests();
    }

    // Methode gibt Testmeldung/ Fehlermeldung als String zurück
    public String getResult() {
        String Fehler = "";
        if (!this.hasCompileErrors()) { // keine CompilationErrors -> Test
            Fehler = "Number of successful tests: " + String.valueOf(this.compileTest().getNumberOfSuccessfulTests()) +
                    "\nNumber of failed tests: " + String.valueOf(this.compileTest().getNumberOfFailedTests()) + " ";
            Collection<TestFailure> test = this.compileTest().getTestFailures();
            ArrayList<String> array = new ArrayList<>();
            test.forEach((e) -> {
                String tmp = e.getMessage();
                array.add(tmp);
            });
            String tmp = array.toString() + " ";
            return Fehler.concat(tmp);
        } else { // hat CompilationErrors
            List<CompilationUnit> list = this.cuHasError();
            ArrayList<String> tmp = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                Collection<CompileError> error = this.compileCode().getCompilerErrorsForCompilationUnit(list.get(i));
                CompilationUnit CUtmp = list.get(i);
                error.forEach((e) -> {
                    tmp.add("\nKlasse " + CUtmp.getClassName() + ": Line " + e.getLineNumber() + ": " + e.getMessage() + " ");
                });
            }
            String t = Fehler.concat(tmp.toString() + " ");
            String tt = t.substring(1, t.length() - 2);
            return "### ERROR ###".concat(tt);
        }
    }

    //Methode bekommt einen Klassennamen und gibt den zugehörigen SourceCode als String zurück
    public String getStringCode(String Klassenname) {
        for (int i = 0; i < units.size(); i++) {
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

    //Methode gibt Klassennamen der Programme aus
    public List<String> getNameCode() {
        List<String> stringCode = new ArrayList<>();
        code.forEach((c) -> {
            stringCode.add(c.getClassName());
        });
        return stringCode;
    }

    // private methods:

    // splittet units in Code und tests ArrayList
    private void split() {
        units.forEach((element) -> {
            if (element.isATest())
                tests.add(element);
            else
                code.add(element);
        });
    }

    private CompilerResult compileCode() {
        synchronized (units) {
            CompilationUnit[] array = units.toArray(new CompilationUnit[units.size()]);
            JavaStringCompiler JSC = CompilerFactory.getCompiler(array);
            JSC.compileAndRunTests();
            return JSC.getCompilerResult();
        }
    }

    private TestResult compileTest() {
        synchronized (units) {
            CompilationUnit[] array = units.toArray(new CompilationUnit[units.size()]);
            JavaStringCompiler JSC = CompilerFactory.getCompiler(array);
            JSC.compileAndRunTests();
            return JSC.getTestResult();
        }
    }

    // gibt List<CU> zurück, die ComilerErros haben
    private List<CompilationUnit> cuHasError() {
        List<CompilationUnit> list = new ArrayList<>();
        units.forEach(e -> {
            JavaStringCompiler jsc = CompilerFactory.getCompiler(e);
            jsc.compileAndRunTests();
            CompilerResult cr = jsc.getCompilerResult();
            if (cr.hasCompileErrors())
                list.add(e);
        });
        return list;
    }

    public static void main(String[] args) {
        CompilationUnit a = new CompilationUnit("bla", "import org.junit.*;public class bla{@Test public void test(){blubb.dat();}}", true);
        CompilationUnit b = new CompilationUnit("blubb",
                "public class blubb{public static void dot() {}} ", false);
        CompilationUnit c = new CompilationUnit("bsp", "public class bsp{} ", false);
        List<CompilationUnit> liste = new ArrayList<>();
        liste.add(a);
        liste.add(b);
        liste.add(c);
        SourceCode sc = new SourceCode(liste);
        System.out.println("Fehler?:" + sc.compileCode().hasCompileErrors() + " Anzahl:" + sc.numberOfFailedTests());
        System.out.println(sc.getResult());
    }
}
