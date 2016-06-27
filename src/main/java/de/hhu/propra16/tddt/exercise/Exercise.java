package de.hhu.propra16.tddt.exercise;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

/**
 * Created by felix on 6/27/16.
 */
public class Exercise {

    public SourceCode getSources() {
        return sources;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    private final SourceCode sources;
    private final String name;
    private final String description;

    public Exercise(SourceCode sources, String name, String description) {
        this.sources = sources;
        this.name = name;
        this.description = description;
    }
}
