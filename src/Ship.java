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

        int x = (int) (dX * (Game.getWIDTH() - 1 - dx * sectionNumber));
        int y = (int) (dY * (Game.getHEIGHT() - 1 - dy * sectionNumber));
        for (int i = 0; i < sectionNumber; i++) {
            sections.add(new ShipSection(x + dx * i, y + dy * i));
        }
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
}
