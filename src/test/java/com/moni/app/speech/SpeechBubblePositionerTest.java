package com.moni.app.speech;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.Test;

class SpeechBubblePositionerTest {
    private static final Rectangle2D BOUNDS = new Rectangle2D(0, 0, 1000, 800);

    @Test
    void usesLeftPlacementAtTheRightScreenEdge() {
        SpeechBubblePosition position = SpeechBubblePositioner.position(872, 300, 128, 160, 220, 110, BOUNDS);

        assertEquals(SpeechBubblePosition.Placement.LEFT, position.placement());
        assertEquals(646, position.x());
    }

    @Test
    void usesRightPlacementAtTheLeftScreenEdge() {
        SpeechBubblePosition position = SpeechBubblePositioner.position(0, 300, 128, 160, 220, 110, BOUNDS);

        assertEquals(SpeechBubblePosition.Placement.RIGHT, position.placement());
        assertEquals(134, position.x());
    }

    @Test
    void usesBelowPlacementWhenThereIsNoRoomAbove() {
        SpeechBubblePosition position = SpeechBubblePositioner.position(400, 0, 128, 160, 220, 110, BOUNDS);

        assertEquals(SpeechBubblePosition.Placement.BELOW, position.placement());
        assertEquals(166, position.y());
    }

    @Test
    void alwaysKeepsTheBubbleInsideVisualBounds() {
        SpeechBubblePosition position = SpeechBubblePositioner.position(990, 790, 128, 160, 220, 110, BOUNDS);

        assertTrue(position.x() >= BOUNDS.getMinX());
        assertTrue(position.y() >= BOUNDS.getMinY());
        assertTrue(position.x() + 220 <= BOUNDS.getMaxX());
        assertTrue(position.y() + 110 <= BOUNDS.getMaxY());
    }
}
