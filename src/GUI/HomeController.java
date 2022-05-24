package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private Button phase1Button;

    @FXML
    private Button phase2Button;
    private Stage stage;
    private Scene scene;

    public void phase1Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Controller.setPhase(1);
        stage = (Stage) phase1Button.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Boarding Time Phase 1");
        stage.show();
    }

    public void phase2Handler(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Controller.setPhase(2);
        stage = (Stage) phase2Button.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Boarding Time Phase 2");
        stage.show();
    }

}
