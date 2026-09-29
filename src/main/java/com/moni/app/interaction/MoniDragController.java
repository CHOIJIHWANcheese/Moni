package com.moni.app.interaction;

import java.util.Objects;
import java.util.Random;
import java.time.Duration;

import com.moni.app.animation.SpriteAnimator;
import com.moni.app.sleep.SleepController;
import com.moni.app.walking.RandomIntSource;
import com.moni.app.walking.WalkController;
import com.moni.app.walking.WalkDirection;
import com.moni.app.walking.WalkPlan;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/** Handles primary-button grabbing and dragging of the Moni stage. */
public final class MoniDragController {
    private static final double MOUSE_ANCHOR_X = 64;
    private static final double MOUSE_ANCHOR_Y = 30;

    private final Stage stage;
    private final ImageView imageView;
    private final Image idleImage;
    private final Image grabImage;
    private final Image walkLeftImage;
    private final Image walkRightImage;
    private final Image sleepImage;
    private final SpriteAnimator idleAnimator;
    private final SpriteAnimator grabAnimator;
    private final SpriteAnimator walkAnimator;
    private final SpriteAnimator sleepAnimator;
    private final MoniStateMachine stateMachine = new MoniStateMachine();
    private final WalkController walkController;
    private final SleepController sleepController;

    public MoniDragController(
            Stage stage,
            ImageView imageView,
            Image idleImage,
            Image grabImage,
            Image walkLeftImage,
            Image walkRightImage,
            Image sleepImage,
            SpriteAnimator idleAnimator,
            SpriteAnimator grabAnimator,
            SpriteAnimator walkAnimator,
            SpriteAnimator sleepAnimator,
            Duration sleepAfter
    ) {
        this.stage = Objects.requireNonNull(stage, "stage must not be null");
        this.imageView = Objects.requireNonNull(imageView, "imageView must not be null");
        this.idleImage = Objects.requireNonNull(idleImage, "idleImage must not be null");
        this.grabImage = Objects.requireNonNull(grabImage, "grabImage must not be null");
        this.walkLeftImage = Objects.requireNonNull(walkLeftImage, "walkLeftImage must not be null");
        this.walkRightImage = Objects.requireNonNull(walkRightImage, "walkRightImage must not be null");
        this.sleepImage = Objects.requireNonNull(sleepImage, "sleepImage must not be null");
        this.idleAnimator = Objects.requireNonNull(idleAnimator, "idleAnimator must not be null");
        this.grabAnimator = Objects.requireNonNull(grabAnimator, "grabAnimator must not be null");
        this.walkAnimator = Objects.requireNonNull(walkAnimator, "walkAnimator must not be null");
        this.sleepAnimator = Objects.requireNonNull(sleepAnimator, "sleepAnimator must not be null");
        RandomIntSource random = new Random()::nextInt;
        walkController = new WalkController(stage, random, this::beginWalking, this::finishWalking);
        sleepController = new SleepController(Objects.requireNonNull(sleepAfter, "sleepAfter must not be null"), this::beginSleeping);
    }

    public void start() {
        showIdle();
        walkController.scheduleNextWalk();
        sleepController.start();
    }

    public void install(Scene scene) {
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }

    public void stop() {
        walkController.stop();
        sleepController.stop();
        stopAllAnimations();
        stateMachine.resetToIdle();
    }

    static StagePosition calculateStagePosition(
            double mouseScreenX,
            double mouseScreenY,
            double mouseAnchorX,
            double mouseAnchorY
    ) {
        return new StagePosition(mouseScreenX - mouseAnchorX, mouseScreenY - mouseAnchorY);
    }

    private void handleMousePressed(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY || !stateMachine.beginGrabbing()) {
            return;
        }

        sleepController.recordPrimaryInteraction();
        walkController.stop();
        stopAllAnimations();
        imageView.setImage(grabImage);
        grabAnimator.start();
        moveStage(event);
        event.consume();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (stateMachine.state() != MoniStateMachine.State.GRABBING) {
            return;
        }

        moveStage(event);
        event.consume();
    }

    private void handleMouseReleased(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY || !stateMachine.releaseGrabToIdle()) {
            return;
        }

        showIdle();
        walkController.scheduleNextWalk();
        sleepController.restartAfterInteraction();
        event.consume();
    }

    private void moveStage(MouseEvent event) {
        StagePosition position = calculateStagePosition(
                event.getScreenX(),
                event.getScreenY(),
                MOUSE_ANCHOR_X,
                MOUSE_ANCHOR_Y
        );
        stage.setX(position.x());
        stage.setY(position.y());
    }

    private boolean beginWalking(WalkPlan plan) {
        if (!stateMachine.beginWalking(plan.direction())) {
            return false;
        }
        stopAllAnimations();
        imageView.setImage(plan.direction() == WalkDirection.LEFT ? walkLeftImage : walkRightImage);
        walkAnimator.start();
        return true;
    }

    private void finishWalking() {
        if (stateMachine.finishWalkingToIdle()) {
            showIdle();
        }
    }

    private void beginSleeping() {
        if (!stateMachine.beginSleeping()) {
            return;
        }
        walkController.stop();
        stopAllAnimations();
        imageView.setImage(sleepImage);
        sleepAnimator.start();
    }

    private void showIdle() {
        stopAllAnimations();
        imageView.setImage(idleImage);
        idleAnimator.start();
    }

    private void stopAllAnimations() {
        idleAnimator.stop();
        grabAnimator.stop();
        walkAnimator.stop();
        sleepAnimator.stop();
    }

    record StagePosition(double x, double y) {
    }
}
