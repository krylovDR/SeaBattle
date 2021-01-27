package org.suai.seabattle.model;

import org.suai.seabattle.model.level.Level;
import org.suai.seabattle.model.level.TileType;
import org.suai.seabattle.model.utils.ResourceLoader;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {

    private ArrayList<InetAddress> IP;
    private ArrayList<Integer> ports;
    private DatagramSocket server;
    private ArrayList<RoomThread> rooms;

    public static void main(String[] args) throws IOException {
        new Server();
    }

    public Server () throws IOException {
        server = new DatagramSocket(9876);

        IP = new ArrayList<InetAddress>();
        ports = new ArrayList<Integer>();
        rooms = new ArrayList<RoomThread>();


        while (true) {
            byte[] receiveData = new byte[1];
            byte[] sendData = new byte[1];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            server.receive(receivePacket);

            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();

            IP.add(IPAddress);
            ports.add(port);
            System.out.println(IPAddress);
            System.out.println(port);
            ResourceLoader.writeLOG(IPAddress.toString());
            ResourceLoader.writeLOG(((Integer)port).toString());

            byte[] message = receivePacket.getData();

            int q = 0;
            if (message[0] == 1) {

                DatagramSocket room = new DatagramSocket();
                RoomThread ct = new RoomThread(room, receivePacket.getAddress(), receivePacket.getPort());
                rooms.add(ct);
                ct.start();

            } else if (message[0] == 2) {
                for (int i = 0; i < rooms.size(); i++) {
                    if (rooms.get(i).getPlayersCount() == 1) {
                        System.out.println("Free room found");
                        ResourceLoader.writeLOG("Free room found");

                        rooms.get(i).setIPPlayer2(receivePacket.getAddress());
                        rooms.get(i).setPortPlayer2(receivePacket.getPort());

                        System.out.println(receivePacket.getAddress());
                        System.out.println(receivePacket.getPort());
                        ResourceLoader.writeLOG(receivePacket.getAddress().toString());
                        ResourceLoader.writeLOG(((Integer)receivePacket.getPort()).toString());

                        rooms.get(i).secondPlayerConnected();
                        q = 1;
                        break;
                    }
                }

                if (q == 0) {
                    System.out.println("Free room is not found");
                    ResourceLoader.writeLOG("Free room is not found");
                    q = 1;
                }
            }
        }
    }

    private class RoomThread extends Thread {
        private DatagramSocket roomSocket;
        private int playersCount = 1;
        private int readyToPlayCount = 0;
        private Level level1;
        private Level level2;

        private InetAddress IPPlayer1;
        private int portPlayer1;

        private InetAddress IPPlayer2;
        private int portPlayer2;

        public int getPlayersCount () {
            return playersCount;
        }

        public void setIPPlayer2 (InetAddress IP) {
            IPPlayer2 = IP;
        }

        public void setPortPlayer2 (int port) {
            portPlayer2 = port;
        }

        public RoomThread (DatagramSocket s, InetAddress IP, int port) {
            roomSocket = s;
            IPPlayer1 = IP;
            portPlayer1 = port;
            byte[] sendData = new byte[1];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPPlayer1, portPlayer1);

            try {
                roomSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void secondPlayerConnected () {
            System.out.println("Second player is connected");
            ResourceLoader.writeLOG("Second player is connected");

            byte[] sendData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPPlayer2, portPlayer2);

            try {
                roomSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
            playersCount = 2;
        }

        public void run () {
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                try {
                    System.out.println("Wait to the packet");
                    ResourceLoader.writeLOG("Wait to the packet");

                    roomSocket.receive(receivePacket);

                    System.out.println("The packet received");
                    ResourceLoader.writeLOG("The packet received");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] message = receivePacket.getData();

                // пакет с уровнем
                if (message[0] == 0) {
                    System.out.println("Packet with level received");
                    ResourceLoader.writeLOG("Packet with level received");

                    int[][] tileMap = new int[10][10];
                    int k = 1;

                    for (int i = 0; i < 10; i++) {
                        for (int j = 0; j < 10; j++) {
                            tileMap[i][j] = message[k];
                            k++;
                        }
                    }
                    System.out.println("Level coped");
                    ResourceLoader.writeLOG("Level coped");

                    if (receivePacket.getPort() == portPlayer1 || receivePacket.getAddress() == IPPlayer1) {
                        System.out.println("This is first man's level");
                        ResourceLoader.writeLOG("This is first man's level");

                        level1 = new Level(tileMap);
                        readyToPlayCount++;

                    } else if (receivePacket.getPort() == portPlayer2 || receivePacket.getAddress() == IPPlayer2) {
                        System.out.println("This is second man's level");
                        ResourceLoader.writeLOG("This is second man's level");

                        level2 = new Level(tileMap);
                        readyToPlayCount++;

                    }

                    if (readyToPlayCount == 2) {
                        System.out.println("Both players are ready, because of received levels");
                        ResourceLoader.writeLOG("Both players are ready, because of received levels");

                        byte[] sendData1 = new byte[1024];
                        sendData1[0] = 5;
                        byte[] sendData2 = new byte[1024];
                        sendData2[0] = 6;

                        DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPPlayer1, portPlayer1);
                        try {
                            System.out.println("Sending packet to first player");
                            ResourceLoader.writeLOG("Sending packet to first player");

                            roomSocket.send(sendPacket1);

                            System.out.println("Sending packet to first player is done");
                            ResourceLoader.writeLOG("Sending packet to first player is done");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, IPPlayer2, portPlayer2);
                        try {
                            System.out.println("Sending packet to second player");
                            ResourceLoader.writeLOG("Sending packet to second player");

                            roomSocket.send(sendPacket2);

                            System.out.println("Sending packet to second player is done");
                            ResourceLoader.writeLOG("Sending packet to second player is done");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // обработка выстрела
                } else if (message[0] == 1) {
                    System.out.println("Packet with shot received");
                    ResourceLoader.writeLOG("Packet with shot received");

                    int width = message[1];
                    int height = message[2];
                    byte[] sendData1 = new byte[1024];
                    byte[] sendData2 = new byte[1024];

                    if (receivePacket.getPort() == portPlayer1 || receivePacket.getAddress() == IPPlayer1) {
                        System.out.println("First man shoot");
                        ResourceLoader.writeLOG("First man shoot");

                        if (level2.getTile(width, height) == TileType.CLEAR_FIELD) {
                            System.out.println("and missed");
                            ResourceLoader.writeLOG("and missed");

                            level2.selectTile(width, height, TileType.MISS);

                            sendData1[0] = 0; // ваш выстрел
                            sendData1[1] = 0; // промах
                            sendData1[2] = (byte)(width + 13); // координата по ширине
                            sendData1[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPPlayer1, portPlayer1);
                            try {
                                System.out.println("Sending packet to first player");
                                ResourceLoader.writeLOG("Sending packet to first player");

                                roomSocket.send(sendPacket1);

                                System.out.println("Sending packet to first player is done");
                                System.out.println("First man missed");
                                ResourceLoader.writeLOG("Sending packet to first player is done");
                                ResourceLoader.writeLOG("First man missed");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            sendData2[0] = 1; // вражеский выстрел
                            sendData2[1] = 0; // промах
                            sendData2[2] = (byte)(width + 1); // координата по ширине
                            sendData2[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, IPPlayer2, portPlayer2);
                            try {
                                System.out.println("Sending packet to second player");
                                ResourceLoader.writeLOG("Sending packet to second player");

                                roomSocket.send(sendPacket2);

                                System.out.println("Sending packet to second player is done");
                                ResourceLoader.writeLOG("Sending packet to second player is done");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {

                            level2.selectTile(width, height, TileType.HIT);
                            System.out.println("and hit");
                            ResourceLoader.writeLOG("and hit");

                            sendData1[0] = 0; // ваш выстрел
                            sendData1[1] = 1; // попадание
                            sendData1[2] = (byte)(width + 13); // координата по ширине
                            sendData1[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPPlayer1, portPlayer1);
                            try {
                                System.out.println("Sending packet to first player");
                                ResourceLoader.writeLOG("Sending packet to first player");

                                roomSocket.send(sendPacket1);

                                System.out.println("Sending packet to first player is done");
                                ResourceLoader.writeLOG("Sending packet to first player is done");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            sendData2[0] = 1; // вражеский выстрел
                            sendData2[1] = 1; // попадание
                            sendData2[2] = (byte)(width + 1); // координата по ширине
                            sendData2[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, IPPlayer2, portPlayer2);
                            try {
                                System.out.println("Sending packet to second player");
                                ResourceLoader.writeLOG("Sending packet to second player");

                                roomSocket.send(sendPacket2);

                                System.out.println("Sending packet to second player is done");
                                ResourceLoader.writeLOG("Sending packet to second player is done");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (!level2.isAnyShips()) {
                                System.out.println("All ships are destroyed");
                                ResourceLoader.writeLOG("All ships are destroyed");

                                byte[] sendData3 = new byte[1024];
                                byte[] sendData4 = new byte[1024];

                                sendData3[0] = 3; // вражеские корабли уничтожены (победа)
                                sendData4[0] = 4; // наши корабли уничтожены (поражение)

                                DatagramPacket sendPacket3 = new DatagramPacket(sendData3, sendData3.length, IPPlayer1, portPlayer1);
                                try {
                                    System.out.println("Sending packet to first player");
                                    ResourceLoader.writeLOG("Sending packet to first player");

                                    roomSocket.send(sendPacket3);

                                    System.out.println("Sending packet to first player is done");
                                    System.out.println("First man victory");
                                    ResourceLoader.writeLOG("Sending packet to first player is done");
                                    ResourceLoader.writeLOG("First man victory");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                DatagramPacket sendPacket4 = new DatagramPacket(sendData4, sendData4.length, IPPlayer2, portPlayer2);
                                try {
                                    System.out.println("Sending packet to second player");
                                    ResourceLoader.writeLOG("Sending packet to second player");

                                    roomSocket.send(sendPacket4);

                                    System.out.println("Sending packet to second player is done");
                                    System.out.println("Second man defeated");
                                    ResourceLoader.writeLOG("Sending packet to second player is done");
                                    ResourceLoader.writeLOG("Second man defeated");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                    } else if (receivePacket.getPort() == portPlayer2 || receivePacket.getAddress() == IPPlayer2) {
                        System.out.println("Second man shoot");
                        ResourceLoader.writeLOG("Second man shoot");

                        if (level1.getTile(width, height) == TileType.CLEAR_FIELD) {
                            System.out.println("and missed");
                            ResourceLoader.writeLOG("and missed");

                            level1.selectTile(width, height, TileType.MISS);

                            sendData1[0] = 0; // ваш выстрел
                            sendData1[1] = 0; // промах
                            sendData1[2] = (byte)(width + 13); // координата по ширине
                            sendData1[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPPlayer2, portPlayer2);
                            try {
                                System.out.println("Sending packet to second player");
                                ResourceLoader.writeLOG("Sending packet to second player");

                                roomSocket.send(sendPacket1);

                                System.out.println("Sending packet to second player is done");
                                ResourceLoader.writeLOG("Sending packet to second player is done");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            sendData2[0] = 1; // вражеский выстрел
                            sendData2[1] = 0; // промах
                            sendData2[2] = (byte)(width + 1); // координата по ширине
                            sendData2[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, IPPlayer1, portPlayer1);
                            try {
                                System.out.println("Sending packet to first player");
                                ResourceLoader.writeLOG("Sending packet to first player");

                                roomSocket.send(sendPacket2);

                                System.out.println("Sending packet to first player is done");
                                ResourceLoader.writeLOG("Sending packet to first player is done");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        } else {

                            level1.selectTile(width, height, TileType.HIT);
                            System.out.println("and hit");
                            ResourceLoader.writeLOG("and hit");

                            sendData1[0] = 0; // ваш выстрел
                            sendData1[1] = 1; // попадание
                            sendData1[2] = (byte)(width + 13); // координата по ширине
                            sendData1[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPPlayer2, portPlayer2);
                            try {
                                System.out.println("Sending packet to second player");
                                ResourceLoader.writeLOG("Sending packet to second player");

                                roomSocket.send(sendPacket1);

                                System.out.println("Sending packet to second player is done");
                                ResourceLoader.writeLOG("Sending packet to second player is done");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            sendData2[0] = 1; // вражеский выстрел
                            sendData2[1] = 1; // попадание
                            sendData2[2] = (byte)(width + 1); // координата по ширине
                            sendData2[3] = (byte)(height + 1); // координата по высоте

                            DatagramPacket sendPacket2 = new DatagramPacket(sendData2, sendData2.length, IPPlayer1, portPlayer1);
                            try {
                                System.out.println("Sending packet to first player");
                                ResourceLoader.writeLOG("Sending packet to first player");

                                roomSocket.send(sendPacket2);

                                System.out.println("Sending packet to first player is done");
                                ResourceLoader.writeLOG("Sending packet to first player is done");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (!level1.isAnyShips()) {
                                byte[] sendData3 = new byte[1024];
                                byte[] sendData4 = new byte[1024];
                                System.out.println("All ships are destroyed");
                                ResourceLoader.writeLOG("All ships are destroyed");

                                sendData3[0] = 3; // вражеские корабли уничтожены (победа)
                                sendData4[0] = 4; // наши корабли уничтожены (поражение)

                                DatagramPacket sendPacket3 = new DatagramPacket(sendData3, sendData3.length, IPPlayer2, portPlayer2);
                                try {
                                    System.out.println("Sending packet to second player");
                                    ResourceLoader.writeLOG("Sending packet to second player");

                                    roomSocket.send(sendPacket3);

                                    System.out.println("Sending packet to second player is done");
                                    ResourceLoader.writeLOG("Sending packet to second player is done");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Second man victory");
                                ResourceLoader.writeLOG("Second man victory");

                                DatagramPacket sendPacket4 = new DatagramPacket(sendData4, sendData4.length, IPPlayer1, portPlayer1);
                                try {
                                    System.out.println("Sending packet to first player");
                                    ResourceLoader.writeLOG("Sending packet to first player");

                                    roomSocket.send(sendPacket4);

                                    System.out.println("Sending packet to first player is done");
                                    ResourceLoader.writeLOG("Sending packet to first player is done");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("First man defeated");
                                ResourceLoader.writeLOG("First man defeated");
                            }

                        }

                    }

                } else if (message[0] == 2) {
                    System.out.println("Packet with QUIT action received");
                    ResourceLoader.writeLOG("Packet with QUIT action received");

                    byte[] sendData = new byte[1024];
                    sendData[0] = 2; // код 2 - выигрыш

                    if (receivePacket.getPort() == portPlayer1 || receivePacket.getAddress() == IPPlayer1) {
                        System.out.println("First player leave");
                        ResourceLoader.writeLOG("First player leave");

                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPPlayer2, portPlayer2);
                        try {
                            System.out.println("Sending packet to second player");
                            ResourceLoader.writeLOG("Sending packet to second player");

                            roomSocket.send(sendPacket);

                            System.out.println("Sending packet to second player is done");
                            ResourceLoader.writeLOG("Sending packet to second player is done");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else if (receivePacket.getPort() == portPlayer2 || receivePacket.getAddress() == IPPlayer2) {
                        System.out.println("Second player leave");
                        ResourceLoader.writeLOG("Second player leave");

                        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPPlayer1, portPlayer1);
                        try {
                            System.out.println("Sending packet to first player");
                            ResourceLoader.writeLOG("Sending packet to first player");

                            roomSocket.send(sendPacket);

                            System.out.println("Sending packet to first player is done");
                            ResourceLoader.writeLOG("Sending packet to first player is done");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }

                System.out.println("******************************************");
                System.out.println(receivePacket.getPort());
                System.out.println(receivePacket.getAddress());

                System.out.println(portPlayer1);
                System.out.println(IPPlayer1);

                System.out.println(portPlayer2);
                System.out.println(IPPlayer2);
                System.out.println("******************************************");
                ResourceLoader.writeLOG("******************************************");
                ResourceLoader.writeLOG(((Integer)(receivePacket.getPort())).toString());
                ResourceLoader.writeLOG(receivePacket.getAddress().toString());

                ResourceLoader.writeLOG(((Integer)portPlayer1).toString());
                ResourceLoader.writeLOG(IPPlayer1.toString());

                ResourceLoader.writeLOG(((Integer)portPlayer2).toString());
                ResourceLoader.writeLOG(IPPlayer2.toString());
                ResourceLoader.writeLOG("******************************************");

            }
        }
    }
}
