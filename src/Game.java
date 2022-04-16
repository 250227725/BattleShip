import java.util.Set;
import java.util.concurrent.Callable;

public class Game implements Callable<String> {
    private final int fieldWidth;
    private final int fieldHeight;
    private final int difficulty;
    private GameStatus status;
    private final Set<Player> players;
    private final GameManager manager;



    private Game(Set<Player> players, int width, int height, int difficulty) {
        this.fieldWidth = width;
        this.fieldHeight = height;
        this.difficulty = difficulty;
        this.status = GameStatus.NEW;
        this.players = players;
        this.manager = new GameManager(players);
    }

    public static Game createGame(Set<Player> players) {
        return createGame(players, 10, 10, 0);
    }

    public static Game createGame(Set<Player> players, int width, int height, int difficulty) {
        if (players==null ||players.size() < 2) throw new IllegalArgumentException("Некорректное количество игроков");
        if (width < Project1st.MIN_FIELD_WIDTH || width > Project1st.MAX_FIELD_WIDTH) throw new IllegalArgumentException("Некорректное значение ширины поля");
        if (height < Project1st.MIN_FIELD_HEIGHT || height > Project1st.MAX_FIELD_HEIGHT) throw new IllegalArgumentException("Некорректное значение высоты поля");
        return new Game(players, width, height, difficulty);
    }

    public static void cancelled() {
    }

    public boolean isActive() {
        return status == GameStatus.ACTIVE;
    }

    public boolean isEnded() {
        return status == GameStatus.ENDED;
    }

    public int playersCount() {
        return players.size();
    }

    public String call() throws GameCancelledException, GameInterruptException {
        GameManager manager = new GameManager(players, fieldWidth, fieldHeight, difficulty);
        manager.initPlayers(this);
        status = GameStatus.ACTIVE;
        while (isActive())
        {
            manager.nextPlayer();
            manager.greetingPlayer();
            boolean nextPlayerTurn = false;
            while (!nextPlayerTurn) {
                manager.showEnemyBattleField();
                CellSample shoot = manager.getPlayerShootGuess();
                CellStatus result = manager.executePlayerShootGuess(shoot);
                switch (result) { //todo try extract
                    case MISSED: {
                        manager.showMessage("Вы промахнулись!");
                        nextPlayerTurn = true;
                        break;
                    }
                    case HITTED: {
                        manager.showMessage("Вы повредили корабль противника.");
                        break;
                    }
                    case DESTROYED: {
                        if (!manager.checkAliveEnemy()) {
                            status = GameStatus.ENDED;
                            manager.showMessage("Вы уничтожили последний корабль и победили! Игра окончена.");
                            return manager.getSummaryGameResult();
                        }
                        else {
                            manager.showMessage("Вы уничтожили корабль противника.");
                        }
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException();
                    }

                }
                manager.fillEnemyBattleField(shoot, result);
            }
        }
        return "Game over";
    }


    private Ship.ShipHitStatus checkSuggests(Cell shoot, Player player) {
        Ship.ShipHitStatus result = Ship.ShipHitStatus.MISSED;
        for (Player p : players) {
            if (p != player && p.isAlive()) {
                result = p.checkShoot(shoot);
            }
        }
        return result;
    }

    boolean checkAliveEnemy(Player player) {
        return players.stream()
                .anyMatch(p -> p.isAlive() && p != player);
    }

    public int getFieldWidth() {
        return fieldWidth;
    }

    public int getFieldHeight() {
        return fieldHeight;
    }

    enum GameStatus {
        NEW,
        ACTIVE,
        ENDED
    }
}
