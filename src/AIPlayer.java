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
        return new HashMap<>();
    }

    @Override
    public CellSample guess(int height, int width) {
        List<CellSample> possibleCells = getPossibleCells();
        return possibleCells.get((int) Math.random() * possibleCells.size());
    }


}
