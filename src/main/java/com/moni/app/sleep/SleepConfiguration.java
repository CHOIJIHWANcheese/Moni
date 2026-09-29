package com.moni.app.sleep;

import java.time.Duration;
import java.util.List;

/** Parses the optional demonstration timeout while keeping the production default at ten minutes. */
public final class SleepConfiguration {
    private static final String SLEEP_AFTER_PREFIX = "--sleep-after-seconds=";
    public static final Duration DEFAULT_SLEEP_AFTER = Duration.ofMinutes(10);

    private SleepConfiguration() {
    }

    public static Duration fromArguments(List<String> arguments) {
        Duration configuredDuration = null;
        for (String argument : arguments) {
            if (!argument.startsWith(SLEEP_AFTER_PREFIX)) {
                continue;
            }
            if (configuredDuration != null) {
                throw new IllegalArgumentException("--sleep-after-seconds may only be provided once");
            }
            String value = argument.substring(SLEEP_AFTER_PREFIX.length());
            try {
                long seconds = Long.parseLong(value);
                if (seconds <= 0) {
                    throw new IllegalArgumentException("--sleep-after-seconds must be a positive integer");
                }
                configuredDuration = Duration.ofSeconds(seconds);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("--sleep-after-seconds must be a positive integer", exception);
            }
        }
        return configuredDuration == null ? DEFAULT_SLEEP_AFTER : configuredDuration;
    }
}
