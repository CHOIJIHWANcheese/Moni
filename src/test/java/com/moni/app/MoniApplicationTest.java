package com.moni.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MoniApplicationTest {

    @Test
    void applicationClassIsAvailable() {
        assertEquals("MoniApplication", MoniApplication.class.getSimpleName());
    }

    @Test
    void idleSpriteIsAvailableOnTheClasspath() {
        assertNotNull(
                MoniApplication.class.getResource("/moni/moni_idle.png"),
                "The idle sprite must be packaged as the /moni/moni_idle.png classpath resource."
        );
    }
}
