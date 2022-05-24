package Backend;/*
 * This File is creates for the project of EE364
 *
 * Authors :
 * ABDULLAH KHAMIS ALBIJALI
 * AHMED SULTAN ALJEDANI
 * FARIS ALI ALHARTHI
 * BANDER ALSULAMI
 *
 */

import java.util.Arrays;

public class Display {
    private final static String lineSeparator = "+----+----------+----------+----------+----------------------------+----------+----------+----------+";

    public static void plane(Plane plane) {
        String[][] table = new String[31][7];

        for (int row = 0; row <= 30; row++) {
            for (int col = 0; col < 3; col++) {
                if (plane.getSeatsState()[row][col] != null) {
                    table[row][col] = plane.getSeatsState()[row][col].getBoardingPassTicket().getPassengerName();
                } else {
                    table[row][col] = "    ";
                }

            }
            if (plane.getAisle().get(row) != null) {
                String[] seatSymp = {"A", "B", "C", "E", "F", "G"};
                int passengerSeatingRow = (plane.getAisle().get(row).getBoardingPassTicket().getSeatNumber() / 6 + 1);
                String passengerSeatingCol = seatSymp[(plane.getAisle().get(row).getBoardingPassTicket().getSeatNumber() % 6)];
                table[row][3] = plane.getAisle().get(row).getBoardingPassTicket().getPassengerName() +
                        " " + passengerSeatingRow + passengerSeatingCol +
                        "  " + plane.getAisle().get(row).getLastAction();
            } else {
                table[row][3] = "";
            }


            for (int col = 3; col < 6; col++) {

                if (plane.getSeatsState()[row][col] != null) {
                    table[row][col + 1] = plane.getSeatsState()[row][col].getBoardingPassTicket().getPassengerName() ;
                } else {
                    table[row][col + 1] = "    ";
                }

            }

        }

        String print = "| %-3s|   %-6s |   %-6s |   %-6s |   %-25s|   %-6s |   %-6s |   %-6s | %n";
        System.out.format(print,"#", "A", "B", "C", "Aisle", "E", "F", "G");

        System.out.println(lineSeparator);


        for (int i = 1; i < table.length; i++) {
            String[] seatRow =  table[i];
            System.out.format(print , i + "", seatRow[0],seatRow[1], seatRow[2], seatRow[3], seatRow[4], seatRow[5],seatRow[6]);

        }
        System.out.println(lineSeparator);
    }

    public static void timeAnalysis(Passenger[] passengers, double totalTime) {
        System.out.println("+------------------------------------------------+");
        System.out.println("|                 Time Analysis                  |");
        System.out.println("+-----------+-----------+------------+-----------+");
        System.out.println("| passenger | Real Time | Ideal Time | Hold Time |");
        System.out.println("+-----------+-----------+------------+-----------+");
        String fTotalTime = formatTime(totalTime);
        Arrays.stream(passengers).forEach(passenger -> {

            String passengerName = passenger.getBoardingPassTicket().getPassengerName();
            String realTime = formatTime(passenger.getTimer());
            String holdTime = formatTime(passenger.getHoldTimer()) ;
            String idealTime = formatTime(passenger.getIdealTime());

            System.out.printf("| %-9s | %-10s| %-11s| %-10s|%n", passengerName ,realTime, idealTime, holdTime);
        });
        System.out.println("+-----------+-----------+------------+-----------+");
        System.out.println("+----------------------+");
        System.out.printf("| Total Time | %-7s |%n", fTotalTime);
        System.out.println("+----------------------+");


        System.out.println("+------------------------------------------------------------------------+");
        System.out.println("|                          Timelapse Analysis                            |");
        System.out.println("+----------------+-----------------+-----------------+-------------------+");
        System.out.println("|      Time      |     On Seats    |    Remaining    |       Ratio       |");
        System.out.println("+----------------+-----------------+-----------------+-------------------+");

        int[][] timeLaps = new int[(int) (totalTime / 60) + 1][4];
        for (int i = 1; i < timeLaps.length; i++) {
            timeLaps[i][0] = i ;

            for (Passenger passenger : passengers) {
                int ptime = (int)passenger.getGlobalTimer() / 60;
                if (ptime <= timeLaps[i][0]) {
                    timeLaps[i][1]++;
                }
            }
            timeLaps[i][3] = timeLaps[i][1] - timeLaps[i - 1][1];
            timeLaps[i][2] = 180 - timeLaps[i][1];
            int[] time = timeLaps[i];
            System.out.printf("| %-14s |  %-15s|  %-15s|  %-17s|%n", time[0] + " min", time[1] , time[2]  , time[3] + " P/min");
        }
        System.out.println("+----------------+-----------------+-----------------+-------------------+");



    }
    public static String formatTime(double time){

        int hours =  (int)time / 3600;
        int minutes =  ((int)time % 3600) / 60;
        int seconds = (int)time % 60;

        return hours + ":" + minutes + ":" + seconds;
    }
    public static void line() {
        System.out.println(lineSeparator);
    }
}
