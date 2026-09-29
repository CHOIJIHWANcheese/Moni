package com.moni.app.foreground;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class WindowsForegroundApplicationProviderTest {

    @Test
    void excludesMonisOwnProcessIdWithoutCallingUser32() {
        long currentProcessId = ProcessHandle.current().pid();

        assertTrue(WindowsForegroundApplicationProvider.shouldExcludeProcess(currentProcessId, currentProcessId));
        assertTrue(WindowsForegroundApplicationProvider.shouldExcludeProcess(0, currentProcessId));
        assertFalse(WindowsForegroundApplicationProvider.shouldExcludeProcess(currentProcessId + 1, currentProcessId));
    }
}
