package Phase_2;

/*
 * This File is creates for the project of EE364
 * Authors :
 * ABDULLAH KHAMIS ALBIJALI
 * AHMED SULTAN ALJEDANI
 * FARIS ALI ALHARTHI
 * BANDER ALSULAMI
 *
 */

import Backend.Passenger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FlightAttendant implements BoardingStrategy{


    @Override
    public Passenger[] callPassengerByZone(Passenger[] passengers) {
        Passenger[] organizedPassengers = new Passenger[passengers.length];
        ArrayList<ArrayList<Passenger>> listRow = new ArrayList<>();
        for (int i = 0; i < 31; i++) {
            listRow.add(new ArrayList<Passenger>());
        }
        for (Passenger passenger :
                passengers) {
            int row = passenger.getBoardingPassTicket().getSeatNumber() / 6 + 1;
            listRow.get(row).add(passenger);
        }
        int pCounter = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = listRow.size() - 1; j > 0 ; j--) {
                organizedPassengers[pCounter] = listRow.get(j).get(i);
                pCounter++;
            }
        }

        return organizedPassengers;
    }

    @Override
    public void helpPassengerWithLuggage(Passenger passenger) {
        passenger.setSeatingPreparationTime(passenger.getSeatingPreparationTime() / 2);
    }
}
