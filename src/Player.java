import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public abstract class Player {
    private final String name;
    private final boolean isHuman;
    private boolean isAlive;
    Map<Ship, String> ships;
    protected CellStatus[][] enemyBattlefield; //todo: multiplayer mode should be MAP (PlayerID, CellStatus[][])
    private int shootCount;

    protected Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.ships = new HashMap<>();
    }

    public int increaseShotCount() {
        return shootCount++;
    }

    public int getShootCount() {
        return shootCount;
    }

    public abstract boolean isHuman();
    public abstract Player clone();

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


    public void init(CellStatus[][] playerField) throws GameCancelledException, GameInterruptException {
        init(playerField, null);
    }

    public void init(CellStatus[][] playerField, IOManager manager) throws GameCancelledException, GameInterruptException {
        if (isAlive()) throw new UnsupportedOperationException();
        enemyBattlefield = playerField;
        ships = generateShips(GameService.copyBattleField(playerField), manager);
        isAlive = true;
    }

    abstract Map<Ship, String> generateShips(CellStatus[][] playerField, IOManager manager) throws GameCancelledException, GameInterruptException;

    public CellStatus checkShoot(Cell shoot) {
        Optional<CellStatus> result = ships.keySet().stream()
                .filter(Ship::isAlive)
                .map(s -> s.hit(shoot))
                .filter(s -> s != CellStatus.MISSED)
                .findAny();
        if (result.isEmpty())  return CellStatus.MISSED;
        if (result.get() == CellStatus.DESTROYED && ships.keySet().stream().noneMatch(Ship::isAlive)) {
            isAlive = false;
        }
        return result.get();
    }


    public void fillEnemyBattlefield(CellSample shoot, CellStatus result) {
        if (shoot==null || shoot.getY() > enemyBattlefield.length-1 || shoot.getX() > enemyBattlefield[0].length-1) {
            throw new IllegalArgumentException();
        }
        enemyBattlefield[shoot.getY()][shoot.getX()] = result;

        if (result==CellStatus.DESTROYED) {
            markHittedAsDestroyed(shoot);
        }
    }

    protected void markHittedAsDestroyed(CellSample cell) {
        cell.getNeighbors().stream()
            .filter(s -> !s.isOutOfRange(enemyBattlefield.length, enemyBattlefield[0].length) && enemyBattlefield[s.getY()][s.getX()] == CellStatus.HITTED)
            .forEach(s -> {
                enemyBattlefield[s.getY()][s.getX()] = CellStatus.DESTROYED;
                markHittedAsDestroyed(s);
            });
    }


    public abstract CellSample guess(int height, int width);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return isHuman == player.isHuman && name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isHuman);
    }
}
