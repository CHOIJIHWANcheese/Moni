package com.moni.app.sleep;

import java.time.Duration;
import java.util.Objects;

import javafx.animation.PauseTransition;

/** JavaFX lifecycle-safe inactivity timer that requests one transition into sleep. */
public final class SleepController {
    private final SleepTimingPolicy timingPolicy;
    private final Runnable onSleepDue;
    private final PauseTransition inactivityTimer = new PauseTransition();

    public SleepController(Duration sleepAfter, Runnable onSleepDue) {
        timingPolicy = new SleepTimingPolicy(sleepAfter);
        this.onSleepDue = Objects.requireNonNull(onSleepDue, "onSleepDue must not be null");
        inactivityTimer.setOnFinished(event -> onSleepDue.run());
    }

    public void start() {
        restartAfterInteraction();
    }

    public void recordPrimaryInteraction() {
        timingPolicy.recordInteraction(System.currentTimeMillis());
        inactivityTimer.stop();
    }

    public void restartAfterInteraction() {
        long nowMillis = System.currentTimeMillis();
        timingPolicy.recordInteraction(nowMillis);
        inactivityTimer.stop();
        inactivityTimer.setDuration(javafx.util.Duration.millis(timingPolicy.remainingMillis(nowMillis)));
        inactivityTimer.playFromStart();
    }

    public void stop() {
        inactivityTimer.stop();
    }
}
