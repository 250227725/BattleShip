import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    public List<Player> getPlayers() {
        return players;
    }

    private static Game game;
    private List<Player> players;
    private static final int WIDTH = 10;
    private static final int HEIGHT = 10;
    private static int[] shipsSettings;
    static {
        shipsSettings = new int[]{4, 3, 2, 1};
        // Индекс массива - количество секций, значение - количество кораблей данного типа.
    }

    public static int[] getShipsSettings() {
        return shipsSettings;
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

    public static void main(String[] args) {
        initNewGame();
        //game.run();
        Player player = new Player(true);
        player.generateShips();
        game.printBattleField(player.getBattleField());
    }

    private static void initNewGame() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(true));
        players.add(new Player(false));
        game = new Game(players);
    }

    public void printBattleField(BattleField battleField) {
        System.out.println("      А     Б     В     Г     Д     Е     Ж     З     И     К");
        System.out.println("   ┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
        for (int y = 0; y < battleField.field.length; y ++) {
            System.out.print((y+1 < 10 ? " ": "") + (y+1) +  " │");
            for (int x = 0; x < battleField.field[y].length; x++) {
                String sign = " ";
                if (battleField.field[y][x] == 9) {
                    sign = "X";
                }
                if (battleField.field[y][x] == 6) {
                    sign = ".";
                }
                System.out.print("  " + sign + "  │");
            }
            System.out.println();
            if (y == battleField.field.length-1) {
                System.out.println("   ┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘");
            }
            else{
                System.out.println("   ┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
            }
        }
    }
}
