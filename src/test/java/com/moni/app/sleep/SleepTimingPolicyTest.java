package com.moni.app.sleep;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import org.junit.jupiter.api.Test;

class SleepTimingPolicyTest {

    @Test
    void primaryInteractionResetsTheInactivityDeadline() {
        SleepTimingPolicy policy = new SleepTimingPolicy(Duration.ofMinutes(10));
        policy.recordInteraction(0);

        assertFalse(policy.isSleepDue(599_999));
        policy.recordInteraction(500_000);
        assertFalse(policy.isSleepDue(1_099_999));
        assertTrue(policy.isSleepDue(1_100_000));
    }
}
