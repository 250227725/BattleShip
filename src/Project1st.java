public class Project1st {
    public static IOManager IO_MANAGER = new IOManager(ConsoleInputManager.getInstance(), ConsoleOutputManager.getInstance());


    public static void main(String... args) {
        GameSettings settings;
        try {
            settings = GameService.initSettings(IO_MANAGER); //todo: move logic to GameManager as static method
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
