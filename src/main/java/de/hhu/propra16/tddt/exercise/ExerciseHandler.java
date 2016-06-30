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
    private boolean bExs;


    public ExerciseHandler(Supplier<ExerciseBuilder> exerciseBuilderFactory) {
        this.exerciseBuilderFactory = exerciseBuilderFactory;
    }

    @Override
    public void startElement(String URI, String localName, String qName, Attributes atts) {
        qName = qName.toLowerCase();
        if(qName.equals("exercises")) bExs = true;
        else if (bExs && qName.equals("exercise")) {
            String name = null;
            if(atts.getLength() > 0) name = getAttribute(atts, "name", 0);
            exerciseBuilder = exerciseBuilderFactory.get();
            exerciseBuilder.setName(name);
        }
        else if (bExs && qName.equals("class")) {
            exerciseBuilder.setClassName(getAttribute(atts, "name", 0));
        }
        else if (bExs && qName.equals("test")) {
            exerciseBuilder.setTestName(getAttribute(atts, "name", 0));
        }
        else if (bExs && qName.equals("babysteps")) {
            boolean condition = getAttribute(atts, "value", 0).toLowerCase().equals("true");
            exerciseBuilder.setBabySteps(condition);
            if(condition) {
                exerciseBuilder.setTime(parseTime(getAttribute(atts, "time", 1)));
            }
        }
        else if (bExs && qName.equals("timetracking")){
            exerciseBuilder.setTracking(getAttribute(atts, "value", 0).toLowerCase().equals("true"));
        }
    }

    @Override
    public void endElement(String URI, String localeName, String qName) {
        qName = qName.toLowerCase();
        trim();
        if (bExs && qName.equals("description")) {
            exerciseBuilder.setDescription(current);
            System.out.println(current);
        }
        else if (bExs && qName.equals("class")) {
            exerciseBuilder.setClassCode(current);
            System.out.println(current);
        }
        else if (bExs && qName.equals("test")) {
            exerciseBuilder.setTestCode(current);
            System.out.println(current);
        } else if (bExs && qName.equals("exercise")) exercises.add(exerciseBuilder.build());
        else if(qName.equals("exercises")) bExs = false;

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
            if (test == '\n'){
                current = current.substring(i+1);
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
        String result = "";
        if (atts.getLocalName(index).equals(equation)) result = atts.getValue(index);
        return result;
    }

    private Duration parseTime(String time){
        Duration dTime = null;
        if(time.contains(":")) {
            String[] timeSections = time.split(":");
            dTime = Duration.parse("PT"+timeSections[0]+"M"+timeSections[1]+"S");
        }
        return dTime;
    }

    List<Exercise> getExercises(){
        return exercises;
    }
}
