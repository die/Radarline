package com.github.waifu.gfx;

import com.github.waifu.enums.GraphicType;
import java.awt.*;

public class GFX {

    private boolean visible = false;
    private boolean movable = false;
    private int width = 0;
    private int height = 0;
    private int positionX = 0;
    private int positionY = 0;
    private int offsetX = 0;
    private int offsetY = 0;
    private Color color = Color.WHITE;
    private GraphicType type;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isMovable() {
        return movable;
    }

    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(int offsetX) {
        this.offsetX = offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(int offsetY) {
        this.offsetY = offsetY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GraphicType getType() {
        return type;
    }

    public void setType(GraphicType type) {
        this.type = type;
    }

    public void drawGraphic(Graphics g) {
        g.drawString("Default", Toolkit.getDefaultToolkit().getScreenSize().width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2);
    }
}
