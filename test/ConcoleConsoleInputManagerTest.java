import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConcoleConsoleInputManagerTest {

    // todo: incorrect test beacuse it's depend on test starting sequence.

    String SYSTEM_IN = "lightning\r\nexit\r\n\r\nlightning";
    ConsoleInputManager manager;
    {
        System.setIn(new ByteArrayInputStream(SYSTEM_IN.getBytes()));
        manager = ConsoleInputManager.getInstance();
    }

    @Test
    public void getNameTest() throws IOException {
        String playerName = "lightning";
        assertThat(manager.getPlayerName(), equalTo(Optional.of(playerName)));
    }

    @Test
    public void getNameExitTest() throws IOException {
        String playerName = "exit";
        assertThat(manager.getPlayerName(), equalTo(Optional.empty()));
    }

    @Test
    public void getNameRetryTest() throws IOException {
        String playerName = "lightning";
        assertThat(manager.getPlayerName(), equalTo(Optional.of(playerName)));
    }

}
