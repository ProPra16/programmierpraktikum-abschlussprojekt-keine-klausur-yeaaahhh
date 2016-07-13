package de.hhu.propra16.tddt.sourcecode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Natter on 07.07.16.
 */
public class SourceCodeComparator {
    private static int change = 0;
    private static List<String> altCode = new ArrayList<>();
    private static List<String> neuCode = new ArrayList<>();
    private static List<String> altTest = new ArrayList<>();
    private static List<String> neuTest = new ArrayList<>();

    // Methoden newLines Code/Test gibt die Anzahl der Linien zurück, die neu hinzugefügt wurden
    public static List<Integer> newLinesCode(SourceCode alt, SourceCode neu) {
        split(alt, neu);
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < altCode.size(); i++) {
            String[] altArray = altCode.get(i).split("\n");
            String[] neuArray = neuCode.get(i).split("\n");
            lines.add(altArray.length - neuArray.length);
        }
        return lines;
    }

    public static List<Integer> newLinesTest(SourceCode alt, SourceCode neu) {
        split(alt, neu);
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < altTest.size(); i++) {
            String[] altArray = altTest.get(i).split("\n");
            String[] neuArray = neuTest.get(i).split("\n");
            lines.add(altArray.length - neuArray.length);
        }
        return lines;
    }

    // Methoden changedLines Code/Test gibt die Anzahl der geänderten Zeilen zurück
    public static List<Integer> changedLinesCode(SourceCode alt, SourceCode neu) {
        split(alt, neu);
        return countChangedLines(altCode, neuCode);
    }

    private static List<Integer> countChangedLines(List<String> altLines, List<String> neuLines) {
        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < altLines.size(); i++) {
            String[] altArray = altLines.get(i).split("\n");
            String[] neuArray = neuLines.get(i).split("\n");
            List<String> tmp = new ArrayList<>();
            tmp = compare(altArray, neuArray);
            if (altArray.length <= neuArray.length) {
                for (int j = altArray.length; j < neuArray.length; j++) {
                    tmp.add(neuArray[j]);
                }
            }
            lines.add(tmp.size());
        }
        return lines;
    }

    public static List<Integer> changedLinesTest(SourceCode alt, SourceCode neu) {
        split(alt, neu);
        return countChangedLines(altTest, neuTest);
    }

    private static List<String> compare(String[] altArray, String[] neuArray) {
        List<String> diff = new ArrayList<>();
        for (int i = 0; i < altArray.length; i++) {
            if (altArray[i].equals(neuArray[i]))
                continue;
            else {
                diff.add(i + ": " + neuArray[i]);
            }
        }
//        change = diff.size();
        return diff;
    }

    private static void split(SourceCode alt, SourceCode neu) {
        altCode = new ArrayList<>();
        altTest = new ArrayList<>();
        neuCode = new ArrayList<>();
        neuTest = new ArrayList<>();
        alt.getNameCode().forEach((a) -> {
            altCode.add(alt.getStringCode(a));
        });
        neu.getNameCode().forEach((n) -> {
            neuCode.add(neu.getStringCode(n));
        });
        alt.getNameTest().forEach((aT) -> {
            altTest.add(alt.getStringCode(aT));
        });
        neu.getNameTest().forEach((t) -> {
            neuTest.add(neu.getStringCode(t));
        });
    }

    public static void main(String[] args) {
//        String string1 = "mimi\nHallo\nWie gehts?\nLass mal was machen";
//        String string2 = "Hallo\nWie geht es dir?\nLass mal was machen\nheute?";
//        System.out.println(changedLines(string1, string2));
    }
}
