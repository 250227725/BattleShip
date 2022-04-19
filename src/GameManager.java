import java.util.*;
import java.util.stream.Collectors;

public class GameManager { //todo: use only Game class fields
    private Deque<Player> players = new LinkedList<>();
    private Player currentPlayer;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int difficulty;
    public final int[] shipsSetup;
    private final IOManager ioManager = Project1st.IO_MANAGER;
    private final Game game;

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
        this.shipsSetup = Project1st.shipsSetup;
        generatePlayerSequence(gamePlayers);
        this.game = Game.createGame(gamePlayers);
    }

    public static GameManager getInstance(GameSettings settings) {
        return new GameManager(settings.getPlayers(), settings.getFieldHeight(), settings.getFieldWidth(), settings.getDifficulty());
    }

    public boolean repeat() {
        //todo implement logic
        showMessage("Хотите сыграть еще раз?");
        return false;
    }

    /**
     * Starting the game
     */
    public void startGame() {
        showMessage(Messages.CLEAR_SCREEN);
        showMessage(Messages.START);
        game.startGame();
    }

    /**
     * Return players list
     */
    public Deque<Player> getPlayers() {
        return players;
    }
    public int playersCount() {return players.size();}

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
        List<Player> playerList = gamePlayers.stream()
                .map(Player::clone)
                .collect(Collectors.toList());
        while(playerList.size() > 0) {
            int index = (int) (Math.random() * playerList.size());
            players.add(playerList.get(index));
            playerList.remove(index);
        }
    }

    public void initPlayers() throws GameCancelledException, GameInterruptException {
        CellStatus[][] playerField = GameService.getEmptyField(fieldHeight, fieldWidth);
        for (Player player :players) {
            showMessage(Messages.CLEAR_SCREEN);
            showMessage("Инициализация кораблей игрока " + player.getName());
            player.init(GameService.copyBattleField(playerField));
        }
    }

    public void fillPlayerEnemyBattleField(CellSample shoot, CellStatus result) {
        currentPlayer.fillEnemyBattlefield(shoot, result);
    }

    public void greetingPlayer() {
        ioManager.showMessage("Ход игрока " + currentPlayer.getName());
    }

    public void showEnemyBattleField() {
        ioManager.printBattlefield(currentPlayer.getEnemyBattlefield());
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

    public void run() {
        try {
            initPlayers();
            startGame();
            while (game.isActive()) {
                nextPlayer();
                greetingPlayer();
                boolean nextPlayerTurn = false;
                while (!nextPlayerTurn) {
                    showEnemyBattleField();
                    CellSample shoot = getPlayerShootGuess();
                    CellStatus result = executePlayerShootGuess(shoot);
                    fillEnemyBattleField(shoot, result);
                    switch (result) { //todo try extract
                        case MISSED: {
                            showMessage("Вы промахнулись!");
                            nextPlayerTurn = true;
                            break;
                        }
                        case HITTED: {
                            showMessage("Вы повредили корабль противника.");
                            break;
                        }
                        case DESTROYED: {
                            if (!checkAliveEnemy()) {
                                game.endGame();
                                showMessage(Messages.WIN);
                                showEnemyBattleField();
                                showMessage(getSummaryGameResult());
                                return;
                            } else {
                                showMessage("Вы уничтожили корабль противника.");
                            }
                            break;
                        }
                        default: {
                            throw new IllegalArgumentException();
                        }

                    }
                }
            }
        }
        catch (GameCancelledException | GameInterruptException e) {
            game.endGame();
            showMessage("Game canceled");
        }
    }
}
