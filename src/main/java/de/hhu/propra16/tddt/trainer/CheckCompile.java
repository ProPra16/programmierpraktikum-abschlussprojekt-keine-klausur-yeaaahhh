package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

public interface CheckCompile {

    boolean check(SourceCode sc, Phase phase);
}
