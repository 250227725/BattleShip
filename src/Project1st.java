public class Project1st {

    public static final int MAX_FIELD_WIDTH = Cell.HorizontalCellNames.values().length;
    public static final int MAX_FIELD_HEIGHT = 100;
    public static final int MIN_FIELD_WIDTH = 5;
    public static final int MIN_FIELD_HEIGHT = 5;

    //todo: Delete next two poarametrs and modify method for using Game class instance fields
    public static final int FIELD_WIDTH = 10;
    public static final int FIELD_HEIGHT = 10;

    public static void main(String... args) {
        GameService service = GameService.getInstance(new IOManager(ConsoleInputManager.getInstance(), ConsoleOutputManager.getInstance()));
        try {
            Game game = service.initGame();
            game.call();
        }
        catch (GameCancelledException | GameInterruptException e) {
            Game.cancelled();
        }
    }
}
