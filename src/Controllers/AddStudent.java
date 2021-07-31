package Controllers;

import Database.MarkJDBCDAO;
import Database.Students;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.Toolkit;

public class AddStudent {

    @FXML
    private TextField firstName;

    @FXML
    private TextField surname;

    @FXML
    private TextField USOSNumber;

    @FXML
    private TextField pointsI;

    @FXML
    private TextField pointsII;

    @FXML
    private Button addButton;

    private int numberSelection;

    @FXML
    void initialize(int numberSelection) {
        this.numberSelection = numberSelection;
    }

    @FXML
    public void addStudent() {
        String firstNameString = firstName.getText();
        String surnameString = surname.getText();
        int USOSNumberInt = 0, pointsIInt = 0, pointsIIInt = 0;
        try {
            if (firstNameString.isEmpty() || surnameString.isEmpty()) throw new Exception();
            USOSNumberInt = Integer.parseInt(USOSNumber.getText());
            pointsIInt = Integer.parseInt(pointsI.getText());
            pointsIIInt = Integer.parseInt(pointsII.getText());
            if (USOSNumberInt <= 0 || pointsIInt <= 0 || pointsIIInt <= 0) throw new NumberFormatException();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepoprawny format");
            alert.setHeaderText(null);
            alert.setContentText("Niepoprawny format liczbowy bądź podana liczba nie jest dodatnia.");
            alert.show();
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Puste pola");
            alert.setHeaderText(null);
            alert.setContentText("Żadne pole nie może pozostać puste.");
            alert.show();
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        Students student = new Students(firstNameString, surnameString, USOSNumberInt, pointsIInt, pointsIIInt, pointsIInt + pointsIIInt, "0");
        MarkJDBCDAO.dbConn.addStudent(student, numberSelection);
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
