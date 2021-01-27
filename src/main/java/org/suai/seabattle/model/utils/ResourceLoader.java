package org.suai.seabattle.model.utils;

import org.suai.seabattle.model.parameters.ComputationData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ResourceLoader {

    public static final String  PATH_LVL    = "src/main/resources/"; // путь к ресурсам
    public static final String  PATH    = "/data/img/";

    // загрузка изображения
    public static Image loadImage (String fileName) {
        Image image = null; // изображение

        image = new ImageIcon(ResourceLoader.class.getResource(PATH + fileName)).getImage(); // загрузка изображения

        return image; // возвращаем изображение
    }

    // считывание уровня из файла
    public static int[][] levelParser(String fileName) {
        int[][] result = new int[ComputationData.TILES_IN_HEIGHT][ComputationData.TILES_IN_WIDTH]; // массив на выход

        // открываем ресурс в try/catch (чтобы он сам закрывался, в случае исключения)
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_LVL + fileName))) {
            String line;
            int j = 0;

            // считывание построчно до конца файла
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" "); // массив токенов с числами, пропуская делиметеры (пробелы)

                // запись в двумерный массив int
                for (int i = 0; i < tokens.length; i++){
                    result[j][i] = Integer.parseInt(tokens[i]);
                }
                j++; // переход на следующую строку в массиве int
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /*// сохранение уровня в файл
    public static void exportLevel (int[][] level, String fileName) {

        // открытие файла, так чтобы он при исключении закрывался сам
        try (FileWriter writer = new FileWriter(PATH_LVL + fileName)) {

            StringBuilder result = new StringBuilder(); // оформление уровеня под запись в файл
            int i = 0;
            int j = 1;
            while (i < ComputationData.TILES_IN_HEIGHT) {
                result.append(level[i][0]);
                while (j < ComputationData.TILES_IN_WIDTH) {
                    result.append(" ");
                    result.append(level[i][j]);
                    j++;
                }
                if (i + 1 != ComputationData.TILES_IN_HEIGHT) {
                    result.append("\n");
                }
                j = 1;
                i++;
            }

            writer.write(result.toString()); // запись в файл

        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    public static void writeLOG (String text) {
        try(FileWriter writer = new FileWriter("log.txt", true)) {
            writer.write(text);
            writer.append("\n");
            writer.flush();
        } catch(IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
