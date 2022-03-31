import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameService {
    private static GameService instance = new GameService();
    private GameService() {}
    public static GameService getInstance() {
        return instance;
    }

    public void addShip(Player player, int[][] newShipCoordinates) {
        Ship newShip = Ship.getInstance(newShipCoordinates);
        CellStatus[][] playerGameField = player.getBattleField();
        Set<Cell> busyField = new HashSet<>();
        Arrays.stream(newShip.sections)
                .forEach(s -> {
                    if (playerGameField[s.getX()][s.getY()] == CellStatus.BUSY) throw new IllegalArgumentException();
                    busyField.addAll(addBusyCell(s));
                });
        busyField.forEach(s -> playerGameField[s.getX()][s.getY()] = CellStatus.BUSY);
        player.getShips().add(newShip);
    }

    public Set<Cell> addBusyCell(int[] cell) {
        return addBusyCell(new ShipSection(cell[0], cell[1]));
    }

    public Set<Cell> addBusyCell(ShipSection section) {
        Set<Cell> busyCell = new HashSet<>();

        busyCell.add(new Cell(section.getX(), section.getY()) {});

        if (section.getX() != 0) { //left of
            busyCell.add(new Cell(section.getX() - 1, section.getY()) {});
            if ((section.getY() != 0)) {
                busyCell.add(new Cell(section.getX() - 1, section.getY() - 1) {});
            }
            if ((section.getY() != Project1st.FIELD_HEIGHT - 1)) {
                busyCell.add(new Cell(section.getX() - 1, section.getY() + 1) {});
            }
        }

        if (section.getX() != Project1st.FIELD_WIDTH - 1) {//right of
            busyCell.add(new Cell(section.getX() + 1, section.getY()) {});
            if ((section.getY() != 0)) {
                busyCell.add(new Cell(section.getX() + 1, section.getY() - 1) {});
            }
            if ((section.getY() != Project1st.FIELD_HEIGHT - 1)) {
                busyCell.add(new Cell(section.getX() + 1, section.getY() + 1) {});
            }
        }

        if ((section.getY() != 0)) {
            busyCell.add(new Cell(section.getX(), section.getY() - 1) {});
        }
        if ((section.getY() != Project1st.FIELD_HEIGHT - 1)) {
            busyCell.add(new Cell(section.getX(), section.getY() + 1) {});
        }

        return busyCell;
    }

    public void checkBattleField(CellStatus[][] battleField, int[][] newShipCoordinates) {
        Arrays.stream(newShipCoordinates)
                .forEach(s -> {
                    if (battleField[s[0]][s[1]] == CellStatus.BUSY) throw new IllegalArgumentException();
                });
    }

    public Set<Cell> getBusyCell(int[][] newShipCoordinates) {
        Set<Cell> busyField = new HashSet<>();
        Arrays.stream(newShipCoordinates)
                .forEach(s -> {
                    busyField.addAll(addBusyCell(s));
                });
        return busyField;
    }

    public List<Player> createPlayers() {
        return null;
    }
}
