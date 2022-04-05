import java.util.*;

public class GameService {

    private final IOManager manager;

    private GameService(IOManager manager) {
        this.manager = manager;
    }

    public static GameService getInstance(IOManager manager) {
        return new GameService(manager);
    }

    public static CellStatus[][] getEmptyField(int fieldHeight, int fieldWidth) {
        CellStatus[][] battleField = new CellStatus[fieldHeight][fieldWidth];
        {
            for (int y = 0; y < battleField.length; y++ ) {
                for (int x = 0; x < battleField[0].length; x++) {
                    battleField[y][x] = CellStatus.UNKNOWN;
                }
            }
        }
        return battleField;
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

    public Set<Player> getPlayers(int playersQuantity) throws GameCancelledException, GameInterruptException {
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

    public Set<Player> getAIPlayers(int playersQuantity) {
        Set<Player> aiPlayers = new HashSet<>();
        while (playersQuantity > 0) {
            aiPlayers.add(new AIPlayer("AI_" + playersQuantity));
            playersQuantity--;
        }
        return aiPlayers;
    }

    public Player getHumanPlayer(int playersCount) throws GameCancelledException, GameInterruptException {
        String message = "Введите имя игрока №" + playersCount + " или exit для завершения игры" +
                " или оставьте имя пустым для создания AI игроков";
        manager.showMessage(message);
        String name = getStringValue(message);
        if (name==null || name.equals("")) {
            return null;
        }
        return new HumanPlayer(name);
    }

    public int getPlayersQuantity() throws GameCancelledException, GameInterruptException{
        String message = "Введите количество игроков или exit для завершения игры";
        manager.showMessage(message);
        return getIntegerValue(message);
    }

    public int getIntegerValue(String repeatMessage) throws GameCancelledException, GameInterruptException {
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

    public String getStringValue(String repeatMessage) throws GameCancelledException, GameInterruptException {
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

    public void addHumanShip(CellStatus[][] playerField, int size, Map<Ship, String> ships, int index) throws GameCancelledException, GameInterruptException {
        while (true) {
            CellSample[] cells = getCellsForShip(size);
            if (!checkFieldAvailability(playerField, cells)) {
                manager.showMessage("Невозможно разместить корабль на выбранные поля");
                continue;
            }

            fillBusyCell(playerField, cells);
            ships.put(Ship.getInstance(cells), size + "-палубный корабль, №" + (size - index));
            break;
        }
    }

    public CellSample[] getCellsForShip(int size) throws GameCancelledException, GameInterruptException {
        String message = "Введите координаты начальной и конечной точки корабля, разделенные знаком минус. Размер корабля - " + size + ":";
        manager.showMessage(message);
        while (true) {
            String[] data = getStringValue(message).trim().split("-");
            if (data.length <1 || data.length > 2) {
                manager.showMessage("Некорректное значение" + message);
                continue;
            }

            Optional<CellSample> cell1 = getCell(data[1]);
            Optional<CellSample> cell2;
            if (data.length == 2) {
                cell2 = getCell(data[2]);
            }
            else {
                cell2 = cell1;
            }

            if (cell1.isEmpty() || cell2.isEmpty() || cell1.get().notEqualLine(cell2.get())) {
                manager.showMessage("Некорректное значение" + message);
                continue;
            }
            return cell1.get().cellSequence(cell2.get());
        }
    }

    public Optional<CellSample> getCell(String attempt) {
        int x = Cell.HorizontalCellNames.valueOf(attempt.trim().substring(0, 1).toUpperCase()).ordinal();
        int y = Integer.parseInt(attempt.trim().substring(1).trim()) - 1;
        if (checkCoordinates(y, x)) return Optional.of(new CellSample(y, x)); //todo move check to Cell class
        return Optional.empty();
    }

    public boolean checkCoordinates(int y, int x) { //todo: need to check real gameField dimensions
        return x >= 0 && y >= 0 && x < Project1st.MAX_FIELD_WIDTH && y < Project1st.MAX_FIELD_HEIGHT;
    }

    public boolean checkFieldAvailability(CellStatus[][] playerField, CellSample[] shipCell) {
        for (CellSample cell : shipCell) {
            if (playerField[cell.getY()][cell.getX()] == CellStatus.BUSY) return false;
        }
        return true;
    }

    public void fillBusyCell(CellStatus[][] playerField, CellSample[] cells) {
        for (CellSample cell : cells) {
            playerField[cell.getY()][cell.getX()] = CellStatus.BUSY;
        }
    }
}
