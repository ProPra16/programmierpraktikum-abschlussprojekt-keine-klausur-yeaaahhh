package de.hhu.propra16.tddt.userinterface;

import javafx.beans.property.IntegerProperty;

/**
 * A CodeField manages multiple areas, to which text can be shown, and from
 * which modified text can be read.
 * The areas are identified with a String name
 */
public interface CodeField {

    /**
     * Sets all contained areas editable/non-editable
     *
     * @param value The editable property
     */
    void setEditable(boolean value);

    /**
     * Shows text to a specific area.
     * If an area with this name does not exist, it will be created.
     *
     * @param name the name of the area to which the text should be shown
     * @param text the text to show
     */
    void showText(String name, String text);

    /**
     * Gets text from a specific area.
     *
     * @param name the name of the area to get text from
     * @return the (from the user modified) text or an empty String, if the
     * area does not exist.
     */
    String getTextOf(String name);

    /**
     * Indicates, how many changes have been made over all contained areas.
     *
     * @return an IntegerProperty indicating change count
     */
    IntegerProperty editNumber();
}
