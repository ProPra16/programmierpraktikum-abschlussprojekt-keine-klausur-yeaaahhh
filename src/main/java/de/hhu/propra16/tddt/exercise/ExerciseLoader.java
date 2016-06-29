package de.hhu.propra16.tddt.exercise;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExerciseLoader {


    private static List<Exercise> loadFile(InputSource is) {
        List<Exercise> list = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ExerciseHandler handler = new ExerciseHandler();
            saxParser.parse(is, handler);
            list = handler.getExercises();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Path p = Paths.get("katalog/Exercise.xml");
        //Reader input = new StringReader("<exercises><exercise name=\"Test\"></exercise></exercises>");
        InputSource in = new InputSource(p.toString());
        loadFile(in);
    }
}
