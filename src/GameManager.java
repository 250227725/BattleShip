import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private Deque<Player> players = new LinkedList<>();

    public Deque<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (!players.contains(player))
            players.add(player);
    }

    public void orderPlayers() {
    }

    public Player getNextPlayer() {
        Player player = players.pop();
        return player;
    }
}
