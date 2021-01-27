package org.suai.seabattle.model.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.*;

class ResourceLoaderTest {

    @Test
    void levelParser() {
        int[][] actual = ResourceLoader.levelParser( "levelTest.lvl");
        int[][] expected = {{0,0,0,0,1,1,1,1,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0},
                            {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {1,1,0,0,1,1,1,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0},
                            {0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,8,0,0,0,0,0,0,0},
                            {0,7,0,0,1,0,0,0,0,0,1,0,0,0,0,8,0,0,0,0,0,0,0},
                            {0,0,0,0,1,1,1,1,0,0,1,0,0,0,0,8,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,8,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
                            {0,1,0,0,0,1,0,0,0,0,1,1,0,1,0,1,0,0,0,0,0,0,0},
                            {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                            {1,0,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        assertArrayEquals(expected, actual);
    }

}