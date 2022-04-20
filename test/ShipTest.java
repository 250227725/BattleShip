import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ShipTest {

    @Test
    public void shipFactoryOneSectionTest() {
        int x = 3;
        int y = 2;
        CellSample[] sections = new CellSample[]{new CellSample(y,x)};
        Ship ship = Ship.getInstance(sections);
        assertThat(ship.getLength(), equalTo(1));
    }

    @Test
    public void shipFactoryLengthTest() {
        int size = ((int) (Math.random() * 3)) + 2;
        int x = 2;
        int y = 2;
        CellSample[] sections = new CellSample[size];
        for (int i = 0; i < size; i++) {
            sections[i] = new CellSample(y, x + i);
        }
        Ship ship = Ship.getInstance(sections);
        assertThat(ship.getLength(), equalTo(size));
    }

    @Test
    public void shipFactoryContentTest() {
        int size = ((int) (Math.random() * 3)) + 2;
        int x = 2;
        int y = 2;
        CellSample[] sections = new CellSample[size];
        for (int i = size - 1; i >= 0; i--) {
            sections[size-1-i] = new CellSample(y, x + i);
        }
        Ship ship = Ship.getInstance(sections);
        Arrays.sort(sections);
        List<Cell> source = Arrays.asList(sections);
        List<Cell> result = Arrays.asList(ship.getSections());
        assertThat(result, equalTo(source));
    }

    @Test
    public void shipFactoryNullCellTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(null));
    }

    @Test
    public void shipFactoryZeroSizeShipLengthTest() {
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new CellSample[0]));
    }

    @Test
    public void shipFactoryOverSizeShipLengthTest() {
        int size = GameSettings.DEFAULT_SHIP_SETTINGS.length;
        CellSample[] sections = new CellSample[size];
        for (int x = 0; x < size; x++) {
            sections[x] = new CellSample(0, x);
        }
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(sections));
    }

    @Test
    public void shipFactoryIncorrectRangeCellTest1() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new CellSample[]{new CellSample(-1,3), new CellSample(0,3)}));
    }

    @Test
    public void shipFactoryIncorrectRangeCellTest3() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new CellSample[]{new CellSample(GameSettings.MAX_FIELD_HEIGHT,3), new CellSample(GameSettings.MAX_FIELD_HEIGHT,3)}));
    }

    // Horizontal out of Range is controlled by Cell.HorizontalCellName


    @Test
    public void shipFactoryIncorrectSequenceCellTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new CellSample[]{new CellSample(2,3), new CellSample(2,5)}));
    }

    @Test
    public void shipFactoryIncorrectDirectionCellTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new CellSample[]{new CellSample(2,3), new CellSample(3,2)}));
    }

    @Test
    public void hitTest() {
        Ship theFirstOne = Ship.getInstance(new CellSample[]{new CellSample(0, 0), new CellSample(0, 1)});
        assertThat(theFirstOne.hit(new CellSample(0, 1)), equalTo(CellStatus.HITTED));
    }

    @Test
    public void destroyTest1() {
        Ship theFirstOne = Ship.getInstance(new CellSample[]{new CellSample(0, 0), new CellSample(0, 1)});
        theFirstOne.hit(new CellSample(0, 0));
        assertThat(theFirstOne.hit(new CellSample(0, 1)), equalTo(CellStatus.DESTROYED));
    }

    @Test
    public void destroyTest2() {
        Ship theFirstOne = Ship.getInstance(new CellSample[]{new CellSample(0, 0), new CellSample(0, 1)});
        theFirstOne.hit(new CellSample(0, 0));
        theFirstOne.hit(new CellSample(0, 1));
        assertThat(false, equalTo(theFirstOne.isAlive()));
    }

    @Test
    public void missTest() {
        Ship theFirstOne = Ship.getInstance(new CellSample[]{new CellSample(0, 0), new CellSample(0, 1)});
        assertThat(theFirstOne.hit(new CellSample(1, 1)), equalTo(CellStatus.MISSED));
    }

    @Test
    public void aliveAfterHitTest() {
        Ship theFirstOne = Ship.getInstance(new CellSample[]{new CellSample(0, 0), new CellSample(0, 1)});
        theFirstOne.hit(new CellSample(0, 0));
        assertThat(true, equalTo(theFirstOne.isAlive()));
    }


}
