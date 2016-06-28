package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
import vk.core.api.CompilerResult;
import vk.core.internal.InternalCompiler;
import vk.core.internal.InternalResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains test classes as well as normal classes
 * WIP
 */
public class SourceCode {

    private final List<CompilationUnit> units;
    private final List<CompilationUnit> tests = new ArrayList<>();
    private final List<CompilationUnit> code = new ArrayList<>();

    public SourceCode(List<CompilationUnit> units) {
        this.units = units;
    }

    // Methode splittet units in ArrayList aus Tests und Codes
    public void split() {
        units.forEach((element)-> {
            if (element.isATest())
                tests.add(element);
            else
                code.add(element);
        });
    }

    public void compileTest() {
        split();
        List<InternalResult> result = new ArrayList<>();
        synchronized (tests) {
            tests.forEach((element) -> {
                InternalCompiler IC = (InternalCompiler) CompilerFactory.getCompiler(element);
                IC.compileAndRunTests();
                result.add((InternalResult) IC.getTestResult());
            });
        }
    }

    public void compileCode() {
        split();
        List<InternalResult> result = new ArrayList<>();
        synchronized (code) {
            code.forEach((element) -> {
                InternalCompiler IC = (InternalCompiler) CompilerFactory.getCompiler(element);
                IC.compileAndRunTests();
                result.add((InternalResult) IC.getCompilerResult());
            });
        }
    }

    // Methode wird vom Trainer aufgerufen; Trainer weiß in welcher Phase wir uns befinden
    // className: getClassName() , String aus Textfield für Content, boolean je nach Phase
    public void addCompilationUnit(CompilationUnit cu) {
        units.add(cu);
    }

    public static void main(String[] args) {
        CompilationUnit a = new CompilationUnit("Beispiel", "public class bla{static void blaa{Beispiel2.do(2)}}", true);
        CompilationUnit b = new CompilationUnit("Beispiel2", "public class blubb{ static void blub{}} ", false);
        List<CompilationUnit> liste = new ArrayList<>();
        liste.add(a);
        liste.add(b);
        SourceCode sc = new SourceCode(liste);
      sc.compileTest();
    }

}
