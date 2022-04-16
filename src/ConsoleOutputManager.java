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
//    public void printBattlefield(CellStatus[][] battleField) {
//        for (int i = 0; i < battleField.length; i++) {
//            if (i==0) System.out.println(".---------------.");
//            System.out.print("|");
//            for (int j = 0; j < battleField[0].length; j++) {
//                if (j != 0) System.out.print("|");
//                switch (battleField[i][j]) {
//                    case MISSED -> System.out.print(" M ");
//                    case HITTED -> System.out.print(" H ");
//                    case DESTROYED -> System.out.print(" D ");
//                    default -> System.out.print(" U ");
//                }
//            }
//            System.out.print("|");
//            System.out.println();
//            if (i == battleField.length - 1) System.out.print(".---------------.");
//        }
//    }

    public void printBattlefield(Player player) {
        printBattlefield(player.getEnemyBattlefield());
    }

    public void printBattlefield(CellStatus[][] battleField) {
        for (int i = -1; i <= battleField.length; i++) {
            if (i == -1 || i == battleField.length) System.out.print("+");
            if (i > -1 && i < battleField.length) System.out.print("|");
            for (int j = 0; j < battleField[0].length; j++) {
                if (i == -1 || i == battleField.length) {
                    if (j != 0) System.out.print("----");
                    else System.out.print("---");
                }
                else {
                    if (j != 0) System.out.print("|");
                    switch (battleField[i][j]) {
                        case MISSED -> System.out.print(" M ");
                        case HITTED -> System.out.print(" H ");
                        case DESTROYED -> System.out.print(" D ");
                        case BUSY ->  System.out.print(" B ");
                        default -> System.out.print(" U ");
                    }
                }
            }
            if (i == -1 || i == battleField.length) System.out.print("+");
            if (i > -1 && i < battleField.length) System.out.print("|");
            System.out.println();
        }
    }
}
