package Backend;

public class SpecialNeed extends Passenger {
    boolean isBoardLuggage;

    public SpecialNeed(String name, int age, boolean isBoardLuggage) {
        super(name, age);
        this.isBoardLuggage = isBoardLuggage;
    }
}
