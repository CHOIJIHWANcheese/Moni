package com.moni.app.speech;

import javafx.geometry.Rectangle2D;

/** Pure screen-bounded placement rules for the speech bubble popup. */
public final class SpeechBubblePositioner {
    private static final int GAP = 6;

    private SpeechBubblePositioner() {
    }

    public static SpeechBubblePosition position(
            double stageX,
            double stageY,
            double stageWidth,
            double stageHeight,
            int bubbleWidth,
            int bubbleHeight,
            Rectangle2D visualBounds
    ) {
        int minX = rounded(visualBounds.getMinX());
        int minY = rounded(visualBounds.getMinY());
        int maxX = rounded(visualBounds.getMaxX());
        int maxY = rounded(visualBounds.getMaxY());
        int characterX = rounded(stageX);
        int characterY = rounded(stageY);
        int characterWidth = rounded(stageWidth);
        int characterHeight = rounded(stageHeight);
        int centeredX = characterX + (characterWidth - bubbleWidth) / 2;
        int aboveY = characterY - bubbleHeight - GAP;

        if (aboveY >= minY) {
            if (centeredX >= minX && centeredX + bubbleWidth <= maxX) {
                return new SpeechBubblePosition(centeredX, aboveY, SpeechBubblePosition.Placement.ABOVE);
            }
            int leftX = characterX - bubbleWidth - GAP;
            if (leftX >= minX) {
                return new SpeechBubblePosition(leftX, aboveY, SpeechBubblePosition.Placement.LEFT);
            }
            int rightX = characterX + characterWidth + GAP;
            if (rightX + bubbleWidth <= maxX) {
                return new SpeechBubblePosition(rightX, aboveY, SpeechBubblePosition.Placement.RIGHT);
            }
            return new SpeechBubblePosition(clamp(centeredX, minX, maxX - bubbleWidth), aboveY,
                    SpeechBubblePosition.Placement.ABOVE);
        }

        int belowY = characterY + characterHeight + GAP;
        return new SpeechBubblePosition(
                clamp(centeredX, minX, maxX - bubbleWidth),
                clamp(belowY, minY, maxY - bubbleHeight),
                SpeechBubblePosition.Placement.BELOW
        );
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(value, max));
    }

    private static int rounded(double value) {
        return (int) Math.round(value);
    }
}
