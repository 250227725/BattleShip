import java.util.List;

public class Player {
    private boolean isHuman;
    private List<Ship> ships;

    public boolean isHuman() {
        return isHuman;
    }

    public Player(boolean isHuman, List ships) {
        this.isHuman = isHuman;
        this.ships = ships;
    }
}
