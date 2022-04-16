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
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(new HashSet<>()));
    }

    @Test
    public void createGameOnePlayerTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("test"));
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(players));
    }

    @Test
    public void createGameMinWidthTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(players, Project1st.MIN_FIELD_WIDTH - 1, 10, 0));
    }

    @Test
    public void createGameMaxWidthTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(players, Project1st.MAX_FIELD_WIDTH + 1, 10, 0));
    }

    @Test
    public void createGameMinHeightTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(players, 10, Project1st.MIN_FIELD_HEIGHT - 1, 0));
    }

    @Test
    public void createGameMaxHeightTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> Game.createGame(players, 10, Project1st.MAX_FIELD_HEIGHT + 1, 0));
    }
}
