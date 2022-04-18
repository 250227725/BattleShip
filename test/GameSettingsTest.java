import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameSettingsTest {

    @Test
    public void createGameSettingsNoPlayerTest() {
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(new HashSet<>()));
    }

    @Test
    public void createGameSettingsOnePlayerTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("test"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players));
    }

    @Test
    public void createGameSettingsThreePlayerTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("test1"));
        players.add(new HumanPlayer("test2"));
        players.add(new HumanPlayer("test3"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players));
    }

    @Test
    public void createGameSettingsMinWidthTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players, GameSettings.MIN_FIELD_WIDTH - 1, 10, 0));
    }

    @Test
    public void createGameSettingsMaxWidthTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players, GameSettings.MAX_FIELD_WIDTH + 1, 10, 0));
    }

    @Test
    public void createGameSettingsMinHeightTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players, 10, GameSettings.MIN_FIELD_HEIGHT - 1, 0));
    }

    @Test
    public void createGameSettingsMaxHeightTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players, 10, GameSettings.MAX_FIELD_HEIGHT + 1, 0));
    }
    @Test
    public void createGameSettingsMinDifficultyTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players, 10, GameSettings.MIN_DIFFICULTY - 1, 0));
    }

    @Test
    public void createGameSettingsMaxDifficultyTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        assertThrows(IllegalArgumentException.class, () -> GameSettings.createSettings(players, 10, GameSettings.MAX_DIFFICULTY + 1, 0));
    }
}
