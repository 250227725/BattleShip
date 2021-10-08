import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameManagerTest {

    @Test
    public void ordered_players_list_matched_to_init_list() {
        List<Player> initPlayers = new ArrayList<>();
        int playerQuantity = (int) (Math.random() * 10 + 3);
        for (int i = 0; i < playerQuantity; i++) {
            initPlayers.add(new Player());
        }
        GameManager manager = new GameManager(initPlayers);
        assertThat(new HashSet<Player>(initPlayers), equalTo(new HashSet<Player>(manager.getPlayers())));
    }

    @Test
    public void one_cycle_of_nextPlayer_is_fully_ordered_players_list() {
        GameManager manager = new GameManager();
        Set<Player> initPlayers = new HashSet<>(manager.getPlayers());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        for (Player player : initPlayers) {
            manager.addPlayer(player);
        }

        manager.createPlayerSequence();

        Set<Player> orderedPlayers = new HashSet<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }
        assertThat(initPlayers, equalTo(orderedPlayers));
    }

    @Test
    public void sequenced_nextPlayer_for_whole_game_cycle() {
        GameManager manager = new GameManager();
        Set<Player> initPlayers = new HashSet<>(manager.getPlayers());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        for (Player player : initPlayers) {
            manager.addPlayer(player);
        }

        manager.createPlayerSequence();

        List<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }

        for (int turn = 0; turn < (Game.FIELD_WIDTH * Game.FIELD_HEIGHT); turn++) {
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
        GameManager manager = new GameManager();
        List<Player> initPlayers = new ArrayList<>(manager.getPlayers());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        for (Player player : initPlayers) {
            manager.addPlayer(player);
        }

        manager.createPlayerSequence();

        List<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }

        assertThat(initPlayers, not(equalTo(orderedPlayers)));
    }
}
