package de.hhu.propra16.tddt.userinterface;

public class DisplayerGroup {

    private final MessageDisplay messages;
    private final PhaseDisplay phases;

    public DisplayerGroup(MessageDisplay messages, PhaseDisplay phases) {
        this.messages = messages;
        this.phases = phases;
    }

    public MessageDisplay messageDisplay() {
        return messages;
    }

    public PhaseDisplay PhaseDisplay() {
        return phases;
    }

}
