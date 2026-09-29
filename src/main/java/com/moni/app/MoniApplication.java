package com.moni.app;

import javafx.application.Application;
import javafx.application.Platform;
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
    private static final double FRAME_WIDTH = 128;
    private static final double FRAME_HEIGHT = 160;
    private static final double SCREEN_MARGIN = 24;

    @Override
    public void start(Stage stage) {
        var imageView = new ImageView(loadIdleSprite());
        imageView.setViewport(new Rectangle2D(0, 0, FRAME_WIDTH, FRAME_HEIGHT));
        imageView.setSmooth(false);

        var scene = new Scene(new Group(imageView), FRAME_WIDTH, FRAME_HEIGHT, Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setAlwaysOnTop(true);
        stage.show();
        Platform.runLater(() -> positionInBottomRight(stage));
    }

    private static Image loadIdleSprite() {
        URL resource = MoniApplication.class.getResource(IDLE_SPRITE_RESOURCE);
        if (resource == null) {
            throw new IllegalStateException(
                    "Required idle sprite is missing from the classpath: " + IDLE_SPRITE_RESOURCE
                            + ". Ensure the root assets directory is included in main resources."
            );
        }

        Image image = new Image(resource.toExternalForm());
        if (image.isError()) {
            throw new IllegalStateException(
                    "Failed to load idle sprite from classpath resource " + IDLE_SPRITE_RESOURCE,
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
