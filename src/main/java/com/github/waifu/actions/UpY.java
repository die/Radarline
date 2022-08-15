package com.github.waifu.actions;

import com.github.waifu.gfx.GFX;
import com.github.waifu.gui.GUI;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class UpY extends AbstractAction {

    public UpY() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (GFX g : GUI.component.getGraphicsList()) {
            if (g.isMovable()) {
                g.setOffsetY(g.getOffsetY() - 1);
                GUI.updateOverlay();
            }
        }
    }
}
