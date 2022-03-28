import java.util.Optional;

public interface InputManager {
    Optional<String> getPlayerName();
    Optional<Cell> getPlayerGuess();
    Optional<Integer[][]> getShipCoordinate();
}
