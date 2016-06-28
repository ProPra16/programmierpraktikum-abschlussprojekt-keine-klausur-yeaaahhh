package de.hhu.propra16.tddt.exercise;

import de.hhu.propra16.tddt.sourcecode.SourceCode;

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
    private final Options options;

    Exercise(String name, String description, SourceCode sources, Options options) {
        this.sources = sources;
        this.name = name;
        this.description = description;
        this.options = options;
    }
}
