package org.suai.seabattle.model.level;

import static org.junit.jupiter.api.Assertions.*;

class LevelTest {

    @org.junit.jupiter.api.Test
    void swapColor() {
        int[][] tileMap = {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 4, 0, 0, 0, 0, 0, 5, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                           {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};
        Level lvl = new Level(tileMap);
        lvl.swapColor();

        int actual1 = lvl.getTile(3, 1).numeric();
        int actual2 = lvl.getTile(9, 1).numeric();

        int expected1 = 6;
        int expected2 = 6;

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @org.junit.jupiter.api.Test
    void selectTile() {
        int[][] tileMap = new int[2][2];

        Level lvl = new Level(tileMap);

        lvl.selectTile(1,1,TileType.HIT);

        int actual = lvl.getTile(1,1).numeric();
        int expected = 3;

        assertEquals(expected, actual);
    }

}