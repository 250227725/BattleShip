public abstract class Cell implements Comparable<Cell> {
    private final int x;
    private final int y;

    public Cell(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public Cell(HorizontalCellNames x, int y) {
        this(y-1, x.ordinal());
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
        if (!(obj instanceof Cell)) return false;
        Cell c = (Cell) obj;
        return x == c.x && y == c.y;
    }

    @Override
    public int hashCode() {
        return y * 1024 + x;
    }

    public int compareTo(Cell c) {
        if (c == null) throw new NullPointerException();
        if (x == c.x) {
            return Integer.compare(y, c.y);
        }
        return Integer.compare(x, c.x);

    }

    @Override
    public String toString() {
        return "[x: " + getX() + " | y: " + getY() + "](" + HorizontalCellNames.values()[getX()] + getY() + ")";
    }

    enum HorizontalCellNames {
        A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z
    }
}
