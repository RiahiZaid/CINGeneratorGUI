// Pipeline pour le projet CINGeneratorGUI (JavaFX/Maven)

pipeline {
    agent any // Exécuter le pipeline sur n'importe quel agent disponible

    // Définition des outils Maven et JDK pour le pipeline
    // Ces noms (JDK_21 et M4) doivent correspondre aux noms configurés
    // dans 'Manage Jenkins' -> 'Global Tool Configuration'.
    tools {
        // Remplacez 'JDK_21' par le nom exact que vous avez donné à votre configuration JDK
        jdk 'JDK_21'
        // Remplacez 'M4' par le nom exact que vous avez donné à votre configuration Maven
        maven 'M4'
    }

    stages {
        // ======================================
        // STAGE 1: CLONAGE (Automatique)
        // ======================================

        // ======================================
        // STAGE 2: BUILD et TEST
        // ======================================
        stage('Build & Test') {
            steps {
                echo 'Démarrage de la construction Maven...'

                // Exécute: clean install package
                // 'install' inclut la compilation et le lancement des tests unitaires
                sh 'mvn clean install package -DskipTests'

                // Remplacez -DskipTests par 'sh 'mvn clean install package'
                // si vous avez des tests unitaires à exécuter.
            }
        }

        // ======================================
        // STAGE 3: ARCHIVAGE DE L'ARTÉFACT
        // ======================================
        stage('Archive Artifacts') {
            steps {
                echo 'Archivage de l\'artéfact JAR'

                // Archive le fichier JAR créé par l'étape 'package'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        // ======================================
        // STAGE 4: DEPLOIEMENT (Placeholder)
        // ======================================
        stage('Deploy') {
            steps {
                echo 'Le déploiement est prêt. Ajouter ici les étapes de déploiement (ex: transfert SFTP du JAR).'
            }
        }
    }
}
