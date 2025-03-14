package cricketgame;


// Interfaces allow you to define a set of method signatures without providing the implementation details. This allows you to specify what needs to be done without specifying how it should be done

public interface Strokeable {
    @Deprecated
    void shot();
    // @Deprecated is what we call annotation,is used to indicate that the shot() method is no longer recommended for use.

    void stroke();
    // abstract method is a method declared without implementation
    //interfaces make use of abstract methods

    // Static method

    // interfaces can contain static methods 
    // interfaces cannot be instantiated (we cant make objects of an interface) directly
    // so to use these functions , we use Strokeable.showInfo();

    static void showInfo() {
        System.out.println("This interface represents strokeable cricket equipment.");
    }
}