package Controllers;

import Database.Groups;
import Database.MarkJDBCDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.Toolkit;

public class AddDialog {

    @FXML
    private TextField classTerm;

    @FXML
    private Button addButton;

    @FXML
    public void addGroup() {
        String term = classTerm.getText();
        try {
            if (term.isEmpty()) throw new Exception();
        }
        catch (Exception e) {
            Alert errorAdd = new Alert(Alert.AlertType.ERROR);
            errorAdd.setTitle("Błąd dodawania grupy");
            errorAdd.setHeaderText(null);
            errorAdd.setContentText("Pole nie może pozostać puste.");
            errorAdd.show();
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        Groups group = new Groups(term);
        MarkJDBCDAO.dbConn.addGroup(group);
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
    }
}
