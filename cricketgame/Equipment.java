// Equipment.java (abstract class)
package cricketgame;

public abstract class Equipment {
    protected String brand;

    public Equipment(String brand) {
        this.brand = brand;
    }
    // there can be no direct objects of this class!

    // Abstract method to be implemented by subclasses
    public abstract void display();
}
// Equipment is an abstract class that serves as a base class for different types of cricket equipment.
// It contains a single field brand, which represents the brand of the equipment.
// The class has a constructor CricketEquipment(String brand) to initialize the brand field.
// It declares an abstract method display(), which will be implemented by concrete subclasses to display specific details of each type of equipment.