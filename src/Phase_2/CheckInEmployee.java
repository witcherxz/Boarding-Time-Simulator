package Phase_2;


import Backend.*;

public class CheckInEmployee implements PassengerPlacingStratigy, PrintBoardingPass {

    public void printBoardingPass(Passenger[] passengers) {

        int seatCounter = 0;


        // Placing Special Need passengers in first
        for (Passenger passenger : passengers) {
            if (passenger instanceof SpecialNeed) {
                passenger.getBoardingPassTicket().setSeatNumber(seatCounter);
                seatCounter++;
            }
        }
        // Placing Old passengers in second
        for (Passenger passenger : passengers) {
            if (passenger instanceof Solo && passenger.getAge() >= 60) {
                passenger.getBoardingPassTicket().setSeatNumber(seatCounter);
                seatCounter++;
            }
        }
        // Placing the other passenger in the remaining seats
        for (Passenger passenger : passengers) {

            if (passenger.getAge() < 60 && passenger instanceof Solo) {
                passenger.getBoardingPassTicket().setSeatNumber(seatCounter);
                seatCounter++;
            }

        }

        for (Passenger passenger : passengers) {
            if (passenger instanceof Family) {
                Passenger[] familyMembers = ((Family) passenger).getMembers();
                for (Passenger member : familyMembers) {
                    member.getBoardingPassTicket().setSeatNumber(seatCounter);
                    seatCounter++;
                }
                passenger.getBoardingPassTicket().setSeatNumber(seatCounter - 5);
            }

        }

        // Calculating Ideal time
        for (Passenger passenger : passengers) {
            int row = passenger.getBoardingPassTicket().getSeatNumber() / 6 + 1;
            passenger.setIdealTime(row * passenger.getWalkTime() + passenger.getSeatingPreparationTime() + passenger.getSeatingTime());
        }
    }

    @Override
    public void familyCheck() {

    }

    @Override
    public void requestSpecialNeedService(Passenger passenger) {
        if (passenger instanceof SpecialNeed) {
            passenger.setWalkTime(passenger.getWalkTime() / 5);
            passenger.setSeatingPreparationTime(passenger.getSeatingPreparationTime() / 5);

        }
    }
}
