package de.hhu.propra16.tddt.exercise;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;


class ExerciseHandler extends DefaultHandler {

    private String current = "";
    private ExerciseBuilder exercise;
    private List<Exercise> exercises = new ArrayList<>();

    @Override
    public void startElement(String URI, String localName, String qName, Attributes atts){
        if(qName.equals("exercise")){
            exercise = new ExerciseBuilder(getAttribute(atts, "name", 0));
        }
        if(qName.equals("class")){
            exercise.setClassName(getAttribute(atts,"name", 0));
        }
        if(qName.equals("test")){
            exercise.setTestName(getAttribute(atts, "name",0));
        }
        if(qName.equals("babysteps")){
            exercise.setBabySteps(getAttribute(atts, "value", 0).toLowerCase().equals("true"));
//            TODO add time parser
//            if(exercise.getBabysteps()) {
//                exercise.setTimer(getAttribute(atts, "time", 1));
//            }

        }
    }

    @Override
    public void endElement(String URI, String localeName, String qName){
        trim(getWhitespace(current));
        if(qName.equals("description")){
            exercise.setDescription(current);
            System.out.println(current);

        }
        if(qName.equals("class")){
            exercise.setClassCode(current);
            System.out.println(current);

        }
        if(qName.equals("test")){
            exercise.setTestCode(current);
            System.out.println(current);

        }

        if(qName.equals("exercise")) exercises.add(exercise.build());
        current = "";
    }

    @Override
    public void characters(char[] ch, int start, int length){
        current += new String(ch, start, length);
    }

    private int getWhitespace(String objective){
        int counter = 0;
        int length = objective.length();
        for (int i = 0; i < length; i++) {
            char test = objective.charAt(i);
            if (test != ' ' && test != '\n') {
                break;
            }
            if(test == '\n') counter = 0;
            counter++;
        }
        return (counter/4) *4;
    }

    private void trim(int spaces){
        String whitespaces = "";
        for(int i = 0; i < spaces; i++){
            whitespaces += " ";
        }
        String[] parts = current.split(whitespaces);
        current = "";
        for(int i = 1; i < parts.length; i++){
            current += parts[i];
        }
    }

    private String getAttribute(Attributes atts, String equation, int index){
        String result ="";
        if(atts.getLocalName(index).equals(equation)) result = atts.getValue(index);
        return result;
    }
}
