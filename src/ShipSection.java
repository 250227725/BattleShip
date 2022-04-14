public class ShipSection extends Cell{
    private boolean isAlive = true;

    public ShipSection(int y, int x) {
        super(y, x);
    }
    public ShipSection(CellSample cell) {
        super(cell.getY(), cell.getX());
    }
    public boolean isAlive() {
        return isAlive;
    }
    public boolean hit(Cell attempt) {
        if (equals(attempt) && isAlive()) {
            isAlive = false;
            return true;
        }
        return false;
    }
}
