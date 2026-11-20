package dev.reveles.main;

import dev.reveles.controller.CardController;
import dev.reveles.view.MainWindow;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage)
    {
        CardController controller = new CardController();

        controller.loadCardsFromCsv("src/main/resources/dev/reveles/cartas_yugioh_completo.csv");

        MainWindow window = new MainWindow(controller);

        Scene scene = new Scene(window.getRoot(), 950, 600);

        stage.setTitle("Yu-Gi-Oh!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}