public class ShipSection extends Cell{
    private boolean isAlive = true;

    public ShipSection(int x, int y) {
        super(y, x);
    }

    public ShipSection(Cell cell) {
        super(cell.getY(), cell.getX());
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void hit() {
        isAlive = false;
    }
}
