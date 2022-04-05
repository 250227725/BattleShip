import org.junit.jupiter.api.Test;

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


    @Test
    public void checkFieldAvailabilityTrueTest() {
        CellStatus[][] playerField = {
                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY},
                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.EMPTY, CellStatus.EMPTY},
                {CellStatus.EMPTY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY},
                {CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY},
                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY}
        };
        CellSample[] shipCell = {new CellSample(Cell.HorizontalCellNames.D, 2), new CellSample(Cell.HorizontalCellNames.D, 3), new CellSample(Cell.HorizontalCellNames.D, 4)};
        assertThat(Project1st.service.checkFieldAvailability(playerField, shipCell), equalTo(true));
    }

    @Test
    public void checkFieldAvailabilityFalseTest() {
        CellStatus[][] playerField = {
                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY},
                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.EMPTY, CellStatus.EMPTY},
                {CellStatus.EMPTY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.BUSY, CellStatus.BUSY},
                {CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY},
                {CellStatus.BUSY, CellStatus.BUSY, CellStatus.EMPTY, CellStatus.BUSY, CellStatus.EMPTY}
        };
        CellSample[] shipCell = {new CellSample(Cell.HorizontalCellNames.D, 2), new CellSample(Cell.HorizontalCellNames.D, 3), new CellSample(Cell.HorizontalCellNames.D, 4)};
        assertThat(Project1st.service.checkFieldAvailability(playerField, shipCell), equalTo(false));
    }

    @Test
    public void getEmptyFieldTest() {
        CellStatus[][] playerField = {
                {CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN},
                {CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN},
                {CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN},
                {CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN},
                {CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN, CellStatus.UNKNOWN}
        };
        assertThat(GameService.getEmptyField(5,4), equalTo(playerField));
    }



}
