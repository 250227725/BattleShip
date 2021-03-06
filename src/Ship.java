import java.util.*;

public class Ship {
    private final ShipSection[] sections;
    private boolean isAlive;
    private int aliveSectionCount;

    private Ship(ShipSection[] sections) {
        this.aliveSectionCount = sections.length;
        this.isAlive = true;
        this.sections = sections;
    }
    public static Ship getInstance(CellSample[] cells) {
        if (cells == null || cells.length == 0 || cells.length > GameSettings.DEFAULT_SHIP_SETTINGS.length - 1) {
            throw new IllegalArgumentException();
        }

        if (cells.length == 1) {
            return new Ship(new ShipSection[]{new ShipSection(cells[0])});
        }

        Arrays.sort(cells);
        if (!CellSample.checkSequence(cells)) {
            throw new IllegalArgumentException();
        }

        ShipSection[] sections = new ShipSection[cells.length];
        for (int i = 0; i < cells.length; i++) {
            if (cells[i].isOutOfRange()) {
                throw new IllegalArgumentException(cells[i].toString());
            }
            sections[i] = new ShipSection(cells[i]);
        }

        return new Ship(sections);
    }
    private void destroy() {
        isAlive = false;
    }

    public ShipSection[] getSections() {
        return sections;
    }
    public int getLength() {
        return sections.length;
    }
    public boolean isAlive() {
        return isAlive;
    }

    public CellStatus hit(Cell attempt) {
        if (!isAlive()) return CellStatus.MISSED;
        for (int i = 0; i < sections.length; i++) {
            if (sections[i].hit(attempt)) {
                aliveSectionCount--;
                if (aliveSectionCount == 0) {
                    destroy();
                    return CellStatus.DESTROYED;
                }
                return CellStatus.HITTED;
            }
        }
        return CellStatus.MISSED;
    }
}
