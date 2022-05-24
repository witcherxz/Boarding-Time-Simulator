package Backend;

import java.util.ArrayList;
import java.util.List;

public abstract class Passenger {

    private double holdTimer = 0;
    private double actionTimeCounter = 0;
    private double globalTimer = 0;
    private double idealTime = 0;
    private int currentRow = 0;
    private int age;
    private ArrayList<Actions> history = new ArrayList<>(List.of(Actions.OnBoardSteps));
    private Actions lastAction = Actions.OnBoardSteps;
    private final BordingPassTicket boardingPassTicket = new BordingPassTicket();
    private final String name = boardingPassTicket.getPassengerName();
    private double walkTime = 0; // in Seconds
    private double seatingPreparationTime = 0; // in Seconds
    private double seatingTime = 0;
    private double timer = 0;

    public Passenger(String name, int age) {

        boolean isSpecialNeed = this instanceof SpecialNeed;
        // Old Solo
        if (age >= 60 && !isSpecialNeed) {
            this.walkTime = 5;
            this.seatingPreparationTime = 50;
            this.seatingTime = 10;
            // under 60 Solo
        } else if (age < 60 && !isSpecialNeed) {
            this.walkTime = 2;
            this.seatingPreparationTime = 20;
            this.seatingTime = 5;
            // Special Need
        } else {
            this.walkTime = 10;
            this.seatingPreparationTime = 120;
            this.seatingTime = 20;
        }
        this.actionTimeCounter = this.walkTime;
        this.boardingPassTicket.setPassengerName(name);
        this.age = age;
    }

    public void setWalkTime(double walkTime) {
        this.walkTime = walkTime;
    }

    public void setSeatingTime(double seatingTime) {
        this.seatingTime = seatingTime;
    }

    public int getAge() {
        return age;
    }

    public double getActionTimeCounter() {
        return actionTimeCounter;
    }

    public void setActionTimeCounter(double actionTimeCounter) {
        this.actionTimeCounter = actionTimeCounter;
    }

    public double getHoldTimer() {
        return holdTimer;
    }

    public void incHoldTimer(double holdTimer) {
        this.holdTimer += holdTimer;
    }

    public double getIdealTime() {
        return idealTime;
    }

    public void setIdealTime(double idealTime) {
        this.idealTime = idealTime;
    }

    public double getGlobalTimer() {
        return globalTimer;
    }

    public void setGlobalTimer(double globalTimer) {
        this.globalTimer = globalTimer;
    }

    public void incCurrentRow() {
        this.currentRow++;
    }

    public void incTimer(double time) {
        this.timer += time;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public Actions getLastAction() {
        return lastAction;
    }

    public BordingPassTicket getBoardingPassTicket() {
        return boardingPassTicket;
    }

    public double getWalkTime() {
        return walkTime;
    }

    public double getSeatingPreparationTime() {
        return seatingPreparationTime;
    }

    public void setSeatingPreparationTime(double seatingPreparationTime) {
        this.seatingPreparationTime = seatingPreparationTime;
    }

    public double getSeatingTime() {
        return seatingTime;
    }

    public double getTimer() {
        return timer;
    }

    public Actions nextAction(ArrayList<Passenger> aisle) {
        if (currentRow == ((this.boardingPassTicket.getSeatNumber() / 6) + 1)) {
            if (lastAction.equals(Actions.Move) && this.walkTime == this.actionTimeCounter) {
                this.history.add(Actions.PrepareToSeat);
                this.lastAction = Actions.PrepareToSeat;
                return Actions.PrepareToSeat;
            }
            if (lastAction.equals(Actions.SeatDown) && this.seatingTime == this.actionTimeCounter) {
                this.history.add(Actions.Done);
                this.lastAction = Actions.Done;
                return Actions.SeatDown;
            }
            if (lastAction.equals(Actions.PrepareToSeat) && this.seatingPreparationTime == this.actionTimeCounter) {
                this.history.add(Actions.SeatDown);
                this.lastAction = Actions.SeatDown;
                return Actions.PrepareToSeat;
            }
            return Actions.Done;
        } else {
            if (aisle.get(this.currentRow + 1) == null) {
                this.lastAction = Actions.Move;
                this.history.add(Actions.Move);
            } else {
                if (!lastAction.equals(Actions.OnBoardSteps)) {
                    this.lastAction = Actions.Hold;
                    this.history.add(Actions.Hold);
                    this.holdTimer++;
                }
            }

            return Actions.Move;
        }
    }

    public enum Actions {OnBoardSteps, Move, Hold, PrepareToSeat, SeatDown, Done}

    private static String formatTime(double time){

        int hours =  (int)time / 3600;
        int minutes =  ((int)time % 3600) / 60;
        int seconds = (int)time % 60;

        return hours + ":" + minutes + ":" + seconds;
    }

    @Override
    public String toString() {
        String[] symple = {"A", "B", "C", "D", "E", "F"};
        return
                "Passenger Name : " + this.boardingPassTicket.getPassengerName() + "\n" +
                        "Passenger Type : " + this.getClass().getSimpleName() +"\n" +
                        "Passenger Age : " + this.age + "\n" +
                        "seatNumber : " + ((this.boardingPassTicket.getSeatNumber() / 6) + 1) + symple[this.boardingPassTicket.getSeatNumber() % 6] + "\n" +
                        "Current Action : " + this.getLastAction() + "\n"+
                        "The time needs to walk one step : " + this.walkTime +"s"+ "\n"+
                        "Preparation Time : " + this.seatingPreparationTime +"s"+ "\n"+
                        "Time spend on the aisle : " + formatTime(this.timer) + "\n" +
                        "Ideal Time : " + formatTime(this.idealTime) + "\n";

    }
}
