import java.util.Collections;
import java.util.Deque;
import java.util.Set;
import java.util.concurrent.Callable;

public class Game implements Callable<String> {
    private final int fieldWidth;
    private final int fieldHeight;
    private final int difficulty;
    private GameStatus status;
    private final Set<Player> players;
    private final GameManager manager;



    private Game(Set<Player> players, int width, int height, int difficulty) {
        this.fieldWidth = width;
        this.fieldHeight = height;
        this.difficulty = difficulty;
        this.status = GameStatus.NEW;
        this.players = players;
        this.manager = new GameManager(players);
    }

    public static Game createGame(Set<Player> players) {
        return createGame(players, 10, 10, 0);
    }

    public static Game createGame(Set<Player> players, int width, int height, int difficulty) {
        if (players==null ||players.size() < 2) throw new IllegalArgumentException("Некорректное количество игроков");
        if (width < Project1st.MIN_FIELD_WIDTH || width > Project1st.MAX_FIELD_WIDTH) throw new IllegalArgumentException("Некорректное значение ширины поля");
        if (height < Project1st.MIN_FIELD_HEIGHT || height > Project1st.MAX_FIELD_HEIGHT) throw new IllegalArgumentException("Некорректное значение высоты поля");
        return new Game(players, width, height, difficulty);
    }

    public static void cancelled() {
    }

    public boolean isActive() {
        return status == GameStatus.ACTIVE;
    }

    public boolean isEnded() {
        return status == GameStatus.ENDED;
    }

    public int playersCount() {
        return players.size();
    }

    public String call() {
        manager.initPlayers();
        manager.startGame();
        while (isActive())
        {
            //manager.nextTurn();
            status = GameStatus.ENDED;
        }
        return null;
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    enum GameStatus {
        NEW,
        ACTIVE,
        ENDED
    }
}
