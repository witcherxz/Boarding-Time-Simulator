package GUI;

import Backend.*;
import Phase_1.CheckInEmployee;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller  implements Initializable{

    @FXML
    private GridPane asile;

    @FXML
    private Button start;

    @FXML
    private Button end;

    @FXML
    private GridPane leftSeats;

    @FXML
    private Button next;

    @FXML
    private Button run;

    @FXML
    private GridPane rightSeats;

    @FXML
    private Label time;

    @FXML
    private TableView<PassengerTime> timeAnalysis;

    @FXML
    private TableColumn<PassengerTime, String> passengerCol;

    @FXML
    private TableColumn<PassengerTime, String> holdTimeCol;

    @FXML
    private TableColumn<PassengerTime, String> idealTimeCol;


    @FXML
    private TableColumn<PassengerTime, String> realTimeCol;

    private boolean isSeatsFull = false;

    private static int phase = 2;
    private int totalTime = 0;
    private int cnt = 0;
    private int[] randAge1;
    private int[] randAge2;
    private int[] randLuggage;
    private boolean loopOver = false;
    private static ArrayList<Integer> randList;
    private Passenger[] currentPassenger;

    public static ArrayList<Integer> getRandList() {
        return randList;
    }

    private int[] randGen(int len, int min, int max){
        int [] randList = new int[len];
        for (int i = 0; i < len; i++) {
            randList[i] = (int) Math.floor(Math.random() * (max - min)) + min;
        }

        return randList;
    }

    private static String formatTime(double time){

        int hours =  (int)time / 3600;
        int minutes =  ((int)time % 3600) / 60;
        int seconds = (int)time % 60;

        return hours + ":" + minutes + ":" + seconds;
    }

    private Rectangle rectGen(Color color){
        Rectangle rect = new Rectangle();
        StackPane stackPane = new StackPane();
        rect.setX(300);
        rect.setY(200);
        rect.setWidth(20);
        rect.setHeight(20);
        rect.setFill(color);
        rect.setStrokeWidth(5);
        rect.setArcHeight(10);
        rect.setArcWidth(10);

        return rect;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        randAge1 = randGen(170, 18, 100);
        randAge2 = randGen(10, 18, 100);
        randLuggage = randGen(170, 0, 2);
        ArrayList<Integer> list= new ArrayList<>();
        for (int i = 0; i < 180; i++) {
            list.add(i, i);
        }
        Collections.shuffle(list);
        randList = list;

        for (int i = 0; i < 180 / 2; i++) {
            Rectangle rectangle = rectGen(Color.GRAY);

            int row = i / 3;
            int col = i % 3;
            leftSeats.add(rectangle, col, row + 1);
        }

        for (int i = 0; i < 180 / 2; i++) {
            Rectangle rectangle = rectGen(Color.GRAY);

            int row = i / 3;
            int col = i % 3;
            rightSeats.add(rectangle, col, row + 1);
        }

    }


    @FXML
    void nextEvent(ActionEvent event) {
        cnt += 1;
        eventHandler(cnt);
    }

    @FXML
    void previousEvent(ActionEvent event) {
        if (cnt > 0) {
            cnt -= 1;
        }
        eventHandler(cnt);
    }

    @FXML
    void fastPreviousEvent(ActionEvent event) {
        if (cnt > 0) {
            cnt -= 60;
        }
        eventHandler(cnt);
    }

    @FXML
    void fastNextEvent(ActionEvent event) {
        cnt += 60;
        eventHandler(cnt);
    }

    @FXML
    void startEvent(ActionEvent event) {
        cnt = 0;
        eventHandler(cnt);
    }

    @FXML
    void stepForwardEvent(ActionEvent event) {
        cnt += 60*10;
        eventHandler(cnt);
    }
    @FXML
    void stepBackEvent(ActionEvent event) {
        cnt -= 60*10;
        eventHandler(cnt);
    }

    @FXML
    public void backToHome(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Stage stage = (Stage) start.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Boarding Time Phase 1");
        isSeatsFull = true;
        stage.show();
    }

    @FXML
    public void aboutEvent(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("About");
        alert.setTitle("About");
        alert.setContentText("This project is a boarding time simulator created for the final project of EE364");
        alert.showAndWait();
    }

    @FXML
    void runEvent(ActionEvent event) {
        loopOver = !loopOver;
        eventHandler(cnt);
        if (loopOver){
            run.setText("STOP");
        }else {
            run.setText("RUN");
        }

        Thread thread = new Thread(() -> {
            while (loopOver && !isSeatsFull) {
                try {
                    cnt += 2;
                    Thread.sleep(500);
                    // Update the UI on the JavaFX Application Thread
                    Platform.runLater(() -> {
                        eventHandler(cnt);
                    });
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        thread.start();
    }

    private void eventHandler(int num){
        Plane plane = loop(num);

        ArrayList<Passenger> planeAisle = plane.getAisle();
        Passenger[][] seats = plane.getSeatsState();
        time.setText(formatTime(totalTime));
        for (int i = 0; i < 180 / 2; i++) {
            int row = i / 3;
            int col = i % 3;
            Passenger lseat = seats[row + 1][col];
            Passenger rseat = seats[row + 1][col + 3];

            Rectangle empty1 = rectGen(Color.GRAY);
            Rectangle empty2 = rectGen(Color.GRAY);
            Rectangle full1 = rectGen(Color.web("#161E54"));
            Rectangle full2 = rectGen(Color.web("#161E54"));

            if (lseat != null){
                leftSeats.add(full1, col, row + 1);
                Tooltip tooltip = new Tooltip(lseat.toString());
                tooltip.setShowDelay(Duration.ZERO);
                tooltip.setShowDuration(Duration.INDEFINITE);
                Tooltip.install(full1,tooltip);
            }else {
                leftSeats.add(empty1, col, row + 1);
            }

            if (rseat != null){
                rightSeats.add(full2, col, row + 1);
                Tooltip tooltip = new Tooltip(rseat.toString());
                tooltip.setShowDelay(Duration.ZERO);
                tooltip.setShowDuration(Duration.INDEFINITE);
                Tooltip.install(full2,tooltip);
            }else {
                rightSeats.add(empty2, col, row + 1);
            }
        }
        for (int i = 0; i < 30; i++) {

            Rectangle empty = rectGen(Color.WHITE);

            if (planeAisle.get(i + 1) != null){
                Rectangle moving = rectGen(Color.web("#7CD1B8"));
                Rectangle prepareToSeat = rectGen(Color.web("#EBE645"));
                Rectangle hold = rectGen(Color.web("#EC255A"));
                if (planeAisle.get(i + 1).getLastAction().equals(Passenger.Actions.Move)) {
                    Tooltip tooltip = new Tooltip(planeAisle.get(i + 1).toString());
                    tooltip.setShowDelay(Duration.ZERO);
                    tooltip.setShowDuration(Duration.INDEFINITE);
                    Tooltip.install(moving,tooltip);
                    asile.add(moving, 0, i + 1);
                }
                if (planeAisle.get(i + 1).getLastAction().equals(Passenger.Actions.PrepareToSeat)) {
                    Tooltip tooltip = new Tooltip(planeAisle.get(i + 1).toString());
                    tooltip.setShowDelay(Duration.ZERO);
                    tooltip.setShowDuration(Duration.INDEFINITE);
                    Tooltip.install(prepareToSeat,tooltip);
                    asile.add(prepareToSeat, 0, i + 1);
                }
                if (planeAisle.get(i + 1).getLastAction().equals(Passenger.Actions.Hold)){
                    Tooltip tooltip = new Tooltip(planeAisle.get(i + 1).toString());
                    tooltip.setShowDelay(Duration.ZERO);
                    tooltip.setShowDuration(Duration.INDEFINITE);
                    Tooltip.install(hold,tooltip);
                    asile.add(hold, 0, i + 1);
                }
            }else {
                asile.add(empty, 0,i + 1);
            }
        }
        ArrayList<Boolean> isFullList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            isFullList.add(Arrays.stream(seats[i]).allMatch(Objects::nonNull));
        }

        updateTable();
        boolean isFull = isFullList.stream().allMatch(aBoolean -> aBoolean);
        if (isFull){
            next.setDisable(true);
            isSeatsFull = true;
            end.setDisable(true);
            run.setDisable(true);

//            timeAnalysis.getColumns().addAll(passengerCol, idealTimeCol, realTimeCol, holdTimeCol);

        }else {
            next.setDisable(false);
            isSeatsFull = false;
            end.setDisable(false);
            run.setDisable(false);
        }
    }

    private void updateTable(){
        ObservableList<PassengerTime> passengers = FXCollections.observableArrayList();

        for (Passenger passenger : currentPassenger) {
                String passengerName = passenger.getBoardingPassTicket().getPassengerName();
                String realTime = formatTime(passenger.getTimer());
                String holdTime = formatTime(passenger.getHoldTimer());
                String idealTime = formatTime(passenger.getIdealTime());
                passengers.add(new PassengerTime(passengerName, realTime, holdTime, idealTime));
        }

        passengerCol.setCellValueFactory(new PropertyValueFactory<>("passengerName"));
        idealTimeCol.setCellValueFactory(new PropertyValueFactory<>("idealTime"));
        realTimeCol.setCellValueFactory(new PropertyValueFactory<>("realTime"));
        holdTimeCol.setCellValueFactory(new PropertyValueFactory<>("holdTime"));
        timeAnalysis.setItems(passengers);
        timeAnalysis.getColumns().set(0, passengerCol);
        timeAnalysis.getColumns().set(1, realTimeCol);
        timeAnalysis.getColumns().set(2, idealTimeCol);
        timeAnalysis.getColumns().set(3, holdTimeCol);
    }


    public static int getPhase() {
        return phase;
    }

    public static void setPhase(int phase) {
        Controller.phase = phase;
    }


    // Main loop
    private Plane loop(int cycle){
        final double cycleTime = 1;
        final int numberOfPassenger = 180;
        totalTime = 0;
        PrintBoardingPass checkInEmployee;

        if (phase == 1){
            checkInEmployee = new CheckInEmployee();
        }else {
            checkInEmployee = new Phase_2.CheckInEmployee();
        }

        Plane plane = new Plane();
        // Initialize the aisle with null
        for (int i = 0; i < 31; i++) {
            plane.getAisle().add(null);
        }
        // Generating fixed amount of passenger so that we can compare between Phase1 and Phase2 effectively
        // SOLO passenger generator
        Passenger[] passengers = new Passenger[numberOfPassenger];
        currentPassenger = passengers;
        String[] luggage = {"Heavy", "Normal"};
        int pCounter = 0;
        for (int i = 0; i < 170; i++) {
            String passengerName = "P" + (pCounter + 1);
            int passengerAge = randAge1[i];
            int luggageSelector = randLuggage[i];
            passengers[pCounter] = new Solo(passengerName, passengerAge, luggage[luggageSelector]);
            pCounter++;
        }
        // Special Need passenger generator
        for (int i = 0; i < 10; i++) {
            String passengerName = "SP" + (pCounter + 1);
            int passengerAge = randAge2[i];
            passengers[pCounter] = new SpecialNeed(passengerName, passengerAge, true);
            pCounter++;
        }

        checkInEmployee.printBoardingPass(passengers);
        if (phase == 2) {
            Phase_2.BoardingStrategy flightAttendant = new Phase_2.FlightAttendant();
            passengers = flightAttendant.callPassengerByZone(passengers);
        }

        int counter = 0;

        while (counter < cycle){
            for (int i = 0; i < passengers.length; i++) {
                if (!passengers[i].getLastAction().equals(Passenger.Actions.OnBoardSteps) &&
                        !passengers[i].getLastAction().equals(Passenger.Actions.Done)) {
                    passengers[i].incTimer(cycleTime);
                    if (!passengers[i].getLastAction().equals(Passenger.Actions.Hold))
                        passengers[i].setActionTimeCounter(passengers[i].getActionTimeCounter() + cycleTime);
                }
                Passenger.Actions action = passengers[i].nextAction(plane.getAisle());
                int row = passengers[i].getCurrentRow();
                if (action.equals(Passenger.Actions.Move) &&
                        plane.getAisle().get(row + 1) == null &&
                        passengers[i].getWalkTime() == passengers[i].getActionTimeCounter()) {
                    plane.getAisle().set(row + 1, passengers[i]);
                    plane.getAisle().set(row, null);
                    passengers[i].incTimer(passengers[i].getWalkTime());
                    passengers[i].incCurrentRow();
                    passengers[i].setActionTimeCounter(0);

                } else if (action.equals(Passenger.Actions.PrepareToSeat) &&
                        passengers[i].getSeatingPreparationTime() == passengers[i].getActionTimeCounter()) { // Prepare To Seat
                    // ADD the preparation time to the passenger behind the current passenger
                    passengers[i].setActionTimeCounter(0);

                } else if (action.equals(Passenger.Actions.SeatDown) &&
                        passengers[i].getSeatingTime() == passengers[i].getActionTimeCounter()) { // Seat Down
                    passengers[i].setGlobalTimer(totalTime);
                    int col = passengers[i].getBoardingPassTicket().getSeatNumber() % 6;
                    plane.getSeatsState()[row][col] = passengers[i];
                    plane.getAisle().set(row, null);
                    passengers[i].setActionTimeCounter(0);

                }
            }
            boolean allDone = Arrays.stream(passengers).allMatch(passenger -> passenger.getLastAction().equals(Passenger.Actions.Done));
            if (allDone){
                cnt = totalTime;
                break;
            }

            totalTime += cycleTime;
            counter ++;


        }

        return plane;
    }
}
