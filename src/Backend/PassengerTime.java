package Backend;

public class PassengerTime {

    private String passengerName;
    private String realTime;
    private String holdTime;
    private String idealTime;

    public String getPassengerName() {
        return passengerName;
    }

    public String getRealTime() {
        return realTime;
    }

    public String getHoldTime() {
        return holdTime;
    }

    public String getIdealTime() {
        return idealTime;
    }

    public PassengerTime(String passengerName, String realTime, String holdTime, String idealTime) {
        this.passengerName = passengerName;
        this.realTime = realTime;
        this.holdTime = holdTime;
        this.idealTime = idealTime;
    }
}
