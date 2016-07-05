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

    public MessageDisplay getMessageDisplay() {
        return messages;
    }

    public PhaseDisplay getPhaseDisplay() {
        return phases;
    }

    public ErrorDisplay getErrorDisplay() {
        return errors;
    }

    public TimeDisplay getTimeDisplay() {
        return time;
    }

}
