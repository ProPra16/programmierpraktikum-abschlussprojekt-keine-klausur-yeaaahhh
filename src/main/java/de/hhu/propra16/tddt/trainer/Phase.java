package de.hhu.propra16.tddt.trainer;

public enum Phase {
    RED, GREEN, BLACK;

    private Phase next, previous;

    static {
        RED.next = GREEN;
        GREEN.next = BLACK;
        BLACK.next = RED;

        RED.previous = BLACK;
        GREEN.previous = RED;
        BLACK.previous = GREEN;
    }

    public Phase next() {
        return next;
    }

    public Phase previous() {
        return previous;
    }
}
