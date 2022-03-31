import java.util.HashSet;
import java.util.Set;

public class Player {
    private final String name;
    private Set<Ship> ships;

    public Set<Ship> getShips() {
        return ships;
    }

    private boolean isAlive = true;
    private CellStatus[][] battleField = new CellStatus[Project1st.FIELD_HEIGHT][Project1st.FIELD_WIDTH];
    {
        for (int y = 0; y < battleField.length; y++ ) {
            for (int x = 0; x < battleField[0].length; x++) {
                battleField[y][x] = CellStatus.UNKNOWN;
            }
        }
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

    public Player(String name) {
        this.name = name;
        this.ships = new HashSet<>();
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
}
