package Controllers;

import Database.MarkJDBCDAO;
import Database.Students;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;

public class EditStudent {

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

    private Stage stage;

    private int idStudent;

    @FXML
    void initialize(Students student, Stage stage) {
        firstName.setText(student.getFirstName());
        surname.setText(student.getSurname());
        USOSNumber.setText(String.valueOf(student.getUSOSNumber()));
        pointsI.setText(String.valueOf(student.getPointsI()));
        pointsII.setText(String.valueOf(student.getPointsII()));
        this.stage = stage;
        idStudent = MarkJDBCDAO.dbConn.getIdStudent(student);
    }

    @FXML
    public void updateStudent() {
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
        MarkJDBCDAO.dbConn.editStudent(student, idStudent);
        stage.close();
    }
}
