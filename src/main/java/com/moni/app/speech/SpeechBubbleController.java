package com.moni.app.speech;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

import javafx.animation.PauseTransition;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

/** Owns a transparent owner-bound popup and its timed output-only messages. */
public final class SpeechBubbleController {
    private final Stage owner;
    private final SpeechBubbleView view = new SpeechBubbleView();
    private final Popup popup = new Popup();
    private final PauseTransition displayTimer = new PauseTransition();
    private final ChangeListener<Number> positionListener = (observable, oldValue, newValue) -> reposition();
    private Runnable onMessageFinished = () -> { };

    public SpeechBubbleController(Stage owner) {
        this.owner = Objects.requireNonNull(owner, "owner must not be null");
        popup.setAutoFix(false);
        popup.setAutoHide(false);
        popup.setHideOnEscape(false);
        popup.getContent().add(view);
        displayTimer.setOnFinished(event -> {
            hide();
            onMessageFinished.run();
        });
        owner.xProperty().addListener(positionListener);
        owner.yProperty().addListener(positionListener);
        owner.widthProperty().addListener(positionListener);
        owner.heightProperty().addListener(positionListener);
    }

    public void setOnMessageFinished(Runnable onMessageFinished) {
        this.onMessageFinished = Objects.requireNonNull(onMessageFinished, "onMessageFinished must not be null");
    }

    public void showMessage(String text, Duration duration) {
        view.setMessage(text);
        if (!popup.isShowing()) {
            popup.show(owner);
        }
        reposition();
        displayTimer.stop();
        displayTimer.setDuration(javafx.util.Duration.millis(duration.toMillis()));
        displayTimer.playFromStart();
    }

    public void hide() {
        displayTimer.stop();
        popup.hide();
    }

    public void dispose() {
        hide();
        owner.xProperty().removeListener(positionListener);
        owner.yProperty().removeListener(positionListener);
        owner.widthProperty().removeListener(positionListener);
        owner.heightProperty().removeListener(positionListener);
    }

    private void reposition() {
        if (!popup.isShowing()) {
            return;
        }
        SpeechBubblePosition position = SpeechBubblePositioner.position(
                owner.getX(),
                owner.getY(),
                owner.getWidth(),
                owner.getHeight(),
                SpeechBubbleView.WIDTH,
                SpeechBubbleView.HEIGHT,
                currentScreen().getVisualBounds()
        );
        view.setPlacement(position.placement());
        popup.setX(position.x());
        popup.setY(position.y());
    }

    private Screen currentScreen() {
        List<Screen> screens = Screen.getScreensForRectangle(
                owner.getX(), owner.getY(), Math.max(owner.getWidth(), 1), Math.max(owner.getHeight(), 1)
        );
        return screens.isEmpty() ? Screen.getPrimary() : screens.getFirst();
    }
}
