package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

public interface Editor {

    void show(SourceCode code);

    SourceCode get();
}
