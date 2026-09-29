package com.moni.app.foreground;

import java.util.Optional;

/** Obtains the current foreground application when the host platform supports it. */
@FunctionalInterface
public interface ForegroundApplicationProvider {
    Optional<ForegroundApplication> foregroundApplication();
}
