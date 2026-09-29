package com.moni.app.interaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.moni.app.walking.WalkDirection;
import org.junit.jupiter.api.Test;

class MoniStateMachineTest {

    @Test
    void walkingReturnsToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginWalking(WalkDirection.LEFT));
        assertEquals(MoniStateMachine.State.WALKING_LEFT, stateMachine.state());
        assertTrue(stateMachine.finishWalkingToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }

    @Test
    void primaryPressDuringWalkingTransitionsThroughGrabbingToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginWalking(WalkDirection.RIGHT));
        assertTrue(stateMachine.beginGrabbing());
        assertEquals(MoniStateMachine.State.GRABBING, stateMachine.state());
        assertTrue(stateMachine.releaseGrabToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }
}
