import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private Deque<Player> players = new LinkedList<>();

    public GameManager(List<Player> gamePlayers) {
        generatePlayerSequence(gamePlayers);
    }

    @Deprecated
    public GameManager() {

    }

    public Deque<Player> getPlayers() {
        return players;
    }

    public void generatePlayerSequence(List<Player> gamePlayers) {
        List<Player> playerList = new ArrayList<>(gamePlayers);
        while(playerList.size() > 0) {
            int index = (int) (Math.random() * playerList.size());
            players.add(playerList.get(index));
            playerList.remove(index);
        }
    }

    public Player getNextPlayer() {
        Player player = players.pop();
        players.add(player);
        return player;
    }

    @Deprecated
    public void addPlayer(Player player) {
    }

    @Deprecated
    public void createPlayerSequence() {
    }
}
