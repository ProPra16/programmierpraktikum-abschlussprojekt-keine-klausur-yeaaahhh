package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.CompilationUnit;
import vk.core.api.CompilerResult;
import vk.core.api.TestResult;

import java.util.List;

/**
 * Contains test classes as well as normal classes
 * WIP
 */
public class SourceCode {

    private final List<CompilationUnit> units;

    SourceCode(List<CompilationUnit> units) {
        this.units = units;
    }

    public CompilerResult compileCode() {
        return null;
    }

    public TestResult compileTest() {
        return null;
    }

}
