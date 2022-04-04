import java.util.*;

public class Ship {
    private final int length;
    //todo: made sections as private
    public final ShipSection[] sections;
    private boolean isAlive;
    private int aliveSectionCount;

    private Ship(Cell[] coords) {
        this.length = coords[0].distance(coords[1]);
        this.aliveSectionCount = this.length;
        this.isAlive = true;
        this.sections = (ShipSection[]) coords;
    }

    public static Ship getInstance(Cell[] coords) { //todo: add Cell siquence checks
        if (coords == null || coords.length != 2) {
            throw new IllegalArgumentException();
        }
        return new Ship(coords);
    }

    public int getLength() {
        return length;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void destroy() {
        isAlive = false;
    }

    public boolean shootCheck(Cell shoot) {
        Optional<ShipSection> hittedSection = Arrays.stream(sections)
                .filter((s) -> s.equals(shoot) && s.isAlive())
                .findFirst();
        if (hittedSection.isEmpty()) return false;

        hittedSection.get().hit(new Cell(1,1){}); //todo change it
        if (--aliveSectionCount == 0) {
            destroy();
        }
        return true;
    }
}
