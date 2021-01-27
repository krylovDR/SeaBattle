package org.suai.seabattle.view.panels;

import org.suai.seabattle.model.utils.ResourceLoader;
import org.suai.seabattle.view.graphics.TexturePath;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends JPanel {

    // фоновое изображение в Main Menu
    @Override
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        g.drawImage(ResourceLoader.loadImage(TexturePath.BACKGROUND), 0, 0, null);
    }

}