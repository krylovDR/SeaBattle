package org.suai.seabattle.model.player;

import org.suai.seabattle.model.level.Level;
import org.suai.seabattle.model.parameters.ComputationData;
import org.suai.seabattle.view.graphics.Texture;
import org.suai.seabattle.view.graphics.TexturePath;

import java.awt.*;

public class Player {

    private int numberHeight; // текущий номер ячейки по высоте
    private int numberWidth; // по ширине

    private Texture texture; // текстура активной ячейки

    // конструктор
    public Player() {
        // начальные точки активной ячейки
        numberHeight = ComputationData.START_TILE_IN_HEIGHT;
        numberWidth = ComputationData.START_TILE_IN_WIDTH;

        texture = new Texture(TexturePath.CELL); // загрузка необходимой текстуры
    }

    // сдвинуться вверх
    public void goUp () {
        int newHeight = numberHeight; // чтобы не уйти туда, куда не следует
        newHeight--; // вверх на ячейку
        // проверка, не вышел ли за пределы
        if (newHeight < ComputationData.START_TILE_IN_HEIGHT) {
            newHeight = ComputationData.START_TILE_IN_HEIGHT;
        }
        numberHeight = newHeight;
    }

    // свдинуться вниз
    public void goDown () {
        int newHeight = numberHeight;
        newHeight++;
        if (newHeight >= ComputationData.END_TITLE_IN_HEIGHT - 1) {
            newHeight = ComputationData.END_TITLE_IN_HEIGHT - 2;
        }
        numberHeight = newHeight;
    }

    // сдвинуться влево
    public void goLeft () {
        int newWidth = numberWidth;
        newWidth--;
        if (newWidth < ComputationData.START_TILE_IN_WIDTH) {
            newWidth = ComputationData.START_TILE_IN_WIDTH;
        }
        numberWidth = newWidth;
    }

    // сдвинуться вправо
    public void goRight () {
        int newWidth = numberWidth;
        newWidth++;
        if (newWidth >= ComputationData.END_TILE_IN_WIDTH) {
            newWidth = ComputationData.END_TILE_IN_WIDTH - 1;
        }
        numberWidth = newWidth;
    }

    public int getNumberHeight () {
        return numberHeight;
    }

    public int getNumberWidth () {
        return numberWidth;
    }

    public void render (Graphics2D g) {
        texture.render(g, numberWidth * ComputationData.TILE_SCALE, numberHeight * ComputationData.TILE_SCALE);
    }

}