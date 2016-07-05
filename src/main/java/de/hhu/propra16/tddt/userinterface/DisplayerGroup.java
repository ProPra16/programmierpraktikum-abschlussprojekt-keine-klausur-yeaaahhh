package de.hhu.propra16.tddt.userinterface;

public class DisplayerGroup {

    private final MessageDisplay messages;
    private final PhaseDisplay phases;
    private final ErrorDisplay errors;
    private final TimeDisplay time;

    public DisplayerGroup(MessageDisplay messages, PhaseDisplay phases, ErrorDisplay errors, TimeDisplay time) {
        this.messages = messages;
        this.phases = phases;
        this.errors = errors;
        this.time = time;
    }

    public MessageDisplay messageDisplay() {
        return messages;
    }

    public PhaseDisplay phaseDisplay() {
        return phases;
    }

    public ErrorDisplay errorDisplay() {
        return errors;
    }

    public TimeDisplay timeDisplay() {
        return time;
    }
}
