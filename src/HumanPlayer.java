import java.util.HashMap;
import java.util.Map;

public class HumanPlayer extends Player{
    public HumanPlayer(String name) {
        super(name, true);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    public Player clone() {
        return new HumanPlayer(this.getName());
    }

    @Override
    Map<Ship, String> generateShips(CellStatus[][] playerField, IOManager manager) throws GameCancelledException, GameInterruptException {
        manager.showMessage("Расставить корабли автоматически? (y - для авторасстановки или ENTER для ручного ввода");
        if (manager.readLine().equalsIgnoreCase("y")) return ShipGenerator.getShips(playerField.length, playerField[0].length, GameSettings.DEFAULT_SHIP_SETTINGS);

        Map<Ship, String> player_ships = new HashMap<>();
        if (manager == null) {
            throw new IllegalArgumentException();
        }
        int[] setup = GameSettings.DEFAULT_SHIP_SETTINGS;
        for (int size = 1; size < setup.length; size++) {
            for (int index = setup[size]; index > 0; index--) {
                player_ships.put(getHumanShip(playerField, size, index, manager), "Ship size:"+size+"|index:"+index);
            }
        }
        return player_ships;
    }

    @Override
    public CellSample guess(IOManager manager) throws GameCancelledException, GameInterruptException {
        if (manager == null) {
            throw new IllegalArgumentException();
        }
        return GameService.getPlayerGuess(enemyBattlefield.length, enemyBattlefield[0].length, manager);
    }

    private Ship getHumanShip(CellStatus[][] playerField, int size, int index, IOManager manager) throws GameCancelledException, GameInterruptException {
        Ship result;
        while (true) {
            CellSample[] cells = GameService.getCellsForShip(size, playerField.length, playerField[0].length, manager);
            if (!GameService.checkFieldAvailability(playerField, cells)) {
                manager.showMessage("Невозможно разместить корабль на выбранные поля");
                continue;
            }

            if (cells.length != size) {
                manager.showMessage("Некорректный размер корабля");
                continue;
            }

            GameService.fillBusyCell(playerField, cells);
            result = Ship.getInstance(cells);
            manager.printBattlefield(playerField);
            break;
        }
        return result;
    }
}
