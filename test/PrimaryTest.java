import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrimaryTest {

    @Test
    public void mainTest() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(true));
        players.add(new Player(false));
        Game game = new Game(players);
        assertThat(game.getPlayers().get(0).isHuman(), not(game.getPlayers().get(1).isHuman()));
    }
}
