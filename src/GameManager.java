import java.io.IOException;
import java.util.*;

public class GameManager {
    private Deque<Player> players = new LinkedList<>();

    /**
     * Create gameManager for game
     * @param gamePlayers - players list
     */
    public GameManager(Set<Player> gamePlayers) {
        generatePlayerSequence(gamePlayers);
    }

    /**
     * Starting the game
     */
    public void startGame() {

    }

    /**
     * Return players list
     */
    public Deque<Player> getPlayers() {
        return players;
    }

    /**
     * Return next player
     */
    public Player getNextPlayer() {
        Player player = players.pop();
        players.add(player);
        return player;
    }

    /**
     * Generate sequence of players for serial game turn
     * @param gamePlayers - players list for game
     */
    private void generatePlayerSequence(Set<Player> gamePlayers) {
        List<Player> playerList = new ArrayList<>(gamePlayers);
        while(playerList.size() > 0) {
            int index = (int) (Math.random() * playerList.size());
            players.add(playerList.get(index));
            playerList.remove(index);
        }
    }

    public void initPlayers(Game game) {
        players.stream().forEach((x) -> x.init(game));
    }







    public static Optional<Cell> getPlayerGuess() throws GameCancelledException {  //todo: delete static
        OutputManager out = ConsoleOutputManager.getInstance();
        InputManager in = ConsoleInputManager.getInstance();
        return getPlayerGuess(out, in);
    }

    public static Optional<Cell> getPlayerGuess(OutputManager out, InputManager in) throws GameCancelledException { //todo: delete static
        out.showMessage("Введите координаты поля для выстрела:");
        while(true) {
            try {
                String str = in.read();
                if (str != null && !str.equals("")) {
                    if (str.equalsIgnoreCase("exit")) {
                        return Optional.empty();
                    }
                    //todo create CellFactory with checking range
                    String strX = str.trim().substring(0, 1).toUpperCase();
                    String strY = str.trim().substring(1).trim();
                    int y = Integer.parseInt(strY);
                    Cell.HorizontalCellNames x = Cell.HorizontalCellNames.valueOf(strX);
                    if (y <= Project1st.FIELD_HEIGHT && y > 0 && x.ordinal() < Project1st.FIELD_WIDTH) {
                        Cell guess = new Cell(x, y) {};
                        return Optional.of(guess);
                    }
                }
            }
            catch (IllegalArgumentException  e) {
            }
            out.showMessage("Некорректный ввод. Введите координаты поля для выстрела");
        }
    }

    public static Optional<Integer[][]> getShipCoordinate() throws GameCancelledException {  //todo: delete static
        OutputManager out = ConsoleOutputManager.getInstance();
        InputManager in = ConsoleInputManager.getInstance();
        return getShipCoordinate(out, in);
    }

    public static Optional<Integer[][]> getShipCoordinate(OutputManager out, InputManager in) throws GameCancelledException {  //todo: delete static

        out.showMessage("Введите координаты начальной и конечной точки корабля, разделенные знаком минус:");
        while(true) {
            try {
                String str = in.read();
                if (str != null && !str.equals("")) {
                    if (str.equalsIgnoreCase("exit")) {
                        return Optional.empty();
                    }
                    //todo create CellFactory with checking range
                    String[] data = str.trim().split("-");
                    if (data.length == 2 || data.length == 1) {
                        int x0 = Cell.HorizontalCellNames.valueOf(data[0].trim().substring(0, 1).toUpperCase()).ordinal();
                        int y0 = Integer.parseInt(data[0].trim().substring(1).trim()) - 1;

                        int x1;
                        int y1;

                        if (data.length == 2) {
                            x1 = Cell.HorizontalCellNames.valueOf(data[1].trim().substring(0, 1).toUpperCase()).ordinal();
                            y1 = Integer.parseInt(data[1].trim().substring(1).trim()) - 1;
                        }
                        else {
                            x1 = x0;
                            y1 = y0;
                        }

                        int dx = Math.abs(x1 - x0);
                        int dy = Math.abs(y1 - y0);

//                        Predicate<Integer> positive = (i) -> i >= 0;
//                        Predicate<Integer> horizontalRange = (x) -> x < Game.FIELD_WIDTH;
//                        Predicate<Integer> verticalRange = (y) -> y < Game.FIELD_HEIGHT;
//
//                        if (
//                                positive.and(horizontalRange).test(x0)
//                                        && positive.and(horizontalRange).test(x1)
//                                        && positive.and(verticalRange).test(y0)
//                                        && positive.and(verticalRange).test(y1)

                        if (
                                x0 < Project1st.FIELD_WIDTH && x0 >= 0
                                        && x1 < Project1st.FIELD_WIDTH && x1 >= 0
                                        && y0 < Project1st.FIELD_HEIGHT && y0 >= 0
                                        && y1 < Project1st.FIELD_HEIGHT && y1 >= 0
                                        && (dx == 0 || dy == 0)
                        ) {
                            Integer[][] coordinates = new Integer[dx + dy + 1][2];
                            if (dx + dy == 0) {
                                coordinates[0] = new Integer[]{x0, y0};
                            } else {
                                int x = Math.min(x0, x1);
                                int y = Math.min(y0, y1);
                                for (int i = 0; i <= dx + dy; i++) {
                                    if (dx > 0) {
                                        coordinates[i] = new Integer[]{x + i, y};
                                    } else if (dy > 0) {
                                        coordinates[i] = new Integer[]{y, y + i};
                                    }
                                }
                            }
                            return Optional.of(coordinates);
                        }
                    }
                }
            }
            catch (IllegalArgumentException  e) {
            }
            out.showMessage("Некорректный ввод. Введите координаты корабля");
        }
    }


}
