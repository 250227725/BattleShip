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
        int playersQuantity = getPlayersQuantity(); //todo: add check for quantity range
        //int width = getFieldWidth(); //todo: add check for quantity range
        //int height = getFieldHeight(); //todo: add check for quantity range
        //int difficulty = getDifficulty(); //todo: add realisation
        Set<Player> players = getPlayers(playersQuantity);
        //return Game.createGame(players, width, height, difficulty);
        return Game.createGame(players);
    }

    Set<Player> getPlayers(int playersQuantity) throws GameCancelledException, GameInterruptException {
        int playersCount = 1;
        Set<Player> players = new HashSet<>();
        while (playersQuantity > 0) {
            Player player = getHumanPlayer(playersCount);
            if (player == null) break;
            if (players.contains(player)) {
                manager.showMessage("Игрок уже был добавлен ранее");
            }
            else {
                players.add(player);
                playersQuantity--;
                playersCount++;
            }
        }
        players.addAll(getAIPlayers(playersQuantity));
        return players;
    }

    Set<Player> getAIPlayers(int playersQuantity) {
        Set<Player> aiPlayers = new HashSet<>();
        while (playersQuantity > 0) {
            aiPlayers.add(new AIPlayer("AI_" + playersQuantity));
            playersQuantity--;
        }
        return aiPlayers;
    }

    Player getHumanPlayer(int playersCount) throws GameCancelledException, GameInterruptException {
        String message = "Введите имя игрока №" + playersCount + " или exit для завершения игры" +
                " или оставьте имя пустым для создания AI игроков";
        manager.showMessage(message);
        String name = getStringValue(message);
        if (name==null || name.equals("")) {
            return null;
        }
        return new HumanPlayer(name);
    }

    int getDifficulty() {
        return getParametrTest();
    }

    int getFieldHeight() throws GameCancelledException, GameInterruptException {
        String message = "Введите высоту игрового поля или exit для завершения игры. " +
                "Высота поля должна быть в диапазоне от " +
                Project1st.MIN_FIELD_HEIGHT + " до " + Project1st.MAX_FIELD_HEIGHT;
        manager.showMessage(message);
        return getIntegerValue(message);
    }

    int getFieldWidth() throws GameCancelledException, GameInterruptException {
        String message = "Введите ширину игрового поля или exit для завершения игры. " +
                "Ширина поля должна быть в диапазоне от " +
                Project1st.MIN_FIELD_WIDTH + " до " + Project1st.MAX_FIELD_WIDTH;
        manager.showMessage(message);
        return getIntegerValue(message);
    }

    int getPlayersQuantity() throws GameCancelledException, GameInterruptException{
        String message = "Введите количество игроков или exit для завершения игры";
        manager.showMessage(message);
        return getIntegerValue(message);
    }

    int getIntegerValue(String repeatMessage) throws GameCancelledException, GameInterruptException {
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

    String getStringValue(String repeatMessage) throws GameCancelledException, GameInterruptException {
        int errorCount = 0;
        int maxAttempt = 5;
        while (true) {
            try {
                return manager.read();
            }
            catch (NullPointerException e) {
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
