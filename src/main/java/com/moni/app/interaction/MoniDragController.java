package com.moni.app.interaction;

import java.util.Objects;

import com.moni.app.animation.SpriteAnimator;
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
    private final SpriteAnimator idleAnimator;
    private final SpriteAnimator grabAnimator;
    private final DragStateMachine stateMachine = new DragStateMachine();

    public MoniDragController(
            Stage stage,
            ImageView imageView,
            Image idleImage,
            Image grabImage,
            SpriteAnimator idleAnimator,
            SpriteAnimator grabAnimator
    ) {
        this.stage = Objects.requireNonNull(stage, "stage must not be null");
        this.imageView = Objects.requireNonNull(imageView, "imageView must not be null");
        this.idleImage = Objects.requireNonNull(idleImage, "idleImage must not be null");
        this.grabImage = Objects.requireNonNull(grabImage, "grabImage must not be null");
        this.idleAnimator = Objects.requireNonNull(idleAnimator, "idleAnimator must not be null");
        this.grabAnimator = Objects.requireNonNull(grabAnimator, "grabAnimator must not be null");
    }

    public void install(Scene scene) {
        imageView.addEventHandler(MouseEvent.MOUSE_PRESSED, this::handleMousePressed);
        scene.addEventFilter(MouseEvent.MOUSE_DRAGGED, this::handleMouseDragged);
        scene.addEventFilter(MouseEvent.MOUSE_RELEASED, this::handleMouseReleased);
    }

    public void stop() {
        idleAnimator.stop();
        grabAnimator.stop();
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

        idleAnimator.stop();
        imageView.setImage(grabImage);
        grabAnimator.start();
        moveStage(event);
        event.consume();
    }

    private void handleMouseDragged(MouseEvent event) {
        if (stateMachine.state() != DragStateMachine.State.GRABBING) {
            return;
        }

        moveStage(event);
        event.consume();
    }

    private void handleMouseReleased(MouseEvent event) {
        if (event.getButton() != MouseButton.PRIMARY || !stateMachine.releaseToIdle()) {
            return;
        }

        grabAnimator.stop();
        imageView.setImage(idleImage);
        idleAnimator.start();
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

    record StagePosition(double x, double y) {
    }
}
