package cricketgame;

public class Game {
    public static void main(String[] args) {
        Cricketer cricketer = new Cricketer("Generic Cricketer", 10);
        Batsman batsman = new Batsman("Virat Kohli", 32, 100, 12000);
        Bowler bowler = new Bowler("Jasprit Bumrah", 27, 80, 200);

        System.out.println(cricketer.toString()); // Polymorphic call to toString
        System.out.println(batsman.toString());   // Polymorphic call to toString
        System.out.println(bowler.toString());    // Polymorphic call to toString
    }
}

