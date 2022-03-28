import java.io.BufferedReader;
import java.io.InputStreamReader;

public class OutputManager {
    private static final OutputManager instance = new OutputManager();

    private OutputManager() {
    }

    public OutputManager getInstance() {
        return instance;
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
