package com.moni.app.interaction;

/** Pure state transitions for the character drag interaction. */
final class DragStateMachine {
    enum State {
        IDLE,
        GRABBING
    }

    private State state = State.IDLE;

    State state() {
        return state;
    }

    boolean beginGrabbing() {
        if (state != State.IDLE) {
            return false;
        }
        state = State.GRABBING;
        return true;
    }

    boolean releaseToIdle() {
        if (state != State.GRABBING) {
            return false;
        }
        state = State.IDLE;
        return true;
    }

    void resetToIdle() {
        state = State.IDLE;
    }
}
