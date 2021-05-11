public class ShipSection implements Coordinable {
    private int x;
    private int y;
    private boolean isAlive;

    public ShipSection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
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
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (obj instanceof Coordinable) {
            Coordinable shoot = (Coordinable) obj;
            if (this.getX() == shoot.getX() && this.getY() == shoot.getY()) return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getX() * this.getY() * 31;
    }
}
