package de.hhu.propra16.tddt.exercise;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


class ExerciseHandler extends DefaultHandler {

    private String current = "";
    private ExerciseBuilder exercise;
    private List<Exercise> exercises = new ArrayList<>();


    private boolean bExs;
    @Override
    public void startElement(String URI, String localName, String qName, Attributes atts) {
        qName = qName.toLowerCase();
        if(qName.equals("exercises")) bExs = true;
        else if (bExs && qName.equals("exercise")) {
            String name = "";
            if(atts.getLength() > 0) name = getAttribute(atts, "name", 0);
            exercise = new ExerciseBuilder(name);
        }
        else if (bExs && qName.equals("class")) {
            exercise.setClassName(getAttribute(atts, "name", 0));
        }
        else if (bExs && qName.equals("test")) {
            exercise.setTestName(getAttribute(atts, "name", 0));
        }
        else if (bExs && qName.equals("babysteps")) {
            boolean condition = getAttribute(atts, "value", 0).toLowerCase().equals("true");
            exercise.setBabySteps(condition);
            if(condition) {
                exercise.setTime(parseTime(getAttribute(atts,"time", 1)));
            }
        }
        else if (bExs && qName.equals("timetracking")){
            exercise.setTracking(getAttribute(atts, "value", 0).toLowerCase().equals("true"));
        }
    }

    @Override
    public void endElement(String URI, String localeName, String qName) {
        qName = qName.toLowerCase();
        trim();
        if (bExs && qName.equals("description")) {
            exercise.setDescription(current);
            System.out.println(current);

        }
        if (bExs && qName.equals("class")) {
            exercise.setClassCode(current);
            System.out.println(current);
        }

        if (bExs && qName.equals("test")) {
            exercise.setTestCode(current);
            System.out.println(current);
        }

        if (bExs && qName.equals("exercise")) exercises.add(exercise.build());
        current = "";

        if(qName.equals("exercises")) bExs = false;
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
