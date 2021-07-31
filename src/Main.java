import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("FXML/main.fxml"));
        primaryStage.setTitle("StudentMax");
        primaryStage.setScene(new Scene(root, 650, 600));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest((e) -> {
            ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
            ButtonType cancelButton = new ButtonType("Anuluj", ButtonType.CANCEL.getButtonData());
            Alert quitDialog = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz zamknąć aplikację?", okButton, cancelButton);
            quitDialog.setTitle("Zamykanie aplikacji");
            quitDialog.setHeaderText(null);
            Optional<ButtonType> result = quitDialog.showAndWait();
            if (result.isPresent() && result.get() == okButton) Platform.exit();
            e.consume();
        });
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
