package org.suai.seabattle.controller;

import org.suai.seabattle.model.level.Level;
import org.suai.seabattle.model.level.TileType;
import org.suai.seabattle.model.parameters.ComputationData;
import org.suai.seabattle.model.parameters.GameState;
import org.suai.seabattle.model.player.Player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Input implements KeyListener {

    private Player player; // создание активной ячейки пользователя
    private Level level; // создание уровня
    private GameState gs = GameState.UNREADY; // состояние игры

    private int serverPort = 9876; // порт сервера
    private int port = 9876; // порт клиента
    private InetAddress IPAddress; // адрес
    private boolean isHost;
    private DatagramSocket clientSocket;

    public Input(String fileName, boolean isHost) throws IOException {
        this.isHost = isHost;

        if (isHost) {
            IPAddress = InetAddress.getByName("localhost");
            clientSocket = new DatagramSocket();

            byte[] sendData = new byte[1];
            sendData[0] = 1;
            byte[] receiveData = new byte[1];

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            port = receivePacket.getPort();
        } else {
            IPAddress = InetAddress.getByName("localhost");
            clientSocket = new DatagramSocket();

            byte[] sendData = new byte[1];
            sendData[0] = 2;
            byte[] receiveData = new byte[1];

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            port = receivePacket.getPort();
        }

        //level = new Level(fileName);
        level = new Level();
        level.fillRand(fileName, level.getColor());
        player = new Player();

        ResponseThread rt = new ResponseThread();
        rt.start();
    }

    public void keyTyped (KeyEvent key) { }

    // обработка нажатия на клавишу
    public void keyPressed (KeyEvent key) {
            // вверх --------------------------------------------------------------------------------------------------
        if (key.getKeyCode() == KeyEvent.VK_UP) {

            player.goUp();

            // вниз ---------------------------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_DOWN) {

            player.goDown();

            // влево --------------------------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_LEFT) {

            player.goLeft();

            // вправо -------------------------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_RIGHT) {

            player.goRight();

            // выстрел по клетке --------------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_SPACE && gs == GameState.CONTINUE &&
                level.canBeHit(player.getNumberWidth(), player.getNumberHeight())) {

            System.out.println("Пробле нажался");

            byte[] sendData = new byte[1024];
            sendData[0] = 1; // код 1 - выстрел

            sendData[1] = (byte)(player.getNumberWidth() - 13);
            sendData[2] = (byte)(player.getNumberHeight() - 1);

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Пакет отправился");

            gs = GameState.WAIT;

            System.out.println("режим вейт");

            // выход --------------------------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {

            byte[] sendData = new byte[1024];
            sendData[0] = 2; // код 2 - игрок отключился

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Пакет quit отправился");

            gs = GameState.QUIT;

            // перемешать корабли -------------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_R && gs == GameState.UNREADY) {

            level.fillRand(ComputationData.LEVEL_NAME, level.getColor());

            // поменять цвет ------------------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_C) {

            level.swapColor();

            // подтвердить готовность ---------------------------------------------------------------------------------
        } else if (key.getKeyCode() == KeyEvent.VK_ENTER && gs == GameState.UNREADY) {

            System.out.println("enter нажался");
            byte[] sendData = new byte[1024];
            sendData[0] = 0; // код 0 - отправка уровня

            int k = 1;
            for (int i = 1; i < 11; i++) {
                for (int j = 1; j < 11; j++) {
                    sendData[k] = (byte)level.getTileMap()[i][j];
                    k++;
                }
            }

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                clientSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Пакет отправился");

            gs = GameState.READY;
            System.out.println("теперь реди");
            // --------------------------------------------------------------------------------------------------------
        }
    }

    public void keyReleased (KeyEvent key) { }

    // вывод на экран активной ячейки
    public void renderUser (Graphics2D g) {
        player.render(g);
    }

    // вывод на экран уровня
    public void renderLevel (Graphics2D g) {
        level.render(g);
    }

    // геттер для состояния игры
    public GameState getGS () {
        return gs;
    }

    // сеттер для состояния игры
    public void setGS (GameState gs) {
        this.gs = gs;
    }

    // геттер для уровня
    public Level getLevel () {
        return level;
    }

    private class ResponseThread extends Thread {
        public void run() {
            byte[] receiveData = new byte[1024];

            System.out.println(clientSocket.getPort());
            while(true) {

                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    clientSocket.receive(receivePacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Пакет пришёл на клиент");

                byte[] message = receivePacket.getData();

                // мой выстрел
                if (message[0] == 0) {
                    System.out.println("Это мой выстрел");
                    // промах
                    if (message[1] == 0) {
                        System.out.println("И это промах");
                        level.selectTile(message[2], message[3], TileType.MISS);

                        // попадание
                    } else {
                        System.out.println("И это попадание");
                        level.selectTile(message[2], message[3], TileType.HIT);

                    }

                    // вражеский выстрел
                } else if (message[0] == 1) {
                    System.out.println("Это противник выстрелил");
                    // промах
                    if (message[1] == 0) {
                        System.out.println("И он промахнулся");
                        level.selectTile(message[2], message[3], TileType.MISS);
                        gs = GameState.CONTINUE;

                        // попадание
                    } else {
                        System.out.println("И он попал");
                        level.selectTile(message[2], message[3], TileType.HIT);
                        gs = GameState.CONTINUE;

                    }

                    // противник отключился или все вражеские корабли уничтожены
                } else if (message[0] == 2 || message[0] == 3) {
                    System.out.println("Победа");
                    gs = GameState.WIN;

                    // все ваши корабли уничтожены (поражение)
                } else if (message[0] == 4) {
                    System.out.println("Поражение");
                    gs = GameState.LOSE;

                    // ваш ход
                } else if (message[0] == 5) {
                    System.out.println("я могу ходить");
                    gs = GameState.CONTINUE;

                    // ход противника
                } else if (message[0] == 6) {
                    System.out.println("я должен ждать");
                    gs = GameState.WAIT;

                }
                else {
                    System.out.println("Error in received packet");
                }

            }
        }
    }
}
