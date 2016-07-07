package de.hhu.propra16.tddt.userinterface;

import de.hhu.propra16.tddt.sourcecode.SourceCode;
import javafx.beans.property.BooleanProperty;

public interface Editor {

    /**
     * Displays SourceCode
     *
     * @param code           to be displayed, not null
     * @param sourceEditable true, if the displayed non-test code should editable
     * @param testEditable   true, if the displayed test code should be editable
     */
    void show(SourceCode code, boolean sourceEditable, boolean testEditable);

    /**
     * Reads the (possibly modified) code
     *
     * @return A SourceCode instance from the edited displayed code.
     * returns an empty SourceCode instance, if {@link #show(SourceCode, boolean, boolean)}
     * was not called before.
     */
    SourceCode get();

    /**
     * Indicates, whether the code has changed since the last {@link #get()}
     * @return a BooleanProperty indicating change
     */
    BooleanProperty changed();
}
