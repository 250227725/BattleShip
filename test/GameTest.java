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
    public void createGameTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        Game game = Game.createGame(players);
        assertThat(game.playersCount(), equalTo(2));
    }

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

    @Test
    public void checkAliveEnemyTrueTest() {
        Set<Player> players = new HashSet<>();
        Player p1 = new HumanPlayer("Player One");
        Player p2 = new HumanPlayer("Player Two");
        Player p3 = new HumanPlayer("Player Three  ");
        p1.setAlive(true);
        p2.setAlive(false);
        p3.setAlive(true);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        Game game = Game.createGame(players);
        assertThat(game.checkAliveEnemy(p1), equalTo(true));
    }

    @Test
    public void checkAliveEnemyFalseTest() {
        Set<Player> players = new HashSet<>();
        Player p1 = new HumanPlayer("Player One");
        Player p2 = new HumanPlayer("Player Two");
        Player p3 = new HumanPlayer("Player Three  ");
        p1.setAlive(true);
        p2.setAlive(false);
        p3.setAlive(false);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        Game game = Game.createGame(players);
        assertThat(game.checkAliveEnemy(p1), equalTo(false));
    }
}
