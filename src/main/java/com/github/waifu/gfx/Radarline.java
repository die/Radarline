package com.github.waifu.gfx;

import com.github.waifu.enums.Game;
import com.github.waifu.enums.GraphicType;
import java.awt.*;

public class Radarline extends GFX {

    public Radarline() {
        /* Modified Defaults */
        setWidth(1);
        setHeight(120);
        setPositionX(Game.BLACK_OPS_II.getRadarWidth());
        setPositionY(Game.BLACK_OPS_II.getRadarHeight());
        setType(GraphicType.RADAR_LINE);
    }

    @Override
    public void drawGraphic(Graphics g) {
        g.setColor(getColor());
        g.drawRect(Game.BLACK_OPS_II.getRadarWidth() + getOffsetX(), Game.BLACK_OPS_II.getRadarHeight() + getOffsetY(), getWidth(), getHeight());
    }
}
