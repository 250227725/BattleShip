import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerTest {
    @Test
    public void saveCorrectedNameTest() {
        String name = "John Smith";
        Player player = new HumanPlayer(name);
        assertThat(player.getName(), equalTo(name));
    }

    @Test
    public void initEnemyBattleFieldHeightTest() {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        player.initEnemyBattlefield(height, width);
        assertThat(player.getEnemyBattlefield().length, equalTo(height));
    }

    @Test
    public void initEnemyBattleFieldWidthTest() {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        player.initEnemyBattlefield(height, width);
        assertThat(player.getEnemyBattlefield()[0].length, equalTo(width));
    }

    @Test
    public void initEnemyBattleFieldEmptyTest() {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        player.initEnemyBattlefield(height, width);
        boolean result = Arrays.stream(player.getEnemyBattlefield())
                .flatMap(line -> Arrays.stream(line))
                .anyMatch(cell -> cell != CellStatus.UNKNOWN);
        assertThat(false, equalTo(result));
    }

    @Test
    public void initEnemyBattleFieldFillTest1() {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int shootY = 2;
        int shootX= 3;
        CellSample shoot = new CellSample(shootY, shootX);
        Ship.ShipHitStatus shootResult = Ship.ShipHitStatus.HITED;
        CellStatus cellStatus = CellStatus.HITTED;
        player.initEnemyBattlefield(height, width);
        player.fillBattlefield(shoot, shootResult);
        boolean result = false;
        for (int y = 0; y < player.getEnemyBattlefield().length; y++) {
            for (int x = 0; x < player.getEnemyBattlefield()[0].length; x++) {
                result = player.getEnemyBattlefield()[y][x] == CellStatus.UNKNOWN && shootY != y && shootX != x;
            }
        }
        assertThat(false, equalTo(result));
    }

    @Test
    public void initEnemyBattleFieldFillTest2() {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int y = 2;
        int x = 3;
        CellSample shoot = new CellSample(y, x);
        Ship.ShipHitStatus shootResult = Ship.ShipHitStatus.HITED;
        CellStatus cellStatus = CellStatus.HITTED;
        player.initEnemyBattlefield(height, width);
        player.fillBattlefield(shoot, shootResult);
        assertThat(cellStatus, equalTo(player.getEnemyBattlefield()[y][x]));
    }

    @Test
    public void initEnemyBattleFieldFillTest3() {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int y = 2;
        int x = 3;
        CellSample shoot = new CellSample(y, x);
        Ship.ShipHitStatus shootResult = Ship.ShipHitStatus.DESTROYED;
        CellStatus cellStatus = CellStatus.DESTROYED;
        player.initEnemyBattlefield(height, width);
        player.fillBattlefield(shoot, shootResult);
        assertThat(cellStatus, equalTo(player.getEnemyBattlefield()[y][x]));
    }

    @Test
    public void initEnemyBattleFieldFillTest4() {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int y = 2;
        int x = 3;
        CellSample shoot = new CellSample(y, x);
        Ship.ShipHitStatus shootResult = Ship.ShipHitStatus.MISSED;
        CellStatus cellStatus = CellStatus.MISSED;
        player.initEnemyBattlefield(height, width);
        player.fillBattlefield(shoot, shootResult);
        assertThat(cellStatus, equalTo(player.getEnemyBattlefield()[y][x]));
    }

//    @Test
//    public void addShipTest() {
//        Player player = new HumanPlayer("Test");
//        player.addShip(new int[][]{{0,0}, {0,1}});
//        assertThat(player.getShips().size(), equalTo(1));
//    }
//
//    @Test
//    public void multiAddTest() {
//        Player player = new HumanPlayer("Test");
//        int lenght = ((int) (Math.random() * 5 + 1));
//        for (int i = 0; i < lenght; i++) {
//            player.addShip(new int[][]{{i * 2, i * 2}});
//        }
//        assertThat(player.getShips().size(), equalTo(lenght));
//    }
//
//    @Test
//    public void dublicateAddTest() throws IllegalArgumentException{
//        Player player = new HumanPlayer("Test");
//        player.addShip(new int[][]{{0,0}, {0,1}});
//        assertThrows(IllegalArgumentException.class, () -> player.addShip(new int[][]{{0,0}, {0,1}}));
//    }
//
//    @Test
//    public void neighbourAddHorizontalTest() throws IllegalArgumentException{
//        Player player = new HumanPlayer("Test");
//        player.addShip(new int[][]{{0,0}, {0,1}});
//        assertThrows(IllegalArgumentException.class, () -> player.addShip(new int[][]{{0,2}, {0,3}}));
//    }
//
//    @Test
//    public void neighbourAddVerticalTest() throws IllegalArgumentException{
//        Player player = new HumanPlayer("Test");
//        player.addShip(new int[][]{{0,0}, {0,1}});
//        assertThrows(IllegalArgumentException.class, () -> player.addShip(new int[][]{{1,0}, {1,1}}));
//    }

}
