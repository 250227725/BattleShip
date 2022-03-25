import java.util.Arrays;
import java.util.Optional;

public class Ship {
    private final int lenght;
    private final ShipSection[] sections;
    private boolean isAlive = true;
    private int aliveSectionCount;

    public Ship(ShipSection[] sections) {
        this.lenght = sections.length;
        this.sections = sections;
        this.aliveSectionCount = this.lenght;
    }

    public int getLenght() {
        return lenght;
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

        hittedSection.get().hit();
        if (--aliveSectionCount == 0) {
            destroy();
        }
        return true;
    }
}
