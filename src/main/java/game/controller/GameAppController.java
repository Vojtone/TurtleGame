package game.controller;

import game.Main;
import game.model.generator.DataGenerator;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameAppController {

    private Stage primaryStage;
    private GameOverviewController ovc;
    public static int lvl = 1;
    public static boolean boardExists = false;

    public GameAppController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void initRootLayout() {
        try {
            this.primaryStage.setTitle("Turtle Game");
            // load layout from FXML file
            FXMLLoader loader = new FXMLLoader();
            //System.err.println("FXML resource: " + Main.class.getResource("/game.fxml"));
            loader.setLocation(Main.class.getResource("/fxml/game.fxml"));
            BorderPane rootLayout = (BorderPane) loader.load();

            // set initial data into controller
            GameOverviewController controller = loader.getController();
            controller.setAppController(this);
            controller.setData(DataGenerator.generateGameData(GameAppController.lvl, null));

            ovc = controller;
            // add layout to a scene and show them all
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setMaximized(true);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }

    }

    public boolean showLoopDialog() {
        try {
            // Load the fxml file and create a new stage for the dialog
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/fxml/loop.fxml"));
            BorderPane page = (BorderPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create loop");
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            LoopController loopc = loader.getController();
            loopc.setData(ovc,ovc.getBoardData());
            loopc.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}