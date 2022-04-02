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
    void generateShips() {
        int[] setup = Project1st.shipsSetup;
        for (int i = 1; i < setup.length; i++) {
            ships.put(null, "");
        }
    }
}
