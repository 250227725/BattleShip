import java.util.Arrays;

public class HumanPlayer extends Player{
    public HumanPlayer(String name) {
        super(name, true);
    }

    @Override
    public boolean isHuman() {
        return true;
    }

    @Override
    void generateShips(CellStatus[][] emptyPlayerField) {
        int[] setup = Project1st.shipsSetup;
        for (int i = 1; i < setup.length; i++) {
            for (int j = setup[i]; j > 0; j--) {
                ships.put(GameService.getHumanShip(), i + "-палубный корабль, №" + String.valueOf(j-i));
            }
        }
    }
}
