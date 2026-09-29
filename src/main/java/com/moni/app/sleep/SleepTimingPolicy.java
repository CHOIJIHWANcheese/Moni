package com.moni.app.sleep;

import java.time.Duration;
import java.util.Objects;

/** Pure inactivity deadline policy used by the JavaFX sleep timer. */
public final class SleepTimingPolicy {
    private final long sleepAfterMillis;
    private long interactionDeadlineMillis;

    public SleepTimingPolicy(Duration sleepAfter) {
        sleepAfterMillis = Objects.requireNonNull(sleepAfter, "sleepAfter must not be null").toMillis();
        if (sleepAfterMillis <= 0) {
            throw new IllegalArgumentException("sleepAfter must be positive");
        }
    }

    public void recordInteraction(long nowMillis) {
        interactionDeadlineMillis = nowMillis + sleepAfterMillis;
    }

    public boolean isSleepDue(long nowMillis) {
        return nowMillis >= interactionDeadlineMillis;
    }

    public long remainingMillis(long nowMillis) {
        return Math.max(0, interactionDeadlineMillis - nowMillis);
    }
}
