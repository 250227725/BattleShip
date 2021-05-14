public class Shoot implements BattleFieldCell{
    private int x;
    private int y;

    public Shoot(int x, int y) {
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
