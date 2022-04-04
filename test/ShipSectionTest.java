import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ShipSectionTest {

    @Test
    public void shipSectionHitTest() {
        int x = 3;
        int y = 2;
        CellSample attempt = new CellSample(y, x);
        CellSample shipCell = new CellSample(y, x);
        ShipSection shipSection = new ShipSection(shipCell);
        assertThat(true, equalTo(shipSection.hit(attempt)));
    }

    @Test
    public void shipSectionMissTest() {
        int x = 3;
        int y = 2;
        CellSample attempt = new CellSample(y, x);
        CellSample shipCell = new CellSample(x, y);
        ShipSection shipSection = new ShipSection(shipCell);
        assertThat(false, equalTo(shipSection.hit(attempt)));
    }

    @Test
    public void shipSectionIsNotAliveAfterHitTest() {
        int x = 3;
        int y = 2;
        CellSample attempt = new CellSample(y, x);
        CellSample shipCell = new CellSample(y, x);
        ShipSection shipSection = new ShipSection(shipCell);
        shipSection.hit(attempt);
        assertThat(false, equalTo(shipSection.isAlive()));
    }

    @Test
    public void shipSectionIsAliveAfterMissTest() {
        int x = 3;
        int y = 2;
        CellSample attempt = new CellSample(y, x);
        CellSample shipCell = new CellSample(x, y);
        ShipSection shipSection = new ShipSection(shipCell);
        shipSection.hit(attempt);
        assertThat(true, equalTo(shipSection.isAlive()));
    }
}
