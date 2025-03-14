import java.util.ArrayList;
import java.util.List;

class KitBag implements Cloneable {
    private List<Bat> bats;

    public KitBag() {
        this.bats = new ArrayList<>();
    }

    public void addBat(Bat bat) {
        bats.add(bat);
    }

    @Override
    public KitBag clone() {
        try {
            // Shallow copy
            KitBag clone = (KitBag) super.clone();


            // now to convert this shallow into a Deep copy for bats list
            clone.bats = new ArrayList<>(bats.size());
            for (Bat bat : bats) {
                clone.bats.add(new Bat(bat.getBrand(), bat.getBatsman()));
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            // This should never happen, as we are implementing Cloneable
            throw new AssertionError();
        }
    }

    public List<Bat> getBats() {
        return bats;
    }

    public static void main(String[] args) {
        // Create original library
        KitBag originalKitBag = new KitBag();
        originalKitBag.addBat(new Bat("Bat1", "Batsman1"));
        originalKitBag.addBat(new Bat("Bat2", "Batsman2"));
        originalKitBag.addBat(new Bat("Bat3", "Batsman3"));

        // Shallow copy
        KitBag shallowCopy = originalKitBag.clone();

        // Deep copy
        KitBag deepCopy = new KitBag();
        for (Bat bat : originalKitBag.getBats()) {
            deepCopy.addBat(new Bat(bat.getBrand(), bat.getBatsman()));
        }

        // Modify the original library to see if it affects the copies
        originalKitBag.addBat(new Bat("Bat4", "Batsman4"));

        // Print original and copied libraries to verify shallow and deep copy
        System.out.println("Original library: " + originalKitBag.getBats());
        System.out.println("Shallow copy: " + shallowCopy.getBats());
        System.out.println("Deep copy: " + deepCopy.getBats());
    }
}

class Bat {
    private String brand;
    private String batsman;

    public Bat(String brand, String batsman) {
        this.brand = brand;
        this.batsman = batsman;
    }

    public String getBrand() {
        return brand;
    }

    public String getBatsman() {
        return batsman;
    }

    @Override
    public String toString() {
        return "Bat{" +
                "brand='" + brand + '\'' +
                ", batsman='" + batsman + '\'' +
                '}';
    }
}
