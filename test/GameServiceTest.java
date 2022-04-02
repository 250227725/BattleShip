import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameServiceTest {

    @Test
    public void getIntegerValueInterrupt1Test() throws GameCancelledException, GameInterruptException{
        GameService service = GameService.getInstance(new IOManager(new TestInputManager(""), ConsoleOutputManager.getInstance()));
        assertThrows(GameInterruptException.class, () -> service.getIntegerValue("Введите число"));
    }

    @Test
    public void getIntegerValueInterrupt2Test() throws GameCancelledException, GameInterruptException{
        GameService service = GameService.getInstance(new IOManager(new TestInputManager("d\ra\rs\ra\ra\ra\ra\ra\ra\ra\ra"), ConsoleOutputManager.getInstance()));
        assertThrows(GameInterruptException.class, () -> service.getIntegerValue("Введите число"));
    }

    @Test
    public void getIntegerValueNormalTest() throws GameCancelledException, GameInterruptException{
        int playersQuantity = 2;
        GameService service = GameService.getInstance(new IOManager(new TestInputManager("d\r" + String.valueOf(playersQuantity)), ConsoleOutputManager.getInstance()));
        assertThat(service.getIntegerValue("Введите число"), equalTo(playersQuantity));
    }

    @Test
    public void getIntegerValueCanceledTest() throws GameCancelledException, GameInterruptException{
        GameService service = GameService.getInstance(new IOManager(new TestInputManager("exit"), ConsoleOutputManager.getInstance()));
        assertThrows(GameCancelledException.class, () -> service.getIntegerValue("Введите число"));
    }

    @Test
    public void getPlayersQuantityTest() throws GameCancelledException, GameInterruptException{
        int count = 3;
        GameService service = GameService.getInstance(new IOManager(new TestInputManager("p1\r\r\r"), ConsoleOutputManager.getInstance()));
        assertThat(service.getPlayers(count).size(), equalTo(count));
    }

    @Test
    public void getPlayersContentTest() throws GameCancelledException, GameInterruptException{
        int count = 5;
        GameService service = GameService.getInstance(new IOManager(new TestInputManager("p1\rp2\r\r\r"), ConsoleOutputManager.getInstance()));
        Set<Player> players = service.getPlayers(count);
        int human = (int) players.stream().filter((x) -> x.isHuman()).count();
        int ai = (int) players.stream().filter((x) -> !x.isHuman()).count();
        assertThat(new int[] {human, ai}, equalTo(new int[]{2, 3}));
    }



    // old versions of test
//
//    @Test
//    public void addBusyCell1Test() {
//        Set<Cell> busyCell = GameService.getInstance(manager).addBusyCell(new ShipSection(0,0));
//        Set<Cell> sample = new HashSet<>();
//        sample.add(new Cell(0,0) {});
//        sample.add(new Cell(0,1) {});
//        sample.add(new Cell(1,0) {});
//        sample.add(new Cell(1,1) {});
//        assertThat(busyCell, equalTo(sample));
//    }
//
//    @Test
//    public void addBusyCell2Test() {
//        Set<Cell> busyCell = GameService.getInstance(manager).addBusyCell(new ShipSection(Project1st.FIELD_WIDTH - 1,0));
//        Set<Cell> sample = new HashSet<>();
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,0) {});
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,1) {});
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,0) {});
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,1) {});
//        assertThat(busyCell, equalTo(sample));
//    }
//
//    @Test
//    public void addBusyCell3Test() {
//        Set<Cell> busyCell = GameService.getInstance(manager).addBusyCell(new ShipSection(0,Project1st.FIELD_HEIGHT - 1));
//        Set<Cell> sample = new HashSet<>();
//        sample.add(new Cell(0,Project1st.FIELD_HEIGHT - 1) {});
//        sample.add(new Cell(1,Project1st.FIELD_HEIGHT - 1) {});
//        sample.add(new Cell(0,Project1st.FIELD_HEIGHT - 2) {});
//        sample.add(new Cell(1,Project1st.FIELD_HEIGHT - 2) {});
//        assertThat(busyCell, equalTo(sample));
//    }
//
//    @Test
//    public void addBusyCell4Test() {
//        Set<Cell> busyCell = GameService.getInstance(manager).addBusyCell(new ShipSection(Project1st.FIELD_WIDTH - 1,Project1st.FIELD_HEIGHT - 1));
//        Set<Cell> sample = new HashSet<>();
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,Project1st.FIELD_HEIGHT - 1) {});
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,Project1st.FIELD_HEIGHT - 2) {});
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,Project1st.FIELD_HEIGHT - 1) {});
//        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,Project1st.FIELD_HEIGHT - 2) {});
//        assertThat(busyCell, equalTo(sample));
//    }
//
//    @Test
//    public void addBusyCell5Test() {
//        Set<Cell> busyCell = GameService.getInstance(manager).addBusyCell(new ShipSection(3,3));
//        Set<Cell> sample = new HashSet<>();
//        sample.add(new Cell(2,2) {});
//        sample.add(new Cell(3,2) {});
//        sample.add(new Cell(4,2) {});
//        sample.add(new Cell(2,3) {});
//        sample.add(new Cell(3,3) {});
//        sample.add(new Cell(4,3) {});
//        sample.add(new Cell(2,4) {});
//        sample.add(new Cell(3,4) {});
//        sample.add(new Cell(4,4) {});
//        assertThat(busyCell, equalTo(sample));
//    }
//
//    @Test
//    public void simpleAddTest() {
//        Player player = new Player("Test");
//        GameService.getInstance(manager).addShip(player, new int[][]{{0,0}, {0,1}});
//        assertThat(player.getShips().size(), equalTo(1));
//    }
//
//    @Test
//    public void multiAddTest() {
//        Player player = new Player("Test");
//        GameService service = GameService.getInstance(manager);
//        int lenght = ((int) (Math.random() * 5 + 1));
//        for (int i = 0; i < lenght; i++) {
//            service.addShip(player, new int[][]{{i * 2, i * 2}});
//        }
//        assertThat(player.getShips().size(), equalTo(lenght));
//    }
//
//    @Test
//    public void dublicateAddTest() throws IllegalArgumentException{
//        Player player = new Player("Test");
//        GameService service = GameService.getInstance(manager);
//        service.addShip(player, new int[][]{{0,0}, {0,1}});
//        assertThrows(IllegalArgumentException.class, () -> service.addShip(player, new int[][]{{0,0}, {0,1}}));
//    }
//
//    @Test
//    public void neighbourAddHorizontalTest() throws IllegalArgumentException{
//        Player player = new Player("Test");
//        GameService service = GameService.getInstance(manager);
//        service.addShip(player, new int[][]{{0,0}, {0,1}});
//        assertThrows(IllegalArgumentException.class, () -> service.addShip(player, new int[][]{{0,2}, {0,3}}));
//    }
//
//    @Test
//    public void neighbourAddVerticalTest() throws IllegalArgumentException{
//        Player player = new Player("Test");
//        GameService service = GameService.getInstance(manager);
//        service.addShip(player, new int[][]{{0,0}, {0,1}});
//        assertThrows(IllegalArgumentException.class, () -> service.addShip(player, new int[][]{{1,1}, {2,1}}));
//    }
//
//    @Test
//    public void createPlayersTest() {
//        GameService service = GameService.getInstance(manager);
//        List<Player> players = service.createPlayers();
//        List<Player> test = new ArrayList<Player>();
//        assertThat(players, equalTo(test));
//    }

}
