// Ball.java
package cricketgame;

public class Ball extends Equipment implements Bounceable, Swingable {
    //multiple inheritance using interfaces
    private String color;

    public Ball(String brand, String color) {
        super(brand);
        this.color = color;
    }

    @Override
    public void display() {
        System.out.println("Brand: " + brand + ", Type: Ball, Color: " + color);
    }

    @Override
    public void bounce() {
        System.out.println("Bouncing the ball.");
    }

    @Override
    public void swing() {
        System.out.println("Swinging the ball.");
    }
}

// Ball is another concrete subclass of Equipment, representing a cricket ball.
// It contains an additional field color, representing the color of the ball.
// The constructor Ball(String brand, String color) initializes the brand field using the superclass constructor and initializes the color field.
// It overrides the display() method to print specific details of the ball, including its brand and color.