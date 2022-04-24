import java.util.*;

public class AIPlayer extends Player{
    public AIPlayer(String name) {
        super(name, false);
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    @Override
    public Player clone() {
        return new AIPlayer(this.getName());
    }

    @Override
    Map<Ship, String> generateShips(CellStatus[][] emptyPlayerField, IOManager manager) {
        return new HashMap<>();
    }

    @Override
    public CellSample guess(int height, int width) {
        List<CellSample> possibleCells = getPossibleCells();
        return possibleCells.get((int) Math.random() * possibleCells.size());
    }

    private List<CellSample> getPossibleCells() {
        List<CellSample> result = new ArrayList<>();
        if (hasHitted()) {
            result = getNeighbors();
        }
        else {
            result = getAllUnknown();
        }
        return result;
    }

    private List<CellSample> getNeighbors() {
        List<CellSample> result = new ArrayList<>();
        List<CellSample> hitted = new ArrayList<>();
        for (int y = 0; y < enemyBattlefield.length; y++) {
            for (int x = 0; x < enemyBattlefield[y].length; x++) {
                if (enemyBattlefield[y][x] == CellStatus.HITTED) {
                    hitted.add(new CellSample(y, x));
                }
            }
        }
        if (hitted.size() > 1) {
            int dir = hitted.get(1).getX() - hitted.get(2).getX() != 0 ? 1 : 0; //1 - horizontal, 0 - vertical
            if (dir == 1) {
                if (hitted.get(1).getX() > 0 && enemyBattlefield[hitted.get(1).getY()][hitted.get(1).getX()-1] == CellStatus.UNKNOWN) {
                    result.add(new CellSample(hitted.get(1).getY(), hitted.get(1).getX()-1));
                }

                if (hitted.get(hitted.size()-1).getX() < enemyBattlefield[0].length - 1 && enemyBattlefield[hitted.get(hitted.size()-1).getY()][hitted.get(hitted.size()-1).getX()+1] == CellStatus.UNKNOWN) {
                    result.add(new CellSample(hitted.get(hitted.size()-1).getY(), hitted.get(hitted.size()-1).getX()+1));
                }
            }
            else {
                if (hitted.get(1).getY() > 0 && enemyBattlefield[hitted.get(1).getY()-1][hitted.get(1).getX()] == CellStatus.UNKNOWN) {
                    result.add(new CellSample(hitted.get(1).getY()-1, hitted.get(1).getX()));
                }

                if (hitted.get(hitted.size()-1).getY() < enemyBattlefield.length - 1 && enemyBattlefield[hitted.get(hitted.size()-1).getY()+1][hitted.get(hitted.size()-1).getX()] == CellStatus.UNKNOWN) {
                    result.add(new CellSample(hitted.get(hitted.size()-1).getY()+1, hitted.get(hitted.size()-1).getX()));
                }
            }
        }
        else if (hitted.size() == 1) {

        }

        return result;
    }

    private List<CellSample> getAllUnknown() {
        List<CellSample> result = new ArrayList<>();
        for (int y = 0; y < enemyBattlefield.length; y++) {
            for (int x = 0; x < enemyBattlefield[y].length; x++) {
                if (enemyBattlefield[y][x] == CellStatus.UNKNOWN) {
                    result.add(new CellSample(y, x));
                }
            }
        }
        return result;
    }


}
