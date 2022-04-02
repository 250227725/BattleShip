import java.util.HashSet;
import java.util.Set;

public abstract class Player {
    private final String name;
    private final boolean isHuman;
    private boolean isAlive;
    private Set<Ship> ships;
    private CellStatus[][] battleField = new CellStatus[Project1st.FIELD_HEIGHT][Project1st.FIELD_WIDTH];
    {
        for (int y = 0; y < battleField.length; y++ ) {
            for (int x = 0; x < battleField[0].length; x++) {
                battleField[y][x] = CellStatus.UNKNOWN;
            }
        }
    }

    protected Player(String name, boolean isHuman) {
        this.name = name;
        this.isHuman = isHuman;
        this.ships = new HashSet<>();
    }

    public abstract boolean isHuman();
    public Set<Ship> getShips() {
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
        ships.add(Ship.getInstance(newShipCoordinates));
    }

    public void init() {
        if (isAlive()) return;
        generateShips();
        isAlive = true;
    }

    abstract void generateShips();
}
