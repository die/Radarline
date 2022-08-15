package com.github.waifu.overlay;

import com.github.waifu.gui.Component;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
import java.awt.*;

public class Overlay extends java.awt.Window {

    public Overlay(Frame owner, Component component) {
        super(owner);
        this.setBackground(new Color(0, 0, 0, 0));
        this.add(component);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setAlwaysOnTop(true);
        WinDef.HWND hwnd = new WinDef.HWND();
        hwnd.setPointer(Native.getComponentPointer(this));
        User32.INSTANCE.SetWindowLong(hwnd, WinUser.GWL_EXSTYLE, WinUser.WS_EX_LAYERED | WinUser.WS_EX_TRANSPARENT);
    }
}
