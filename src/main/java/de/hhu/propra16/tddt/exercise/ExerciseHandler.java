package de.hhu.propra16.tddt.exercise;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;


public class ExerciseHandler extends DefaultHandler {

    private boolean isDescription = false;
    private boolean isClass = false;
    private boolean isTest = false;

    @Override
    public void startDocument(){
    }

    @Override
    public void endDocument(){
    }

    @Override
    public void startElement(String URI, String localName, String qName, Attributes atts){
        if(qName.equals("description")) isDescription = true;
        if(qName.equals("class")) isClass = true;
        if(qName.equals("test")) isTest = true;
    }

    @Override
    public void endElement(String URI, String localeName, String qName){
        if(qName.equals("description")) isDescription = false;
        if(qName.equals("class")) isClass = false;
        if(qName.equals("test")) isTest = false;
    }

    @Override
    public void characters(char[] ch, int start, int length){
        if(isDescription) ;
        if(isClass) ;
        if(isTest) ;
    }
}
