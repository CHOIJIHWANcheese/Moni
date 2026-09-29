package com.moni.app.walking;

/** A bounded horizontal movement selected for one automatic walk. */
public record WalkPlan(WalkDirection direction, double startX, double targetX) {
}
