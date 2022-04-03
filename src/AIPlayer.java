public class AIPlayer extends Player{
    public AIPlayer(String name) {
        super(name, false);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    void generateShips(CellStatus[][] emptyPlayerField) {
    }
}
