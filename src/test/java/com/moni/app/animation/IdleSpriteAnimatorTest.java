package com.moni.app.animation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class IdleSpriteAnimatorTest {

    @Test
    void nextFrameIndexAdvancesAndLoopsToTheFirstFrame() {
        assertEquals(1, SpriteSheetAnimator.nextFrameIndex(0, 4, 0));
        assertEquals(2, SpriteSheetAnimator.nextFrameIndex(1, 4, 0));
        assertEquals(3, SpriteSheetAnimator.nextFrameIndex(2, 4, 0));
        assertEquals(0, SpriteSheetAnimator.nextFrameIndex(3, 4, 0));
    }

    @Test
    void nextFrameIndexRejectsInvalidFrameValues() {
        assertThrows(IllegalArgumentException.class, () -> SpriteSheetAnimator.nextFrameIndex(-1, 4, 0));
        assertThrows(IllegalArgumentException.class, () -> SpriteSheetAnimator.nextFrameIndex(4, 4, 0));
        assertThrows(IllegalArgumentException.class, () -> SpriteSheetAnimator.nextFrameIndex(0, 0, 0));
    }
}
