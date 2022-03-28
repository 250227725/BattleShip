import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConsoleInputManagerTest {

    // todo: incorrect test beacuse it's depend on test starting sequence.

//    String SYSTEM_IN = "lightning\r\nexit\r\n\r\nlightning";
//    String SYSTEM_IN = "яя\r\nb     2";
    String SYSTEM_IN = "b2-b4";
    ConsoleInputManager manager;
    {
        System.setIn(new ByteArrayInputStream(SYSTEM_IN.getBytes()));
        manager = ConsoleInputManager.getInstance();
    }

//    @Test
//    public void getNameTest() {
//        String playerName = "lightning";
//        assertThat(manager.getPlayerName(), equalTo(Optional.of(playerName)));
//    }
//
//    @Test
//    public void getNameExitTest() {
//        String playerName = "exit";
//        assertThat(manager.getPlayerName(), equalTo(Optional.empty()));
//    }
//
//    @Test
//    public void getNameRetryTest() {
//        String playerName = "lightning";
//        assertThat(manager.getPlayerName(), equalTo(Optional.of(playerName)));
//    }

//    @Test
//    public void getGuessTest() {
//        Cell guess = new Cell(1, 1) {};
//        assertThat(manager.getPlayerGuess().get(), equalTo(guess));
//    }

    @Test
    public void getShipCoordinateTest() {
        Integer[][] test = new Integer[][] {{1,1}, {1,2}, {1,3}};
        Integer[][] guess = manager.getShipCoordinate().get();
        assertThat(test, equalTo(guess));
    }
}
