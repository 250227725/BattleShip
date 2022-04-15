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
        manager.initPlayers(this); // todo: init manager here to hide it from other Threads. It also must create copy of Players.
        status = GameStatus.ACTIVE;
        while (isActive())
        {
            Player player = manager.getNextPlayer(); // remove
            manager.nextPlayer();
            //manager.greetingPlayer();
            Project1st.service.playerWelcome(player);
            while (true) {
                //manager.showEnemyBattleField();
                Project1st.service.showEnemyBattleField(player);
                //manager.getPlayerShootGuess(); //todo: manager should hold info about gamefield size
                CellSample shoot = Project1st.service.getPlayerGuess(fieldHeight, fieldWidth);
                //CellStatus result = manager.checkPlayerShootGuess();
                Ship.ShipHitStatus result = checkSuggests(shoot, player);
                if (result == Ship.ShipHitStatus.MISSED) {
                    System.out.println("Вы промахнулись!");
                    break;
                }
                else if (result == Ship.ShipHitStatus.DESTROYED) {
                    if (!checkAliveEnemy(player)) {
                    //    if (!manager.checkAliveEnemy(player)) {
                            System.out.println("Вы уничтожили последний корабль и победили! Игра окончена.");
                        status = GameStatus.ENDED;
                    }
                    else {
                        System.out.println("Вы уничтожили корабль противника.");
                    }
                }
                else if (result == Ship.ShipHitStatus.HITED) {
                    System.out.println("Вы повредили корабль противника.");
                }
                else {
                    throw new IllegalArgumentException();
                }
               // manager.fillPlayerEnemyBattleField(shoot, result);
                Project1st.service.fillEnemyBattleField(player, shoot, result);
            }
        }
        return null;
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
