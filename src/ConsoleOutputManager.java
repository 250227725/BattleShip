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

    public void printBattlefield(CellStatus[][] battleField) {
        if (battleField == null || battleField[0] == null) return;
        int width = battleField.length;
        int height = battleField[0].length;
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < height; y++) {
            if (y == 0) {
                result.append("    ");
                for (int x = 0; x < width; x++) {
                    result.append(" ").append(Cell.HorizontalCellNames.values()[x]).append("  ");
                }
                result.append("\r\n");

                for (int x = 0; x < width; x++) {
                    if (x==0) {
                        result.append("   ┌");
                    }
                    result.append("───");
                    if (x+1 == width) {
                            result.append("┐");
                    }
                    else {
                            result.append("┼");
                    }
                }
                result.append("\r\n");
            }

            if (y+1 < 10) {
                result.append(" ");
            }
            result.append(y+1).append(" │");


            for (int x = 0; x < width; x++) {
                result.append(" ");
                switch (battleField[y][x]) {
                    case MISSED -> result.append("*");
                    case HITTED -> result.append("╳");
                    case DESTROYED -> result.append("╪");
                    case BUSY -> result.append("░");
                    case SHIP -> result.append("█");
                    default -> result.append(" ");
                }
                result.append(" │");
            }
            result.append("\r\n");

            result.append("   ");
            if (y+1 == height) {
                result.append("└");
            }
            else {
                result.append("┼");
            }

            for (int x = 0; x < width; x++) {
                result.append("───");
                if (x+1 < width) {
                    if (y+1 < height) {
                        result.append("┼");
                    }
                    else {
                        result.append("┴");
                    }
                }
                else {
                    if (y+1 < height) {
                        result.append("┤");
                    }
                    else {
                        result.append("┘");
                    }
                }
            }
            result.append("\r\n");
        }
        System.out.println(result);
    }
}
