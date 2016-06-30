package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerFactory;
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
    private void split() {
        units.forEach((element) -> {
            if (element.isATest())
                tests.add(element);
            else
                code.add(element);
        });
    }

    public void compile() {
        List<InternalResult> result = new ArrayList<>();
        synchronized (units) {
            units.forEach((element) -> {
                InternalCompiler IC = (InternalCompiler) CompilerFactory.getCompiler(element);
                IC.compileAndRunTests();
                result.add((InternalResult) IC.getTestResult());
            });
        }
    }

    // Methode bekommt einen Klassennamen und gibt den SourceCode als String zurück
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


    // Methode wird vom Trainer aufgerufen; Trainer weiß in welcher Phase wir uns befinden
    // className: getClassName() , String aus Textfield für Content, boolean je nach Phase
    //public void addCompilationUnit(CompilationUnit cu) {units.add(cu);}

    public static void main(String[] args) {
        CompilationUnit a = new CompilationUnit("Beispiel", "public class bla{static void blaa{Beispiel2.do(2)}}", true);
        CompilationUnit b = new CompilationUnit("Beispiel2", "public class blubb{ static void blub{}} ", false);
        CompilationUnit c = new CompilationUnit("bsp", "public blaaa ", true);
        List<CompilationUnit> liste = new ArrayList<>();
        liste.add(a);
        liste.add(b);
        liste.add(c);
        SourceCode sc = new SourceCode(liste);
      sc.compile();
        List<String> bb = new ArrayList<>();
        bb = sc.getNameCode();
        for (int i = 0; i<bb.size(); i++)
            System.out.println(bb.get(i));


        //System.out.println(sc.getStringCode("Beispiel2"));
    }

}
