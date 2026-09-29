package com.moni.app.animation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/** Plays the looping idle frames declared in {@code moni_sprites.json}. */
public final class IdleSpriteAnimator {
    private static final int FRAME_WIDTH = 128;
    private static final int FRAME_HEIGHT = 160;
    private static final int FRAME_COUNT = 4;
    private static final int[] FRAME_DURATIONS_MILLIS = {360, 360, 240, 360};

    private final ImageView imageView;
    private final Timeline timeline;

    public IdleSpriteAnimator(ImageView imageView) {
        this.imageView = Objects.requireNonNull(imageView, "imageView must not be null");
        this.timeline = createTimeline();
        showFrame(0);
    }

    public void start() {
        showFrame(0);
        timeline.playFromStart();
    }

    public void stop() {
        timeline.stop();
    }

    static int nextFrameIndex(int frameIndex, int frameCount) {
        if (frameCount <= 0) {
            throw new IllegalArgumentException("frameCount must be greater than zero");
        }
        if (frameIndex < 0 || frameIndex >= frameCount) {
            throw new IllegalArgumentException("frameIndex must be within the frame count");
        }
        return (frameIndex + 1) % frameCount;
    }

    private Timeline createTimeline() {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration elapsed = Duration.ZERO;
        int frameIndex = 0;

        for (int transition = 0; transition < FRAME_COUNT; transition++) {
            elapsed = elapsed.add(Duration.millis(FRAME_DURATIONS_MILLIS[frameIndex]));
            frameIndex = nextFrameIndex(frameIndex, FRAME_COUNT);
            int nextFrame = frameIndex;
            keyFrames.add(new KeyFrame(elapsed, event -> showFrame(nextFrame)));
        }

        Timeline idleTimeline = new Timeline(keyFrames.toArray(KeyFrame[]::new));
        idleTimeline.setCycleCount(Animation.INDEFINITE);
        return idleTimeline;
    }

    private void showFrame(int frameIndex) {
        imageView.setViewport(new Rectangle2D(
                frameIndex * FRAME_WIDTH,
                0,
                FRAME_WIDTH,
                FRAME_HEIGHT
        ));
    }
}
