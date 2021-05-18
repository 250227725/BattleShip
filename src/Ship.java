import java.util.ArrayList;
import java.util.List;

public class Ship {
    private boolean isAlive;
    private List<ShipSection> sections;
    {
        sections = new ArrayList<>();
    }

    public Ship(int sectionNumber) {
        double d = Math.random();
        int orientation = (int) Math.round(d);
        int dx = 0;
        int dy = 0;
        if (orientation == 1) {
            dx = 1;
        }
        else {
            dy = 1;
        }

        double dX = Math.random();


        double dY = Math.random();

        //int x = (int) (dX * (Game.getWIDTH() - 1 - dx * sectionNumber));
        int maxX = Game.getWIDTH() - dx * sectionNumber;
        int x = (int) (dX * maxX);
        if (x == maxX) {
            x = maxX - 1;
        }
//        int y = (int) (dY * (Game.getHEIGHT() - 1 - dy * sectionNumber));
        int maxY = Game.getHEIGHT() - dy * sectionNumber;
        int y = (int) (dY * maxY);
        if (y == maxY) {
            y = maxY - 1;
        }
        for (int i = 0; i < sectionNumber; i++) {
            sections.add(new ShipSection(x + dx * i, y + dy * i));
        }
        this.isAlive = true;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public List<ShipSection> getSections() {
        return sections;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int checkShoot(BattleFieldCell shoot) {
        int result = 0;
        if (sections.contains(shoot)) {
            ShipSection aim = sections.get(sections.indexOf(shoot));
            if (aim.isAlive()) {
                aim.setAlive(false);
                boolean shipIsAlive = false;
                for (ShipSection section : sections) {
                    if (section.isAlive()) {
                        shipIsAlive = true;
                    }
                }
                if (shipIsAlive) {
                    result = 1;
                }
                else {
                    result = 2;
                    isAlive = false;
                }
            }
            else {
                result = -1;
            }

        }
        return result;
    }
}
