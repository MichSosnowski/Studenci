package Controllers;

import Database.MarkJDBCDAO;
import Database.Students;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

public class ShowGroup {

    @FXML
    private TableView<Students> tableView;

    @FXML
    private TextField maxPoints;

    @FXML
    private MainController mainController;

    private int numberSelection;

    @FXML
    void initialize(int numberSelection) {
        tableView.setPlaceholder(new Label("Brak danych"));

        TableColumn<Students, String> column = new TableColumn<>("Imię");
        column.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        column.setPrefWidth(100);
        tableView.getColumns().add(column);

        column = new TableColumn<>("Nazwisko");
        column.setCellValueFactory(new PropertyValueFactory<>("surname"));
        column.setPrefWidth(100);
        tableView.getColumns().add(column);

        column = new TableColumn<>("Numer USOS");
        column.setCellValueFactory(new PropertyValueFactory<>("USOSNumber"));
        column.setPrefWidth(90);
        tableView.getColumns().add(column);

        column = new TableColumn<>("Punkty I");
        column.setCellValueFactory(new PropertyValueFactory<>("pointsI"));
        column.setPrefWidth(86);
        tableView.getColumns().add(column);

        column = new TableColumn<>("Punkty II");
        column.setCellValueFactory(new PropertyValueFactory<>("pointsII"));
        column.setPrefWidth(86);
        tableView.getColumns().add(column);

        column = new TableColumn<>("Suma");
        column.setCellValueFactory(new PropertyValueFactory<>("sum"));
        column.setPrefWidth(85);
        tableView.getColumns().add(column);

        column = new TableColumn<>("Ocena");
        column.setCellValueFactory(new PropertyValueFactory<>("mark"));
        column.setPrefWidth(85);
        tableView.getColumns().add(column);

        this.numberSelection = numberSelection;
        updateTableView();
    }

    private void updateTableView() {
        tableView.getItems().clear();
        List<Students> students = MarkJDBCDAO.dbConn.findAllStudents(numberSelection);
        for (Students student : students) tableView.getItems().add(student);
    }

    @FXML
    public void addStudent() {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/addStudent.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        AddStudent addStudent = loader.getController();
        addStudent.initialize(numberSelection);
        dialog.setScene(new Scene(root, 415, 250));
        dialog.setTitle("Dodawanie studenta...");
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
        updateTableView();
    }

    @FXML
    public void editStudent() {

    }

    @FXML
    public void removeStudent() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            MarkJDBCDAO.dbConn.removeStudent(tableView.getSelectionModel().getSelectedItem());
            updateTableView();
        }
    }

    @FXML
    public void giveMark() {
        int MaxPoints = 0;
        try {
            MaxPoints = Integer.parseInt(maxPoints.getText());
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText(null);
            alert.setContentText("Niepoprawne dane albo niewypełnione pole.");
            alert.show();
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        ObservableList<Students> list = tableView.getItems();
        Iterator<Students> iter = list.iterator();
        while (iter.hasNext()) {
            Students student = iter.next();
            int idStudent = MarkJDBCDAO.dbConn.getIdStudent(student);
            double procent = student.getSum() / (double) MaxPoints * 100;
            if (procent >= 50 && procent < 60) MarkJDBCDAO.dbConn.giveMark(idStudent, "3");
            else if (procent >= 60 && procent < 70) MarkJDBCDAO.dbConn.giveMark(idStudent, "3.5");
            else if (procent >= 70 && procent < 80) MarkJDBCDAO.dbConn.giveMark(idStudent, "4");
            else if (procent >= 80 && procent < 90) MarkJDBCDAO.dbConn.giveMark(idStudent, "4.5");
            else if (procent >= 90) MarkJDBCDAO.dbConn.giveMark(idStudent, "5");
        }
        updateTableView();
    }

    @FXML
    public void giveTwo() {
        int MaxPoints = 0;
        try {
            MaxPoints = Integer.parseInt(maxPoints.getText());
        }
        catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Niepoprawne dane");
            alert.setHeaderText(null);
            alert.setContentText("Niepoprawne dane albo niewypełnione pole.");
            alert.show();
            Toolkit.getDefaultToolkit().beep();
            return;
        }
        ObservableList<Students> list = tableView.getItems();
        Iterator<Students> iter = list.iterator();
        while (iter.hasNext()) {
            Students student = iter.next();
            int idStudent = MarkJDBCDAO.dbConn.getIdStudent(student);
            double procent = student.getSum() / (double) MaxPoints * 100;
            if (procent < 50) MarkJDBCDAO.dbConn.giveMark(idStudent, "2");
        }
        updateTableView();
    }

    @FXML
    public void goBack() {
        mainController.initialize();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
}
