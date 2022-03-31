import java.util.Deque;
import java.util.stream.Collectors;

public class Game implements Runnable{
    private final Deque<Player> players;
    private GameStatus status = GameStatus.NEW;
    private Game(Deque<Player> players) {
        this.players = players;
    }

    public static Game createGame(Deque<Player> players) throws IllegalArgumentException {
        long count = players.stream()
                .distinct()
                .count();
        if (count < 2 || count != players.size()) {
            throw new IllegalArgumentException();
        }
        return new Game(players);
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
