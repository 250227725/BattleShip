import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameServiceTest {

    @Test
    public void addBusyCell1Test() {
        Set<Cell> busyCell = GameService.getInstance().addBusyCell(new ShipSection(0,0));
        Set<Cell> sample = new HashSet<>();
        sample.add(new Cell(0,0) {});
        sample.add(new Cell(0,1) {});
        sample.add(new Cell(1,0) {});
        sample.add(new Cell(1,1) {});
        assertThat(busyCell, equalTo(sample));
    }

    @Test
    public void addBusyCell2Test() {
        Set<Cell> busyCell = GameService.getInstance().addBusyCell(new ShipSection(Project1st.FIELD_WIDTH - 1,0));
        Set<Cell> sample = new HashSet<>();
        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,0) {});
        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,1) {});
        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,0) {});
        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,1) {});
        assertThat(busyCell, equalTo(sample));
    }

    @Test
    public void addBusyCell3Test() {
        Set<Cell> busyCell = GameService.getInstance().addBusyCell(new ShipSection(0,Project1st.FIELD_HEIGHT - 1));
        Set<Cell> sample = new HashSet<>();
        sample.add(new Cell(0,Project1st.FIELD_HEIGHT - 1) {});
        sample.add(new Cell(1,Project1st.FIELD_HEIGHT - 1) {});
        sample.add(new Cell(0,Project1st.FIELD_HEIGHT - 2) {});
        sample.add(new Cell(1,Project1st.FIELD_HEIGHT - 2) {});
        assertThat(busyCell, equalTo(sample));
    }

    @Test
    public void addBusyCell4Test() {
        Set<Cell> busyCell = GameService.getInstance().addBusyCell(new ShipSection(Project1st.FIELD_WIDTH - 1,Project1st.FIELD_HEIGHT - 1));
        Set<Cell> sample = new HashSet<>();
        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,Project1st.FIELD_HEIGHT - 1) {});
        sample.add(new Cell(Project1st.FIELD_WIDTH - 1,Project1st.FIELD_HEIGHT - 2) {});
        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,Project1st.FIELD_HEIGHT - 1) {});
        sample.add(new Cell(Project1st.FIELD_WIDTH - 2,Project1st.FIELD_HEIGHT - 2) {});
        assertThat(busyCell, equalTo(sample));
    }

    @Test
    public void addBusyCell5Test() {
        Set<Cell> busyCell = GameService.getInstance().addBusyCell(new ShipSection(3,3));
        Set<Cell> sample = new HashSet<>();
        sample.add(new Cell(2,2) {});
        sample.add(new Cell(3,2) {});
        sample.add(new Cell(4,2) {});
        sample.add(new Cell(2,3) {});
        sample.add(new Cell(3,3) {});
        sample.add(new Cell(4,3) {});
        sample.add(new Cell(2,4) {});
        sample.add(new Cell(3,4) {});
        sample.add(new Cell(4,4) {});
        assertThat(busyCell, equalTo(sample));
    }

    @Test
    public void simpleAddTest() {
        Player player = new Player("Test");
        GameService.getInstance().addShip(player, new int[][]{{0,0}, {0,1}});
        assertThat(player.getShips().size(), equalTo(1));
    }

    @Test
    public void multiAddTest() {
        Player player = new Player("Test");
        GameService service = GameService.getInstance();
        int lenght = ((int) (Math.random() * 5 + 1));
        for (int i = 0; i < lenght; i++) {
            service.addShip(player, new int[][]{{i * 2, i * 2}});
        }
        assertThat(player.getShips().size(), equalTo(lenght));
    }

    @Test
    public void dublicateAddTest() throws IllegalArgumentException{
        Player player = new Player("Test");
        GameService service = GameService.getInstance();
        service.addShip(player, new int[][]{{0,0}, {0,1}});
        assertThrows(IllegalArgumentException.class, () -> service.addShip(player, new int[][]{{0,0}, {0,1}}));
    }

    @Test
    public void neighbourAddHorizontalTest() throws IllegalArgumentException{
        Player player = new Player("Test");
        GameService service = GameService.getInstance();
        service.addShip(player, new int[][]{{0,0}, {0,1}});
        assertThrows(IllegalArgumentException.class, () -> service.addShip(player, new int[][]{{0,2}, {0,3}}));
    }

    @Test
    public void neighbourAddVerticalTest() throws IllegalArgumentException{
        Player player = new Player("Test");
        GameService service = GameService.getInstance();
        service.addShip(player, new int[][]{{0,0}, {0,1}});
        assertThrows(IllegalArgumentException.class, () -> service.addShip(player, new int[][]{{1,1}, {2,1}}));
    }

    @Test
    public void createPlayersTest() {
        GameService service = GameService.getInstance();
        List<Player> players = service.createPlayers();
        List<Player> test = new ArrayList<Player>();
        assertThat(players, equalTo(test));
    }

}
