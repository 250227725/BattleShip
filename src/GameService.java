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
        Arrays.stream(battleField)
                .forEach(s -> Arrays.fill(s, CellStatus.UNKNOWN));
        return battleField;
    }

    public static CellStatus[][] copyBattleField(CellStatus[][] battleField) {
        if (battleField==null) {
            return null;
        }
        final CellStatus[][] result = new CellStatus[battleField.length][];
        for (int i = 0; i < battleField.length; i++) {
            result[i] = Arrays.copyOf(battleField[i], battleField[i].length);
        }
        return result;
    }

    public GameSettings initSettings() throws GameCancelledException, GameInterruptException {
        int playersQuantity = getPlayersQuantity(); //todo: add check for quantity range
        //int width = getFieldWidth(); //todo: add check for quantity range
        //int height = getFieldHeight(); //todo: add check for quantity range
        //int difficulty = getDifficulty(); //todo: add realisation
        Set<Player> players = getPlayers(playersQuantity);
        //return Game.createGame(players, width, height, difficulty);
        return GameSettings.createSettings(players);
    }

    public Set<Player> getPlayers(int playersQuantity) throws GameCancelledException, GameInterruptException {
        int playersCount = 1;
        Set<Player> players = new HashSet<>();
        while (playersQuantity > 0) {
            Player player = getHumanPlayer(playersCount);
            if (player == null) break;
            if (players.contains(player)) {
                manager.showMessage("Игрок уже был добавлен ранее");
            } else {
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
        if (name == null || name.equals("")) {
            return null;
        }
        return new HumanPlayer(name);
    }

    public int getPlayersQuantity() throws GameCancelledException, GameInterruptException {
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
            } catch (NumberFormatException | NullPointerException e) {
                errorCount++;
                if (maxAttempt <= errorCount) {
                    manager.showMessage("Превышено количество попыток ввода. Игра прервана");
                    throw new GameInterruptException();
                } else {
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
            } catch (NullPointerException e) {
                errorCount++;
                if (maxAttempt <= errorCount) {
                    manager.showMessage("Превышено количество попыток ввода. Игра прервана");
                    throw new GameInterruptException();
                } else {
                    manager.showMessage("Введено некорректное значение. " + repeatMessage);
                }
            }
        }
    }

    public void addHumanShip(CellStatus[][] playerField, int size, Map<Ship, String> ships, int index) throws GameCancelledException, GameInterruptException {
        while (true) {
            CellSample[] cells = getCellsForShip(size, playerField.length, playerField[0].length);
            if (!checkFieldAvailability(playerField, cells)) {
                manager.showMessage("Невозможно разместить корабль на выбранные поля");
                continue;
            }

            fillBusyCell(playerField, cells);
            ships.put(Ship.getInstance(cells), size + "-палубный корабль, №" + (size - index));
            manager.printBattlefield(playerField);
            break;
        }
    }

    public CellSample[] getCellsForShip(int size, int height, int width) throws GameCancelledException, GameInterruptException {
        String message = "Введите координаты начальной и конечной точки корабля, разделенные знаком минус. Размер корабля - " + size + ":";
        manager.showMessage(message);
        while (true) {
            String[] data = getStringValue(message).trim().split("-");
            if (data.length < 1 || data.length > 2) {
                manager.showMessage("Некорректное значение" + message);
                continue;
            }

            Optional<CellSample> cell1 = getCellFromString(data[0], height, width);
            Optional<CellSample> cell2;
            if (data.length == 2 && !data[0].equals(data[1])) {
                cell2 = getCellFromString(data[1], height, width);
            } else {
                cell2 = cell1;
            }

            if (cell1.isEmpty() || cell2.isEmpty() || cell1.get().notEqualLine(cell2.get())) {
                manager.showMessage("Некорректное значение" + message);
                continue;
            }
            return cell1.get().cellSequence(cell2.get());
        }
    }

    public static Optional<CellSample> getCellFromString(String attempt, int height, int width) {
        try {
            int x = Cell.HorizontalCellNames.valueOf(attempt.trim().substring(0, 1).toUpperCase()).ordinal();
            int y = Integer.parseInt(attempt.trim().substring(1).trim()) - 1;
            if (y < 0 || x >= width || y >= height) return Optional.empty();
            return Optional.of(new CellSample(y, x));
        } catch (IllegalArgumentException | StringIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    public CellSample getPlayerGuess(int height, int width) throws GameCancelledException, GameInterruptException {
        String message = "Введите координаты поля для выстрела:";
        manager.showMessage(message);
        while (true) {
            String data = getStringValue(message).trim();
            Optional<CellSample> cell = getCellFromString(data, height, width);
            if (cell.isPresent()) return cell.get();
            manager.showMessage("Некорректный ввод. " + message);
        }
    }

    public boolean checkFieldAvailability(CellStatus[][] playerField, CellSample[] shipCell) {
        for (CellSample cell : shipCell) {
            if (playerField[cell.getY()][cell.getX()] == CellStatus.BUSY) return false;
        }
        return true;
    }

    public void fillBusyCell(CellStatus[][] playerField, CellSample[] cells) {
        for (CellSample baseCell : cells) {
            for (CellSample cell : baseCell.getNeighbors()) {
                try {
                    if (playerField[cell.getY()][cell.getX()] != CellStatus.SHIP) {
                        playerField[cell.getY()][cell.getX()] = CellStatus.BUSY;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    //NOP
                }
            }
            playerField[baseCell.getY()][baseCell.getX()] = CellStatus.SHIP;
        }
    }

    public void playerWelcome(Player player) {
        manager.showMessage("Ход игрока " + player.getName());
    }

    public void showEnemyBattleField(Player player) {
        manager.printBattlefield(player.getEnemyBattlefield());
    }

    public void fillEnemyBattleField(Player player, CellSample shoot, Ship.ShipHitStatus result) {
        player.fillEnemyBattlefield(shoot, result);
    }

    public static CellStatus castShipToCellStatus(Ship.ShipHitStatus status) {
        return status == Ship.ShipHitStatus.MISSED ? CellStatus.MISSED :
                status == Ship.ShipHitStatus.HITED ? CellStatus.HITTED : CellStatus.DESTROYED;
    }


}
