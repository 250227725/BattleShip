public class CellSample extends Cell{
    public CellSample(HorizontalCellNames x, int y) {
        super(x, y);
    }

    public CellSample(int y, int x) {
        super(y,x);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equalLine(CellSample cell) {
        return (this.x == cell.x || this.y == cell.y);
    };

    public boolean notEqualLine(CellSample cell) {
        return !equalLine(cell);
    };

    public int distance(CellSample coord) {
        if (notEqualLine(coord)) throw new IllegalArgumentException();
        return Math.abs(x + y - coord.x - coord.y) + 1;
    };

    public CellSample[] cellSequence(CellSample end) {
        int size = distance(end);
        if (size == 1) {
            return new CellSample[]{this};
        }
        CellSample[] result = new CellSample[size];
        int x0 = Math.min(x, end.x);
        int y0 = Math.min(y, end.y);
        for (int i = 0; i < size; i++) {
            if (x == end.x) {
                result[i] = new CellSample(y0 + i, x0) ;
            }
            else {
                result[i] = new CellSample(y0, x0 + i);
            }
        }
        return result;
    };
}
