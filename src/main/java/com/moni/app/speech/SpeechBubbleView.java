package com.moni.app.speech;

import java.net.URL;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/** Pixel-styled, output-only speech bubble content for a transparent Popup. */
public final class SpeechBubbleView extends Group {
    public static final int WIDTH = 220;
    public static final int HEIGHT = 110;
    private static final String BACKGROUND_RESOURCE = "/ui/moni_speech_bubble.png";
    private static final int MAX_MESSAGE_LENGTH = 160;
    private static final int TEXT_LEFT = 22;
    private static final int TEXT_TOP = 18;
    private static final int TEXT_RIGHT = 22;
    private static final int TEXT_BOTTOM = 30;
    private static final Color TEXT_COLOR = Color.rgb(54, 43, 34);

    private final ImageView background = new ImageView(loadBackground());
    private final Label label = new Label();

    public SpeechBubbleView() {
        background.setFitWidth(WIDTH);
        background.setFitHeight(HEIGHT);
        background.setPreserveRatio(false);
        background.setSmooth(false);

        label.setLayoutX(TEXT_LEFT);
        label.setLayoutY(TEXT_TOP);
        label.setPrefWidth(WIDTH - TEXT_LEFT - TEXT_RIGHT);
        label.setMaxWidth(WIDTH - TEXT_LEFT - TEXT_RIGHT);
        label.setPrefHeight(HEIGHT - TEXT_TOP - TEXT_BOTTOM);
        label.setMaxHeight(HEIGHT - TEXT_TOP - TEXT_BOTTOM);
        label.setWrapText(true);
        label.setTextOverrun(OverrunStyle.ELLIPSIS);
        label.setTextFill(TEXT_COLOR);
        label.setFont(Font.font("Malgun Gothic", 14));
        getChildren().addAll(background, label);
    }

    public void setMessage(String message) {
        String safeMessage = message == null ? "" : message;
        if (safeMessage.length() > MAX_MESSAGE_LENGTH) {
            safeMessage = safeMessage.substring(0, MAX_MESSAGE_LENGTH - 1) + "…";
        }
        label.setText(safeMessage);
    }

    public void setPlacement(SpeechBubblePosition.Placement placement) {
        boolean flipHorizontally = placement == SpeechBubblePosition.Placement.RIGHT;
        boolean flipVertically = placement == SpeechBubblePosition.Placement.BELOW;
        background.setScaleX(flipHorizontally ? -1 : 1);
        background.setTranslateX(flipHorizontally ? WIDTH : 0);
        background.setScaleY(flipVertically ? -1 : 1);
        background.setTranslateY(flipVertically ? HEIGHT : 0);
    }

    private static Image loadBackground() {
        URL resource = SpeechBubbleView.class.getResource(BACKGROUND_RESOURCE);
        if (resource == null) {
            throw new IllegalStateException("Required speech bubble asset is missing from the classpath: "
                    + BACKGROUND_RESOURCE);
        }

        Image image = new Image(resource.toExternalForm());
        if (image.isError()) {
            throw new IllegalStateException("Failed to load speech bubble asset from classpath resource "
                    + BACKGROUND_RESOURCE, image.getException());
        }
        return image;
    }
}
