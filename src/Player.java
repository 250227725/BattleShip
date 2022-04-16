import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public abstract class Player {
    private final String name;
    private final boolean isHuman;
    private boolean isAlive;
    Map<Ship, String> ships;
    private CellStatus[][] enemyBattlefield; //todo: multiplayer mode should be MAP (PlayerID, CellStatus[][])

    protected Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.ships = new HashMap<>();
    }

    public abstract boolean isHuman();
    public Map<Ship, String> getShips() {
        return ships;
    }
    public boolean isAlive() {
        return isAlive;
    }
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public CellStatus[][] getEnemyBattlefield() {
        return enemyBattlefield;
    }

    public String getName() {
        return name;
    }

    public void init(Game game) throws GameCancelledException, GameInterruptException {
        if (isAlive()) return;
        initEnemyBattlefield(game);
        CellStatus[][] playerField = GameService.getEmptyField(game.getFieldHeight(), game.getFieldWidth());
        generateShips(playerField);
        isAlive = true;
    }

    private void initEnemyBattlefield(Game game) {
        initEnemyBattlefield(game.getFieldHeight(), game.getFieldWidth());
    }

    public void initEnemyBattlefield(int height, int width) {
        enemyBattlefield = GameService.getEmptyField(height, width);
    }

    abstract void generateShips(CellStatus[][] playerField) throws GameCancelledException, GameInterruptException;

    public Ship.ShipHitStatus checkShoot(Cell shoot) {
        Optional<Ship.ShipHitStatus> result = ships.entrySet().stream()
                .filter(s -> s.getKey().isAlive())
                .map(s -> s.getKey().hit(shoot))
                .filter(s -> s != Ship.ShipHitStatus.MISSED)
                .findAny();
        return result.isEmpty() ? Ship.ShipHitStatus.MISSED : result.get();
    };

    public void fillEnemyBattlefield(CellSample shoot, Ship.ShipHitStatus result) {
        fillEnemyBattlefield(shoot, result == Ship.ShipHitStatus.MISSED ? CellStatus.MISSED :
                        result == Ship.ShipHitStatus.HITED ? CellStatus.HITTED : CellStatus.DESTROYED);
    }

    public void fillEnemyBattlefield(CellSample shoot, CellStatus result) {
        if (shoot==null || shoot.getY() > enemyBattlefield.length-1 || shoot.getX() > enemyBattlefield[0].length-1) {
            throw new IllegalArgumentException();
        }
        enemyBattlefield[shoot.getY()][shoot.getX()] = result;
    }
}
