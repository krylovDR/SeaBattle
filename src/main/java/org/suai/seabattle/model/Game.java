package org.suai.seabattle.model;

import org.suai.seabattle.controller.Input;
import org.suai.seabattle.model.parameters.ComputationData;
import org.suai.seabattle.model.parameters.GameState;
import org.suai.seabattle.model.utils.Time;
import org.suai.seabattle.view.Display;
import org.suai.seabattle.view.graphics.Background;
import org.suai.seabattle.view.graphics.TexturePath;

import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Game implements Runnable {

    private static boolean running; // запущена ли игра
    private Thread gameThread; // дополнительный процесс для игры
    private Graphics2D graphics; // объект графики для изменений в окне
    private Input input; // для ввода
    private Background bg; // фон

    // конструктор
    public Game(boolean isHost) throws IOException {
        running = false; // игра не запущена

        Display.create(ComputationData.WIDTH, ComputationData.HEIGHT, ComputationData.TITLE,
                ComputationData.CLEAR_COLOR, ComputationData.NUM_BUFFERS); // создание окна игры
        graphics = Display.getGraphics(); // для рисований изменений в окне

        input = new Input(ComputationData.LEVEL_NAME, isHost); // содание объекта ввода
        Display.addGameInputListener(input); // создание связи ввода с дисплеем

        bg = new Background(TexturePath.BATTLEFIELD); // создание фона

    }

    // запуск игры
    // synchronized - чтобы функция не могла вызываться из разных процессов (Thread) одновременно
    public synchronized void start() {
        if (running) {
            return; // если запущен - ничего не делать
        }

        running = true; // игра запустилась
        gameThread = new Thread(this, "SeaBattle"); // конструктор с параметром класса,
        // имплементриуещего с Runnable, и имя потока
        gameThread.start(); // запустит метод run()
    }

    // остановка игры
    public synchronized void stop() {
        if (!running) {
            return; // если не запущен - ничего не делать
        }

        running = false; // игра остановлена

        cleanUp(); // завершение работы
    }

    // расчёты
    private void update() {

        if (input.getGS() == GameState.QUIT) {
            stop();
        }

        if (input.getGS() == GameState.WIN) {
            bg.changeTexture(TexturePath.WIN);
        } else if (input.getGS() == GameState.LOSE) {
            bg.changeTexture(TexturePath.LOSE);
        }

    }

    // "рисует" всё посчитанное в update для вывода на экран
    private void render() {
        Display.clear(); // очищает буфер на цвет в конструкторе

        bg.render(graphics); // задний фон

        if (!(input.getGS() == GameState.WIN || input.getGS() == GameState.LOSE)) {
            input.renderLevel(graphics); // уровень
            input.renderUser(graphics); // активная ячейка
        }

        Display.swapBuffers(); // обновить картинку на экране

    }

    // главная функция с циклом
    public void run() {
        int fps = 0; // количество кадров в секунду
        int upd = 0; // количество апдейтов в секунду
        int updl = 0; // количество апдейтов в цикле подряд

        long count = 0; // для подсчёта общего времени игры

        float delta = 0; // для подсчёта количества апдейтов
        long lastTime = Time.get(); // время запуска цикла
        boolean render = false; // нужно ли обновлять экран
        long elapsedTime; // время итерации цикла
        long now; // время в цикле на данный момент

        while (running) {
            now = Time.get(); // настоящее время
            elapsedTime = now - lastTime; // время итерации цикла
            lastTime = now; // время начала итерации цикла

            count += elapsedTime; // общее время игры

            render = false;
            delta += (elapsedTime / ComputationData.UPDATE_INTERVAL); // количество апдейтов на каждое целое число

            // 60 апдейтов в секунду
            while (delta > 1) {
                update(); // обновляем
                upd++; // подсчёт количества апдейтов
                delta--;

                if (render) {
                    updl++; // подсчёт количества апдейтов в цикле подряд
                } else {
                    render = true; // на экране что-то должно изменится после апдейта
                }
            }

            if (render) {
                render(); // перерисовываем экран
                fps++; // подсчёт fps
            } else {
                try {
                    Thread.sleep(ComputationData.IDLE_TIME); // даём отдохнуть процессу
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // вывод данных в заголовок
            if (count >= Time.SECOND) {
                Display.setTitle(ComputationData.TITLE + " || Fps: " + fps + " | Upd: " + upd + " | Updl: " + updl);
                upd = 0;
                fps = 0;
                updl = 0;
                count = 0;
            }

        }
    }

    // для закрытия ресурсов
    private void cleanUp() {
        Display.destroy(); // закрыть окно
    }

}