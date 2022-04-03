import java.util.Arrays;
import java.util.Optional;

public class HumanPlayer extends Player{
    public HumanPlayer(String name) {
        super(name, true);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    void generateShips(CellStatus[][] playerField) throws GameCancelledException, GameInterruptException {
        int[] setup = Project1st.shipsSetup;
        for (int size = 1; size < setup.length; size++) {
            for (int index = setup[size]; index > 0; index--) {
                ships.put(Project1st.service.getHumanShip(playerField, size), size + "-палубный корабль, №" + String.valueOf(size - index));
            }
        }
    }
}
