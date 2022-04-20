import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public Set<CellSample> getNeighbors() {
        Set<CellSample> result = new HashSet<>();
        if (x > 0) {
            if (y > 0) result.add(new CellSample(y-1, x-1));
            result.add(new CellSample(y, x-1));
            if (y < GameSettings.MAX_FIELD_HEIGHT - 1) result.add(new CellSample(y+1, x-1));
        }

        if (y > 0) result.add(new CellSample(y-1, x));
        result.add(new CellSample(y, x));
        if (y < GameSettings.MAX_FIELD_HEIGHT - 1) result.add(new CellSample(y+1, x));

        if (x < GameSettings.MAX_FIELD_WIDTH - 1) {
            if (y > 0) result.add(new CellSample(y-1, x+1));
            result.add(new CellSample(y, x+1));
            if (y < GameSettings.MAX_FIELD_HEIGHT - 1) result.add(new CellSample(y+1, x+1));
        }
        return result;
    };

    public static boolean checkSequence(CellSample[] sequence) {
        if (sequence.length == 1) return true;
        int dx = sequence[1].getX() - sequence[0].getX();
        int dy = sequence[1].getY() - sequence[0].getY();

        if (dx + dy != 1 || (dx != 1 && dy != 1)) {
            return false;
        }

        for (int i = 0; i < sequence.length; i++) {
            if (i > 0 && (dx != sequence[i].getX() - sequence[i-1].getX() ||  dy != sequence[i].getY() - sequence[i-1].getY())) {
                return false;
            }
        }
        return true;
    }
}
