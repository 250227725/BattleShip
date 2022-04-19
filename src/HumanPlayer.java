import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    private Ship getHumanShip(CellStatus[][] playerField, int size, int index, IOManager manager) throws GameCancelledException, GameInterruptException {
        Ship result;
        while (true) {
            CellSample[] cells = GameService.getCellsForShip(size, playerField.length, playerField[0].length, manager);
            if (!GameService.checkFieldAvailability(playerField, cells)) {
                manager.showMessage("Невозможно разместить корабль на выбранные поля");
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
