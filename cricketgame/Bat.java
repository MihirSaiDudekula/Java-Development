package cricketgame;

public class Bat extends Equipment implements Strokeable, Swingable {
    //multiple inheritance using interfaces
    private int weight;

    public Bat(String brand, int weight) {
        super(brand);
        this.weight = weight;
    }

    @Override
    public void display() {
        System.out.println("Brand: " + brand + ", Type: Bat, Weight: " + weight + " grams");
    }

    @Override
    public void stroke() {
        System.out.println("Making a stroke with the bat.");
    }

    @Override
    public void swing() {
        System.out.println("Swinging the bat.");
    }
}

// Bat is a concrete subclass of Equipment, representing a cricket bat.
// It contains an additional field weight, representing the weight of the bat in grams.
// The constructor Bat(String brand, int weight) initializes the brand field using the superclass constructor and initializes the weight field.
// It overrides the display() method to print specific details of the bat, including its brand and weight.