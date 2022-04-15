import java.util.*;

public class GameManager {
    private Deque<Player> players = new LinkedList<>();
    private Player  currentPlayer;

    /**
     * Create gameManager for game
     * @param gamePlayers - players list
     */
    public GameManager(Set<Player> gamePlayers) {
        generatePlayerSequence(gamePlayers);
    }

    /**
     * Starting the game
     */
    public void startGame() {

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
    public Player getNextPlayer() { //todo : test for alive player
        Player player = players.pop();
        players.add(player);
        return player;
    } //todo: made private

    public void nextPlayer() {
        currentPlayer = getNextPlayer();
    }

    /**
     * Generate sequence of players for serial game turn
     * @param gamePlayers - players list for game
     */
    private void generatePlayerSequence(Set<Player> gamePlayers) {
        List<Player> playerList = new ArrayList<>(gamePlayers);
        while(playerList.size() > 0) {
            int index = (int) (Math.random() * playerList.size());
            players.add(playerList.get(index));
            playerList.remove(index);
        }
    }

    public void initPlayers(Game game) throws GameCancelledException, GameInterruptException {
        for (Player player :players) {
            player.init(game);
        }
    }

    public void fillPlayerEnemyBattleField(CellSample shoot, CellStatus result) {
        currentPlayer.fillEnemyBattlefield(shoot, result);
    }

}
