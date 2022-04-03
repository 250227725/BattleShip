import java.util.HashMap;
import java.util.Map;
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
    public void addShip(int[][] newShipCoordinates) throws IllegalArgumentException{
        GameService service = GameService.getInstance(new IOManager(ConsoleInputManager.getInstance(), ConsoleOutputManager.getInstance())); //todo get GameService as argument
        service.checkBattleField(battleField, newShipCoordinates);
        Set<Cell> busyCell = service.getBusyCell(newShipCoordinates);
        busyCell.forEach(cell -> battleField[cell.getX()][cell.getY()] = CellStatus.BUSY);
        ships.put(Ship.getInstance(newShipCoordinates), "");
    }

    public void init(Game game) throws GameCancelledException, GameInterruptException {
        if (isAlive()) return;
        battleField = GameService.getEmptyField(game.getFieldHeight(), game.getFieldWidth());
        CellStatus[][] playerField = GameService.getEmptyField(game.getFieldHeight(), game.getFieldWidth());
        generateShips(playerField);
        isAlive = true;
    }

    abstract void generateShips(CellStatus[][] playerField) throws GameCancelledException, GameInterruptException;
}
