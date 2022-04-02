import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ShipTest {

    @Test
    public void shipFactoryBasicTest() {
        int[][] sections = new int[][]{{2,3}, {2,4}};
        assertThat(Ship.getInstance(sections).getLenght(), equalTo(sections.length));
    }

    @Test
    public void shipFactoryLengthTest() {
        int lenght = ((int) (Math.random() * 5 + 1));
        int[][] sections = new int[lenght][2];
        for (int i = 0; i < lenght; i++) {
            sections[i] = new int[]{0, i};
        }
        assertThat(Ship.getInstance(sections).getLenght(), equalTo(lenght));
    }

    @Test
    public void shipFactoryDublicateSectionTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new int[][]{{2,3}, {2,3}}));
    }

    @Test
    public void shipFactoryNullSectionTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(null));
    }

    @Test
    public void shipFactoryIncorrectSectionTest() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new int[][]{{2,3}, {2,4,0}}));
    }

    @Test
    public void shipFactoryIncorrectSequenceSection1Test() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new int[][]{{2,3}, {2,5}}));
    }

    @Test
    public void shipFactoryIncorrectSequenceSection2Test() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new int[][]{{2,3}, {4,3}}));
    }

    @Test
    public void shipFactoryIncorrectSequenceSection3Test() throws IllegalArgumentException{
        assertThrows(IllegalArgumentException.class, () -> Ship.getInstance(new int[][]{{2,3}, {3,4}}));
    }

    @Test
    public void hitTest() {
        Ship theFirstOne = Ship.getInstance(new int[][]{{0, 0}, {0, 1}, {0, 2}});
        assertThat(true, equalTo(theFirstOne.shootCheck(new Cell(1, 0){})));
    }

    @Test
    public void missTest() {
        Ship theFirstOne = Ship.getInstance(new int[][]{{0, 0}, {0, 1}, {0, 2}});
        assertThat(false, equalTo(theFirstOne.shootCheck(new Cell(0, 1){})));
    }

    @Test
    public void aliveAfterHitTest() {
        Ship theFirstOne = Ship.getInstance(new int[][]{{0, 0}, {0, 1}});
        theFirstOne.shootCheck(new Cell(0, 0){});
        assertThat(true, equalTo(theFirstOne.isAlive()));
    }

    @Test
    public void destroyTest() {
        Ship theFirstOne = Ship.getInstance(new int[][]{{0, 0}, {0, 1}});
        theFirstOne.shootCheck(new Cell(0, 0){});
        theFirstOne.shootCheck(new Cell(1, 0){});
        assertThat(false, equalTo(theFirstOne.isAlive()));
    }

}
