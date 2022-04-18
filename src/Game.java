import java.util.Set;
import java.util.concurrent.Callable;

public class Game implements Callable<String> {
    private final int fieldWidth;
    private final int fieldHeight;
    private final int difficulty;
    private final Set<Player> players;
    private GameStatus status;

    private Game(Set<Player> players, int width, int height, int difficulty) {
        this.fieldWidth = width;
        this.fieldHeight = height;
        this.difficulty = difficulty;
        this.status = GameStatus.NEW;
        this.players = players;
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

    public boolean isActive() {
        return status == GameStatus.ACTIVE;
    }
    public String call() {
        try {
            GameManager manager = new GameManager(players, fieldWidth, fieldHeight, difficulty);
            manager.initPlayers();
            status = GameStatus.ACTIVE;
            manager.startGame();
            while (isActive()) {
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
                            } else {
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
        catch (GameCancelledException | GameInterruptException e) {
            status = GameStatus.ENDED;
            return "Game canceled";
        }
    }

    public void startGame() {
        if (status!=GameStatus.NEW) throw new IllegalArgumentException();
        status = GameStatus.ACTIVE;
    }

    public void endGame() {
        if (status!=GameStatus.ACTIVE) throw new IllegalArgumentException();
        status = GameStatus.ENDED;
    }

    private enum GameStatus {
        NEW,
        ACTIVE,
        ENDED
    }
}
