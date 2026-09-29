package com.moni.app.animation;

import java.util.Objects;

import javafx.scene.image.ImageView;

/** Plays the grab start frame once, then loops its hold frames. */
public final class GrabSpriteAnimator implements SpriteAnimator {
    private static final int FRAME_WIDTH = 128;
    private static final int FRAME_HEIGHT = 160;
    private static final int HOLD_LOOP_FROM_FRAME = 1;
    private static final int[] FRAME_DURATIONS_MILLIS = {120, 180, 180, 180};

    private final SpriteSheetAnimator animator;

    public GrabSpriteAnimator(ImageView imageView) {
        animator = new SpriteSheetAnimator(
                Objects.requireNonNull(imageView, "imageView must not be null"),
                FRAME_WIDTH,
                FRAME_HEIGHT,
                FRAME_DURATIONS_MILLIS,
                HOLD_LOOP_FROM_FRAME
        );
    }

    @Override
    public void start() {
        animator.start();
    }

    @Override
    public void stop() {
        animator.stop();
    }
}
