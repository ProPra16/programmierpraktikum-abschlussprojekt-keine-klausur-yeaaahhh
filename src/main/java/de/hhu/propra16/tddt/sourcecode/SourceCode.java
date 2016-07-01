package de.hhu.propra16.tddt.sourcecode;

import org.junit.Test;
import vk.core.api.*;
import java.util.ArrayList;
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
    }

    // Methode splittet units in ArrayList aus Tests und Codes
    private void split() {
        units.forEach((element) -> {
            if (element.isATest())
                tests.add(element);
            else
                code.add(element);
        });
    }

    // CompilerResult
    public List<CompilerResult> compileCode() {
        List<CompilerResult> result = new ArrayList<>();
        synchronized (units) {
            CompilationUnit[] array = (CompilationUnit[]) units.toArray(new CompilationUnit[units.size()]);
            JavaStringCompiler JSC = CompilerFactory.getCompiler(array);
            JSC.compileAndRunTests();
            result.add(JSC.getCompilerResult());
        }
        return result;
    }
    // TestResult
    public List<TestResult> compileTest() {
        List<TestResult> result = new ArrayList<>();
        synchronized (units) {
            CompilationUnit[] array = (CompilationUnit[]) units.toArray(new CompilationUnit[units.size()]);
            JavaStringCompiler JSC = CompilerFactory.getCompiler(array);
            JSC.compileAndRunTests();
            result.add(JSC.getTestResult());
        }
        return result;
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
        split();
        List<String> stringTest = new ArrayList<>();
        tests.forEach((t) -> {
            stringTest.add(t.getClassName());
        });
        return stringTest;
    }

    //Methode gibt Klassenname der Programme aus
    public List<String> getNameCode() {
        split();
        List<String> stringCode = new ArrayList<>();
        code.forEach((c) -> {
            stringCode.add(c.getClassName());
        });
        return stringCode;
    }

    public static void main(String[] args) {
        CompilationUnit a = new CompilationUnit("bla", "public class bla{static void blaa{Beispiel2.do(2)}}", true);
        CompilationUnit b = new CompilationUnit("blubb", "public class blubb{ static void blub{}} ", false);
        CompilationUnit c = new CompilationUnit("bsp", "public bsp{} ", true);
        List<CompilationUnit> liste = new ArrayList<>();
        liste.add(a);
        liste.add(b);
        liste.add(c);
        SourceCode sc = new SourceCode(liste);
        sc.compileCode();
        sc.compileTest();
    }

}
