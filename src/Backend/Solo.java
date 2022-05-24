package Backend;

public class Solo extends Passenger {
    private String typeofLuggage;

    public Solo(String name, int age, String typeofLuggage) {
        super(name, age);
        this.typeofLuggage = typeofLuggage;

        if (typeofLuggage.equals("Heavy")) {
            this.setSeatingPreparationTime(getSeatingPreparationTime() + 5);
        }
    }

    public String getTypeofLuggage() {
        return typeofLuggage;
    }

    public void setTypeofLuggage(String typeofLuggage) {
        this.typeofLuggage = typeofLuggage;
    }
}
