import java.util.Deque;
import java.util.Set;
import java.util.concurrent.Callable;

public class Game implements Callable<String> {
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

    public static Game createGame(Set<Player> players, int width, int height, int difficulty) {
        return null;
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
        // 1. Get battleShips from Players
        // 2. Generate battleShips for AI
        // 3. Create GameManager
        status = GameStatus.ACTIVE;
        while (isActive())
        {
            System.out.println("The Game");
            status = GameStatus.ENDED;
        }
        // 4. Run Game Manager
        // 5. Show game statistic
        // 6. Quit
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
