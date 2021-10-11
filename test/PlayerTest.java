import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlayerTest {
    @Test
    public void saveCorrectedNameTest() {
        String name = "John Smith";
        Player player = new Player(name);
        assertThat(player.getName(), equalTo(name));
    }

    @Test
    public void initCorrectBattleField() {
        Player player = new Player("John Smith");
        for(CellStatus[] line : player.getBattleField()) {
            for (CellStatus cell : line) {
                assertThat(cell, equalTo(CellStatus.UNKNOWN));
            }
        }
    }

}
