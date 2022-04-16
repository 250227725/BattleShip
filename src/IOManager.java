public class IOManager implements InputManager, OutputManager{
    private final InputManager in;
    private final OutputManager out;

    public IOManager(InputManager in, OutputManager out) {
        this.in = in;
        this.out = out;
    }

    public String read() throws GameCancelledException {
        String data = in.read();
        if (data.equalsIgnoreCase("exit")) {
           throw new GameCancelledException();
        }
        return data;
    }

    @Override
    public void showMessage(String message) {
        out.showMessage(message);
    }

    @Override
    public void printBattlefield(CellStatus[][] battleField) {
        out.printBattlefield(battleField);
    }

    @Override
    public void printBattlefield(Player player) {
        out.printBattlefield(player);
    }
}
