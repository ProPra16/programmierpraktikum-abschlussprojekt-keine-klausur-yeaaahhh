package de.hhu.propra16.tddt.sourcecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Natter on 07.07.16.
 */
public class SourceCodeComparator {

    // Methode Gibt die Anzahl der Linien zurück, die hinzugefügt wurden im neuen String
    public static int StringLines(String alt, String neu) {
        String[] altArray = alt.split("\n");
        String[] neuArray = neu.split("\n");
        return neuArray.length - altArray.length;
    }

    public static List<String> StringCompare(String alt, String neu) {
        String[] altArray = alt.split("\n");
        String[] neuArray = neu.split("\n");
//        Arrays.sort(altArray);
//        Arrays.sort(neuArray);
        List<String> StringList = new ArrayList<>();
        if (neuArray.length >= altArray.length) {
            StringList  = compare(altArray, neuArray);
            for (int i = altArray.length; i< neuArray.length; i++) {
                StringList.add(i+": "+neuArray[i]);
            }
        }
        else
            StringList = compare(neuArray, altArray);
        return StringList;
    }

    private static List<String> compare(String[] altArray, String[] neuArray){
        List<String> diff = new ArrayList<>();
        for(int i=0; i< altArray.length; i++) {
            if(altArray[i].equals(neuArray[i]))
                continue;
            else {
                diff.add(i+": "+neuArray[i]);
            }
        }
        return diff;
    }

    public static String ChangeLines(String alt, String neu) {
         return "Es wurden "+StringLines(alt, neu) +" Zeilen hinzugefügt.\nEs wurden "+StringCompare(alt,neu).size()+" Zeilen geändert.";
    }

    public static void main(String[] args) {
        String string1 = "mimi\nHallo\nWie gehts?\nLass mal was machen";
        String string2 = "Hallo\nWie geht es dir?\nLass mal was machen\nheute?";
        System.out.println(ChangeLines(string1, string2));
    }
}
