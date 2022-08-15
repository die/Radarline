package com.github.waifu.gui;

import com.github.waifu.actions.DownY;
import com.github.waifu.actions.LeftX;
import com.github.waifu.actions.RightX;
import com.github.waifu.actions.UpY;
import com.github.waifu.enums.GraphicType;
import com.github.waifu.gfx.GFX;
import com.github.waifu.overlay.Overlay;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import javax.swing.*;
import java.awt.Robot;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

public class GUI extends JFrame {

    private JButton enableButton;
    private JPanel panel;
    private JCheckBox drawRadarLineCheckBox;
    private JCheckBox drawOpenDotCheckBox;
    private JButton chooseColorButton;
    private JCheckBox drawDotCheckBox;
    private JButton enableDynamicButton;
    private JCheckBox moveRadarLineCheckBox;
    private JCheckBox moveDotCheckBox;
    private static Overlay overlay;
    public static Component component;
    private boolean dynamic = false;
    private Color color = Color.WHITE;

    public GUI() {
        component = new Component();
        setContentPane(panel);
        setSize(200, 150);
        setVisible(true);
        setResizable(false);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icon.ico"))).getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        enableButton.addActionListener(e -> {
            if (enableButton.getText().equals("Enable")) {
                overlay = new Overlay(null, component);
                enableButton.setText("Disable");
            } else if (enableButton.getText().equals("Disable")) {
                overlay.dispose();
                enableButton.setText("Enable");
            }
        });

        chooseColorButton.addActionListener(e -> {
            this.color = JColorChooser.showDialog(this, "Select a color:", Color.white);
            for (GFX GFX : component.getGraphicsList()) {
                GFX.setColor(this.color);
            }
            updateOverlay();
        });

        drawRadarLineCheckBox.addActionListener(e -> {
            if (enableButton.getText().equals("Disable")) {
                for (GFX GFX : component.getGraphicsList()) {
                    if (GFX.getType().equals(GraphicType.RADAR_LINE)) {
                        GFX.setVisible(drawRadarLineCheckBox.isSelected());
                        updateOverlay();
                    }
                }
            }
        });

        drawOpenDotCheckBox.addActionListener(e -> {
            if (enableButton.getText().equals("Disable")) {
                for (GFX GFX : component.getGraphicsList()) {
                    if (GFX.getType().equals(GraphicType.OPEN_DOT)) {
                        GFX.setVisible(drawOpenDotCheckBox.isSelected());
                        updateOverlay();
                    }
                }
            }
        });

        drawDotCheckBox.addActionListener(e -> {
            if (enableButton.getText().equals("Disable")) {
                for (GFX GFX : component.getGraphicsList()) {
                    if (GFX.getType().equals(GraphicType.DOT)) {
                        GFX.setVisible(drawDotCheckBox.isSelected());
                        updateOverlay();
                    }
                }
            }
        });

        moveRadarLineCheckBox.addActionListener(e -> {
            List<GFX> GFXList = component.getGraphicsList();
            for (GFX GFX : GFXList) {
                if (GFX.getType().equals(GraphicType.RADAR_LINE)) {
                    GFX.setMovable(moveRadarLineCheckBox.isSelected());
                }
            }
        });

        moveDotCheckBox.addActionListener(e -> {
            List<GFX> GFXList = component.getGraphicsList();
            for (GFX GFX : GFXList) {
                if (GFX.getType().equals(GraphicType.DOT) || GFX.getType().equals(GraphicType.OPEN_DOT)) {
                    GFX.setMovable(moveDotCheckBox.isSelected());
                }
            }
        });

        enableDynamicButton.addActionListener(e -> {
            if (enableDynamicButton.getText().contains("Enable")) {
                dynamic = true;
                enableDynamicButton.setText("Disable Dynamic");
            } else if (enableDynamicButton.getText().contains("Disable")) {
                dynamic = false;
                enableDynamicButton.setText("Enable Dynamic");
            }
        });

        pack();

        ActionListener taskPerformer = evt -> {
            boolean detected = updateRadarline();

            if (detected && dynamic) {
                int r = this.color.getRed();
                int b = this.color.getBlue();
                int g = this.color.getGreen();

                Color inverted = new Color(255 - r, 255 - g, 255 - b);
                GFX radarline = component.findGraphic(GraphicType.RADAR_LINE);
                Color color = radarline.getColor();
                if (overlay != null && !color.equals(inverted)) {
                    component.getGraphicsList().forEach(graphic -> graphic.setColor(inverted));
                    updateOverlay();
                }
            } else {
                Color color = component.getGraphicsList().get(0).getColor();
                if (overlay != null && !color.equals(this.color)) {
                    component.getGraphicsList().forEach(graphic -> graphic.setColor(this.color));
                    updateOverlay();
                }
            }
        };
        Timer timer = new Timer(0, taskPerformer);
        timer.setRepeats(true);
        timer.start();

        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "W");
        this.getRootPane().getActionMap().put("W", new UpY());
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0, false), "S");
        this.getRootPane().getActionMap().put("S", new DownY());
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "A");
        this.getRootPane().getActionMap().put("A", new LeftX());
        this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "D");
        this.getRootPane().getActionMap().put("D", new RightX());
    }

    public static void updateOverlay() {
        overlay.dispose();
        overlay = new Overlay(null, component);
    }

    private boolean updateRadarline() {
        if (enableButton.getText().equals("Disable") && dynamic) {
            try {
                Robot robot = new Robot();
                GFX radarline = component.findGraphic(GraphicType.RADAR_LINE);
                int position = component.getGraphicsList().get(0).getPositionX() - 2; // radius = 2
                int width = 4; // diameter
                BufferedImage bufferedImage = robot.createScreenCapture(new Rectangle(position + radarline.getOffsetX(), radarline.getPositionY() + radarline.getOffsetY(), width, radarline.getHeight()));
                for (int y = 0; y < bufferedImage.getHeight(); y++) {
                    for (int x = 0; x < bufferedImage.getWidth(); x++) {
                        //System.out.println("(" + x + "," + y + ") " + color.getRed() + "/" + color.getGreen() + "/" + color.getBlue());
                        if (x < 2) {
                            Color colors = new Color(bufferedImage.getRGB(x, y));
                            if (colors.getRed() > 200 && colors.getGreen() < 200 && colors.getBlue() < 200) {
                                return true;
                            }
                        }
                    }
                }
                return false;
            } catch (Exception ignored) { }
            return false;
        }
        return false;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        panel = new JPanel();
        panel.setLayout(new GridLayoutManager(5, 7, new Insets(0, 0, 0, 0), -1, -1));
        enableButton = new JButton();
        enableButton.setText("Enable");
        panel.add(enableButton, new GridConstraints(0, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        drawRadarLineCheckBox = new JCheckBox();
        drawRadarLineCheckBox.setText("Draw Radar Line");
        panel.add(drawRadarLineCheckBox, new GridConstraints(1, 0, 2, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chooseColorButton = new JButton();
        chooseColorButton.setText("Choose Color");
        panel.add(chooseColorButton, new GridConstraints(0, 4, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        drawOpenDotCheckBox = new JCheckBox();
        drawOpenDotCheckBox.setText("Draw Open Dot");
        panel.add(drawOpenDotCheckBox, new GridConstraints(2, 6, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Discord: su#4008 Contact for feature requests");
        panel.add(label1, new GridConstraints(4, 0, 1, 5, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        drawDotCheckBox = new JCheckBox();
        drawDotCheckBox.setText("Draw Filled Dot");
        panel.add(drawDotCheckBox, new GridConstraints(3, 6, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enableDynamicButton = new JButton();
        enableDynamicButton.setText("Enable Dynamic");
        panel.add(enableDynamicButton, new GridConstraints(3, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveRadarLineCheckBox = new JCheckBox();
        moveRadarLineCheckBox.setText("Move Radar Line");
        panel.add(moveRadarLineCheckBox, new GridConstraints(1, 2, 2, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        moveDotCheckBox = new JCheckBox();
        moveDotCheckBox.setText("Move Dot");
        panel.add(moveDotCheckBox, new GridConstraints(3, 2, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return panel;
    }

}
