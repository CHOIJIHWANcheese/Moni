package com.moni.app.animation;

import java.util.Objects;

import javafx.scene.image.ImageView;

/** Plays either direction of the looping walk sprite sheet. */
public final class WalkSpriteAnimator implements SpriteAnimator {
    private static final int FRAME_WIDTH = 128;
    private static final int FRAME_HEIGHT = 160;
    private static final int[] FRAME_DURATIONS_MILLIS = {120, 120, 120, 120};

    private final SpriteSheetAnimator animator;

    public WalkSpriteAnimator(ImageView imageView) {
        animator = new SpriteSheetAnimator(
                Objects.requireNonNull(imageView, "imageView must not be null"),
                FRAME_WIDTH,
                FRAME_HEIGHT,
                FRAME_DURATIONS_MILLIS,
                0
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
