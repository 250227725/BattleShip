import java.util.*;

public class AIPlayer extends Player{
    public AIPlayer(String name) {
        super(name, false);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public Player clone() {
        return new AIPlayer(this.getName());
    }

    @Override
    Map<Ship, String> generateShips(CellStatus[][] emptyPlayerField, IOManager manager) {
        return ShipGenerator.getShips(emptyPlayerField.length, emptyPlayerField[0].length, GameSettings.DEFAULT_SHIP_SETTINGS);
    }

    @Override
    public CellSample guess(IOManager manager) {
        List<CellSample> possibleCells = getPossibleCells();
        return possibleCells.get((int) (Math.random() * possibleCells.size()));
    }

    private List<CellSample> getPossibleCells() {
        return GameService.getPossibleNeighborsCells(enemyBattlefield);
    }

}
