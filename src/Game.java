import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    public List<Player> getPlayers() {
        return players;
    }

    private List<Player> players;
    private static Game game;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static Map<Integer, Integer> shipsSettings;

    public static Map<Integer, Integer> getShipsSettings() {
        return shipsSettings;
    }

    // Первый параметр - количество секций, второй - количество кораблей данного типа.
    static {
        shipsSettings = new HashMap<>();
        shipsSettings.put(1, 4);
        shipsSettings.put(2, 3);
        shipsSettings.put(3, 2);
        shipsSettings.put(4, 1);
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public Game(List players) {
        this.players = players;
    }

    public void run() {

    }

    public static List<Ship> generateShips() {
        List<Ship> ships = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : getShipsSettings().entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                ships.add(new Ship());
                //Формируем корабли с количеством палуб равным entry.getKey()
                // Созданный корабль нужно проверить на предмет соприкасания с другими кораблями.
                // Предумотреть ситуацию, что игровое поле не содержит мест в которых можно расположить корабль

            }
        }
        return ships;
    }

    public static void main(String[] args) {
//        initNewGame();
//        game.run();
    }

    private static void initNewGame() {
//        List<Player> players = new ArrayList<>();
//        players.add(new Player(true, game.generateShips()));
//        players.add(new Player(false, game.generateShips()));
//        game = new Game(players);
    }
}
