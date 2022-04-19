public class Project1st {

    public static final int MAX_FIELD_WIDTH = Cell.HorizontalCellNames.values().length;
    public static final int MAX_FIELD_HEIGHT = 100;
    public static final int MIN_FIELD_WIDTH = 5;
    public static final int MIN_FIELD_HEIGHT = 5;
    public static final int[] shipsSetup = new int[]{0, 2, 1};//{0,4,3,2,1}; //todo: It should depend on gamefield size;

    public static IOManager IO_MANAGER = new IOManager(ConsoleInputManager.getInstance(), ConsoleOutputManager.getInstance());
    public static final GameService service = GameService.getInstance(IO_MANAGER);

    //todo: Delete next two poarametrs and modify method for using Game class instance fields
    public static final int FIELD_WIDTH = 10;
    public static final int FIELD_HEIGHT = 10;

    public static void main(String... args) {
        GameSettings settings;
        try {
            settings = service.initSettings(); //todo: move logic to GameManager as static method
        }
        catch (GameCancelledException | GameInterruptException e) {
            IO_MANAGER.showMessage("Инициализация игры прервана");
            return;
        }
        while (true) {
            GameManager manager = GameManager.getInstance(settings);
            manager.run();
            if (!manager.repeat())
                return;
        }
    }
}
