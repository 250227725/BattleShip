import java.util.Set;

public class GameSettings {
    public static final int MAX_FIELD_WIDTH = Cell.HorizontalCellNames.values().length;
    public static final int MIN_FIELD_WIDTH = 5;
    private static final int DEFAULT_FIELD_WIDTH = 10;

    public static final int MAX_FIELD_HEIGHT = 100;
    public static final int MIN_FIELD_HEIGHT = 5;
    private static final int DEFAULT_FIELD_HEIGHT = 10;

    public static final int MAX_DIFFICULTY = 3;
    public static final int MIN_DIFFICULTY = 1;
    private static final int DEFAULT_DIFFICULTY = 1;

    public static final int[] DEFAULT_SHIP_SETTINGS = new int[]{0, 2, 1, 1};//{0,4,3,2,1}; //todo: It should depend on gamefield size;

    public static final int MAX_PLAYERS = 2;
    public static final int MIN_PLAYERS = 2;
    private static final int DEFAULT_PLAYERS = 2;

    private final int fieldWidth;
    private final int fieldHeight;
    private final int difficulty;
    private final Set<Player> players;

    private GameSettings(Set<Player> players, int fieldHeight, int fieldWidth, int difficulty) {
        this.difficulty = difficulty;
        this.fieldHeight = fieldHeight;
        this.fieldWidth = fieldWidth;
        this.players = players;
    }

    public static GameSettings createSettings(Set<Player> players) {
        return createSettings(players, DEFAULT_FIELD_WIDTH, DEFAULT_FIELD_HEIGHT, DEFAULT_DIFFICULTY);
    }
    public static GameSettings createSettings(Set<Player> players, int width, int height, int difficulty) {
        if (players==null || players.size() > MAX_PLAYERS || players.size() < MIN_PLAYERS) throw new IllegalArgumentException("Некорректное количество игроков");
        if (width > MAX_FIELD_WIDTH || width < MIN_FIELD_WIDTH) throw new IllegalArgumentException("Некорректное значение ширины поля");
        if (height > MAX_FIELD_HEIGHT || height < MIN_FIELD_HEIGHT) throw new IllegalArgumentException("Некорректное значение высоты поля");
        if (difficulty > MAX_DIFFICULTY || difficulty < MIN_DIFFICULTY) throw new IllegalArgumentException("Некорректное значение сложности");
        return new GameSettings(players, height, width, difficulty);
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public Set<Player> getPlayers() {
        return players;
    }
}
