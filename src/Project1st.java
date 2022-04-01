public class Project1st {

    public static final int MAX_FIELD_WIDTH = Cell.HorizontalCellNames.values().length;
    public static final int MAX_FIELD_HEIGHT = 100;

    //todo: Delete next two poarametrs and modify method for using Game class instance fields
    public static final int FIELD_WIDTH = 10;
    public static final int FIELD_HEIGHT = 10;

    public static void main(String... args) {
        GameService service = GameService.getInstance(new IOManager(ConsoleInputManager.getInstance(), ConsoleOutputManager.getInstance()));
        Game game = service.initGame();
        game.call();
    }
}
