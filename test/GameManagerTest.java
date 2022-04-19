import org.junit.jupiter.api.Test;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameManagerTest {
    private static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    private Set<Player> generateInitPlayerList() {
        Set<Player> initPlayers = new HashSet<>();
        int playerQuantity = (int) (Math.random() * 10 + 3);
        for (int i = 0; i < playerQuantity; i++) {
            initPlayers.add(new HumanPlayer(generateString(new Random(), "qwertyuiopasdfghjklzxcvbnm", 6)));
        }
        return initPlayers;
    }


    @Test
    public void ordered_players_list_matched_to_init_list() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers, 10, 10, 1);
        assertThat(new HashSet<Player>(initPlayers), equalTo(new HashSet<Player>(manager.getPlayers())));
    }

    @Test
    public void players_clone_after_generate_sequence() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers, 10, 10, 1);
        Player p1 = manager.getPlayers().getFirst();
        boolean result = initPlayers.stream()
                .anyMatch(s -> s == p1);
        assertThat(result, equalTo(false));
    }


    @Test
    public void one_cycle_of_nextPlayer_is_fully_ordered_players_list() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers, 10, 10, 1);

        Set<Player> orderedPlayers = new HashSet<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }
        assertThat(new HashSet<Player>(initPlayers), equalTo(orderedPlayers));
    }

    @Test
    public void sequenced_nextPlayer_for_whole_game_cycle() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers, 10, 10, 1);

        List<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }

        for (int turn = 0; turn < (10 * 10); turn++) {
            for (int i = 0; i < orderedPlayers.size(); i++) {
                assertThat(orderedPlayers.get(i), equalTo(manager.getNextPlayer()));
            }
        }
    }

    @Test
    //@Disabled
    /*Ordered sequence can be the same as init, so failing of this test case
    doesn't mean what method is incorrect. You can run it several times to check result
     */
    public void player_sequence_is_differed_from_init_player_list() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers, 10, 10, 1);

        List<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }

        assertThat(initPlayers, not(equalTo(orderedPlayers)));
    }

    @Test
    public void game_run_test() {
        GameManager manager = new GameManager(generateInitPlayerList(), 10, 10, 1);
        manager.startGame();
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
        GameManager manager = new GameManager(players, 10, 10, 1);
        assertThat(manager.checkAliveEnemy(), equalTo(false));
    }

    @Test
    public void createGameTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        GameManager manager = new GameManager(players, 10, 10, 1);
        assertThat(manager.playersCount(), equalTo(2));
    }

}
