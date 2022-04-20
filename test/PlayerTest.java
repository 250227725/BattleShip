import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlayerTest {
    @Test
    public void saveCorrectedNameTest() {
        String name = "John Smith";
        Player player = new HumanPlayer(name);
        assertThat(player.getName(), equalTo(name));
    }

    @Test
    public void humanPlayerCloneTest1() {
        Player p1 = new HumanPlayer("Test");
        Player clone = p1.clone();
        assertThat(false, equalTo(p1 == clone));
    }

    @Test
    public void humanPlayerCloneTest2() {
        Player p1 = new HumanPlayer("Test");
        assertThat(p1, equalTo(p1));
    }

    @Test
    public void AIPlayerCloneTest1() {
        Player p1 = new AIPlayer("Test");
        Player clone = p1.clone();
        assertThat(false, equalTo(p1 == clone));
    }

    @Test
    public void AIPlayerCloneTest2() {
        Player p1 = new AIPlayer("Test");
        assertThat(p1, equalTo(p1));
    }

    @Test
    public void initEnemyBattleFieldFillTest1() throws GameCancelledException, GameInterruptException {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int shootY = 2;
        int shootX= 3;
        CellSample shoot = new CellSample(shootY, shootX);
        CellStatus shootResult = CellStatus.HITTED;
        IOManager manager = new IOManager(new TestInputManager("a1"), ConsoleOutputManager.getInstance());
        player.init(GameService.getEmptyField(height, width), manager);
        player.fillEnemyBattlefield(shoot, shootResult);
        boolean result = false;
        for (int y = 0; y < player.getEnemyBattlefield().length; y++) {
            for (int x = 0; x < player.getEnemyBattlefield()[0].length; x++) {
                result = player.getEnemyBattlefield()[y][x] == CellStatus.UNKNOWN && shootY != y && shootX != x;
            }
        }
        assertThat(false, equalTo(result));
    }

    @Test
    public void initEnemyBattleFieldFillTest2() throws GameCancelledException, GameInterruptException {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int y = 2;
        int x = 3;
        CellSample shoot = new CellSample(y, x);
        CellStatus shootResult = CellStatus.HITTED;
        IOManager manager = new IOManager(new TestInputManager("a1"), ConsoleOutputManager.getInstance());
        player.init(GameService.getEmptyField(height, width), manager);
        player.fillEnemyBattlefield(shoot, shootResult);
        assertThat(shootResult, equalTo(player.getEnemyBattlefield()[y][x]));
    }

    @Test
    public void initEnemyBattleFieldFillTest3() throws GameCancelledException, GameInterruptException {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int y = 2;
        int x = 3;
        CellSample shoot = new CellSample(y, x);
        CellStatus shootResult = CellStatus.DESTROYED;
        IOManager manager = new IOManager(new TestInputManager("a1"), ConsoleOutputManager.getInstance());
        player.init(GameService.getEmptyField(height, width), manager);
        player.fillEnemyBattlefield(shoot, shootResult);
        assertThat(shootResult, equalTo(player.getEnemyBattlefield()[y][x]));
    }

    @Test
    public void initEnemyBattleFieldFillTest4() throws GameCancelledException, GameInterruptException {
        Player player = new HumanPlayer("Test");
        int height = 6;
        int width = 4;
        int y = 2;
        int x = 3;
        CellSample shoot = new CellSample(y, x);
        CellStatus shootResult = CellStatus.MISSED;
        IOManager manager = new IOManager(new TestInputManager("a1"), ConsoleOutputManager.getInstance());
        player.init(GameService.getEmptyField(height, width), manager);
        player.fillEnemyBattlefield(shoot, shootResult);
        assertThat(shootResult, equalTo(player.getEnemyBattlefield()[y][x]));
    }
}
