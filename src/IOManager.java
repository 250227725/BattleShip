public class IOManager implements InputManager, OutputManager{
    private final InputManager in;
    private final OutputManager out;

    public IOManager(InputManager in, OutputManager out) {
        this.in = in;
        this.out = out;
    }

    public String readLine() throws GameCancelledException {
        String data = in.readLine();
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
}
