package de.hhu.propra16.tddt.userinterface;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.util.HashMap;
import java.util.Map;

public class TabCodeField extends TabPane implements CodeField {

    // Every value in codeAreas must be a child in a tab of this!
    private final Map<String, TextArea> codeAreas = new HashMap<>();

    {
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
    }

    private boolean editable = true;
    @Override
    public void setEditable(boolean value) {
        for (TextArea area : codeAreas.values()) {
            area.setEditable(value);
        }
        editable = value;
    }

    @Override
    public void showText(String name, String text) {
        TextArea area = codeAreas.get(name);
        if (area == null) {
            area = addArea(name);
        }
        area.setText(text);
    }

    private TextArea addArea(String name) {
        // Set TextArea attributes
        TextArea area = new TextArea();
        area.setEditable(editable);
        area.textProperty().addListener(e -> editNumber.setValue(editNumber.getValue() +1 ));

        // Add it to a tab and the tab to this
        codeAreas.put(name, area); // Very important, or the area won't be found in showText and getTextOf
        Tab tab = new Tab(name);
        tab.setContent(area);
        getTabs().add(tab);
        return area;
    }

    @Override
    public String getTextOf(String name) {
        TextArea area = codeAreas.get(name);
        return area == null ? "" : area.getText();
    }

    private final IntegerProperty editNumber = new SimpleIntegerProperty(this, "Edit number", 0);
    @Override
    public IntegerProperty editNumber() {
        return editNumber;
    }
}
