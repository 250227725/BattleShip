public class Player {
    private String name;
    private boolean isAlive = true;
    private CellStatus[][] battleField = new CellStatus[Game.FIELD_HEIGHT][Game.FIELD_WIDTH];
    {
        for (int y = 0; y < battleField.length; y++ ) {
            for (int x = 0; x < battleField[0].length; x++) {
                battleField[y][x] = CellStatus.UNKNOWN;
            }
        }
    }
    

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public CellStatus[][] getBattleField() {
        return battleField;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
