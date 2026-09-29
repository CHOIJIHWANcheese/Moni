package com.moni.app.foreground;

import java.util.Optional;

/** Emits an application only when it differs from the last foreground result. */
public final class ForegroundApplicationTracker {
    private ForegroundApplication lastApplication;

    public Optional<ForegroundApplication> track(Optional<ForegroundApplication> currentApplication) {
        if (currentApplication.isEmpty()) {
            lastApplication = null;
            return Optional.empty();
        }

        ForegroundApplication current = currentApplication.get();
        if (lastApplication != null && current.executablePath().equals(lastApplication.executablePath())) {
            return Optional.empty();
        }
        lastApplication = current;
        return Optional.of(current);
    }
}
