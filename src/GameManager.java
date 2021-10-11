import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameManager {
    private Deque<Player> players = new LinkedList<>();

    /**
     * Create gameManager for game
     * @param gamePlayers - players list
     */
    public GameManager(List<Player> gamePlayers) {
        generatePlayerSequence(gamePlayers);
    }

    /**
     * Return players list
     */
    public Deque<Player> getPlayers() {
        return players;
    }

    /**
     * Return next player
     */
    public Player getNextPlayer() {
        Player player = players.pop();
        players.add(player);
        return player;
    }

    /**
     * Generate sequence of players for serial game turn
     * @param gamePlayers - players list for game
     */
    private void generatePlayerSequence(List<Player> gamePlayers) {
        List<Player> playerList = new ArrayList<>(gamePlayers);
        while(playerList.size() > 0) {
            int index = (int) (Math.random() * playerList.size());
            players.add(playerList.get(index));
            playerList.remove(index);
        }
    }
}
