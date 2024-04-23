package cricketgame;

// Parent class Cricketer
public class Cricketer {
    protected String name;
    protected int matches;

    // Constructor
    public Cricketer(String name) {
        this.name = name;
        this.matches = 0;
    }

    public Cricketer(String name,int matches) {
        this.name = name;
        this.matches = matches;
    }

    public Cricketer(Cricketer other) {
        this.name = other.name;
        this.matches = other.matches;
    }

    // Getter and setter methods for name and matches...
}
