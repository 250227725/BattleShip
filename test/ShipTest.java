import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ShipTest {
    class ShootExample extends Cell {
        ShootExample(int x, int y) {
            super(x, y);
        }
    }

    @Test
    public void shipLengthTest() {
        int lenght = ((int) (Math.random() * 5 + 1));
        ShipSection[] sections = new ShipSection[lenght];
        for (int i = 0; i < lenght; i++) {
            sections[i] = new ShipSection(0, i);
        }
        assertThat(lenght, equalTo(new Ship(sections).getLenght()));
    }

    //todo:
    // 1. Check for dublicate cell into array
    // 2. Check for continuity, sequence and keeping direction

    @Test
    public void hitTest() {
        Ship theFirstOne = new Ship(new ShipSection[]{
                new ShipSection(0, 0),
                new ShipSection(0, 1),
                new ShipSection(0, 2)
        });

        ShootExample shoot = new ShootExample(0,1);
        assertThat(true, equalTo(theFirstOne.shootCheck(shoot)));
    }

    @Test
    public void missTest() {
        Ship theFirstOne = new Ship(new ShipSection[]{
                new ShipSection(0, 0),
                new ShipSection(0, 1),
                new ShipSection(0, 2)
        });

        ShootExample shoot = new ShootExample(1,0);
        assertThat(false, equalTo(theFirstOne.shootCheck(shoot)));
    }

    @Test
    public void aliveAfterHitTest() {
        Ship theFirstOne = new Ship(new ShipSection[]{
                new ShipSection(0, 0),
                new ShipSection(0, 1)
        });

        theFirstOne.shootCheck(new ShootExample(0,0));
        assertThat(true, equalTo(theFirstOne.isAlive()));
    }

    @Test
    public void destroyTest() {
        Ship theFirstOne = new Ship(new ShipSection[]{
                new ShipSection(0, 0),
                new ShipSection(0, 1)
        });

        theFirstOne.shootCheck(new ShootExample(0,0));
        theFirstOne.shootCheck(new ShootExample(0,1));
        assertThat(false, equalTo(theFirstOne.isAlive()));
    }
}
