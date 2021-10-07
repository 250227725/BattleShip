import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GameManagerTest {

    @Test
    public void new_manager_empty_player_list() {
        assertThat(new GameManager().getPlayers().size(), is(0));
    }

    @Test
    public void adding_new_player() {
        GameManager manager = new GameManager();
        manager.addPlayer(new Player());
        assertThat(manager.getPlayers().size(), is(1));
    }

    @Test
    public void adding_same_player() {
        GameManager manager = new GameManager();
        Player player = new Player();
        manager.addPlayer(player);
        manager.addPlayer(player);
        assertThat(manager.getPlayers().size(), is(1));
    }

    @Test
    public void adding_another_player() {
        GameManager manager = new GameManager();
        manager.addPlayer(new Player());
        manager.addPlayer(new Player());
        assertThat(manager.getPlayers().size(), is(2));
    }

    @Test
    public void ordered_players_list_matched_to_init_list() {
        GameManager manager = new GameManager();
        Set<Player> initPlayers = new HashSet<>(manager.getPlayers());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        initPlayers.add(new Player());
        for (Player player : initPlayers) {
            manager.addPlayer(player);
        }
        manager.orderPlayers();
        Set<Player> orderedPlayers = new HashSet<>(manager.getPlayers());
        assertThat(initPlayers, equalTo(orderedPlayers));
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

        manager.orderPlayers();

        Set<Player> orderedPlayers = new HashSet<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }
        assertThat(initPlayers, equalTo(orderedPlayers));
    }

}
