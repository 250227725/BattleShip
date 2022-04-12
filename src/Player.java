import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class Player {
    private final String name;
    private final boolean isHuman;
    private boolean isAlive;
    Map<Ship, String> ships;
    private CellStatus[][] battleField;

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
    public CellStatus[][] getBattleField() {
        return battleField;
    }

    public String getName() {
        return name;
    }

    public void init(Game game) throws GameCancelledException, GameInterruptException {
        if (isAlive()) return;
        battleField = GameService.getEmptyField(game.getFieldHeight(), game.getFieldWidth());
        CellStatus[][] playerField = GameService.getEmptyField(game.getFieldHeight(), game.getFieldWidth());
        generateShips(playerField);
        isAlive = true;
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
}
