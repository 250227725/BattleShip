public class IOManager {
    private final InputManager in;
    private final OutputManager out;

    public IOManager(InputManager in, OutputManager out) {
        this.in = in;
        this.out = out;
    }

    public InputManager in() {
        return in;
    }

    public OutputManager out() {
        return out;
    }
}
