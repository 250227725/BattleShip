import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    @Test
    public void saveCorrectedNameTest() {
        String name = "John Smith";
        Player player = new HumanPlayer(name);
        assertThat(player.getName(), equalTo(name));
    }

    @Test
    public void addShipTest() {
        Player player = new HumanPlayer("Test");
        player.addShip(new int[][]{{0,0}, {0,1}});
        assertThat(player.getShips().size(), equalTo(1));
    }

    @Test
    public void multiAddTest() {
        Player player = new HumanPlayer("Test");
        int lenght = ((int) (Math.random() * 5 + 1));
        for (int i = 0; i < lenght; i++) {
            player.addShip(new int[][]{{i * 2, i * 2}});
        }
        assertThat(player.getShips().size(), equalTo(lenght));
    }

    @Test
    public void dublicateAddTest() throws IllegalArgumentException{
        Player player = new HumanPlayer("Test");
        player.addShip(new int[][]{{0,0}, {0,1}});
        assertThrows(IllegalArgumentException.class, () -> player.addShip(new int[][]{{0,0}, {0,1}}));
    }

    @Test
    public void neighbourAddHorizontalTest() throws IllegalArgumentException{
        Player player = new HumanPlayer("Test");
        player.addShip(new int[][]{{0,0}, {0,1}});
        assertThrows(IllegalArgumentException.class, () -> player.addShip(new int[][]{{0,2}, {0,3}}));
    }

    @Test
    public void neighbourAddVerticalTest() throws IllegalArgumentException{
        Player player = new HumanPlayer("Test");
        player.addShip(new int[][]{{0,0}, {0,1}});
        assertThrows(IllegalArgumentException.class, () -> player.addShip(new int[][]{{1,0}, {1,1}}));
    }

}
