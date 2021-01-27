package org.suai.seabattle.model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void goUp() {
        Player player = new Player();
        player.goDown();
        player.goDown();

        player.goUp();

        int actual1 = player.getNumberWidth();
        int actual2 = player.getNumberHeight();

        int expected1 = 13;
        int expected2 = 2;

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void goDown() {
        Player player = new Player();
        player.goDown();

        int actual1 = player.getNumberWidth();
        int actual2 = player.getNumberHeight();

        int expected1 = 13;
        int expected2 = 2;

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void goLeft() {
        Player player = new Player();
        player.goRight();
        player.goRight();

        player.goLeft();

        int actual1 = player.getNumberWidth();
        int actual2 = player.getNumberHeight();

        int expected1 = 14;
        int expected2 = 1;

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }

    @Test
    void goRight() {
        Player player = new Player();
        player.goRight();

        int actual1 = player.getNumberWidth();
        int actual2 = player.getNumberHeight();

        int expected1 = 14;
        int expected2 = 1;

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}