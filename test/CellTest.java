import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CellTest {

    class CellClass extends Cell {
        public CellClass(int x, int y) {
            super(x,y);
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

}

