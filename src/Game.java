import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Game {

    public List<Player> getPlayers() {
        return players;
    }

    private static Game game;
    private List<Player> players;
    private boolean gameStatus;
    private Player curentTurn;


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

    public Game(List<Player> players) {
        this.players = players;
        this.gameStatus = true;
        this.curentTurn = players.get((int) (Math.random() * (players.size()-1)));
    }

    public void run() {
        /*
        --1. Определить кто ходит
        --2. отрисовать игровое поле
        --3. спросить следующий ход
        4. проверить ход
        5. проверить состояние игры
        6. передать ход
        7. повторить
        */

        int count = 0;
        while (gameStatus) {
            //--1. Определить кто ходит
            //--2. отрисовать игровое поле
            game.printBattleField(curentTurn);
            Player nextTurn = game.players.get((game.players.indexOf(curentTurn) + 1) % game.players.size());


            //--3. спросить следующий ход
            String nextShoot = "";
            try { //try with resources
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                nextShoot = reader.readLine();

            }
            catch (IOException e){

                e.printStackTrace();
            }

            if (nextShoot.equals("q")) {
                gameStatus = false;
                System.out.println("Exit the Gale");
                return;
            }
            else if (nextShoot.equals("w")) {
                System.out.println("Error. Choose correct cell");
                return;
            }


            //получаем координаты ячейки
            int sX = (int) (Math.random() * (WIDTH - 1));
            int sY = (int) (Math.random() * (HEIGHT - 1));

            //4. проверить ход
            /*

            Создать объект ячейка и проверить у другого игрока что в этой ячейке
             */

            int shootStatus = nextTurn.getBattleField().field[sY][sX];

            if (shootStatus == 1) {
                System.out.println("Попал!");
                curentTurn.getEnemyBattleField().field[sY][sX] = 9;
            }
            else {
                System.out.println("Мимо!");
                curentTurn.getEnemyBattleField().field[sY][sX] = 6;
            }





            System.out.println();
            System.out.println();
            System.out.println();


            count++;
            if (count > 100) gameStatus = false;
            System.out.println("Ход: " + count);

            curentTurn = nextTurn;
        }

        System.out.println("Есть победитель");

    }

    public static void main(String[] args) {
        initNewGame();
        game.run();
    }

    private static void initNewGame() {
        List<Player> players = new ArrayList<>();
        players.add(new Player(true));
        players.add(new Player(false));
        for (Player player : players) {
            player.generateShips();
        }
        game = new Game(players);
    }

    public void printBattleField(Player player) {
        System.out.print("      А     Б     В     Г     Д     Е     Ж     З     И     К");
        System.out.print("        ");
        System.out.print("      А     Б     В     Г     Д     Е     Ж     З     И     К");
        System.out.println();

        System.out.print("   ┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
        System.out.print("     ");
        System.out.print("   ┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
        System.out.println();

        for (int y = 0; y < player.getBattleField().field.length; y ++) {
            System.out.print((y+1 < 10 ? " ": "") + (y+1) +  " │");
            for (int x = 0; x < player.getBattleField().field[y].length; x++) {
                String sign = " ";
                if (player.getBattleField().field[y][x] == 9) {
                    sign = "X";
                }
                if (player.getBattleField().field[y][x] == 1) {
                    sign = "0";
                }
                System.out.print("  " + sign + "  │");
            }

            System.out.print("     ");

            System.out.print((y+1 < 10 ? " ": "") + (y+1) +  " │");
            for (int x = 0; x < player.getEnemyBattleField().field[y].length; x++) {
                String enemySign = " ";
                if (player.getEnemyBattleField().field[y][x] == 9) {
                    enemySign = "X";
                }
                if (player.getEnemyBattleField().field[y][x] == 6) {
                    enemySign = ".";
                }
                System.out.print("  " + enemySign + "  │");
            }

            System.out.println();

            if (y == player.getBattleField().field.length-1) {
                System.out.print("   ┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘");
            }
            else{
                System.out.print("   ┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
            }

            System.out.print("     ");

            if (y == player.getBattleField().field.length-1) {
                System.out.print("   ┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘");
            }
            else{
                System.out.print("   ┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
            }

            System.out.println();
        }
    }
}
