package de.hhu.propra16.tddt.exercise;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ExerciseLoader {

    private final URL catalog;

    public ExerciseLoader(URL catalog) {
        this.catalog = catalog;
    }

    public List<Exercise> load() throws IOException {
        return load(catalog.openStream());
    }

    List<Exercise> load(InputStream is) {
        List<Exercise> list = new ArrayList<>();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ExerciseHandler handler = new ExerciseHandler(ExerciseBuilder::new);
            saxParser.parse(is, handler);
            list = handler.getExercises();

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        Path p = Paths.get("katalog/Exercise.xml");
        ExerciseLoader loader = new ExerciseLoader(p.toUri().toURL());
        List<Exercise> list = loader.load();
        for (Exercise exercise : list) {
            System.out.println(exercise.getName());
            System.out.println(exercise.getDescription());
        }
    }
}
