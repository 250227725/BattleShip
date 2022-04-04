import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CellTest {

    class CellClass extends Cell {
        public CellClass(int x, int y) {
            super(y, x);
        }
    }

    class AltClass {
        public int getX() {return 0;}
        public int getY() {return 0;}
    }

    @Test
    public void sameEqualsTest() {
        CellClass c1 = new CellClass(1, 0);
        assertThat(c1, equalTo(c1));
    }

    @Test
    public void equalsTest() {
        CellClass c1 = new CellClass(1, 0);
        CellClass c2 = new CellClass(1, 0);
        assertThat(c1, equalTo(c2));
    }

    @Test
    public void notEqualsTest() {
        CellClass c1 = new CellClass(1, 0);
        CellClass c2 = new CellClass(0, 1);
        assertThat(c1, not(equalTo(c2)));
    }

    @Test
    public void notEqualsObjectTest() {
        CellClass c1 = new CellClass(0, 0);
        assertThat(c1, not(equalTo(new AltClass())));
    }

    @Test
    public void createCellFromNaturalCoordinates() {
        Cell natural = new Cell(Cell.HorizontalCellNames.valueOf("B"), 2) {};
        Cell test = new Cell(1, 1) {};
        assertThat(natural, equalTo(test));
    }

    @Test
    public void notEqualLineCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.D, 2) {};
        assertThat(false, equalTo(start.equalLine(end)));
    }

    @Test
    public void equalLineVerticalCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.C, 2) {};
        assertThat(true, equalTo(start.equalLine(end)));
    }

    @Test
    public void equalLineHorizontalCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.D, 1) {};
        assertThat(true, equalTo(start.equalLine(end)));
    }

    @Test
    public void equalLineOneCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.C, 1) {};
        assertThat(true, equalTo(start.equalLine(end)));
    }

    @Test
    public void distanceExceptionTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.D, 2) {};
        assertThrows(IllegalArgumentException.class, () -> start.distance(end));
    }

    @Test
    public void distanceVerticalCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.C, 3) {};
        assertThat(3, equalTo(start.distance(end)));
    }

    @Test
    public void distanceHorizontalCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.A, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.D, 1) {};
        assertThat(4, equalTo(start.distance(end)));
    }

    @Test
    public void distanceOneCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.C, 1) {};
        assertThat(1, equalTo(start.distance(end)));
    }

    @Test
    public void sequenceOneCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell[] cells = new Cell[] {new Cell(Cell.HorizontalCellNames.C, 1) {}};
        assertThat(start.cellSequence(end), equalTo(cells));
    }

    @Test
    public void sequenceThreeHorizontalCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.A, 1) {};
        Cell end = new Cell(Cell.HorizontalCellNames.C, 1) {};
        Cell[] cells = new Cell[] {start, new Cell(Cell.HorizontalCellNames.B, 1) {}, end};
        assertThat(start.cellSequence(end), equalTo(cells));
    }

    @Test
    public void sequenceThreeVerticalCellTest() {
        Cell start = new Cell(Cell.HorizontalCellNames.C, 0) {};
        Cell end = new Cell(Cell.HorizontalCellNames.C, 2) {};
        Cell[] cells = new Cell[] {start, new Cell(Cell.HorizontalCellNames.C, 1) {}, end};
        assertThat(start.cellSequence(end), equalTo(cells));
    }

    @Test
    public void cellMatrixBusyTest() {
        int x = 2; int y = 2;
        Set<Cell> busyField = new HashSet<>();
        busyField.add(new Cell(y-1, x-1){}); busyField.add(new Cell(y-1, x){}); busyField.add(new Cell(y-1, x+1){});
        busyField.add(new Cell(y, x-1){}); busyField.add(new Cell(y, x){}); busyField.add(new Cell(y, x-1){});
        busyField.add(new Cell(y+1, x-1){}); busyField.add(new Cell(y+1, x){}); busyField.add(new Cell(y+1, x+1){});
        Cell sample = new Cell(Cell.HorizontalCellNames.values()[x], y+1) {};



//                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY},
//                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.EMPTY, CellStatus.EMPTY},
//                {CellStatus.EMPTY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.BUSY, CellStatus.BUSY},
//                {CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY},
//                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY}
//        };
//        Cell[] shipCell = {new Cell(Cell.HorizontalCellNames.D, 2){}, new Cell(Cell.HorizontalCellNames.D, 3){}, new Cell(Cell.HorizontalCellNames.D, 4){}};
//        assertThat(Project1st.service.checkFieldAvailablity(playerField, shipCell), equalTo(false));

    }


}

