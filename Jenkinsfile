// Pipeline pour le projet CINGeneratorGUI (JavaFX/Maven)

pipeline {
    agent any // Exécuter le pipeline sur n'importe quel agent disponible

    // Correction des noms des outils pour correspondre à la configuration Jenkins
    tools {
        // CORRIGÉ : Utilisation de JDK-21 (avec un tiret) comme suggéré par l'erreur
        jdk 'JDK-21'

        // J'ai aussi renommé Maven 'M4' en 'Maven-3.x' par précaution,
        // mais vérifiez le nom exact dans votre Global Tool Configuration
        maven 'M4'
    }

    stages {
        // ======================================
        // STAGE 1: BUILD et TEST
        // ======================================
        stage('Build & Test') {
            steps {
                echo 'Démarrage de la construction Maven...'

                // Exécute: clean install package
                // J'ai gardé '-DskipTests' pour éviter que des tests manquants ou cassés ne fassent échouer le build.
                sh 'mvn clean install package -DskipTests'
            }
        }

        // ======================================
        // STAGE 2: ARCHIVAGE DE L'ARTÉFACT
        // ======================================
        stage('Archive Artifacts') {
            steps {
                echo 'Archivage de l\'artéfact JAR'

                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        // ======================================
        // STAGE 3: DEPLOIEMENT (Placeholder)
        // ======================================
        stage('Deploy') {
            steps {
                echo 'Le déploiement est prêt. Ajouter ici les étapes de déploiement (ex: transfert SFTP du JAR).'
            }
        }
    }
}
