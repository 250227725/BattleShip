import org.junit.jupiter.api.Test;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameManagerTest {

//    @Test
//    public void getPlayerShootGuessTest() throws GameCancelledException, GameInterruptException{
//        GameService service = GameService.getInstance(new IOManager(new TestInputManager("d\ra\rs\ra\ra\ra\ra\ra\ra\ra\ra"), ConsoleOutputManager.getInstance()));
//        assertThrows(GameInterruptException.class, () -> service.getIntegerValue("Введите число"));
//    }

    // todo: incorrect test beacuse it's depend on test starting sequence.

    //    String SYSTEM_IN = "lightning\r\nexit\r\n\r\nlightning";
//    String SYSTEM_IN = "яя\r\nb     2";


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

//    @Test
//    public void getShipCoordinateTest() {
//        String SYSTEM_IN = "a2-z2";
//        InputManager in = new InputManager() {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(SYSTEM_IN.getBytes())));
//            @Override
//            public String read() {
//                try {
//                    return reader.readLine();
//                }
//                catch (IOException e) {
//                    e.printStackTrace();
//                    throw new RuntimeException();
//                }
//            }
//        };
//        OutputManager out = ConsoleOutputManager.getInstance();
//
//        Integer[][] test = new Integer[][] {{0,1},{1,1}};
//        Integer[][] guess = GameManager.getShipCoordinate(out, in).get(); //todo: create GameManager Instance
//        assertThat(guess, equalTo(test));
//    }



    private static String generateString(Random rng, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    private Set<Player> generateInitPlayerList() {
        Set<Player> initPlayers = new HashSet<>();
        int playerQuantity = (int) (Math.random() * 10 + 3);
        for (int i = 0; i < playerQuantity; i++) {
            initPlayers.add(new HumanPlayer(generateString(new Random(), "qwertyuiopasdfghjklzxcvbnm", 6)));
        }
        return initPlayers;
    }


    @Test
    public void ordered_players_list_matched_to_init_list() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers);
        assertThat(new HashSet<Player>(initPlayers), equalTo(new HashSet<Player>(manager.getPlayers())));
    }



    @Test
    public void one_cycle_of_nextPlayer_is_fully_ordered_players_list() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers);

        Set<Player> orderedPlayers = new HashSet<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }
        assertThat(new HashSet<Player>(initPlayers), equalTo(orderedPlayers));
    }

    @Test
    public void sequenced_nextPlayer_for_whole_game_cycle() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers);

        List<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }

        for (int turn = 0; turn < (Project1st.FIELD_WIDTH * Project1st.FIELD_HEIGHT); turn++) {
            for (int i = 0; i < orderedPlayers.size(); i++) {
                assertThat(orderedPlayers.get(i), equalTo(manager.getNextPlayer()));
            }
        }
    }

    @Test
    //@Disabled
    /*Ordered sequence can be the same as init, so failing of this test case
    doesn't mean what method is incorrect. You can run it several times to check result
     */
    public void player_sequence_is_differed_from_init_player_list() {
        Set<Player> initPlayers = generateInitPlayerList();
        GameManager manager = new GameManager(initPlayers);

        List<Player> orderedPlayers = new ArrayList<>();
        for (int i = 0; i < initPlayers.size(); i++) {
            orderedPlayers.add(manager.getNextPlayer());
        }

        assertThat(initPlayers, not(equalTo(orderedPlayers)));
    }

    @Test
    public void game_run_test() {
        GameManager manager = new GameManager(generateInitPlayerList());
        manager.startGame();
    }

    @Test
    public void checkAliveEnemyTrueTest() {
        Set<Player> players = new HashSet<>();
        Player p1 = new HumanPlayer("Player One");
        Player p2 = new HumanPlayer("Player Two");
        Player p3 = new HumanPlayer("Player Three  ");
        p1.setAlive(true);
        p2.setAlive(false);
        p3.setAlive(true);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        GameManager manager = new GameManager(players);
        assertThat(manager.checkAliveEnemy(), equalTo(true));
    }

    @Test
    public void checkAliveEnemyFalseTest() {
        Set<Player> players = new HashSet<>();
        Player p1 = new HumanPlayer("Player One");
        Player p2 = new HumanPlayer("Player Two");
        Player p3 = new HumanPlayer("Player Three  ");
        p1.setAlive(true);
        p2.setAlive(false);
        p3.setAlive(false);
        players.add(p1);
        players.add(p2);
        players.add(p3);
        GameManager manager = new GameManager(players);
        assertThat(manager.checkAliveEnemy(), equalTo(false));
    }

    @Test
    public void createGameTest() {
        Set<Player> players = new HashSet<>();
        players.add(new HumanPlayer("Player One"));
        players.add(new AIPlayer("Player Two"));
        GameManager manager = new GameManager(players);
        assertThat(manager.playersCount(), equalTo(2));
    }

}
