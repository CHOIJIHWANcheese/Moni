package com.moni.app.walking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class WalkPlannerTest {
    private static final RandomIntSource LOWEST_VALUE = bound -> 0;

    @Test
    void leftBoundaryOnlyPlansAWalkToTheRightWithinVisualBounds() {
        WalkPlan plan = WalkPlanner.createPlan(0, 128, 0, 1000, LOWEST_VALUE).orElseThrow();

        assertEquals(WalkDirection.RIGHT, plan.direction());
        assertTrue(plan.targetX() >= 0);
        assertTrue(plan.targetX() <= 872);
    }

    @Test
    void rightBoundaryOnlyPlansAWalkToTheLeftWithinVisualBounds() {
        WalkPlan plan = WalkPlanner.createPlan(872, 128, 0, 1000, LOWEST_VALUE).orElseThrow();

        assertEquals(WalkDirection.LEFT, plan.direction());
        assertTrue(plan.targetX() >= 0);
        assertTrue(plan.targetX() <= 872);
    }

    @Test
    void choosesOnlyDirectionWithEnoughSpace() {
        WalkPlan plan = WalkPlanner.createPlan(40, 128, 0, 1000, LOWEST_VALUE).orElseThrow();

        assertEquals(WalkDirection.RIGHT, plan.direction());
    }
}
