public abstract class Cell implements Comparable<Cell> {
    // todo:
    // 1. implements Comparable<Cell>
    // 2. hashCode()
    private final int x;
    private final int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (!(obj instanceof Cell c)) return false;
        return x == c.x && y == c.y;
    }

    @Override
    public int hashCode() {
        return x * 1024 + y;
    }

    public int compareTo(Cell c) {
        if (c == null) throw new NullPointerException();
        if (x == c.x) {
            return Integer.compare(y, c.y);
        }
        return Integer.compare(x, c.x);

    }
}
