import java.util.*;
import java.util.stream.Collectors;

public class Ship {
    private final int lenght;
    private final ShipSection[] sections;
    private boolean isAlive = true;
    private int aliveSectionCount;

    private Ship(ShipSection[] sections) {
        this.lenght = sections.length;
        this.sections = sections;
        this.aliveSectionCount = this.lenght;
    }

    public static Ship getInstance(int[][] cells) {
        if (cells == null) {
            throw new IllegalArgumentException();
        }

        ShipSection[] sections = Arrays.stream(cells)
                .map(c -> {
                    if (c.length != 2) {
                        throw new IllegalArgumentException();
                    }
                    return new ShipSection(c[0], c[1]);
                })
                .collect(Collectors.toSet())
                .toArray(new ShipSection[]{});

        if (cells.length != sections.length) {
            throw new IllegalArgumentException();
        }

        if (sections.length > 1) {
            Arrays.sort(sections);
            boolean isHorizontal = sections[0].getX() != sections[1].getX();
            for (int i = 1; i < sections.length; i++) {
                if (isHorizontal) {
                    if (sections[i].getX() - sections[i-1].getX() != 1 || sections[i].getY() != sections[i-1].getY()) throw new IllegalArgumentException();
                } else {
                    if (sections[i].getY() - sections[i-1].getY() != 1 || sections[i].getX() != sections[i-1].getX()) throw new IllegalArgumentException();
                }
            }
        }

        return new Ship(sections);
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
