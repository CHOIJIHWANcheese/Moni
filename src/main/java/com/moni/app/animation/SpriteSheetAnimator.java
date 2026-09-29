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

/** Reusable viewport-based animation for a horizontal sprite sheet. */
public final class SpriteSheetAnimator implements SpriteAnimator {
    private final ImageView imageView;
    private final int frameWidth;
    private final int frameHeight;
    private final int[] frameDurationsMillis;
    private final int loopFromFrame;
    private final Timeline introTimeline;
    private final Timeline loopTimeline;

    public SpriteSheetAnimator(
            ImageView imageView,
            int frameWidth,
            int frameHeight,
            int[] frameDurationsMillis,
            int loopFromFrame
    ) {
        this.imageView = Objects.requireNonNull(imageView, "imageView must not be null");
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.frameDurationsMillis = frameDurationsMillis.clone();
        this.loopFromFrame = loopFromFrame;
        validateConfiguration();
        loopTimeline = createLoopTimeline(loopFromFrame);
        introTimeline = loopFromFrame == 0 ? null : createIntroTimeline();
        showFrame(0);
    }

    @Override
    public void start() {
        stop();
        showFrame(0);
        if (introTimeline == null) {
            loopTimeline.playFromStart();
        } else {
            introTimeline.playFromStart();
        }
    }

    @Override
    public void stop() {
        if (introTimeline != null) {
            introTimeline.stop();
        }
        loopTimeline.stop();
    }

    static int nextFrameIndex(int frameIndex, int frameCount, int loopFromFrame) {
        if (frameCount <= 0) {
            throw new IllegalArgumentException("frameCount must be greater than zero");
        }
        if (loopFromFrame < 0 || loopFromFrame >= frameCount) {
            throw new IllegalArgumentException("loopFromFrame must be within the frame count");
        }
        if (frameIndex < 0 || frameIndex >= frameCount) {
            throw new IllegalArgumentException("frameIndex must be within the frame count");
        }
        return frameIndex == frameCount - 1 ? loopFromFrame : frameIndex + 1;
    }

    private Timeline createIntroTimeline() {
        return new Timeline(new KeyFrame(
                Duration.millis(frameDurationsMillis[0]),
                event -> {
                    showFrame(loopFromFrame);
                    loopTimeline.playFromStart();
                }
        ));
    }

    private Timeline createLoopTimeline(int startFrame) {
        List<KeyFrame> keyFrames = new ArrayList<>();
        Duration elapsed = Duration.ZERO;
        int frameIndex = startFrame;
        int transitions = frameDurationsMillis.length - loopFromFrame;

        for (int transition = 0; transition < transitions; transition++) {
            elapsed = elapsed.add(Duration.millis(frameDurationsMillis[frameIndex]));
            frameIndex = nextFrameIndex(frameIndex, frameDurationsMillis.length, loopFromFrame);
            int nextFrame = frameIndex;
            keyFrames.add(new KeyFrame(elapsed, event -> showFrame(nextFrame)));
        }

        Timeline timeline = new Timeline(keyFrames.toArray(KeyFrame[]::new));
        timeline.setCycleCount(Animation.INDEFINITE);
        return timeline;
    }

    private void showFrame(int frameIndex) {
        imageView.setViewport(new Rectangle2D(
                frameIndex * frameWidth,
                0,
                frameWidth,
                frameHeight
        ));
    }

    private void validateConfiguration() {
        if (frameWidth <= 0 || frameHeight <= 0) {
            throw new IllegalArgumentException("frame dimensions must be greater than zero");
        }
        if (frameDurationsMillis.length == 0) {
            throw new IllegalArgumentException("at least one frame duration is required");
        }
        if (loopFromFrame < 0 || loopFromFrame >= frameDurationsMillis.length) {
            throw new IllegalArgumentException("loopFromFrame must be within the frame count");
        }
        for (int duration : frameDurationsMillis) {
            if (duration <= 0) {
                throw new IllegalArgumentException("frame durations must be greater than zero");
            }
        }
    }
}
