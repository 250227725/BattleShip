import org.junit.jupiter.api.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameServiceTest {

    @Test
    public void getIntegerValueInterrupt1Test() throws GameCancelledException, GameInterruptException{
        IOManager manager = new IOManager(new TestInputManager(""), ConsoleOutputManager.getInstance());
        assertThrows(GameInterruptException.class, () -> GameService.getIntegerValue("Введите число", manager));
    }

    @Test
    public void getIntegerValueInterrupt2Test() throws GameCancelledException, GameInterruptException{
        IOManager manager = new IOManager(new TestInputManager("d\ra\rs\ra\ra\ra\ra\ra\ra\ra\ra"), ConsoleOutputManager.getInstance());
        assertThrows(GameInterruptException.class, () -> GameService.getIntegerValue("Введите число", manager));
    }

    @Test
    public void getIntegerValueNormalTest() throws GameCancelledException, GameInterruptException{
        int playersQuantity = 2;
        IOManager manager = new IOManager(new TestInputManager("d\r" + String.valueOf(playersQuantity)), ConsoleOutputManager.getInstance());
        assertThat(GameService.getIntegerValue("Введите число", manager), equalTo(playersQuantity));
    }

    @Test
    public void getIntegerValueCanceledTest() throws GameCancelledException, GameInterruptException{
        IOManager manager = new IOManager(new TestInputManager("exit"), ConsoleOutputManager.getInstance());
        assertThrows(GameCancelledException.class, () -> GameService.getIntegerValue("Введите число", manager));
    }

    @Test
    public void getPlayersQuantityTest() throws GameCancelledException, GameInterruptException{
        int count = 3;
        IOManager manager = new IOManager(new TestInputManager("p1\r\r\r"), ConsoleOutputManager.getInstance());
        assertThat(GameService.getPlayers(count, manager).size(), equalTo(count));
    }

    @Test
    public void getPlayersContentTest() throws GameCancelledException, GameInterruptException{
        int count = 5;
        IOManager manager = new IOManager(new TestInputManager("p1\rp2\r\r\r"), ConsoleOutputManager.getInstance());
        Set<Player> players = GameService.getPlayers(count, manager);
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
        assertThat(GameService.checkFieldAvailability(playerField, shipCell), equalTo(true));
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
        assertThat(GameService.checkFieldAvailability(playerField, shipCell), equalTo(false));
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

    @Test
    public void getCellFromStringTest0() {
        assertThat(GameService.getCellFromString("B3", 5, 5).get(), equalTo(new CellSample(2, 1)));
    }


    @Test
    public void getCellFromStringTest1() {
        assertThat(GameService.getCellFromString("BB", 5, 5), equalTo(Optional.empty()));
    }

    @Test
    public void getCellFromStringTest2() {
        assertThat(GameService.getCellFromString("Я5", 5, 5), equalTo(Optional.empty()));
    }

    @Test
    public void getCellFromStringTest3() {
        assertThat(GameService.getCellFromString("B", 5, 5), equalTo(Optional.empty()));
    }

    @Test
    public void getCellFromStringTest4() {
        assertThat(GameService.getCellFromString("", 5, 5), equalTo(Optional.empty()));
    }

    @Test
    public void getCellFromStringOutOfRangeTest0() {
        assertThat(GameService.getCellFromString("E5", 5, 5).get(), equalTo(new CellSample(4, 4)));
    }

    @Test
    public void getCellFromStringOutOfRangeTest1() {
        assertThat(GameService.getCellFromString("E5", 4, 5), equalTo(Optional.empty()));
    }

    @Test
    public void getCellFromStringOutOfRangeTest2() {
        assertThat(GameService.getCellFromString("E5", 5, 4), equalTo(Optional.empty()));
    }

    @Test
    public void getPlayerGuessTest() throws GameCancelledException, GameInterruptException {
        IOManager manager = new IOManager(new TestInputManager("b3"), ConsoleOutputManager.getInstance());
        assertThat(new CellSample(2, 1), equalTo(GameService.getPlayerGuess(10,10, manager)));
    }

    @Test
    public void checkCopyBattlefieldTest1() {
        CellStatus[][] playerField = {
                {CellStatus.BUSY, CellStatus.BUSY},
                {CellStatus.EMPTY, CellStatus.EMPTY}
        };
        CellStatus[][] copy = GameService.copyBattleField(playerField);
        assertThat(playerField, equalTo(copy));
    }

    @Test
    public void checkCopyBattlefieldTest2() {
        CellStatus[][] playerField = {
                {CellStatus.BUSY, CellStatus.BUSY},
                {CellStatus.EMPTY, CellStatus.EMPTY}
        };
        CellStatus[][] copy = GameService.copyBattleField(playerField);
        playerField[0][0] = CellStatus.EMPTY;
        assertThat(playerField, not(equalTo(copy)));
    }

    @Test
    public void checkCopyBattlefieldTest3() {
        CellStatus[][] playerField = {
                {CellStatus.BUSY, CellStatus.BUSY},
                {CellStatus.EMPTY, CellStatus.EMPTY}
        };
        CellStatus[][] copy = GameService.copyBattleField(playerField);
        playerField[0][0] = CellStatus.EMPTY;
        copy[0][0] = CellStatus.EMPTY;
        assertThat(playerField, equalTo(copy));
    }

    @Test
    public void initEnemyBattleFieldHeightTest() {
        int height = 6;
        int width = 4;
        assertThat(GameService.getEmptyField(height, width).length, equalTo(height));
    }

    @Test
    public void initEnemyBattleFieldWidthTest() {
        int height = 6;
        int width = 4;
        assertThat(GameService.getEmptyField(height, width)[0].length, equalTo(width));
    }

    @Test
    public void initEnemyBattleFieldEmptyTest() {
        int height = 6;
        int width = 4;
        boolean result = Arrays.stream(GameService.getEmptyField(height, width))
                .flatMap(line -> Arrays.stream(line))
                .anyMatch(cell -> cell != CellStatus.UNKNOWN);
        assertThat(false, equalTo(result));
    }
    @Test
    public void getPossibleShotCells_test_00() {
        int height = 2;
        int width = 2;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // H  H
        // H  H
        field[0][0] = CellStatus.HITTED;
        field[0][1] = CellStatus.HITTED;
        field[1][0] = CellStatus.HITTED;
        field[1][1] = CellStatus.HITTED;
        assertThrows(IllegalStateException.class, () -> GameService.getPossibleNeighborsCells(field));
    }

    @Test
    public void getPossibleShotCells_test_01() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells.size(), equalTo(height * width));
    }

    @Test
    public void getPossibleShotCells_test_06() {
        int height = 1;
        int width = 1;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // H
        int x = 0;
        int y = 0;
        field[y][x] = CellStatus.HITTED;
        assertThrows(IllegalStateException.class, () -> GameService.getPossibleNeighborsCells(field));
    }

    @Test
    public void getPossibleShotCells_test_02() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // H  +  U  U  U
        // +  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        int x = 0;
        int y = 0;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x+1));
        result.add(new CellSample(y+1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_03() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  +  H
        // U  U  U  U  +
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        int x = 4;
        int y = 0;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x-1));
        result.add(new CellSample(y+1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_04() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  +
        // U  U  U  +  H
        int x = 4;
        int y = 4;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x-1));
        result.add(new CellSample(y-1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_05() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        // +  U  U  U  U
        // H  +  U  U  U
        int x = 0;
        int y = 4;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x+1));
        result.add(new CellSample(y-1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }


    @Test
    public void getPossibleShotCells_test_07() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  +  U  U
        // U  +  H  +  U
        // U  U  +  U  U
        // U  U  U  U  U
        int x = 2;
        int y = 2;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x+1));
        result.add(new CellSample(y, x-1));
        result.add(new CellSample(y+1, x));
        result.add(new CellSample(y-1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_08() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // +  U  U  U  U
        // H  +  U  U  U
        // +  U  U  U  U
        // U  U  U  U  U
        int x = 0;
        int y = 2;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x+1));
        result.add(new CellSample(y+1, x));
        result.add(new CellSample(y-1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_09() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  +  H  +  U
        // U  U  +  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        int x = 2;
        int y = 0;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x+1));
        result.add(new CellSample(y, x-1));
        result.add(new CellSample(y+1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_10() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  +
        // U  U  U  +  H
        // U  U  U  U  +
        // U  U  U  U  U
        int x = 4;
        int y = 2;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x-1));
        result.add(new CellSample(y+1, x));
        result.add(new CellSample(y-1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_11() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  U  U  U
        // U  U  +  U  U
        // U  +  H  +  U
        int x = 2;
        int y = 4;
        field[y][x] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y, x-1));
        result.add(new CellSample(y, x+1));
        result.add(new CellSample(y-1, x));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_12() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        int x0 = 2;
        int y0 = 0;
        int xN = 2;
        int yN = 4;
        field[y0][x0] = CellStatus.HITTED;
        field[yN-4][xN] = CellStatus.HITTED;
        field[yN-2][xN] = CellStatus.HITTED;
        field[yN-1][xN] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        assertThrows(IllegalStateException.class, () -> GameService.getPossibleNeighborsCells(field));
    }

    @Test
    public void getPossibleShotCells_test_13() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  +  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  +  U  U
        // U  U  U  U  U
        int x0 = 2;
        int y0 = 1;
        int xN = 2;
        int yN = 2;
        field[y0][x0] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y0-1, x0));
        result.add(new CellSample(yN+1, xN));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_14() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  +  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  +  U  U
        int x0 = 2;
        int y0 = 1;
        int xN = 2;
        int yN = 3;
        field[y0][x0] = CellStatus.HITTED;
        field[yN-1][xN] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y0-1, x0));
        result.add(new CellSample(yN+1, xN));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_15() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  +  U  U
        // U  U  U  U  U
        int x0 = 2;
        int y0 = 0;
        int xN = 2;
        int yN = 2;
        field[y0][x0] = CellStatus.HITTED;
        field[yN-1][xN] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(yN+1, xN));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_16() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  +  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        // U  U  H  U  U
        int x0 = 2;
        int y0 = 2;
        int xN = 2;
        int yN = 4;
        field[y0][x0] = CellStatus.HITTED;
        field[yN-1][xN] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y0-1, x0));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_22() {
        int height = 3;
        int width = 3;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U
        // H  H  H
        // U  U  U
        int x0 = 0;
        int y0 = 1;
        int xN = 2;
        int yN = 1;
        field[y0][x0] = CellStatus.HITTED;
        field[yN][xN-1] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        assertThrows(IllegalStateException.class, () -> GameService.getPossibleNeighborsCells(field));
    }

    @Test
    public void getPossibleShotCells_test_23() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // +  H  H  +  U
        // U  U  U  U  U
        // U  U  U  U  U
        int x0 = 1;
        int y0 = 2;
        int xN = 2;
        int yN = 2;
        field[y0][x0] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y0, x0-1));
        result.add(new CellSample(yN, xN+1));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_24() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // +  H  H  H  +
        // U  U  U  U  U
        // U  U  U  U  U
        int x0 = 1;
        int y0 = 2;
        int xN = 2;
        int yN = 2;
        field[y0][x0] = CellStatus.HITTED;
        field[yN][xN-1] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y0, x0-1));
        result.add(new CellSample(yN, xN+1));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_25() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // H  H  H  +  U
        // U  U  U  U  U
        // U  U  U  U  U
        int x0 = 0;
        int y0 = 2;
        int xN = 2;
        int yN = 2;
        field[y0][x0] = CellStatus.HITTED;
        field[yN][xN-1] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(yN, xN+1));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }

    @Test
    public void getPossibleShotCells_test_26() {
        int height = 5;
        int width = 5;
        CellStatus[][] field = GameService.getEmptyField(height, width);
        // U  U  U  U  U
        // U  U  U  U  U
        // U  +  H  H  H
        // U  U  U  U  U
        // U  U  U  U  U
        int x0 = 2;
        int y0 = 2;
        int xN = 4;
        int yN = 2;
        field[y0][x0] = CellStatus.HITTED;
        field[yN][xN-1] = CellStatus.HITTED;
        field[yN][xN] = CellStatus.HITTED;
        Set<CellSample> result = new HashSet<>();
        result.add(new CellSample(y0, x0-1));
        Set<CellSample> cells = new HashSet<>(GameService.getPossibleNeighborsCells(field));
        assertThat(cells, equalTo(result));
    }



}
