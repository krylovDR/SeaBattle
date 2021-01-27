package org.suai.seabattle.model.level;

public enum TileType {

    // перечисляем содержимое клеток
    EMPTY(0),
    CLEAR_FIELD(1),
    MISS(2),
    HIT(3),
    SHIP_GREEN(4),
    SHIP_PURPLE(5),
    SHIP_YELLOW(6);

    private int n; // число для каждой клетки

    // конструктор
    TileType (int n) {
        this.n = n;
    }

    // гетер числа для клетки
    public int numeric () {
        return n;
    }

    // гетер клетки для числа
    public static TileType fromNumeric (int n) {
        return switch (n) {
            case 1 -> CLEAR_FIELD;
            case 2 -> MISS;
            case 3 -> HIT;
            case 4 -> SHIP_GREEN;
            case 5 -> SHIP_PURPLE;
            case 6 -> SHIP_YELLOW;
            default -> EMPTY;
        };
    }

}
