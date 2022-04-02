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



//    public static Game createGame(Deque<Player> players, int width, int height) throws IllegalArgumentException {
//        if (width < 1 || width > Project1st.MAX_FIELD_WIDTH || height < 1 || height > Project1st.MAX_FIELD_HEIGHT) {
//            throw new IllegalArgumentException();
//        }
//        long count = players.stream()
//                .distinct()
//                .count();
//        if (count < 2 || count != players.size()) {
//            throw new IllegalArgumentException();
//        }
//        return new Game(players, width, height);
//    }

    public static Game createGame(Set<Player> players, int width, int height, int difficulty) {
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
