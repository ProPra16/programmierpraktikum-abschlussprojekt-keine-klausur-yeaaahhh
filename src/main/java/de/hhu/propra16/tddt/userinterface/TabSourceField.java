package de.hhu.propra16.tddt.userinterface;

import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.util.Map;

/**
 * Created by felix on 7/5/16.
 */
public class TabSourceField extends TabPane implements SourceField {

    // Every value in codeAreas must be a child!
    private Map<String, TextArea> codeAreas;

    @Override
    public void setEditable(boolean value) {
        for (TextArea area : codeAreas.values()) {
            area.setEditable(value);
        }
    }

    @Override
    public void showText(String name, String text) {
        TextArea area = codeAreas.get(name);
        if (area == null) {
            area = new TextArea();
            codeAreas.put(name, area);
            getChildren().add(area);
        }
        area.setText(text);
    }

    @Override
    public String getTextOf(String name) {
        TextArea area = codeAreas.get(name);
        return area == null ? "" : area.getText();
    }
}
