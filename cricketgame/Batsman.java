package cricketgame;

class Batsman {
    private String name;
    Integer age;
    private int matches;
    private int runs;
    private float avg;
    private float sr;

// Static variable
    private static int totalBatsmen;

    // Constructor with only name parameter
    public Batsman(String name,int age) {
        this.name = name;
        // this.age = new Integer(age);
        this.age = Integer.valueOf(age);
        this.matches = 0;
        this.runs = 0;
        this.avg = 0.0f;
        this.sr = 0.0f;
        totalBatsmen++;
    }

    // Constructor with name, matches, runs parameters
    public Batsman(String name,int age, int matches, int runs) {
        this.name = name;
        this.age = Integer.valueOf(age);
        this.matches = matches;
        this.runs = runs;
        calculateAvg();
        calculateSR();
        totalBatsmen++;
    }

    // Constructor with name, matches, runs, avg, sr parameters
    public Batsman(String name,int age, int matches, int runs, float avg, float sr) 
    {
        this.name = name;
        this.age = Integer.valueOf(age);
        this.matches = matches;
        this.runs = runs;
        this.avg = avg;
        this.sr = sr;
        totalBatsmen++;
    }

    // Constructor with object argument
    public Batsman(Batsman other) {
        this.name = other.name;
        this.age = other.age;
        this.matches = other.matches;
        this.runs = other.runs;
        this.avg = other.avg;
        this.sr = other.sr;
        totalBatsmen++;
    }

    // Calculate batting average
    private void calculateAvg() {
        if (matches > 0) {
            avg = runs / (float) matches;
        }
    }

    // Calculate strike rate
    private void calculateSR() {
        if (runs > 0) {
            sr = runs / (float) matches;
        }
    }

    // Getter methods
        public String getName() {
            return name;
        }

        public int getMatches() {
            return matches;
        }

        public int getRuns() {
            return runs;
        }

        public float getAvg() {
            return avg;
        }

        public float getSr() {
            return sr;
        }

        // Setter methods
        public void setName(String name) {
            this.name = name;
        }

        public void setMatches(int matches) {
            this.matches = matches;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public void setAvg(float avg) {
            this.avg = avg;
        }

        public void setSr(float sr) {
            this.sr = sr;
        }

        public static int getTotalBatsmen() 
        {
                return totalBatsmen;
        }
}
