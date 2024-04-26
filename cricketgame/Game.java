package cricketgame;

public class Game {
    public static void main(String[] args) {
        Cricketer cricketer = new Cricketer("Generic Cricketer", 10);
        Batsman batsman = new Batsman("Virat Kohli", 32, 100, 12000);
        Bowler bowler = new Bowler("Jasprit Bumrah", 27, 80, 200);

        System.out.println(cricketer.toString()); // Polymorphic call to toString
        System.out.println(batsman.toString());   // Polymorphic call to toString
        System.out.println(bowler.toString());    // Polymorphic call to toString


        //Using Generics
        
        // Create a TeamRoster for cricket players (using String as an example)
        TeamRoster<String> cricketTeam = new TeamRoster<>();

        // Add players to the roster
        cricketTeam.add("Rohit");
        cricketTeam.add("Virat");
        cricketTeam.add("Jasprit");

        // Access players from the roster
        System.out.println("Player 1: " + cricketTeam.get(0));
        System.out.println("Player 2: " + cricketTeam.get(1));
        System.out.println("Player 3: " + cricketTeam.get(2));

        // Create a TeamRoster for cricket players (using int as an example)
        TeamRoster<Integer> JerseyNo = new TeamRoster<>();

        // Add players to the roster
        JerseyNo.add(45);
        JerseyNo.add(18);
        JerseyNo.add(7);

        // Access players from the roster
        System.out.println("Player 1s jersey no is: " + JerseyNo.get(0));
        System.out.println("Player 2s jersey no is: " + JerseyNo.get(1));
        System.out.println("Player 3s jersey no is: " + JerseyNo.get(2));

    }
}

