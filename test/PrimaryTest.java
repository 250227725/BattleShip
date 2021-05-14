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
        players.add(new Player(true));
        players.add(new Player(false));
        Game game = new Game(players);
        assertThat(game.getPlayers().get(0).isHuman(), not(game.getPlayers().get(1).isHuman()));
    }

    @Test
    public void checkShipSize() {
        assertThat(new Ship(4).getSections().size(), equalTo(4));
        assertThat(new Ship(3).getSections().size(), not(4));
    }

    @Test
    public void checkTotalShipSectionQuantity() {
        Player player = new Player(true);
        player.generateShips();
        int settingQuantity = 0;
        for (int k = 0; k < Game.getShipsSettings().length; k++) {
            settingQuantity += (k+1) * Game.getShipsSettings()[k];
        }
        int sectionQuantity = 0;
        for (Ship ship : player.getShips()) {
            sectionQuantity += ship.getSections().size();
        }
        int battleFieldSectionQuantity = 0;
        for (int y = 0; y < Game.getHEIGHT(); y++) {
            for (int x = 0; x < Game.getWIDTH(); x++) {
                if (player.getBattleField().field[y][x] == 1) battleFieldSectionQuantity++;
            }
        }
        assertThat(settingQuantity, equalTo(sectionQuantity));
        assertThat(settingQuantity, equalTo(battleFieldSectionQuantity));
    }

//    @Test
//    public void checkNullResultForShipsGenerating() {
//        List<Ship> ships = Game.generateShips();
//        assertThat(ships, notNullValue());
//    }
//
//    @Test
//    public void checkNullShipAfterGenerating() {
//        List<Ship> ships = Game.generateShips();
//        for (Ship ship : ships) {
//            assertThat(ship.getSections(), notNullValue());
//        }
//    }
//
//    @Test
//    public void genreatingShipViaSettings() {
//        List<Ship> ships = Game.generateShips();
//        if (ships != null) {
//        Map<Integer, Integer> shipsSettings = new HashMap<>();
//        for (Ship ship : ships) {
//            int size = ship.getSections().size();
//            if (shipsSettings.get(size) == null) {
//                shipsSettings.put(ship.getSections().size(), 1);
//            }
//            else {
//                shipsSettings.put(ship.getSections().size(), shipsSettings.get(ship.getSections().size()) + 1);
//            }
//        }
//        assertThat(shipsSettings, equalTo(Game.getShipsSettings()));
//        }
//    }
}
