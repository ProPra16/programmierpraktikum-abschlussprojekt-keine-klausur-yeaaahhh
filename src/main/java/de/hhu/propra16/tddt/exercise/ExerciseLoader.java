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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class ExerciseLoader {


    private static List<Exercise> list;
    public ObservableList ExTitles;
    public ObservableList ExDescriptions;


    public ExerciseLoader(InputSource is){
        this.list = loadFile(is);
    }

    public List<Exercise> loadFile(InputSource is) {
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

        this.ExTitles = FXCollections.observableArrayList();
        this.ExDescriptions = FXCollections.observableArrayList();
        for (Exercise i: list) {
            ExTitles.add(i.getName());
            ExDescriptions.add(i.getDescription());
        }

        return list;
    }




    }
  /*  public static void main(String[] args) throws FileNotFoundException {
        Path p = Paths.get("katalog/Exercise.xml");
        InputSource in = new InputSource(p.toString());
        loadFile(in);
    }*/

