public class ShipSection extends Cell{
    private boolean isAlive = true;

    public ShipSection(int x, int y) {
        super(x, y);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void hit() {
        isAlive = false;
    }
}
