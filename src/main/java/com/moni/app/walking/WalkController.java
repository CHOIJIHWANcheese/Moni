package com.moni.app.walking;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/** Schedules and moves a stage through bounded automatic walks. */
public final class WalkController {
    public static final double SPEED_PX_PER_SECOND = 40;
    public static final int MIN_IDLE_DELAY_MILLIS = 4_000;
    public static final int MAX_IDLE_DELAY_MILLIS = 8_000;

    private final Stage stage;
    private final RandomIntSource random;
    private final Predicate<WalkPlan> walkStarted;
    private final Runnable walkFinished;
    private final PauseTransition idleDelay = new PauseTransition();
    private final AnimationTimer movementTimer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            advanceWalk(now);
        }
    };

    private WalkPlan activePlan;
    private long previousFrameNanos;

    public WalkController(
            Stage stage,
            RandomIntSource random,
            Predicate<WalkPlan> walkStarted,
            Runnable walkFinished
    ) {
        this.stage = Objects.requireNonNull(stage, "stage must not be null");
        this.random = Objects.requireNonNull(random, "random must not be null");
        this.walkStarted = Objects.requireNonNull(walkStarted, "walkStarted must not be null");
        this.walkFinished = Objects.requireNonNull(walkFinished, "walkFinished must not be null");
        idleDelay.setOnFinished(event -> beginWalk());
    }

    public void scheduleNextWalk() {
        stop();
        idleDelay.setDuration(Duration.millis(
                MIN_IDLE_DELAY_MILLIS + random.nextInt(MAX_IDLE_DELAY_MILLIS - MIN_IDLE_DELAY_MILLIS + 1)
        ));
        idleDelay.playFromStart();
    }

    public void stop() {
        idleDelay.stop();
        movementTimer.stop();
        activePlan = null;
        previousFrameNanos = 0;
    }

    private void beginWalk() {
        Rectangle2D visualBounds = currentScreen().getVisualBounds();
        WalkPlanner.createPlan(
                stage.getX(),
                stage.getWidth(),
                visualBounds.getMinX(),
                visualBounds.getMaxX(),
                random
        ).ifPresentOrElse(plan -> {
            if (!walkStarted.test(plan)) {
                scheduleNextWalk();
                return;
            }
            activePlan = plan;
            stage.setX(plan.startX());
            previousFrameNanos = 0;
            movementTimer.start();
        }, this::scheduleNextWalk);
    }

    private void advanceWalk(long now) {
        if (activePlan == null) {
            movementTimer.stop();
            return;
        }
        if (previousFrameNanos == 0) {
            previousFrameNanos = now;
            return;
        }

        double elapsedSeconds = (now - previousFrameNanos) / 1_000_000_000.0;
        previousFrameNanos = now;
        double nextX = stage.getX() + activePlan.direction().horizontalSign() * SPEED_PX_PER_SECOND * elapsedSeconds;
        boolean reachedTarget = activePlan.direction() == WalkDirection.LEFT
                ? nextX <= activePlan.targetX()
                : nextX >= activePlan.targetX();
        stage.setX(reachedTarget ? activePlan.targetX() : nextX);
        if (reachedTarget) {
            movementTimer.stop();
            activePlan = null;
            previousFrameNanos = 0;
            walkFinished.run();
            scheduleNextWalk();
        }
    }

    private Screen currentScreen() {
        List<Screen> screens = Screen.getScreensForRectangle(
                stage.getX(),
                stage.getY(),
                Math.max(stage.getWidth(), 1),
                Math.max(stage.getHeight(), 1)
        );
        return screens.isEmpty() ? Screen.getPrimary() : screens.getFirst();
    }
}
