package com.github.waifu.gfx;

import com.github.waifu.enums.Game;
import com.github.waifu.enums.GraphicType;
import java.awt.*;

public class OpenDot extends GFX {

    public OpenDot() {
        /* Modified Defaults */
        setWidth(9);
        setHeight(9);
        setPositionX(Game.BLACK_OPS_II.getRadarWidth());
        setPositionY(Game.BLACK_OPS_II.getRadarHeight());
        setType(GraphicType.OPEN_DOT);
    }

    @Override
    public void drawGraphic(Graphics g) {
        g.setColor(getColor());
        g.drawOval(Game.BLACK_OPS_II.getCrosshairWidth() + getOffsetX(), Game.BLACK_OPS_II.getCrosshairHeight() + getOffsetY(), getWidth(), getHeight());
    }
}
