package com.moni.app.walking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Pure planning logic for selecting a bounded automatic walk. */
public final class WalkPlanner {
    public static final int MIN_DISTANCE_PX = 80;
    public static final int MAX_DISTANCE_PX = 240;

    private WalkPlanner() {
    }

    public static Optional<WalkPlan> createPlan(
            double currentX,
            double stageWidth,
            double visualMinX,
            double visualMaxX,
            RandomIntSource random
    ) {
        if (stageWidth <= 0 || visualMaxX - visualMinX < stageWidth) {
            return Optional.empty();
        }

        double maxStageX = visualMaxX - stageWidth;
        double startX = Math.max(visualMinX, Math.min(currentX, maxStageX));
        double leftSpace = startX - visualMinX;
        double rightSpace = maxStageX - startX;
        List<WalkDirection> directions = new ArrayList<>();
        if (leftSpace >= MIN_DISTANCE_PX) {
            directions.add(WalkDirection.LEFT);
        }
        if (rightSpace >= MIN_DISTANCE_PX) {
            directions.add(WalkDirection.RIGHT);
        }
        if (directions.isEmpty()) {
            return Optional.empty();
        }

        WalkDirection direction = directions.get(random.nextInt(directions.size()));
        int requestedDistance = MIN_DISTANCE_PX
                + random.nextInt(MAX_DISTANCE_PX - MIN_DISTANCE_PX + 1);
        double availableDistance = direction == WalkDirection.LEFT ? leftSpace : rightSpace;
        double distance = Math.min(requestedDistance, availableDistance);
        double targetX = startX + direction.horizontalSign() * distance;
        return Optional.of(new WalkPlan(direction, startX, targetX));
    }
}
