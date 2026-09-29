package com.moni.app.interaction;

import com.moni.app.walking.WalkDirection;

/** Single explicit state model for Moni's idle, grab, and walk behavior. */
final class MoniStateMachine {
    enum State {
        IDLE,
        GRABBING,
        WALKING_LEFT,
        WALKING_RIGHT,
        SLEEPING
    }

    private State state = State.IDLE;

    State state() {
        return state;
    }

    boolean beginWalking(WalkDirection direction) {
        if (state != State.IDLE) {
            return false;
        }
        state = direction == WalkDirection.LEFT ? State.WALKING_LEFT : State.WALKING_RIGHT;
        return true;
    }

    boolean beginGrabbing() {
        if (state == State.GRABBING) {
            return false;
        }
        state = State.GRABBING;
        return true;
    }

    boolean beginSleeping() {
        if (state != State.IDLE && state != State.WALKING_LEFT && state != State.WALKING_RIGHT) {
            return false;
        }
        state = State.SLEEPING;
        return true;
    }

    boolean releaseGrabToIdle() {
        if (state != State.GRABBING) {
            return false;
        }
        state = State.IDLE;
        return true;
    }

    boolean finishWalkingToIdle() {
        if (state != State.WALKING_LEFT && state != State.WALKING_RIGHT) {
            return false;
        }
        state = State.IDLE;
        return true;
    }

    void resetToIdle() {
        state = State.IDLE;
    }
}
