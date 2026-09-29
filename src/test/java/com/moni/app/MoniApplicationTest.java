package com.moni.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MoniApplicationTest {

    @Test
    void applicationClassIsAvailable() {
        assertEquals("MoniApplication", MoniApplication.class.getSimpleName());
    }
}

