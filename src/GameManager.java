import java.util.*;

public class GameManager {
    private Deque<Player> players;
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
        if (isStarted) throw new UnsupportedOperationException();
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

    private CellSample getPlayerShootGuess() throws GameCancelledException, GameInterruptException {
        //todo:
        //2. add duplicate fire check for difficulty lvl
        return currentPlayer.guess(ioManager);
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
        showMessage("Победитель:" + currentPlayer.getName());
        showMessage("Для победы потребовалось выстрелов:" + currentPlayer.getShootCount());
        return "Results was ate by Barbariska";
    }

    public void fillEnemyBattleField(CellSample shoot, CellStatus result) {
        currentPlayer.fillEnemyBattlefield(shoot, result);
    }

    public void run() {
        try {
            initPlayers();
            showMessage(Messages.CLEAR_SCREEN);
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
                    fillUnavailableEnemyBattleField(shoot, result);
                    //todo try extract
                    switch (result) {
                        case MISSED -> {
                            showMessage(Messages.MISSED);
                            nextPlayerTurn = true;
                            //GameService.waitForAnyKey(ioManager);
                            showMessage(Messages.CLEAR_SCREEN);
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
            showMessage(Messages.CLEAR_SCREEN);
            showMessage(Messages.GAME_OVER);
        }
    }

    private void fillUnavailableEnemyBattleField(CellSample shoot, CellStatus result) {
        if (result == CellStatus.MISSED) return;

        if (shoot.getX() > 0) {
            if (shoot.getY() > 0) {
                currentPlayer.enemyBattlefield[shoot.getY() - 1][shoot.getX() - 1] = CellStatus.MISSED;
            }
            if (shoot.getY() < currentPlayer.enemyBattlefield.length - 1) {
                currentPlayer.enemyBattlefield[shoot.getY() + 1][shoot.getX() - 1] = CellStatus.MISSED;
            }
        }

        if (shoot.getX() < currentPlayer.enemyBattlefield[0].length - 1) {
            if (shoot.getY() > 0) {
                currentPlayer.enemyBattlefield[shoot.getY() - 1][shoot.getX() + 1] = CellStatus.MISSED;
            }
            if (shoot.getY() < currentPlayer.enemyBattlefield.length - 1) {
                currentPlayer.enemyBattlefield[shoot.getY() + 1][shoot.getX() + 1] = CellStatus.MISSED;
            }
        }

        if (result == CellStatus.DESTROYED) {
            if (shoot.getY() > 0) {
                if (currentPlayer.enemyBattlefield[shoot.getY() - 1][shoot.getX()] == CellStatus.DESTROYED) {
                    Optional<CellSample> cell = getNextNotDestroyed(shoot.getY(), shoot.getX(), 8);
                    if (cell.isPresent()) {
                        currentPlayer.enemyBattlefield[cell.get().getY()][cell.get().getX()] = CellStatus.MISSED;
                    }
                }
                else {
                    currentPlayer.enemyBattlefield[shoot.getY() - 1][shoot.getX()] = CellStatus.MISSED;
                }
            }

            if (shoot.getX() > 0) {
                if (currentPlayer.enemyBattlefield[shoot.getY()][shoot.getX() - 1] == CellStatus.DESTROYED) {
                    Optional<CellSample> cell = getNextNotDestroyed(shoot.getY(), shoot.getX(), 4);
                    if (cell.isPresent()) {
                        currentPlayer.enemyBattlefield[cell.get().getY()][cell.get().getX()] = CellStatus.MISSED;
                    }
                }
                else {
                    currentPlayer.enemyBattlefield[shoot.getY()][shoot.getX() - 1] = CellStatus.MISSED;
                }
            }

            if (shoot.getY() < currentPlayer.enemyBattlefield.length - 1) {
                if (currentPlayer.enemyBattlefield[shoot.getY() + 1][shoot.getX()] == CellStatus.DESTROYED) {
                    Optional<CellSample> cell = getNextNotDestroyed(shoot.getY(), shoot.getX(), 2);
                    if (cell.isPresent()) {
                        currentPlayer.enemyBattlefield[cell.get().getY()][cell.get().getX()] = CellStatus.MISSED;
                    }
                }
                else {
                    currentPlayer.enemyBattlefield[shoot.getY() + 1][shoot.getX()] = CellStatus.MISSED;
                }
            }

            if (shoot.getX() < currentPlayer.enemyBattlefield[0].length - 1) {
                if (currentPlayer.enemyBattlefield[shoot.getY()][shoot.getX() + 1] == CellStatus.DESTROYED) {
                    Optional<CellSample> cell = getNextNotDestroyed(shoot.getY(), shoot.getX(), 4);
                    if (cell.isPresent()) {
                        currentPlayer.enemyBattlefield[cell.get().getY()][cell.get().getX()] = CellStatus.MISSED;
                    }
                }
                else {
                    currentPlayer.enemyBattlefield[shoot.getY()][shoot.getX() + 1] = CellStatus.MISSED;
                }
            }
        }
    }

    private Optional<CellSample> getNextNotDestroyed(int y, int x, int i) {
        int dx = 0;
        int dy = 0;
        switch (i) {
            case 8 : {
                dy = -1;
            }
            case 4 : {
                dx = -1;
            }
            case 2: {
                dy = 1;
            }
            case 6: {
                dx = 1;
            }
        }

        if (y + dy < 0 || y + dy > currentPlayer.enemyBattlefield.length - 1|| x + dx < 0 || x + dx > currentPlayer.enemyBattlefield[0].length - 1)
            return Optional.empty();

        if (currentPlayer.enemyBattlefield[y+dy][x+dx] == CellStatus.DESTROYED)
            return getNextNotDestroyed(y+dy, x+dx, i);

        return Optional.of(new CellSample(y+dy, x+dx));

    }
}
