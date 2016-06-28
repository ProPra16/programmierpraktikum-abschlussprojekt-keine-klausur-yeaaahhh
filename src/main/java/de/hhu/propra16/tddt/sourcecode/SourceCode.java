package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.CompilationUnit;

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

}
