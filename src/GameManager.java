import java.util.*;

public class GameManager {
    private Deque<Player> players = new LinkedList<>();
    private Player currentPlayer;
    private final int fieldWidth;
    private final int fieldHeight;
    private final int difficulty;
    public final int[] shipsSetup;
    private final IOManager ioManager = Project1st.IO_MANAGER;
    private boolean isStarted = false;
    private boolean isEnded = false;

    public GameManager(Set<Player> gamePlayers, int height, int width, int difficulty) {
        this.fieldHeight = height;
        this.fieldWidth = width;
        this.difficulty = difficulty;
        this.shipsSetup = GameSettings.DEFAULT_SHIP_SETTINGS;
        this.players = generatePlayerSequence(gamePlayers);
    }

    public static GameManager getInstance(GameSettings settings) {
        return new GameManager(settings.getPlayers(), settings.getFieldHeight(), settings.getFieldWidth(), settings.getDifficulty());
    }

    public boolean repeat() {
        //todo implement logic
        showMessage("Хотите сыграть еще раз?");
        return false;
    }

    public void startGame() {
        if (isStarted) throw new UnsupportedOperationException();;
        isStarted = true;
        showMessage(Messages.CLEAR_SCREEN);
        showMessage(Messages.START);
    }

    public Deque<Player> getPlayers() { //todo: made private
        return players;
    }
    public int playersCount() {return players.size();}
    public Player getNextPlayer() { //todo : test for alive player
        Player player = players.pop();
        players.add(player);
        return player;
    } //todo: made private

    public void nextPlayer() {
        currentPlayer = getNextPlayer();
    }

    private Deque<Player> generatePlayerSequence(Set<Player> gamePlayers) {
        Deque<Player> result = new LinkedList<>();
        List<Player> playerList = new ArrayList<>(gamePlayers);
        while(playerList.size() > 0) {
            int index = (int) (Math.random() * playerList.size());
            result.add(playerList.get(index).clone());
            playerList.remove(index);
        }
        return result;
    }

    public void initPlayers() throws GameCancelledException, GameInterruptException {
        CellStatus[][] playerField = GameService.getEmptyField(fieldHeight, fieldWidth);
        for (Player player :players) {
            showMessage(Messages.CLEAR_SCREEN);
            showMessage("Инициализация кораблей игрока " + player.getName());
            player.init(GameService.copyBattleField(playerField), ioManager);
        }
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
        return GameService.getPlayerGuess(fieldHeight, fieldWidth, ioManager);
    }

    public CellStatus executePlayerShootGuess(Cell shoot) {
        CellStatus result = CellStatus.UNKNOWN;
        for (Player player : players) {
            if (player != currentPlayer && player.isAlive()) { //todo: multiplayer mode should return MAP (PlayerID, CellStatus)
                result = player.checkShoot(shoot);
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
        showMessage("Для победы потребовалось выстрелов:" + currentPlayer.getShootCount());
        return "Results was ate by Barbariska";
    }

    public void fillEnemyBattleField(CellSample shoot, CellStatus result) {
        currentPlayer.fillEnemyBattlefield(shoot, result);
    }

    public void run() {
        try {
            initPlayers();
            startGame();
            while (isStarted&&!isEnded) {
                nextPlayer();
                greetingPlayer();
                boolean nextPlayerTurn = false;
                while (!nextPlayerTurn) {
                    showEnemyBattleField();
                    CellSample shoot = getPlayerShootGuess();
                    currentPlayer.increaseShotCount();
                    CellStatus result = executePlayerShootGuess(shoot);
                    fillEnemyBattleField(shoot, result);
                    //todo try extract
                    switch (result) {
                        case MISSED -> {
                            showMessage(Messages.MISSED);
                            nextPlayerTurn = true;
                        }
                        case HITTED -> {
                            showMessage(Messages.HITTED);
                        }
                        case DESTROYED -> {
                            if (!checkAliveEnemy()) {
                                isEnded = true;
                                showMessage(Messages.WIN);
                                showEnemyBattleField();
                                showMessage(getSummaryGameResult());
                                return;
                            } else {
                                showMessage(Messages.DESTROYED);
                            }
                        }
                        default -> {
                            throw new IllegalArgumentException();
                        }
                    }
                }
            }
        }
        catch (GameCancelledException | GameInterruptException e) {
            isEnded = true;
            showMessage(Messages.GAME_OVER);
        }
    }
}
