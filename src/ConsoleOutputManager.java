import java.io.BufferedReader;
import java.io.InputStreamReader;

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
}
