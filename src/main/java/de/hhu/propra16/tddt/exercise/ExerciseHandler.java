package de.hhu.propra16.tddt.exercise;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


class ExerciseHandler extends DefaultHandler {

    private String current = "";
    private Supplier<ExerciseBuilder> exerciseBuilderFactory;
    private ExerciseBuilder exerciseBuilder;
    private List<Exercise> exercises = new ArrayList<>();
    private boolean hasExercises;


    public ExerciseHandler(Supplier<ExerciseBuilder> exerciseBuilderFactory) {
        this.exerciseBuilderFactory = exerciseBuilderFactory;
    }

    @Override
    public void startElement(String URI, String localName, String qName, Attributes atts) {
        qName = qName.toLowerCase();
        if (qName.equals("exercises")) hasExercises = true;
        else if (hasExercises && qName.equals("exercise")) {
            exerciseBuilder = exerciseBuilderFactory.get();
            exerciseBuilder.setName(getAttribute(atts, "name", 0));
        } else if (hasExercises && qName.equals("class")) {
            exerciseBuilder.setClassName(getAttribute(atts, "name", 0));
        } else if (hasExercises && qName.equals("test")) {
            exerciseBuilder.setTestName(getAttribute(atts, "name", 0));
        } else if (hasExercises && qName.equals("babysteps")) {
            boolean hasBabysteps = getAttribute(atts, "value", 0).toLowerCase().equals("true");
            boolean hasNoTime = getAttribute(atts, "time", 1) == null;
            exerciseBuilder.setBabySteps(hasBabysteps);
            if (hasBabysteps && !hasNoTime) {
                exerciseBuilder.setTime(parseTime(getAttribute(atts, "time", 1)));
            }
        } else if (hasExercises && qName.equals("timetracking")) {
            exerciseBuilder.setTracking(getAttribute(atts, "value", 0).toLowerCase().equals("true"));
        }
    }

    @Override
    public void endElement(String URI, String localeName, String qName) {
        qName = qName.toLowerCase();
        trim();
        if (hasExercises && qName.equals("description")) {
            exerciseBuilder.setDescription(current);
        } else if (hasExercises && qName.equals("class")) {
            exerciseBuilder.setClassCode(current);
        } else if (hasExercises && qName.equals("test")) {
            exerciseBuilder.setTestCode(current);
        } else if (hasExercises && qName.equals("exercise")) exercises.add(exerciseBuilder.build());
        else if (qName.equals("exercises")) hasExercises = false;
        current = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        current += new String(ch, start, length);
    }

    private String getWhitespace() {
        String whitespace = "";
        int length = current.length();
        for (int i = 0; i < length; i++) {
            char test = current.charAt(i);
            if (test != ' ' && test != '\n') {
                break;
            }
            if (test == '\n') {
                current = current.substring(i + 1);
                length = current.length();
            }
            whitespace += " ";
        }
        return whitespace;
    }

    private void trim() {
        String whitespaces = getWhitespace();
        String[] parts = current.split(whitespaces);
        current = "";
        for (String part : parts) {
            current += part;
        }
    }

    private String getAttribute(Attributes atts, String equation, int index) {
        if (atts.getLength() == 0 || index >= atts.getLength()) return null;
        boolean condition = atts.getLocalName(index).equals(equation);
        return condition ? atts.getValue(index) : null;
    }

    private Duration parseTime(String time){
        Duration dTime = null;
        if(time.contains(":")) {
            String[] timeSections = time.split(":");
            dTime = Duration.parse("PT"+timeSections[0]+"M"+timeSections[1]+"S");
        }
        return dTime;
    }

    List<Exercise> getExercises() {
        return exercises;
    }
}
