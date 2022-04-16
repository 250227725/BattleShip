import java.util.*;

public class GameManager {
    private Deque<Player> players = new LinkedList<>();
    private Player currentPlayer;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int difficulty;
    private final IOManager ioManager = Project1st.IO_MANAGER;

    /**
     * Create gameManager for game
     * @param gamePlayers - players list
     */
    public GameManager(Set<Player> gamePlayers) {
        this(gamePlayers, 10, 10, 1);
    } //todo : remove

    public GameManager(Set<Player> gamePlayers, int height, int width, int difficulty) {
        this.fieldHeight = height;
        this.fieldWidth = width;
        this.difficulty = difficulty;
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
    private void generatePlayerSequence(Set<Player> gamePlayers) { //todo create clone of Players to protect from outer changes by another threads
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

    public void greetingPlayer() {
        ioManager.showMessage("Ход игрока " + currentPlayer.getName());
    }

    public void showEnemyBattleField() {
        ioManager.printBattlefield(currentPlayer);
    }

    public CellSample getPlayerShootGuess() throws GameCancelledException, GameInterruptException {
        //todo:
        //1. inline dimension checks here
        //2. add dublicate fire check for difficulty lvl
        //3. represent IOManager to Service and make it methods static
        return Project1st.service.getPlayerGuess(fieldHeight, fieldWidth);
    }

    public CellStatus executePlayerShootGuess(Cell shoot) {
        CellStatus result = CellStatus.UNKNOWN;
        for (Player player : players) {
            if (player != currentPlayer && player.isAlive()) { //todo: multiplayer mode should return MAP (PlayerID, CellStatus)
                result = GameService.castShipToCellStatus(player.checkShoot(shoot));
            }
        }
        return result;
    }

    public boolean checkAliveEnemy() {
            return players.stream()
                    .anyMatch(player -> player.isAlive() && player != currentPlayer);
    }

    public void showMessage(String message) {
        ioManager.showMessage(message);
    }

    public String getSummaryGameResult() { //todo
        return "Results was ate by Barbariska";
    }

    public void fillEnemyBattleField(CellSample shoot, CellStatus result) {
        currentPlayer.fillEnemyBattlefield(shoot, result);
    }
}
