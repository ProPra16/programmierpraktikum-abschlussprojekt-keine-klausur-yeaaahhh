package de.hhu.propra16.tddt.trainer;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

/**
 * Created by tim on 30.06.2016.
 */
public interface CheckCompile {

    public Boolean check(SourceCode sc, Phase phase);
}
