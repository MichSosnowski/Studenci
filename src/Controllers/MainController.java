package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MainController {

    @FXML
    private StackPane stackPane;

    @FXML
    void initialize() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/firstPage.fxml"));
        Pane pane = null;
        try {
            pane = loader.load();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        FirstPage firstPageController = loader.getController();
        firstPageController.setMainController(this);
        setScreen(pane);
    }

    void setScreen(Pane pane) {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(pane);
    }
}
