import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrimaryTest {

    @Test
    public void mainTest() {
        BattleShip game = new BattleShip(new Player(true), new Player(false));
        assertThat(game.getPlayer1().isHuman(), not(game.getPlayer2().isHuman()));
    }
}
