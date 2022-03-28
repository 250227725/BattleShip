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
        return Optional.empty();
    }
}
