package Backend;

/*
 * This File is creates for the project of EE364
 *
 * Authors :
 * ABDULLAH KHAMIS ALBIJALI
 * AHMED SULTAN ALJEDANI
 * FARIS ALI ALHARTHI
 * BANDER ALSULAMI
 *
 */

public class Family extends Passenger {
    public Passenger[] getMembers() {
        return members;
    }

    Passenger[] members;

    public Family(String name, Passenger[] members) {
        super(name, 0);
        this.members = members;
        setWalkTime(members[0].getWalkTime() +
                members[1].getWalkTime() +
                members[2].getWalkTime() +
                members[3].getWalkTime());

        setSeatingTime(members[0].getSeatingTime() +
                members[1].getSeatingTime() +
                members[2].getSeatingTime() +
                members[3].getSeatingTime());

        setSeatingPreparationTime(members[0].getSeatingPreparationTime() +
                members[1].getSeatingPreparationTime() +
                members[2].getSeatingPreparationTime() +
                members[3].getSeatingPreparationTime());
    }
}

