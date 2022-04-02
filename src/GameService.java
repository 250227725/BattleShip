import java.util.*;

public class GameService {

    private final IOManager manager;

    private GameService(IOManager manager) {
        this.manager = manager;
    }

    public static GameService getInstance(IOManager manager) {
        return new GameService(manager);
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

    private int getParametrTest() {
        return 0;
    }

    public Game initGame() throws GameCancelledException, GameInterruptException{
        int playersQuantity = getPlayersQuantity();
        int width = getFieldWidth();
        int height = getFieldHeight();
        int difficulty = getDifficulty();
        Set<Player> players = getHumanPlayers();
        int aiPlayersQuantity = playersQuantity - (players == null ? 0 : players.size());
        if (aiPlayersQuantity > 0) {
            players.addAll(getAIPlayers(aiPlayersQuantity));
        }
        return Game.createGame(players, width, height, difficulty);
    }

    private Set<Player> getAIPlayers(int count) {
        return null;
    }

    private Set<Player> getHumanPlayers() {


        return null;
    }

    private int getDifficulty() {
        return getParametrTest();
    }

    private int getFieldHeight() {
        return getParametrTest();
    }


    private int getFieldWidth() {
        return getParametrTest();
    }

    int getPlayersQuantity() throws GameCancelledException, GameInterruptException{
        String message = "Введите количество игроков или exit для завершения игры";
        manager.showMessage(message);
        return getIntegerValue(message);
    }

    private int getIntegerValue(String repeatMessage) throws GameCancelledException, GameInterruptException {
        int errorCount = 0;
        int maxAttempt = 5;
        while (true) {
            try {
                return Integer.parseInt(manager.read());
            }
            catch (NumberFormatException | NullPointerException e) {
                errorCount++;
                if (maxAttempt <= errorCount) {
                    manager.showMessage("Превышено количество попыток ввода. Игра прервана");
                    throw new GameInterruptException();
                }
                else {
                    manager.showMessage("Введено некорректное значение. " + repeatMessage);
                }

            }
        }
    }
}
