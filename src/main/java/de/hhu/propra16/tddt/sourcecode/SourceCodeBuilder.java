package de.hhu.propra16.tddt.sourcecode;

import vk.core.api.CompilationUnit;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;

/**
 * Builder for SourceCode
 */
public class SourceCodeBuilder {

    private final List<CompilationUnit> units = new ArrayList<>();

    public SourceCodeBuilder add(CompilationUnit unit) {
        units.add(unit);
        return this;
    }

    public SourceCode build() {
        return new SourceCode(units);
    }

}
