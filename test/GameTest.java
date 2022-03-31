import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {
    @Test
    public void createGameNoPlayerTest() {
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(new LinkedList<>()));
    }

    @Test
    public void createGameOnePlayerTest() {
        Deque<Player> players = new LinkedList<>();
        players.add(new Player("test"));
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(players));
    }

    @Test
    public void createGameTwoSamePlayerTest() {
        Deque<Player> players = new LinkedList<>();
        Player player = new Player("test");
        players.add(player);
        players.add(player);
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(players));
    }

    @Test
    public void createGameTest() {
        Deque<Player> players = new LinkedList<>();
        players.add(new Player("Player One"));
        players.add(new Player("Player Two"));
        Game game = Game.createGame(players);
        assertThat(game.playersCount(), equalTo(2));
    }
}
