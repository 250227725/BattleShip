import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Game {

    public static final int FIELD_WIDTH = 10;
    public static final int FIELD_HEIGHT = 10;

    public static void main(String[] args) {
        GameService gameService = GameService.getInstance();
        List<Player> players = gameService.createPlayers();
        GameManager gameManager = new GameManager(players);
        gameManager.startGame();
        //todo:
        /*
        IV   0. Запрашиваем размеры игрового поля. По умолчанию 10х10, максимум 20х20
        III  1. Запрашиваем количество игроков. По умолчанию - 2, максимум 4.
        II   2. Запрашиваем ввод имен игроков. Игроки для которых имя не введено будут AI
        II   3. Создаем AI игроков
        I    4. Создаем менеджер игры
        I    5. Запускаем игру
        I    6. Спрашиваем нужна ли переигровка или новая игра? (переигровка -> п.4, новая игра -> п.1, иначе завершаем работу
         */

        //todo: features
        /*
         1. Настройка сложности игры. Простейший вариант - открыть для AI часть пустых ячеек игрового поля человека.
         */

    }

}
