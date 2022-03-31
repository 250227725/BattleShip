import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class ConsoleOutputManager implements OutputManager {
    private static final OutputManager instance = new ConsoleOutputManager();

    private ConsoleOutputManager() {
    }

    public static OutputManager getInstance() {
        return instance;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printBattlefield(CellStatus[][] battleField) {
        for (int i = 0; i < battleField.length; i++) {
            for (int j = 0; j < battleField[0].length; j++) {
                switch (battleField[i][j]) {
                    case MISSED -> System.out.print(" o ");
                    case HITTED -> System.out.print(" x ");
                    case DESTROYED -> System.out.print(" X ");
                    default -> System.out.print("   ");
                }
            }
            System.out.println();
        }
    }
}
