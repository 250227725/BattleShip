import java.util.ArrayList;
import java.util.List;

public class Game {

    public List<Player> getPlayers() {
        return players;
    }

    private List<Player> players;
    private static Game game;

    public Game(List players) {
        this.players = players;
    }

    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        players.add(new Player(true));
        players.add(new Player(false));
        game = new Game(players);


    }
}
