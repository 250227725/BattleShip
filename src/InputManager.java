import java.util.Optional;

public interface InputManager {
    String read() throws GameCancelledException;
}
