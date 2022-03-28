import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
}
