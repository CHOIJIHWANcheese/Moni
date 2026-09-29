package com.moni.app.foreground;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class ForegroundApplicationTrackerTest {

    @Test
    void doesNotNotifyRepeatedForegroundApplication() {
        ForegroundApplication application = application(100, "chrome.exe");
        ForegroundApplicationTracker tracker = new ForegroundApplicationTracker();

        assertTrue(tracker.track(Optional.of(application)).isPresent());
        assertFalse(tracker.track(Optional.of(application)).isPresent());
    }

    @Test
    void doesNotNotifyWhenTheSameExecutableHasAnotherProcessId() {
        ForegroundApplicationTracker tracker = new ForegroundApplicationTracker();

        tracker.track(Optional.of(application(100, "chrome.exe")));
        assertFalse(tracker.track(Optional.of(application(101, "chrome.exe"))).isPresent());
    }

    @Test
    void notifiesExactlyOnceWhenForegroundApplicationChanges() {
        ForegroundApplication chrome = application(100, "chrome.exe");
        ForegroundApplication code = application(200, "Code.exe");
        ForegroundApplicationTracker tracker = new ForegroundApplicationTracker();

        tracker.track(Optional.of(chrome));
        assertEquals(code, tracker.track(Optional.of(code)).orElseThrow());
        assertFalse(tracker.track(Optional.of(code)).isPresent());
    }

    private static ForegroundApplication application(long pid, String name) {
        return new ForegroundApplication(pid, name, Path.of("C:/Program Files", name));
    }
}
