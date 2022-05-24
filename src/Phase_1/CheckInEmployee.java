package Phase_1;

import Backend.Passenger;
import Backend.PrintBoardingPass;
import GUI.Controller;

import java.util.ArrayList;

public class CheckInEmployee implements PrintBoardingPass{
    // Print boarding pass ticket randomly for all passenger
    public void printBoardingPass(Passenger[] passengers) {
        ArrayList<Integer> randomList = Controller.getRandList();
        for (int i = 0; i < 180; i++) {
            passengers[i].getBoardingPassTicket().setSeatNumber(randomList.get(i));
        }

        // Calculating Ideal time
        for (Passenger passenger : passengers) {
            int row = passenger.getBoardingPassTicket().getSeatNumber() / 6 + 1;
            passenger.setIdealTime(row * passenger.getWalkTime() + passenger.getSeatingPreparationTime() + passenger.getSeatingTime());
        }
    }
}
