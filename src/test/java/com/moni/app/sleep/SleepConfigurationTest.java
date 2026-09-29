package com.moni.app.sleep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;

class SleepConfigurationTest {

    @Test
    void defaultsToTenMinutesWithoutAnArgument() {
        assertEquals(Duration.ofMinutes(10), SleepConfiguration.fromArguments(List.of()));
    }

    @Test
    void acceptsPositiveDemonstrationSeconds() {
        assertEquals(Duration.ofSeconds(10), SleepConfiguration.fromArguments(List.of("--sleep-after-seconds=10")));
    }

    @Test
    void rejectsInvalidDemonstrationSeconds() {
        assertThrows(IllegalArgumentException.class,
                () -> SleepConfiguration.fromArguments(List.of("--sleep-after-seconds=0")));
        assertThrows(IllegalArgumentException.class,
                () -> SleepConfiguration.fromArguments(List.of("--sleep-after-seconds=fast")));
    }
}
