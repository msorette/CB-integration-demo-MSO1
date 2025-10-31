# Codebeamer Integration Demo - MSO1

Projet de démonstration pour l'intégration de Codebeamer avec GitHub et CI/CD.

## Description

Ce projet contient une classe Java simple (`Calculator`) avec des tests unitaires, conçu pour démontrer :
- L'intégration GitHub/Codebeamer
- Un pipeline CI/CD automatisé
- La génération de rapports de tests compatibles Codebeamer

## Fonctionnalités

La classe `Calculator` propose trois méthodes :

1. **sayHello()** - Affiche "Hello world"
2. **add(int a, int b)** - Additionne deux nombres
3. **divide(double a, double b)** - Divise deux nombres (avec gestion division par zéro)

## Structure du projet

```
CB-integration-demo-MSO1/
├── .github/
│   └── workflows/
│       └── ci.yml                    # Pipeline CI/CD GitHub Actions
├── src/
│   ├── main/
│   │   └── java/
│   │       └── com/
│   │           └── demo/
│   │               └── Calculator.java    # Classe principale
│   └── test/
│       └── java/
│           └── com/
│               └── demo/
│                   └── CalculatorTest.java # Tests unitaires (4 tests)
├── pom.xml                           # Configuration Maven
├── .gitignore                        # Fichiers ignorés par Git
└── README.md                         # Ce fichier
```

## Prérequis

- **Java 17** ou supérieur
- **Maven 3.6+**
- **Git**

## Installation et utilisation

### Cloner le projet

```bash
git clone git@github.com:msorette/CB-integration-demo-MSO1.git
cd CB-integration-demo-MSO1
```

### Compiler le projet

```bash
mvn clean compile
```

### Exécuter les tests

```bash
mvn test
```

### Générer les rapports de tests

```bash
mvn surefire-report:report
```

Les rapports sont générés dans :
- **Format JUnit XML** : `target/surefire-reports/TEST-*.xml`
- **Format HTML** : `target/site/surefire-report.html`

### Construire le JAR

```bash
mvn package
```

Le fichier JAR sera disponible dans `target/codebeamer-integration-demo-1.0.0.jar`

## Tests unitaires

Le projet contient 4 tests unitaires dans [CalculatorTest.java](src/test/java/com/demo/CalculatorTest.java) :

1. **testSayHello** - Vérifie que la méthode retourne "Hello world"
2. **testAdd** - Vérifie l'addition de plusieurs paires de nombres
3. **testDivide** - Vérifie la division de nombres
4. **testDivideByZero** - Vérifie que la division par zéro lève une `ArithmeticException`

## Pipeline CI/CD

Le workflow GitHub Actions ([.github/workflows/ci.yml](.github/workflows/ci.yml)) s'exécute automatiquement sur :
- Les push vers les branches `main` et `develop`
- Les pull requests vers `main`

### Étapes du pipeline

1. **Checkout** - Récupération du code source
2. **Setup Java 17** - Configuration de l'environnement Java
3. **Compilation** - Compilation du code avec Maven
4. **Tests** - Exécution des tests unitaires
5. **Rapports** - Génération des rapports HTML et XML
6. **Artifacts** - Upload des rapports et JAR comme artifacts

### Artifacts générés

Après chaque exécution du pipeline, les artifacts suivants sont disponibles :

- **junit-test-results** - Fichiers XML JUnit (compatibles Codebeamer)
- **html-test-reports** - Rapports HTML lisibles
- **surefire-reports-complete** - Rapports Surefire complets
- **application-jar** - Le fichier JAR compilé

## Intégration avec Codebeamer

### Récupération des rapports depuis GitHub Actions

1. Allez dans l'onglet **Actions** du repository GitHub
2. Sélectionnez le workflow exécuté
3. Téléchargez l'artifact **junit-test-results**
4. Décompressez le fichier ZIP pour obtenir les fichiers XML

### Import dans Codebeamer

Les fichiers XML JUnit peuvent être importés dans Codebeamer via :

#### Option 1 : Interface Web
1. Connectez-vous à Codebeamer
2. Accédez au projet concerné
3. Utilisez la fonctionnalité d'import de résultats de tests
4. Sélectionnez les fichiers `TEST-*.xml`

#### Option 2 : API REST
Utilisez l'API Codebeamer pour automatiser l'import :

```bash
curl -X POST "https://your-codebeamer-instance/rest/v3/projects/{projectId}/test-runs" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -F "file=@TEST-com.demo.CalculatorTest.xml"
```

## Technologies utilisées

- **Java 17** - Langage de programmation
- **Maven 3.11** - Outil de build
- **JUnit 5.10.1** - Framework de tests
- **GitHub Actions** - Pipeline CI/CD
- **Maven Surefire** - Génération de rapports de tests

## Auteur

Projet créé pour la démonstration d'intégration Codebeamer - MSO1

## Licence

Ce projet est à usage de démonstration uniquement.
