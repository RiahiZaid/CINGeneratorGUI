package ma.cingeneratorgui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox; 
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Chemin absolu pour charger la ressource FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ma/cingeneratorgui/MainView.fxml"));
            VBox root = loader.load(); 

            Scene scene = new Scene(root);
            
            primaryStage.setTitle("Générateur de CIN Marocaine - GUI Avancée");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            System.err.println("Erreur critique lors du chargement de l'interface (FXML) : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        // Cette méthode charge l'historique au démarrage
        CINGenerator.initialiserBaseDeDonnees(); 
        launch(args); 
    }
}