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
    void generateShips(CellStatus[][] emptyPlayerField) throws GameCancelledException, GameInterruptException {
        int[] setup = Project1st.shipsSetup;
        for (int i = 1; i < setup.length; i++) {
            for (int j = setup[i]; j > 0; j--) {
                Cell[] attempt = Project1st.service.getShipCoordinate();
                //Project1st.service.checkShipAvailablity
                ships.put(GameService.getHumanShip(), i + "-палубный корабль, №" + String.valueOf(j-i));
            }
        }
    }
}
