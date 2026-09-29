package com.moni.app.walking;

/** Horizontal movement direction for an automatic walk. */
public enum WalkDirection {
    LEFT(-1),
    RIGHT(1);

    private final int horizontalSign;

    WalkDirection(int horizontalSign) {
        this.horizontalSign = horizontalSign;
    }

    public int horizontalSign() {
        return horizontalSign;
    }
}
