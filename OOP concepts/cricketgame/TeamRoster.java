// Generics allow you to define classes and methods in a way that they can work with any type of data. Instead of specifying a specific type (like String or Integer), you use placeholders called type parameters. These type parameters can be replaced by actual types when you create instances of those classes or call those methods.

public class TeamRoster<T> 
{
    // TeamRoster is a generic class with a type parameter T. This means that T can represent any type of data, and it will be specified when you create an instance of the TeamRoster class.

    // the idea here is we are using generics to implement team roster,the list of all players in our playing XI which will either have all names of players, or their jersey numbers (String or int), which will automatically be decided at compile time
    private T[] players;
    private int size;
    private static final int TEAM_SIZE = 11;

    @SuppressWarnings("unchecked")
    //Java does not allow the direct creation of arrays of generic types due to type erasure. You'll encounter a warning regarding unchecked type casting. While the code will compile, it may lead to runtime issues if incorrect types are added to the array

    // this is not related to us though, we can ignore for now 

    public TeamRoster() {
        this.players = (T[]) new Object[TEAM_SIZE];
        this.size = 0;
    }

    public void add(T item) {
        if (size >= players.length) {
            throw new IndexOutOfBoundsException("Index: " + size + ", Size: " + size);
        }
        players[size++] = item;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return players[index];
    }
}
