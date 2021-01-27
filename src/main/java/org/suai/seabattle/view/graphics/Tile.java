package org.suai.seabattle.view.graphics;

import org.suai.seabattle.model.level.TileType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {

    //private BufferedImage image; // изображение
    private Image image;
    private TileType type; // тип клетки

    // конструктор
    //public Tile (BufferedImage image, TileType type) {
    public Tile (Image image, TileType type) {
        this.type = type;
        this.image = image;
    }

    public void render (Graphics2D g, int x, int y) {
        g.drawImage(image, x, y, null);
    }

    // гетер для типа
    public TileType type () {
        return type;
    }


}
