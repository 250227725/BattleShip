import org.junit.jupiter.api.Test;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellTest {
    @Test
    public void sameEqualsTest() {
        Cell c1 = new CellSample(1, 0);
        assertThat(c1, equalTo(c1));
    }

    @Test
    public void equalsTest() {
        Cell c1 = new CellSample(1, 0);
        Cell c2 = new ShipSection(1, 0);
        assertThat(c1, equalTo(c2));
    }

    @Test
    public void equalsDifferentChildTest() {
        CellSample c1 = new CellSample(1, 0);
        ShipSection c2 = new ShipSection(1, 0);
        assertThat(c1, equalTo(c2));
    }

    @Test
    public void notEqualsTest() {
        Cell c1 = new CellSample(1, 0);
        Cell c2 = new CellSample(0, 1);
        assertThat(c1, not(equalTo(c2)));
    }

    @Test
    public void notEqualsObjectTest() {
        Cell c1 = new CellSample(0, 0);
        class AltClass {
            public int x;
            public int y;
            public AltClass(int y, int x) {
                this.x = x;
                this.y = y;
            }
        }
        assertThat(c1, not(equalTo(new AltClass(0,0))));
    }

    @Test
    public void createCellFromNaturalCoordinates() {
        Cell natural = new CellSample(Cell.HorizontalCellNames.valueOf("B"), 2);
        Cell test = new CellSample(1, 1);
        assertThat(natural, equalTo(test));
    }

    @Test
    public void notEqualLineCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.D, 2);
        assertThat(false, equalTo(start.equalLine(end)));
    }

    @Test
    public void equalLineVerticalCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.C, 2);
        assertThat(true, equalTo(start.equalLine(end)));
    }

    @Test
    public void equalLineHorizontalCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.D, 1);
        assertThat(true, equalTo(start.equalLine(end)));
    }

    @Test
    public void equalLineOneCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.C, 1);
        assertThat(true, equalTo(start.equalLine(end)));
    }

    @Test
    public void distanceExceptionTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.D, 2);
        assertThrows(IllegalArgumentException.class, () -> start.distance(end));
    }

    @Test
    public void distanceVerticalCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.C, 3);
        assertThat(3, equalTo(start.distance(end)));
    }

    @Test
    public void distanceHorizontalCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.A, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.D, 1);
        assertThat(4, equalTo(start.distance(end)));
    }

    @Test
    public void distanceOneCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.C, 1);
        assertThat(1, equalTo(start.distance(end)));
    }

    @Test
    public void sequenceOneCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample[] cells = new CellSample[] {new CellSample(Cell.HorizontalCellNames.C, 1)};
        assertThat(start.cellSequence(end), equalTo(cells));
    }

    @Test
    public void sequenceThreeHorizontalCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.A, 1);
        CellSample end = new CellSample(Cell.HorizontalCellNames.C, 1);
        CellSample[] cells = new CellSample[] {new CellSample(Cell.HorizontalCellNames.A, 1), new CellSample(Cell.HorizontalCellNames.B, 1), end};
        assertThat(start.cellSequence(end), equalTo(cells));
    }

    @Test
    public void sequenceThreeVerticalCellTest() {
        CellSample start = new CellSample(Cell.HorizontalCellNames.C, 0) ;
        CellSample end = new CellSample(Cell.HorizontalCellNames.C, 2) ;
        CellSample[] cells = new CellSample[] {start, new CellSample(Cell.HorizontalCellNames.C, 1), end};
        assertThat(start.cellSequence(end), equalTo(cells));
    }

    @Test
    public void cellMatrixBusyTest() {
        int x = 2; int y = 2;
        Set<CellSample> busyField = new HashSet<>();
        busyField.add(new CellSample(y-1, x-1)); busyField.add(new CellSample(y-1, x));   busyField.add(new CellSample(y-1, x+1));
        busyField.add(new CellSample(y, x-1)); busyField.add(new CellSample(y, x)); busyField.add(new CellSample(y, x+1));
        busyField.add(new CellSample(y+1, x-1)); busyField.add(new CellSample(y+1, x)); busyField.add(new CellSample(y+1, x+1));
        CellSample sample = new CellSample(Cell.HorizontalCellNames.values()[x], y+1) {};
        Set<CellSample> result = sample.getNeighbors();
        result.add(sample);
        assertThat(result, equalTo(busyField));
    }
}

