import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ShipTest {

    @Test
    public void shipFactoryOneSectionTest() {
        int x = 3;
        int y = 2;
        Cell[] sections = new Cell[]{new Cell(y,x){}, new Cell(y,x){}};
        assertThat(Ship.getInstance(sections).getLength(), equalTo(1));
    }

    @Test
    public void shipFactoryLengthTest() {
        int size = ((int) (Math.random() * 5 + 1));
        int x0 = 2;
        int y = 2;
        Cell[] sections = new Cell[]{new Cell(y,x0){}, new Cell(y,x0+size-1){}};
        assertThat(Ship.getInstance(sections).getLength(), equalTo(size));
    }

    @Test
    public void shipFactoryNegotiveLengthTest() {
        int size = ((int) (Math.random() * 5 + 1));
        int x0 = 2;
        int y = 2;
        Cell[] sections = new Cell[]{new Cell(y,x0+size-1){}, new Cell(y,x0){}};
        assertThat(Ship.getInstance(sections).getLength(), equalTo(size));
    }

    @Test
    public void shipFactoryNullCellTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance((Cell[]) null)); //todo: remove class cast
    }

    @Test
    public void shipFactoryIncorrectOneCellTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new Cell[]{new Cell(2,3){}}));
    }

    @Test
    public void shipFactoryIncorrectMultiCellTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new Cell[]{new Cell(2,3){}, new Cell(2,4){}, new Cell(2,5){}}));
    }

    @Test
    public void shipFactoryIncorrectDirectionCellTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new Cell[]{new Cell(2,3){}, new Cell(3,5){}}));
    }

    @Test
    public void hitTest() {
        Ship theFirstOne = Ship.getInstance(new Cell[]{new Cell(0, 0){}, new Cell(0, 2){}});
        assertThat(true, equalTo(theFirstOne.shootCheck(new Cell(1, 0){})));
    }

    @Test
    public void missTest() {
        Ship theFirstOne = Ship.getInstance(new Cell[]{new Cell(0, 0){}, new Cell(0, 2){}});
        assertThat(false, equalTo(theFirstOne.shootCheck(new Cell(1, 1){})));
    }

    @Test
    public void aliveAfterHitTest() {
        Ship theFirstOne = Ship.getInstance(new Cell[]{new Cell(0, 0){}, new Cell(0, 2){}});
        theFirstOne.shootCheck(new Cell(0, 0){});
        assertThat(true, equalTo(theFirstOne.isAlive()));
    }

    @Test
    public void destroyTest() {
        Ship theFirstOne = Ship.getInstance(new Cell[]{new Cell(0, 0){}, new Cell(0, 1){}});
        theFirstOne.shootCheck(new Cell(0, 0){});
        theFirstOne.shootCheck(new Cell(0, 1){});
        assertThat(false, equalTo(theFirstOne.isAlive()));
    }

}
