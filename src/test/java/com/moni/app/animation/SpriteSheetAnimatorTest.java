package com.moni.app.animation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SpriteSheetAnimatorTest {

    @Test
    void grabHoldFramesLoopFromTheConfiguredHoldFrame() {
        assertEquals(2, SpriteSheetAnimator.nextFrameIndex(1, 4, 1));
        assertEquals(3, SpriteSheetAnimator.nextFrameIndex(2, 4, 1));
        assertEquals(1, SpriteSheetAnimator.nextFrameIndex(3, 4, 1));
    }
}
