package ma.cingeneratorgui;

import javafx.fxml.FXML;
import javafx.scene.control.*; 
import java.util.Arrays;
import java.util.List;

public class Controller {

    // CHAMPS DE L'ONGLET 1: GÉNÉRATION
    @FXML private TextField prenomField;
    @FXML private TextField nomField;
    @FXML private TextField dateNaissanceField;
    @FXML private ComboBox<String> genreComboBox;
    @FXML private ComboBox<String> villeGenComboBox;
    @FXML private TextArea resultatAreaGen;
    @FXML private Label messageErreurLabelGen;

    // CHAMPS DE L'ONGLET 2: HISTORIQUE
    @FXML private ComboBox<String> villeAffichComboBox;
    @FXML private TextArea resultatAreaAffich;

    // CHAMPS DE L'ONGLET 3: RECHERCHE
    @FXML private TextField termeRechercheField;
    @FXML private TextArea resultatAreaRech;
    
    // MÉTHODE D'INITIALISATION
    @FXML
    public void initialize() {
        String[] villes = CINGenerator.getVillesDisponibles();
        
        genreComboBox.getItems().addAll("H", "F");
        List<String> listeVilles = Arrays.asList(villes);
        
        villeGenComboBox.getItems().addAll(listeVilles);
        villeAffichComboBox.getItems().addAll(listeVilles);
    }

    // GESTION DU BOUTON GÉNÉRER
    @FXML
    private void handleGeneration() {
        resultatAreaGen.clear();
        messageErreurLabelGen.setText("");

        // Récupération des données entrées par l'utilisateur
        String prenom = prenomField.getText().trim().toUpperCase();
        String nom = nomField.getText().trim().toUpperCase();
        String dateNaissance = dateNaissanceField.getText().trim();
        String genre = genreComboBox.getValue();
        String ville = villeGenComboBox.getValue();

        if (prenom.isEmpty() || nom.isEmpty() || dateNaissance.isEmpty() || genre == null || ville == null) {
            messageErreurLabelGen.setText("❌ Veuillez remplir tous les champs !");
            return;
        }

        try {
            // L'appel à cette méthode sauvegarde l'historique automatiquement
            String cinGenere = CINGenerator.genererCINSimule(
                ville, 
                genre.charAt(0), 
                prenom, 
                nom,    
                dateNaissance 
            );
            
            resultatAreaGen.setText("Génération Réussie : " + cinGenere);
            messageErreurLabelGen.setText("✅ CIN générée et enregistrée (Persistée sur disque).");

        } catch (Exception e) {
            messageErreurLabelGen.setText("❌ Erreur de génération : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // GESTION DU BOUTON AFFICHAGE HISTORIQUE
    @FXML
    private void handleAfficherHistorique() {
        resultatAreaAffich.clear();
        String ville = villeAffichComboBox.getValue();
        
        if (ville == null) {
            resultatAreaAffich.setText("Veuillez choisir une ville.");
            return;
        }
        
        // Récupère les données depuis la liste en mémoire, qui a été chargée depuis le fichier
        String historique = CINGenerator.getHistoriqueParVille(ville);
        resultatAreaAffich.setText(historique);
    }

    // GESTION DU BOUTON RECHERCHE
    @FXML
    private void handleRecherche() {
        resultatAreaRech.clear();
        String terme = termeRechercheField.getText().trim().toUpperCase();

        if (terme.isEmpty()) {
            resultatAreaRech.setText("Veuillez entrer un terme de recherche.");
            return;
        }
        
        String resultats = CINGenerator.rechercherParNomOuPrenom(terme);
        resultatAreaRech.setText(resultats);
    }
}