package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

/**
 * Created by felix on 6/30/16.
 */
public interface Editor {

    void show(SourceCode code, boolean sourceEditable, boolean testEditable);

    SourceCode get();
}
