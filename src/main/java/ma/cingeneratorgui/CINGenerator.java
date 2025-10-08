package ma.cingeneratorgui;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CINGenerator {

    // Liste complète des villes (pour les ComboBox)
    private static final String[] VILLES = {
        "RABAT", "CASABLANCA", "MARRAKECH", "TANGER", "FES", "AGADIR", 
        "MEKNES", "OUJDA", "KENITRA", "TETOUAN", "SAFI", "SALE", "MOHAMMEDIA", 
        "KHOURIBGA", "BENI MELLAL", "ERRACHIDIA", "EL JADIDA", "LAAYOUNE", 
        "DOUCHAMBA", "NAZEM" 
    };
    
    // Nom du fichier de persistance
    private static final String FICHIER_HISTORIQUE = "cin_history.txt";

    // Base de données en mémoire (List), synchronisée avec le fichier
    private static List<String> database = new ArrayList<>();

    // -------------------------------------------------------------------
    // M1: Initialisation (Charge les données existantes du fichier)
    // -------------------------------------------------------------------
    public static void initialiserBaseDeDonnees() {
        System.out.println("INFO: Tentative de chargement de l'historique...");
        chargerHistorique();
        System.out.println("INFO: Base de données initialisée avec " + database.size() + " enregistrements.");
    }
    
    /**
     * Tente de charger les enregistrements depuis le fichier texte.
     */
    private static void chargerHistorique() {
        File file = new File(FICHIER_HISTORIQUE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        database.add(line);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur lors du chargement de l'historique : " + e.getMessage());
            }
        }
        // Ajout de données d'exemple SEULEMENT si la BDD est vide
        if (database.isEmpty()) {
            database.add("CIN: XX11111 | Nom: EL HAKIMI | Prénom: NADIA | Ville: RABAT");
            database.add("CIN: YY22222 | Nom: CHERKAOUI | Prénom: YOUSSEF | Ville: CASABLANCA");
            sauvegarderHistorique(); // Sauvegarde des exemples
        }
    }
    
    /**
     * Sauvegarde tous les enregistrements dans le fichier texte.
     */
    private static void sauvegarderHistorique() {
        // Sauvegarde tous les enregistrements de la liste database dans le fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FICHIER_HISTORIQUE))) {
            for (String record : database) {
                writer.write(record);
                writer.newLine(); // Chaque enregistrement sur une nouvelle ligne
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de l'historique : " + e.getMessage());
        }
    }

    // -------------------------------------------------------------------
    // M2: Fournit la liste des villes
    // -------------------------------------------------------------------
    public static String[] getVillesDisponibles() {
        return VILLES;
    }

    // -------------------------------------------------------------------
    // M3: Logique de Génération (Ajout + Sauvegarde)
    // -------------------------------------------------------------------
    public static String genererCINSimule(String ville, char genre, String prenom, String nom, String dateNaissance) throws Exception {
        
        if (dateNaissance.length() != 10 || !dateNaissance.contains("/")) {
             throw new IllegalArgumentException("Le format de la date est invalide (JJ/MM/AAAA attendu).");
        }
        
        String cinNumber = String.format("%s%05d", prenom.charAt(0), (int)(Math.random() * 100000));
        String result = String.format("CIN: %s | Nom: %s | Prénom: %s | Ville: %s", cinNumber, nom, prenom, ville);

        // Enregistrement dans la liste en mémoire
        database.add(result);
        
        // **PERMANENCE : Sauvegarde immédiate sur le disque**
        sauvegarderHistorique(); 
        
        return result;
    }

    // -------------------------------------------------------------------
    // M4 & M5: Affichage et Recherche (Utilisent la liste 'database' chargée)
    // -------------------------------------------------------------------
    public static String getHistoriqueParVille(String ville) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        sb.append(String.format("--- Historique pour %s ---\n", ville));
        
        for (String record : database) {
            if (record.contains("Ville: " + ville)) {
                sb.append(record).append("\n");
                count++;
            }
        }

        if (count == 0) {
            return String.format("Aucun enregistrement trouvé pour la ville de %s.", ville);
        }
        return sb.toString();
    }

    public static String rechercherParNomOuPrenom(String terme) {
        if (terme == null || terme.trim().isEmpty()) {
            return "Veuillez entrer un terme de recherche valide.";
        }
        
        String upperTerme = terme.trim().toUpperCase();
        StringBuilder sb = new StringBuilder();
        int count = 0;

        sb.append(String.format("--- Résultats pour le terme '%s' ---\n", upperTerme));
        
        for (String record : database) {
            if (record.contains("Nom: " + upperTerme) || record.contains("Prénom: " + upperTerme)) {
                sb.append(record).append("\n");
                count++;
            }
        }
        
        if (count == 0) {
            return String.format("Aucune correspondance trouvée pour le terme '%s'.", upperTerme);
        }
        return sb.toString();
    }
}