import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Optional;

public class ConsoleInputManager implements InputManager{
    private static final ConsoleInputManager instance = new ConsoleInputManager();
    private final BufferedReader reader;

    private ConsoleInputManager() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static ConsoleInputManager getInstance() {
        return instance;
    }

    public Optional<String> getPlayerName() {
        OutputManager out = ConsoleOutputManager.getInstance();
        Optional <String> name;
        out.showMessage("Введите имя игрока или EXIT для выхода");
        while(true) {
            try {
                String str = reader.readLine();
                if (str != null && !str.equals("")) {
                    name = Optional.of(str);
                    break;
                }
            }
            catch (IOException e) {
            }
            out.showMessage("Некорректный ввод. Введите имя игрока или EXIT для выхода");
        }
        out.showMessage(name.get());
        if (name.get().trim().equalsIgnoreCase("exit")) {
            name = Optional.empty();
        }

        return name;
    }

    @Override
    public Optional<Cell> getPlayerGuess() {
        OutputManager out = ConsoleOutputManager.getInstance();
        out.showMessage("Введите координаты поля для выстрела:");
        while(true) {
            try {
                String str = reader.readLine();
                if (str != null && !str.equals("")) {
                    if (str.equalsIgnoreCase("exit")) {
                        return Optional.empty();
                    }
                    //todo create CellFactory with checking range
                    String strX = str.trim().substring(0, 1).toUpperCase();
                    String strY = str.trim().substring(1).trim();
                    int y = Integer.parseInt(strY);
                    Cell.HorizontalCellNames x = Cell.HorizontalCellNames.valueOf(strX);
                    if (y <= Game.FIELD_HEIGHT && y > 0 && x.ordinal() < Game.FIELD_WIDTH) {
                        Cell guess = new Cell(x, y) {};
                        return Optional.of(guess);
                    }
                }
            }
            catch (IOException| IllegalArgumentException  e) {
            }
            out.showMessage("Некорректный ввод. Введите координаты поля для выстрела");
        }
    }

    public Optional<Integer[][]> getShipCoordinate() {
        OutputManager out = ConsoleOutputManager.getInstance();
        out.showMessage("Введите координаты начальной и конечной точки корабля, разделенные знаком минус:");
        while(true) {
            try {
                String str = reader.readLine();
                if (str != null && !str.equals("")) {
                    if (str.equalsIgnoreCase("exit")) {
                        return Optional.empty();
                    }
                    //todo create CellFactory with checking range
                    String[] data = str.trim().split("-");
                    if (data.length == 2) {
                        int x0 = Cell.HorizontalCellNames.valueOf(data[0].trim().substring(0, 1).toUpperCase()).ordinal();
                        int x1 = Cell.HorizontalCellNames.valueOf(data[1].trim().substring(0, 1).toUpperCase()).ordinal();

                        int y0 = Integer.parseInt(data[0].trim().substring(1).trim()) - 1;
                        int y1 = Integer.parseInt(data[1].trim().substring(1).trim()) - 1;

                        if (
                                   x0 < Game.FIELD_WIDTH
                                && x1 < Game.FIELD_WIDTH
                                && y0 < Game.FIELD_HEIGHT && y0 >= 0
                                && y1 < Game.FIELD_HEIGHT && y1 >= 0
                        ) {
                            Integer[][] coordinates = new Integer[x1-x0][y1-y0];
                            //todo: fill Array
                            return Optional.of(coordinates);
                        }
                    }
                }
            }
            catch (IOException| IllegalArgumentException  e) {
            }
            out.showMessage("Некорректный ввод. Введите координаты корабля");
        }
    }
}
