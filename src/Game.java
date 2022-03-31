import java.util.Deque;
import java.util.stream.Collectors;

public class Game implements Runnable{
    private final Deque<Player> players;
    private GameStatus status = GameStatus.NEW;
    private Game(Deque<Player> players, int width, int height) {
        this.players = players;
        this.FIELD_WIDTH = width;
        this.FIELD_HEIGHT = height;
    }
    private final int FIELD_WIDTH;
    private final int FIELD_HEIGHT;

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
        status = GameStatus.ACTIVE;
    }

    enum GameStatus {
        NEW,
        ACTIVE,
        ENDED
    }
}
