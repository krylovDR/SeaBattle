package org.suai.seabattle.model.parameters;

import org.suai.seabattle.model.utils.Time;

public class ComputationData {

    public static final int     WIDTH                   = 840; // ширина окна
    public static final int     HEIGHT                  = 455; // высота окна
    public static final String  TITLE                   = "SeaBattle"; // заголовок окна
    public static final int     CLEAR_COLOR             = 0xff000000; // заполнение окна черным цветом в начале
    public static final int     NUM_BUFFERS             = 3; // количество буферов для BufferStrategy

    public static final int     TILE_SCALE              = 35; // размер одной клетки на карте
    public static final int     TILES_IN_WIDTH          = (WIDTH / TILE_SCALE) - 1;  // количество клеток
    public static final int     TILES_IN_HEIGHT         = (HEIGHT / TILE_SCALE) - 1; // в ширину и высоту
    public static final int     START_TILE_IN_WIDTH     = 13; // начальное местоположение активной ячейки в ширину
    public static final int     START_TILE_IN_HEIGHT    = 1; // начальное местоположение активной ячейки в высоту
    public static final int     END_TILE_IN_WIDTH       = TILES_IN_WIDTH; // конечное местоположение в ширину
    public static final int     END_TITLE_IN_HEIGHT     = TILES_IN_HEIGHT; // конечное местоположение в высоту

    public static final float   UPDATE_RATE             = 60.0f; // подсчёт физики в секунду
    public static final float   UPDATE_INTERVAL         = Time.SECOND / UPDATE_RATE; // время между каждым обновлением
    public static final long    IDLE_TIME               = 1; // время отдыха Thread

    public static final String  LEVEL_NAME              = "level.lvl"; // название файла уровня
    public static final int     MAX_SHIP_4              = 1; // количество кораблей длины 4
    public static final int     MAX_SHIP_3              = 2; // количество кораблей длины 3
    public static final int     MAX_SHIP_2              = 3; // количество кораблей длины 2
    public static final int     MAX_SHIP_1              = 4; // количество кораблей длины 1
    public static final int     MAX_SHIPS               = MAX_SHIP_1 + MAX_SHIP_2 + MAX_SHIP_3 + MAX_SHIP_4; // все

}
