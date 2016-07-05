package de.hhu.propra16.tddt.userinterface;

/**
 * Created by felix on 7/5/16.
 */
public interface SourceField {

    void setEditable(boolean editable);

    void showText(String name, String text);

    void getTextOf(String name);
}
