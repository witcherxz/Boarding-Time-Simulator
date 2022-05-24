package Backend;

import java.util.ArrayList;

public class Plane {
    private ArrayList<Passenger> aisle = new ArrayList<>();
    // Rows:30 , Columns:6  -> 30 elements
    private Passenger[][] seatsState = new Passenger[31][6];
    private double boardingTime = 3.5;

    public ArrayList<Passenger> getAisle() {
        return aisle;
    }

    public Passenger[][] getSeatsState() {
        return seatsState;
    }

    public double getBoardingTime() {
        return boardingTime;
    }

}
