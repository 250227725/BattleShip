public class ShipSection implements BattleFieldCell {
    private int x;
    private int y;
    private boolean isAlive;

    public ShipSection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    @Override
    public int hashCode() {
        return this.getY() * this.getY() * 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof BattleFieldCell) {
            BattleFieldCell cell = (BattleFieldCell) obj;
            if (this.getX() == cell.getX() && this.getY() == cell.getY()) return true;
        }
        return false;
    }
}
