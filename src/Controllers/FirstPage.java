package Controllers;

import Database.MarkJDBCDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class FirstPage {

    @FXML
    private MainController mainController;

    @FXML
    private ListView<String> listView;

    @FXML
    void initialize() {
        MarkJDBCDAO.dbConn = new MarkJDBCDAO();
        if (!MarkJDBCDAO.dbConn.checkTables()) MarkJDBCDAO.dbConn.createTables();
        String[] parts = MarkJDBCDAO.dbConn.findAllGroups().split("\n");
        for (String part : parts) {
            if (!part.isEmpty()) addToListView(part);
        }
        listView.setOnMouseClicked((click) -> {
            if (click.getClickCount() == 2 && listView.getSelectionModel().getSelectedItem() != null) {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/showGroup.fxml"));
                Pane pane = null;
                try {
                    pane = loader.load();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                ShowGroup showGroup = loader.getController();
                showGroup.initialize(listView.getSelectionModel().getSelectedIndex() + 1);
                showGroup.setMainController(mainController);
                mainController.setScreen(pane);
            }
        });
    }

    @FXML
    public void openAddDialog() {
        Stage dialog = new Stage();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/addDialog.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        dialog.setScene(new Scene(root, 450, 150));
        dialog.setTitle("Wprowadzanie grupy...");
        dialog.setResizable(false);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
        refreshListView();
    }

    @FXML
    public void removeGroup() {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            ButtonType yesButton = new ButtonType("Tak", ButtonType.YES.getButtonData());
            ButtonType noButton = new ButtonType("Nie", ButtonType.NO.getButtonData());
            Alert removeGroupDialog = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz usunąć tę grupę?", yesButton, noButton);
            removeGroupDialog.setTitle("Usuwanie grupy");
            removeGroupDialog.setHeaderText(null);
            Optional<ButtonType> result = removeGroupDialog.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                String group = listView.getSelectionModel().getSelectedItem();
                String[] parts = group.split(" ");
                MarkJDBCDAO.dbConn.removeGroup(Integer.parseInt(parts[1].substring(0, parts[1].length() - 1)));
                refreshListView();
            }
        }
    }

    @FXML
    public void exit() {
        ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
        Alert quitDialog = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zamknąć aplikację?", okButton, cancelButton);
        quitDialog.setTitle("Zamykanie aplikacji");
        quitDialog.setHeaderText(null);
        Optional<ButtonType> result = quitDialog.showAndWait();
        if (result.isPresent() && result.get() == okButton) Platform.exit();
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void addToListView(String view) {
        listView.getItems().add(view);
    }

    public void refreshListView() {
        listView.getItems().clear();
        String[] parts = MarkJDBCDAO.dbConn.findAllGroups().split("\n");
        for (String part : parts) {
            if (!part.isEmpty()) addToListView(part);
        }
    }
}
