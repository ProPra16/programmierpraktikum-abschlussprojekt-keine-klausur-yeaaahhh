package de.hhu.propra16.tddt.sourcecode;

/**
 * Created by Natter on 07.07.16.
 */
public class SourceCodeComparator {

    // Methode Gibt die Anzahl der Linien zurück, die hinzugefügt wurden im String
    public static int StringLines(String alt, String neu) {
        String[] altArray = alt.split("\n");
        String[] neuArray = neu.split("\n");
        return neuArray.length - altArray.length;
    }
}
