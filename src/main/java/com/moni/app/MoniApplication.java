package com.moni.app;

import javafx.application.Application;
import javafx.application.Platform;
import com.moni.app.animation.IdleSpriteAnimator;
import com.moni.app.animation.GrabSpriteAnimator;
import com.moni.app.animation.WalkSpriteAnimator;
import com.moni.app.interaction.MoniDragController;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

/** Entry point for the Moni desktop application. */
public final class MoniApplication extends Application {
    private static final String IDLE_SPRITE_RESOURCE = "/moni/moni_idle.png";
    private static final String GRAB_SPRITE_RESOURCE = "/moni/moni_grab.png";
    private static final String WALK_LEFT_SPRITE_RESOURCE = "/moni/moni_walk_left.png";
    private static final String WALK_RIGHT_SPRITE_RESOURCE = "/moni/moni_walk_right.png";
    private static final double FRAME_WIDTH = 128;
    private static final double FRAME_HEIGHT = 160;
    private static final double SCREEN_MARGIN = 24;

    private MoniDragController dragController;

    @Override
    public void start(Stage stage) {
        Image idleImage = loadSprite(IDLE_SPRITE_RESOURCE);
        Image grabImage = loadSprite(GRAB_SPRITE_RESOURCE);
        Image walkLeftImage = loadSprite(WALK_LEFT_SPRITE_RESOURCE);
        Image walkRightImage = loadSprite(WALK_RIGHT_SPRITE_RESOURCE);
        var imageView = new ImageView(idleImage);
        imageView.setSmooth(false);
        var idleAnimator = new IdleSpriteAnimator(imageView);
        var grabAnimator = new GrabSpriteAnimator(imageView);
        var walkAnimator = new WalkSpriteAnimator(imageView);

        var scene = new Scene(new Group(imageView), FRAME_WIDTH, FRAME_HEIGHT, Color.TRANSPARENT);
        dragController = new MoniDragController(
                stage,
                imageView,
                idleImage,
                grabImage,
                walkLeftImage,
                walkRightImage,
                idleAnimator,
                grabAnimator,
                walkAnimator
        );
        dragController.install(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.setOnHidden(event -> dragController.stop());
        stage.show();
        dragController.start();
        Platform.runLater(() -> positionInBottomRight(stage));
    }

    @Override
    public void stop() {
        if (dragController != null) {
            dragController.stop();
        }
    }

    private static Image loadSprite(String resourcePath) {
        URL resource = MoniApplication.class.getResource(resourcePath);
        if (resource == null) {
            throw new IllegalStateException(
                    "Required sprite is missing from the classpath: " + resourcePath
                            + ". Ensure the root assets directory is included in main resources."
            );
        }

        Image image = new Image(resource.toExternalForm());
        if (image.isError()) {
            throw new IllegalStateException(
                    "Failed to load sprite from classpath resource " + resourcePath,
                    image.getException()
            );
        }
        return image;
    }

    private static void positionInBottomRight(Stage stage) {
        Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(visualBounds.getMaxX() - stage.getWidth() - SCREEN_MARGIN);
        stage.setY(visualBounds.getMaxY() - stage.getHeight() - SCREEN_MARGIN);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
