package org.suai.seabattle.model.parameters;

public enum GameState {

    UNREADY(0),
    READY(1),
    CONTINUE(2),
    WAIT(3),
    QUIT(4),
    LOSE(5),
    WIN(6);

    private int n; // число для каждого состояния

    // конструктор
    GameState (int n) {
        this.n = n;
    }

    // гетер числа для состояния
    public int numeric () {
        return n;
    }

    // гетер состояния для числа
    public static GameState fromNumeric (int n) {
        return switch (n) {
            case 1 -> READY;
            case 2 -> CONTINUE;
            case 3 -> WAIT;
            case 4 -> QUIT;
            case 5 -> LOSE;
            case 6 -> WIN;
            default -> UNREADY;
        };
    }
}
