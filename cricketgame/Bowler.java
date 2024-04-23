package cricketgame;

class Bowler extends Cricketer {
    Integer age;
    private float overs;
    private int runs;
    private int wickets;
    private float economy;

    // Static variable
    private static int totalBowlers;

    // Constructor with only name parameter
    public Bowler(String name,int age) {
        super(name);
        this.age = Integer.valueOf(age);
        this.overs = 0.0f;
        this.runs = 0;
        this.wickets = 0;
        this.economy = 0.0f;
        totalBowlers++;
    }

    // Constructor with name, matches, overs parameters
    public Bowler(String name,int age, int matches, float overs) {
        super(name,matches);
        this.age = Integer.valueOf(age);
        this.overs = overs;
        calculateEconomy();
        totalBowlers++;
    }

    // Constructor with all parameters
    public Bowler(String name,int age, int matches, float overs, int runs, int wickets, float economy) {
        super(name,matches);
        this.age = Integer.valueOf(age);
        this.overs = overs;
        this.runs = runs;
        this.wickets = wickets;
        this.economy = economy;
        totalBowlers++;
    }

    public Bowler(Bowler other) {
        super(name);
        this.age = other.age;
        this.overs = other.overs;
        this.runs = other.runs;
        this.wickets = other.wickets;
        this.economy = other.economy;
        totalBowlers++;
    }

    // Calculate economy rate
    private void calculateEconomy() {
        if (overs > 0) {
            economy = runs / overs;
        }
    }

    // Getter methods
        public String getName() {
            return name;
        }

        public int getMatches() {
            return matches;
        }

        public float getOvers() {
            return overs;
        }

        public int getRuns() {
            return runs;
        }

        public int getWickets() {
            return wickets;
        }

        public float getEconomy() {
            return economy;
        }

        // Setter methods
        public void setName(String name) {
            this.name = name;
        }

        public void setMatches(int matches) {
            this.matches = matches;
        }

        public void setOvers(float overs) {
            this.overs = overs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public void setWickets(int wickets) {
            this.wickets = wickets;
        }

        public void setEconomy(float economy) {
            this.economy = economy;
        }

        // Static method to get the total number of batsmen
        public static int getTotalBowlers() {
            return totalBowlers;
        }
}
