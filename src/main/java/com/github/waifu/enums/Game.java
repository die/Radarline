package com.github.waifu.enums;

public enum Game {

    /* Defaults for 1920x1080 */
    BLACK_OPS_II(10, 131, 535, 955);

    private final int radarHeight;
    private final int radarWidth;
    private final int crosshairHeight;
    private final int crosshairWidth;

    Game(int radarHeight, int radarWidth, int crosshairHeight, int crosshairWidth) {
        this.radarHeight = radarHeight;
        this.radarWidth = radarWidth;
        this.crosshairHeight = crosshairHeight;
        this.crosshairWidth = crosshairWidth;
    }

    public int getRadarHeight() {
        return radarHeight;
    }

    public int getRadarWidth() {
        return radarWidth;
    }

    public int getCrosshairHeight() {
        return crosshairHeight;
    }

    public int getCrosshairWidth() {
        return crosshairWidth;
    }
}
