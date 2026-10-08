package com.moni.app.speech;

/** An integer popup position and its chosen relation to the Moni stage. */
public record SpeechBubblePosition(int x, int y, Placement placement) {
    public enum Placement {
        ABOVE,
        LEFT,
        RIGHT,
        BELOW
    }
}
