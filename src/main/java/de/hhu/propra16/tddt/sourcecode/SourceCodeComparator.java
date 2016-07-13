package de.hhu.propra16.tddt.sourcecode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Natter on 07.07.16.
 */
public class SourceCodeComparator {
    private static int change=0;

    // Methoden newLines Code/Test gibt die Anzahl der Linien zurück, die neu hinzugefügt wurden
    public static List<Integer> newLinesCode(SourceCode alt, SourceCode neu) {
        List<String> tmpAlt = new ArrayList<>();
        alt.getNameCode().forEach((a)-> {
            tmpAlt.add(alt.getStringCode(a));
        });
        List<String> tmpNeu = new ArrayList<>();
        alt.getNameCode().forEach((n)-> {
            tmpAlt.add(alt.getStringCode(n));
        });
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i<tmpAlt.size(); i++) {
            String[] altArray = tmpAlt.get(i).split("\n");
            String[] neuArray = tmpNeu.get(i).split("\n");
            lines.add(altArray.length - neuArray.length);
        }
        return lines;
    }
    public static List<Integer> newLinesTest(SourceCode alt, SourceCode neu) {
        List<String> tmpAlt = new ArrayList<>();
        alt.getNameTest().forEach((aT)-> {
            tmpAlt.add(alt.getStringCode(aT));
        });
        List<String> tmpNeu = new ArrayList<>();
        alt.getNameTest().forEach((t)-> {
            tmpAlt.add(alt.getStringCode(t));
        });
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i<tmpAlt.size(); i++) {
            String[] altArray = tmpAlt.get(i).split("\n");
            String[] neuArray = tmpNeu.get(i).split("\n");
            lines.add(altArray.length - neuArray.length);
        }
        return lines;
    }


    // Methode gibt Anzahl der geänderterten Lines zurück
    public static int changedLines(String alt, String neu) {
        return change;
    }

    // Methode gibt geänderte Zeilen zurück als List<String>
    public static List<String> stringCompare(String alt, String neu) {
        String[] altArray = alt.split("\n");
        String[] neuArray = neu.split("\n");
        List<String> StringList = new ArrayList<>();
        if (neuArray.length >= altArray.length) {
            StringList  = compare(altArray, neuArray);
            for (int i = altArray.length; i< neuArray.length; i++) {
                StringList.add(i+": "+neuArray[i]);
            }
        }
        else
            StringList = compare(neuArray, altArray);
        change = StringList.size();
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
        change = diff.size();
        return diff;
    }

//    public static String changeLines(String alt, String neu) {
//         return "Es wurden "+newLines(alt, neu) +" Zeilen hinzugefügt.\nEs wurden "+stringCompare(alt,neu).size()+" Zeilen geändert.";
//    }

    public static void main(String[] args) {
        String string1 = "mimi\nHallo\nWie gehts?\nLass mal was machen";
        String string2 = "Hallo\nWie geht es dir?\nLass mal was machen\nheute?";
//        System.out.println(changedLines(string1, string2));
    }
}
