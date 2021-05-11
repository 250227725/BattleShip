import java.util.List;

public class Ship {
    private boolean isAlive;
    private List<ShipSection> sections;

    public boolean isAlive() {
        return isAlive;
    }

    public List<ShipSection> getSections() {
        return sections;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setSections(List<ShipSection> sections) {
        this.sections = sections;
    }
}
