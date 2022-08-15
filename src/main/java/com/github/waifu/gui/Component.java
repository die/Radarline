package com.github.waifu.gui;

import com.github.waifu.enums.GraphicType;
import com.github.waifu.gfx.Dot;
import com.github.waifu.gfx.GFX;
import com.github.waifu.gfx.OpenDot;
import com.github.waifu.gfx.Radarline;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Component extends JComponent {

    private final List<GFX> GFXList;

    public Component() {

        // TODO: add all instances of GFX via Reflection
        this.GFXList = new ArrayList<>();
        this.GFXList.add(new Radarline());
        this.GFXList.add(new Dot());
        this.GFXList.add(new OpenDot());
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        BufferedImage image = new BufferedImage(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        for (GFX GFX : GFXList) {
            if (GFX.isVisible()) {
                GFX.drawGraphic(graphics);
            }
        }
        g2d.drawImage(image, 0, 0, this);
        g2d.setBackground(new Color(0, 0, 0, 0));
        g2d.dispose();
    }

    public Dimension getPreferredSize() {
        return new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
    }

    public GFX findGraphic(GraphicType graphicType) {
        for (GFX GFX : GFXList) {
            if (GFX.getType().equals(graphicType)) {
                return GFX;
            }
        }
        return null;
    }

    public List<GFX> getGraphicsList() {
        return GFXList;
    }
}
