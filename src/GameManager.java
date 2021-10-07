import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class GameManager {
    public GameManager() {
    }

    public GameManager(Deque<Player> players) {
        this.players = players;
    }

    public void setPlayers(Deque<Player> players) {
        this.players = players;
    }

    private Deque<Player> players = new LinkedList<>();

    public Deque<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {
        if (!players.contains(player))
            players.add(player);
    }

    public void createPlayerSequence() {
        List<Player> allPlayers = new ArrayList<>(players);
        Deque<Player> playerSequence = new LinkedList<>();
        while(allPlayers.size()>0) {
            int index = (int) (Math.random() * allPlayers.size());
            playerSequence.add(allPlayers.get(index));
            allPlayers.remove(index);
        }
        setPlayers(playerSequence);
    }

    public Player getNextPlayer() {
        Player player = players.pop();
        players.add(player);
        return player;
    }
}
