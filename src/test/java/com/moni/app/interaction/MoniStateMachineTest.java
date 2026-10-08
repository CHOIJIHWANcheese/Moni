package com.moni.app.interaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

    @Test
    void idleTransitionsToSleeping() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginSleeping());
        assertEquals(MoniStateMachine.State.SLEEPING, stateMachine.state());
    }

    @Test
    void walkingTransitionsToSleeping() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginWalking(WalkDirection.RIGHT));
        assertTrue(stateMachine.beginSleeping());
        assertEquals(MoniStateMachine.State.SLEEPING, stateMachine.state());
    }

    @Test
    void sleepingTransitionsThroughGrabbingToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        stateMachine.beginSleeping();
        assertTrue(stateMachine.beginGrabbing());
        assertTrue(stateMachine.releaseGrabToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }

    @Test
    void idleTransitionsThroughTalkingToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginTalking());
        assertEquals(MoniStateMachine.State.TALKING, stateMachine.state());
        assertTrue(stateMachine.finishTalkingToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }

    @Test
    void walkingLeftTransitionsThroughTalkingToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginWalking(WalkDirection.LEFT));
        assertTrue(stateMachine.beginTalking());
        assertEquals(MoniStateMachine.State.TALKING, stateMachine.state());
        assertTrue(stateMachine.finishTalkingToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }

    @Test
    void walkingRightTransitionsThroughTalkingToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginWalking(WalkDirection.RIGHT));
        assertTrue(stateMachine.beginTalking());
        assertEquals(MoniStateMachine.State.TALKING, stateMachine.state());
        assertTrue(stateMachine.finishTalkingToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }

    @Test
    void sleepingTransitionsThroughTalkingToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginSleeping());
        assertTrue(stateMachine.beginTalking());
        assertEquals(MoniStateMachine.State.TALKING, stateMachine.state());
        assertTrue(stateMachine.finishTalkingToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }

    @Test
    void talkingAcceptsMessageReplacementWithoutLeavingTalking() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginTalking());
        assertTrue(stateMachine.beginTalking());
        assertEquals(MoniStateMachine.State.TALKING, stateMachine.state());
    }

    @Test
    void grabbingRejectsTalkingWithoutInterruptingTheDrag() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        assertTrue(stateMachine.beginGrabbing());
        assertFalse(stateMachine.beginTalking());
        assertEquals(MoniStateMachine.State.GRABBING, stateMachine.state());
    }

    @Test
    void talkingTransitionsThroughGrabbingToIdle() {
        MoniStateMachine stateMachine = new MoniStateMachine();

        stateMachine.beginTalking();
        assertTrue(stateMachine.beginGrabbing());
        assertTrue(stateMachine.releaseGrabToIdle());
        assertEquals(MoniStateMachine.State.IDLE, stateMachine.state());
    }
}
