package Phase_2;/*
 * This File is creates for the project of EE364
 * Authors :
 * ABDULLAH KHAMIS ALBIJALI
 * AHMED SULTAN ALJEDANI
 * FARIS ALI ALHARTHI
 * BANDER ALSULAMI
 *
 */

import Backend.Passenger;

public interface BoardingStrategy {
    Passenger[] callPassengerByZone(Passenger[] passengers);
    void helpPassengerWithLuggage(Passenger passenger);
}
