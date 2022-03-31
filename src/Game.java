import java.util.Deque;

public class Game implements Runnable{
    private final Deque<Player> players;
    private GameStatus status = GameStatus.NEW;
    private Game(Deque<Player> players, int width, int height) {
        this.players = players;
        this.fieldWidth = width;
        this.fieldHeight = height;
    }
    private final int fieldWidth;
    private final int fieldHeight;

    public static Game createGame(Deque<Player> players, int width, int height) throws IllegalArgumentException {
        if (width < 1 || width > Project1st.MAX_FIELD_WIDTH || height < 1 || height > Project1st.MAX_FIELD_HEIGHT) {
            throw new IllegalArgumentException();
        }
        long count = players.stream()
                .distinct()
                .count();
        if (count < 2 || count != players.size()) {
            throw new IllegalArgumentException();
        }
        return new Game(players, width, height);
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

    public void run() {
        // 1. Get battleShips from Players
        // 2. Generate battleShips for AI
        // 3. Create GameManager
        status = GameStatus.ACTIVE;
        // 4. Run Game Manager
        status = GameStatus.ENDED;
        // 5. Show game statistic
        // 6. Quit
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
