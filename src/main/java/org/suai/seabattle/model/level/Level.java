package org.suai.seabattle.model.level;

import org.suai.seabattle.model.parameters.ComputationData;
import org.suai.seabattle.model.utils.ResourceLoader;
import org.suai.seabattle.view.graphics.Texture;
import org.suai.seabattle.view.graphics.TexturePath;
import org.suai.seabattle.view.graphics.Tile;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Level {

    private int[][] tileMap; // уровень
    private Map<TileType, Tile> tiles; // карта типов клеток и клеток
    private TileType color; // цвет кораблей

    // конструктор с именем файла
    /*public Level(String fileName) {
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.EMPTY, new Tile (null, TileType.EMPTY));
        tiles.put(TileType.CLEAR_FIELD, new Tile (new Texture(TexturePath.CLEAR_FIELD).getImage(),
                TileType.CLEAR_FIELD));
        tiles.put(TileType.MISS, new Tile (new Texture(TexturePath.MISS).getImage(),
                TileType.MISS));
        tiles.put(TileType.HIT, new Tile (new Texture(TexturePath.HIT).getImage(),
                TileType.HIT));
        tiles.put(TileType.SHIP_GREEN, new Tile (new Texture(TexturePath.SHIP_GREEN).getImage(),
                TileType.SHIP_GREEN));
        tiles.put(TileType.SHIP_PURPLE, new Tile (new Texture(TexturePath.SHIP_PURPLE).getImage(),
                TileType.SHIP_PURPLE));
        tiles.put(TileType.SHIP_YELLOW, new Tile (new Texture(TexturePath.SHIP_YELLOW).getImage(),
                TileType.SHIP_YELLOW));

        tileMap = new int[ComputationData.TILES_IN_WIDTH][ComputationData.TILES_IN_HEIGHT];
        tileMap = ResourceLoader.levelParser(fileName); // загружаем уровень из файла
        color = TileType.SHIP_GREEN;
    }*/

    // конструктор с копией уровня
    public Level (int[][] tileMap) {
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.EMPTY, new Tile (null, TileType.EMPTY));
        tiles.put(TileType.CLEAR_FIELD, new Tile (new Texture(TexturePath.CLEAR_FIELD).getImage(),
                TileType.CLEAR_FIELD));
        tiles.put(TileType.MISS, new Tile (new Texture(TexturePath.MISS).getImage(),
                TileType.MISS));
        tiles.put(TileType.HIT, new Tile (new Texture(TexturePath.HIT).getImage(),
                TileType.HIT));
        tiles.put(TileType.SHIP_GREEN, new Tile (new Texture(TexturePath.SHIP_GREEN).getImage(),
                TileType.SHIP_GREEN));
        tiles.put(TileType.SHIP_PURPLE, new Tile (new Texture(TexturePath.SHIP_PURPLE).getImage(),
                TileType.SHIP_PURPLE));
        tiles.put(TileType.SHIP_YELLOW, new Tile (new Texture(TexturePath.SHIP_YELLOW).getImage(),
                TileType.SHIP_YELLOW));

        this.tileMap = tileMap;
        color = TileType.SHIP_GREEN;
    }

    public Level () {
        tiles = new HashMap<TileType, Tile>();
        tiles.put(TileType.EMPTY, new Tile (null, TileType.EMPTY));
        tiles.put(TileType.CLEAR_FIELD, new Tile (new Texture(TexturePath.CLEAR_FIELD).getImage(),
                TileType.CLEAR_FIELD));
        tiles.put(TileType.MISS, new Tile (new Texture(TexturePath.MISS).getImage(),
                TileType.MISS));
        tiles.put(TileType.HIT, new Tile (new Texture(TexturePath.HIT).getImage(),
                TileType.HIT));
        tiles.put(TileType.SHIP_GREEN, new Tile (new Texture(TexturePath.SHIP_GREEN).getImage(),
                TileType.SHIP_GREEN));
        tiles.put(TileType.SHIP_PURPLE, new Tile (new Texture(TexturePath.SHIP_PURPLE).getImage(),
                TileType.SHIP_PURPLE));
        tiles.put(TileType.SHIP_YELLOW, new Tile (new Texture(TexturePath.SHIP_YELLOW).getImage(),
                TileType.SHIP_YELLOW));

        resetTileMap();
        color = TileType.SHIP_GREEN;
    }

    public void render (Graphics2D g) {
        for (int i = 0; i < tileMap.length; i++) {
            for (int j = 0; j < tileMap[i].length; j ++) {
                tiles.get(TileType.fromNumeric(tileMap[i][j])).render(g,
                        j * ComputationData.TILE_SCALE, i * ComputationData.TILE_SCALE);
            }
        }
    }

    public void resetTileMap () {
        tileMap = new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                              {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
    }

    private boolean isFree (int h, int w) {
        return tileMap[h][w] == TileType.CLEAR_FIELD.numeric() &&
                (tileMap[h - 1][w] == TileType.CLEAR_FIELD.numeric() || tileMap[h - 1][w] == TileType.EMPTY.numeric()) &&
                (tileMap[h + 1][w] == TileType.CLEAR_FIELD.numeric() || tileMap[h + 1][w] == TileType.EMPTY.numeric()) &&
                (tileMap[h][w - 1] == TileType.CLEAR_FIELD.numeric() || tileMap[h][w - 1] == TileType.EMPTY.numeric()) &&
                (tileMap[h][w + 1] == TileType.CLEAR_FIELD.numeric() || tileMap[h][w + 1] == TileType.EMPTY.numeric());
    }

    // заполнение уровня кораблями
    public void fillRand (String fileName, TileType shipType) {
        //tileMap = ResourceLoader.levelParser(fileName); // загружаем уровень из файла
        resetTileMap();

        Random r = new Random(); // создание объекта рандома
        int i = 0;

        // генерация кораблей длины 4
        while (i < ComputationData.MAX_SHIP_4) {
            int vec = r.nextInt(10) % 2; // выбор направления
            int first = r.nextInt(10); // до 10 клеток в высоту
            int second = r.nextInt(10); // до 10 клеток в ширину

            // если расположен по горизонтали, иначе по вертикали
            if (vec == 0) {
                if (isFree(first + 1, second + 1) && isFree(first + 1, second + 2) &&
                        isFree(first + 1, second + 3) && isFree(first + 1, second + 4)) {
                    tileMap[first + 1][second + 1] = shipType.numeric();
                    tileMap[first + 1][second + 2] = shipType.numeric();
                    tileMap[first + 1][second + 3] = shipType.numeric();
                    tileMap[first + 1][second + 4] = shipType.numeric();
                } else {
                    continue;
                }

            } else {
                if (isFree(first + 1, second + 1) && isFree(first + 2, second + 1) &&
                        isFree(first + 3, second + 1) && isFree(first + 4, second + 1)) {
                    tileMap[first + 1][second + 1] = shipType.numeric();
                    tileMap[first + 2][second + 1] = shipType.numeric();
                    tileMap[first + 3][second + 1] = shipType.numeric();
                    tileMap[first + 4][second + 1] = shipType.numeric();
                } else {
                    continue;
                }
            }

            i++;
        }

        i = 0;
        // генерация кораблей длины 3
        while (i < ComputationData.MAX_SHIP_3) {
            int vec = r.nextInt(10) % 2; // выбор направления
            int first = r.nextInt(10); // до 10 клеток в высоту
            int second = r.nextInt(10); // до 10 клеток в ширину

            // если расположен по горизонтали, иначе по вертикали
            if (vec == 0) {
                if (isFree(first + 1, second + 1) && isFree(first + 1, second + 2) &&
                        isFree(first + 1, second + 3)) {
                    tileMap[first + 1][second + 1] = shipType.numeric();
                    tileMap[first + 1][second + 2] = shipType.numeric();
                    tileMap[first + 1][second + 3] = shipType.numeric();
                } else {
                    continue;
                }

            } else {
                if (isFree(first + 1, second + 1) && isFree(first + 2, second + 1) &&
                        isFree(first + 3, second + 1)) {
                    tileMap[first + 1][second + 1] = shipType.numeric();
                    tileMap[first + 2][second + 1] = shipType.numeric();
                    tileMap[first + 3][second + 1] = shipType.numeric();
                } else {
                    continue;
                }
            }

            i++;
        }

        i = 0;
        // генерация кораблей длины 2
        while (i < ComputationData.MAX_SHIP_2) {
            int vec = r.nextInt(10) % 2; // выбор направления
            int first = r.nextInt(10); // до 10 клеток в высоту
            int second = r.nextInt(10); // до 10 клеток в ширину

            // если расположен по горизонтали, иначе по вертикали
            if (vec == 0) {
                if (isFree(first + 1, second + 1) && isFree(first + 1, second + 2)) {
                    tileMap[first + 1][second + 1] = shipType.numeric();
                    tileMap[first + 1][second + 2] = shipType.numeric();
                } else {
                    continue;
                }

            } else {
                if (isFree(first + 1, second + 1) && isFree(first + 2, second + 1)) {
                    tileMap[first + 1][second + 1] = shipType.numeric();
                    tileMap[first + 2][second + 1] = shipType.numeric();
                } else {
                    continue;
                }
            }

            i++;
        }

        i = 0;
        // генерация кораблей длины 1
        while (i < ComputationData.MAX_SHIP_1) {
            int first = r.nextInt(10); // до 10 клеток в высоту
            int second = r.nextInt(10); // до 10 клеток в ширину

            // если расположен по горизонтали, иначе по вертикали
            if (isFree(first + 1, second + 1)) {
                tileMap[first + 1][second + 1] = shipType.numeric();
            } else {
                continue;
            }

            i++;
        }

    }

    // возврщяем копию карту уровня
    public int[][] getTileMap () {
        int[][] arr = new int[ComputationData.TILES_IN_HEIGHT][ComputationData.TILES_IN_WIDTH]; // копия уровня

        // копируем содержимое уровня в его копию
        for (int i = 0; i < tileMap.length; i++) {
            System.arraycopy(tileMap[i], 0, arr[i], 0, tileMap[i].length);
        }

        return arr;
    }

    public TileType getColor () {
        return color;
    }

    // поменять цвет
    public void swapColor () {
        color = TileType.fromNumeric(((color.numeric() + 1) % 3) + 4);
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tileMap[i + 1][j + 1] == TileType.SHIP_GREEN.numeric() ||
                        tileMap[i + 1][j + 1] == TileType.SHIP_PURPLE.numeric() ||
                        tileMap[i + 1][j + 1] == TileType.SHIP_YELLOW.numeric()) {
                    tileMap[i + 1][j + 1] = color.numeric();
                }
            }
        }
    }

    // выстрелить в данную клетку
    public void selectTile (int numberTileHeight, int numberTileWidth, TileType tileType) {
        tileMap[numberTileWidth][numberTileHeight] = tileType.numeric();
    }

    // проверка ячейки
    public TileType getTile (int numberTileHeight, int numberTileWidth) {
        return TileType.fromNumeric(tileMap[numberTileWidth][numberTileHeight]);
    }

    // проверка на возможность выстрела в заданную клетку
    public boolean canBeHit(int numberTileHeight, int numberTileWidth) {
        return getTile(numberTileHeight, numberTileWidth) != TileType.MISS &&
                getTile(numberTileHeight, numberTileWidth) != TileType.HIT;
    }

    // проверка на наличие кораблей
    public boolean isAnyShips () {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tileMap[i][j] == TileType.SHIP_GREEN.numeric() ||
                        tileMap[i][j] == TileType.SHIP_PURPLE.numeric() ||
                        tileMap[i][j] == TileType.SHIP_YELLOW.numeric()) {
                    return true;
                }
            }
        }
        return false;
    }

}
