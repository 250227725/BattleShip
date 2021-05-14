public class BattleField {
    int[][] field = new int[Game.getHEIGHT()][Game.getWIDTH()];
    {
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field[0].length; x++) {
                field[y][x] = 0;
            }
        }
    }

    String[] marker = new String[]{" ", ".", "*", "X"};
}
