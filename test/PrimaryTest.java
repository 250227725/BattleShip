import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PrimaryTest {

    @Test
    public void mainTest() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(true, new ArrayList<>()));
        players.add(new Player(false, new ArrayList<>()));
        Game game = new Game(players);
        assertThat(game.getPlayers().get(0).isHuman(), not(game.getPlayers().get(1).isHuman()));
    }

    @Test
    public void checkNullResultForShipsGenerating() {
        List<Ship> ships = Game.generateShips();
        assertThat(ships, notNullValue());
    }

    @Test
    public void checkNullShipAfetrGenerating() {
        List<Ship> ships = Game.generateShips();
        for (Ship ship : ships) {
            assertThat(ship.getSections(), notNullValue());
        }
    }

    @Test
    public void genreatingShipViaSettings() {
        List<Ship> ships = Game.generateShips();
        if (ships != null) {
        Map<Integer, Integer> shipsSettings = new HashMap<>();
        for (Ship ship : ships) {
            int size = ship.getSections().size();
            if (shipsSettings.get(size) == null) {
                shipsSettings.put(ship.getSections().size(), 1);
            }
            else {
                shipsSettings.put(ship.getSections().size(), shipsSettings.get(ship.getSections().size()) + 1);
            }
        }
        assertThat(shipsSettings, equalTo(Game.getShipsSettings()));
        }
    }
}
