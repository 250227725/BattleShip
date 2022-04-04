public class ShipSection extends Cell{
    private boolean isAlive = true;

    private ShipSection(int x, int y) {
        super(y, x);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean hit(Cell cell) {
        if (isAlive()) {
            isAlive = false;
            return true;
        }
        return false;
    }
}
