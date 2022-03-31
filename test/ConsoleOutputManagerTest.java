import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConsoleOutputManagerTest {
    @Test
    public void printBattlefieldTest() {
        OutputManager manager = ConsoleOutputManager.getInstance();
        CellStatus[][] field = new CellStatus[4][4];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = CellStatus.UNKNOWN;
            }
        }
        field[1][1] = CellStatus.MISSED;
        field[1][2] = CellStatus.HITTED;
        field[3][0] = CellStatus.DESTROYED;
        field[3][1] = CellStatus.DESTROYED;
        manager.printBattlefield(field);
    }
}
