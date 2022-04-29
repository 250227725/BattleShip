import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ShipGeneratorTest {

        @Test
        public void getShipsTest() {
            int[] setup = {0, 4, 3, 2, 1};
            Map<Ship, String> ships = ShipGenerator.getShips(10, 10, setup);
            assertThat(true, equalTo(false));
        }
}
