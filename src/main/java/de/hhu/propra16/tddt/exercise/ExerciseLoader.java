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


    public static List<Exercise> loadFile(InputSource is) {
        List<Exercise> list = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ExerciseHandler handler = new ExerciseHandler(ExerciseBuilder::new);
            saxParser.parse(is, handler);
            list = handler.getExercises();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            System.out.println("Input has to be in correct format!");
        }
        return list;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Path p = Paths.get("katalog/Exercise.xml");
        InputSource in = new InputSource(p.toString());
        loadFile(in);
    }
}
