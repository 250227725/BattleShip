import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShipGenerator {
    public Set<ShipSample> possibleShips;
    public Set<ShipSample> generatedShips;
    public int height;
    public int width;
    public int[] setup;


    public ShipGenerator(int width, int height, int[] setup) {
        this.height = height;
        this.width = width;
        this.setup = setup;
        possibleShips = generatePossibleShips(width, height, setup);
        generatedShips = new HashSet<>();
    }

    public Set<ShipSample> generatePossibleShips(int width, int height, int[] setup) {
        Set<ShipSample> ships = new HashSet<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                ships.add(ShipSample.createChipSample(x, y, 1, 0, 0));
                for (int size = 2; size < setup.length; size++) {
                    if (y + size <= height) {
                        ships.add(ShipSample.createChipSample(x, y, size, 0, 1));
                    }
                    if (x + size <= width) {
                        ships.add(ShipSample.createChipSample(x, y, size, 1, 0));
                    }
                }
            }
        }
        return ships;
    }

    public static Map<Ship, String> getShips(int width, int height, int[] setup) {
        ShipGenerator generator = new ShipGenerator(width, height, setup);
        return generator.createShipMap();
    }

    public Map<Ship, String> createShipMap() {
        generateShips();
        Map<Ship, String> result = new HashMap<>();
        generatedShips.stream()
                .map(ShipSample::toShip)
                .forEach(s -> result.put(s, "" + s.getLength()));
        return result;
    }

    public void generateShips() {
        for (int size = setup.length - 1; size > 0; size--) {
            for (int count = 0; count < setup[size]; count++) {
                ShipSample ship = generateShip(size);
                removeUnavailableShipsFromSetup(ship);
                generatedShips.add(ship);
            }
        }
    }

    public ShipSample generateShip(int size) {
        List<ShipSample> availableShips = possibleShips.stream()
                .filter(s -> s.size() == size)
                .toList();
        if (availableShips.size() == 0) {
            throw new IllegalStateException();
        }
        return availableShips.get((int) (Math.random() * availableShips.size()));
    }

    public Set<CellSample> generateUnavailableCells(ShipSample ship) {
        return ship.sections()
                .flatMap(s -> s.getNeighbors().stream())
                .collect(Collectors.toSet());
    }

    public void removeUnavailableShipsFromSetup(ShipSample ship) {
        removeUnavailableShipsFromSetup(generateUnavailableCells(ship));
    }

    public void removeUnavailableShipsFromSetup(Set<CellSample> unavailableCells) {
        Set<ShipSample> shipsForRemove = possibleShips.stream()
                .filter(s -> s.checkIntersection(unavailableCells))
                .collect(Collectors.toSet());
        possibleShips.removeAll(shipsForRemove);
    }

    static class ShipSample{
        private final Set<CellSample> sections;

        public ShipSample(Set<CellSample> sections) {
            this.sections = sections;
        }

        public static ShipSample createChipSample(int x, int y, int size, int dx, int dy) {
            Set<CellSample> cells = new HashSet<>();
            if (size == 1) {
                cells.add(new CellSample(y, x));
            }
            else {
                for (int j = 0; j < size; j++) {
                    cells.add(new CellSample(y + dy * j, x + dx * j));
                }
            }
            return new ShipSample(cells);
        }

        public Stream<CellSample> sections() {
            return sections.stream();
        }
        public int size() {
            return sections.size();
        }
        public boolean checkIntersection(Set<CellSample> unavailableCells) {
            return sections.stream().anyMatch(unavailableCells::contains);
        }

        public Ship toShip() {
            return Ship.getInstance(sections.toArray(CellSample[]::new));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ShipSample that = (ShipSample) o;
            return Objects.equals(sections, that.sections);
        }

        @Override
        public int hashCode() {
            return Objects.hash(sections);
        }
    }
}
