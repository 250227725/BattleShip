import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

class TestInputManager implements InputManager{
    private final BufferedReader reader;
    public TestInputManager(String data) {
        reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))));
    }

    @Override
    public String readLine() throws GameCancelledException{
        try {
            return reader.readLine();
        }
        catch (IOException e) {}
        return "IOException";
    }
}