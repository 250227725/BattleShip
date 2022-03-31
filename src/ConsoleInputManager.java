import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputManager implements InputManager{
    private static final ConsoleInputManager instance = new ConsoleInputManager();
    private final BufferedReader reader;

    private ConsoleInputManager() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public static ConsoleInputManager getInstance() {
        return instance;
    }

    public String read() {
        try {
            return reader.readLine();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(); //todo: Create correct logic
        }
    }
}
