import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Player {
    private boolean isHuman;
    private List<Ship> ships;
    private BattleField battleField;
    private BattleField enemyBattleField;

    public BattleField getEnemyBattleField() {
        return enemyBattleField;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public BattleField getBattleField() {
        return battleField;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public Player(boolean isHuman) {
        this.isHuman = isHuman;
        this.ships = new ArrayList<>();
        this.battleField = new BattleField();
        this.enemyBattleField = new BattleField();
    }

    public void generateShips() {
        for (int k = Game.getShipsSettings().length; k > 0; k--) {
            for (int i = 0; i < Game.getShipsSettings()[k-1]; i++) {
                try {
                    this.generateShip(k);
                }
                catch (Exception e) {
                    this.ships = new ArrayList<>();
                    this.battleField = new BattleField();
                    this.generateShips();
                    System.out.println("Exception");
                }
            }
        }
    }

    private void generateShip(int size) throws Exception{
        Ship ship = null;
        int tryCount = 0;
        while (ship == null) {
            ship = new Ship(size);
            for (ShipSection section : ship.getSections()) {
                if (battleField.field[section.getY()][section.getX()] != 0) {
                    tryCount++;
                    ship = null; break;
                }
            }
            if (tryCount > 1000) throw new Exception() {};
        }
        for (ShipSection section : ship.getSections()) {
            if (section.getY() > 0) {
                if (section.getX() > 0) {
                    battleField.field[section.getY() - 1][section.getX() - 1] = 8;
                }
                battleField.field[section.getY() - 1][section.getX()] = 8;
                if (section.getX() < Game.getWIDTH() - 1) {
                    battleField.field[section.getY() - 1][section.getX() + 1] = 8;
                }
            }
            if (section.getX() > 0) {
                battleField.field[section.getY()][section.getX() - 1] = 8;
            }
            if (section.getX() < Game.getWIDTH() - 1) {
                battleField.field[section.getY()][section.getX() + 1] = 8;
            }
            if (section.getY() < Game.getHEIGHT()) {
                if (section.getX() > 0) {
                    battleField.field[section.getY() + 1][section.getX() - 1] = 8;
                }
                battleField.field[section.getY() + 1][section.getX()] = 8;
                if (section.getX() < Game.getWIDTH() - 1) {
                    battleField.field[section.getY() + 1][section.getX() + 1] = 8;
                }
            }
        }
        for (ShipSection section : ship.getSections()) {
            battleField.field[section.getY()][section.getX()] = 1;
        }
        ships.add(ship);
    }
}
