package org.suai.seabattle.view.panels;

import org.suai.seabattle.model.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainMenu {

    public static final int WIDTH   = 200; // ширина окна
    public static final int HEIGHT  = 210; // высота окна

    private static JFrame startMenu; // окно
    private static boolean created = false; // создано ли окно

    // создание Main Menu
    public static void create () {
        if (created) {
            return; //если окно создано - ничего не делать
        }

        final JFrame startMenu = new JFrame("SeaBattle"); // окно с именем "SeaBattle"
        startMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //закрыть программу на "крестик"

        Dimension size = new Dimension(WIDTH, HEIGHT); // объект с размером листа
        startMenu.setSize(size); // установили размер окна

        startMenu.setLocationRelativeTo(null); //разместить окно по середине экрана
        startMenu.setResizable(false); // запрет пользователю менять размер окна

        MainMenuPanel panel = new MainMenuPanel(); // создание панели с кнопками
        panel.setPreferredSize(size); // размер листа
        panel.setLayout(null); // расположение контента не задано

        // создание кнопки "Create Game"
        JButton startButton = new JButton("Create Game"); // создание кнопки
        startButton.setBounds(30, 30, 140, 30); // местоположение и размер
        panel.add(startButton); // добавление кнопки на панель
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu.dispose(); // закрытие видимости окна главного меню
                Game seaBattle = null; // создание объекта игры
                try {
                    seaBattle = new Game(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                seaBattle.start(); // запуск игру
            }
        });

        // создание кнопки "Connect"
        JButton connectButton = new JButton("Connect"); // создание кнопки
        connectButton.setBounds(30, 70, 140, 30); // местоположение и размер
        panel.add(connectButton); // добавление кнопки на панель
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startMenu.dispose(); // закрытие видимости окна главного меню
                Game seaBattle = null; // создание объекта игры
                try {
                    seaBattle = new Game(false);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                seaBattle.start(); // запуск игру
            }
        });

        // "Help"
        JButton helpButton = new JButton("Help");
        helpButton.setBounds(30, 110, 140, 30);
        panel.add(helpButton);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(startMenu, "Стрелки - движение:\nUP - вверх \nDOWN - вниз \n" +
                                "LEFT - влево \nRIGHT - вправо \nSPACE - сделать выстрел в указанную ячейку \nESCAPE - " +
                                "выйти из игры \nR - перемешать корабли \nС - поменять цвет кораблей \nENTER - " +
                                "подтвердить готовность к игре", "Help",
                        JOptionPane.PLAIN_MESSAGE);
            }
        });

        // "Quit"
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(30, 150, 140, 30);
        panel.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                created = false;
                startMenu.dispose();
            }
        });

        startMenu.add(panel); // добавление панели в окно
        startMenu.pack(); // укладка всего

        created = true; // окно заупстилось

        startMenu.setVisible(true); // делаем окно видимым

    }

}