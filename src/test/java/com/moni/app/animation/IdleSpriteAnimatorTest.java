package com.moni.app.animation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class IdleSpriteAnimatorTest {

    @Test
    void nextFrameIndexAdvancesAndLoopsToTheFirstFrame() {
        assertEquals(1, IdleSpriteAnimator.nextFrameIndex(0, 4));
        assertEquals(2, IdleSpriteAnimator.nextFrameIndex(1, 4));
        assertEquals(3, IdleSpriteAnimator.nextFrameIndex(2, 4));
        assertEquals(0, IdleSpriteAnimator.nextFrameIndex(3, 4));
    }

    @Test
    void nextFrameIndexRejectsInvalidFrameValues() {
        assertThrows(IllegalArgumentException.class, () -> IdleSpriteAnimator.nextFrameIndex(-1, 4));
        assertThrows(IllegalArgumentException.class, () -> IdleSpriteAnimator.nextFrameIndex(4, 4));
        assertThrows(IllegalArgumentException.class, () -> IdleSpriteAnimator.nextFrameIndex(0, 0));
    }
}
