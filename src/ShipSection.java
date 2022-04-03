public class ShipSection extends Cell{
    private boolean isAlive = true;

    private ShipSection(int x, int y) {
        super(y, x);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void hit() {
        isAlive = false;
    }
}
