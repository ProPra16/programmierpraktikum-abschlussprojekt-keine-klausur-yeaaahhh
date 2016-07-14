package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
                if (e.getMessage() != null) {
                String tmp = "Method: "+ e.getMethodName() + " " + e.getMessage();
                array.add(tmp);
                }
                else {
                    String tmp = "Method: "+ e.getMethodName();
                    array.add(tmp);
                }
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

    private CompilerResult compilerResult;
    private CompilerResult compileCode() {
        if (compilerResult == null) {
            compilerResult = compile().getCompilerResult();
        }
        return compilerResult;
    }

    private TestResult testResult;
    private TestResult compileTest() {
        if (testResult == null) {
            testResult = compile().getTestResult();
        }
        return testResult;
    }

    private JavaStringCompiler compile() {
        synchronized (units) {
            CompilationUnit[] array = units.toArray(new CompilationUnit[units.size()]);
            JavaStringCompiler jsc = CompilerFactory.getCompiler(array);
            jsc.compileAndRunTests();
            return jsc;
        }
    }

    // gibt List<CU> zurück, die ComilerErros haben
    private List<CompilationUnit> cuHasError() {
        return units.stream()
                .filter(cu -> compileCode().getCompilerErrorsForCompilationUnit(cu) != null)
                .collect(Collectors.toList());
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
