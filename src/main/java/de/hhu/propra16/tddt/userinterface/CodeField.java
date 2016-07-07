package de.hhu.propra16.tddt.userinterface;

import javafx.beans.property.IntegerProperty;

/**
 * Created by felix on 7/5/16.
 */
public interface CodeField {

    void setEditable(boolean value);

    void showText(String name, String text);

    String getTextOf(String name);

    IntegerProperty editNumber();
}
