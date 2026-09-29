package com.moni.app.interaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DragStateMachineTest {

    @Test
    void releaseReturnsGrabbingStateToIdle() {
        DragStateMachine stateMachine = new DragStateMachine();

        assertTrue(stateMachine.beginGrabbing());
        assertEquals(DragStateMachine.State.GRABBING, stateMachine.state());
        assertTrue(stateMachine.releaseToIdle());
        assertEquals(DragStateMachine.State.IDLE, stateMachine.state());
        assertFalse(stateMachine.releaseToIdle());
    }
}
