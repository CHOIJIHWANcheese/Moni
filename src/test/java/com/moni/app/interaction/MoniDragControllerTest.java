package com.moni.app.interaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MoniDragControllerTest {

    @Test
    void stagePositionUsesMouseScreenCoordinatesAndAnchor() {
        MoniDragController.StagePosition position = MoniDragController.calculateStagePosition(
                1500,
                900,
                64,
                30
        );

        assertEquals(1436, position.x());
        assertEquals(870, position.y());
    }
}
