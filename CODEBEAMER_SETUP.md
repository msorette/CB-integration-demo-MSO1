# Configuration de l'intégration Codebeamer

Ce document explique comment configurer l'upload automatique des résultats de tests vers votre instance Codebeamer.

## Prérequis dans Codebeamer

### 1. Créer les trackers nécessaires (si pas déjà fait)

Dans votre projet Codebeamer (ID: 38), vous devez avoir :

- **Un tracker "Test Cases"** : pour stocker les cas de tests
- **Un tracker "Test Runs"** : pour stocker les résultats d'exécution des tests

### 2. Récupérer les IDs des trackers

1. Allez sur votre instance : https://pp-2510281248me.portal.ptc.io:9443/cb/project/38/
2. Naviguez vers le tracker "Test Cases" et notez l'ID dans l'URL
   - Exemple : `.../tracker/12345` → ID = `12345`
3. Faites de même pour le tracker "Test Runs"

### 3. Créer un utilisateur API ou utiliser vos credentials

Option A : Utiliser votre compte utilisateur
- Username : votre email/login Codebeamer
- Password : votre mot de passe Codebeamer

Option B : Créer un compte technique dédié (recommandé)
- Créer un utilisateur "CI/CD Bot" dans Codebeamer
- Lui donner les permissions nécessaires sur le projet 38

## Configuration des secrets GitHub

Ajoutez les secrets suivants dans votre repository GitHub :

**Settings → Secrets and variables → Actions → New repository secret**

### Secrets obligatoires :

| Nom du secret | Description | Exemple |
|---------------|-------------|---------|
| `CB_USERNAME` | Nom d'utilisateur Codebeamer | `ci-bot@company.com` |
| `CB_PASSWORD` | Mot de passe Codebeamer | `***********` |
| `CB_TEST_CASE_TRACKER_ID` | ID du tracker Test Cases | `12345` |
| `CB_TEST_RUN_TRACKER_ID` | ID du tracker Test Runs | `12346` |

### Secrets optionnels :

| Nom du secret | Description | Exemple |
|---------------|-------------|---------|
| `CB_TEST_CONFIG_ID` | ID de la configuration de test (optionnel) | `98765` |
| `CB_RELEASE_ID` | ID de la release associée (optionnel) | `54321` |

## Test de l'intégration

### 1. Commit et push des modifications

```bash
git add .
git commit -m "Add Codebeamer test results integration"
git push
```

### 2. Vérifier l'exécution du workflow

1. Allez dans l'onglet **Actions** de votre repository GitHub
2. Cliquez sur le dernier workflow exécuté
3. Vérifiez l'étape **"Upload test results to Codebeamer"**
4. Consultez les logs pour voir le résultat de l'upload

### 3. Vérifier dans Codebeamer

1. Connectez-vous à https://pp-2510281248me.portal.ptc.io:9443/cb/project/38/
2. Allez dans le tracker "Test Runs"
3. Vous devriez voir un nouveau Test Run créé avec les résultats

## Logs de débogage

Les logs détaillés de l'upload sont disponibles :
- Dans l'artefact GitHub Actions : `target/upload.log`
- Dans les logs de l'étape "Upload test results to Codebeamer"

## Structure des résultats envoyés

Le script :
1. Compile tous les fichiers `TEST-*.xml` de `target/surefire-reports/`
2. Les zippe dans `target/test_results.zip`
3. Envoie le zip avec la configuration JSON à l'API Codebeamer

Format de la configuration JSON envoyée :
```json
{
  "testConfigurationId": "",
  "testCaseTrackerId": "12345",
  "testCaseId": "",
  "releaseId": "",
  "testRunTrackerId": "12346",
  "buildIdentifier": "123",
  "defaultPackagePrefix": "com.demo"
}
```

## Dépannage

### Erreur 401 (Unauthorized)
- Vérifiez vos credentials (`CB_USERNAME` et `CB_PASSWORD`)
- Vérifiez que l'utilisateur a accès au projet 38

### Erreur 404 (Not Found)
- Vérifiez les IDs des trackers
- Vérifiez que l'endpoint `/rest/xunitresults/` existe sur votre version de Codebeamer

### Erreur 403 (Forbidden)
- Vérifiez les permissions de l'utilisateur sur les trackers Test Cases et Test Runs
- L'utilisateur doit avoir le droit de créer des Test Runs

### Aucun résultat visible dans Codebeamer
- Vérifiez les logs du script : `target/upload.log`
- Vérifiez que le zip contient bien des fichiers XML valides
- Consultez les logs Codebeamer (si vous êtes admin)

## API Codebeamer utilisée

**Endpoint** : `POST /rest/xunitresults/`

**Documentation** : Consultez la documentation de l'API REST de votre instance Codebeamer :
https://pp-2510281248me.portal.ptc.io:9443/cb/api

## Personnalisation

### Modifier le préfixe de package
Dans [ci.yml](.github/workflows/ci.yml), ligne 106 :
```yaml
PACKAGE_PREFIX: "com.demo"
```

### Ajouter un ID de release
Créez le secret `CB_RELEASE_ID` avec l'ID de votre release

### Associer à une configuration de test
Créez le secret `CB_TEST_CONFIG_ID` avec l'ID de votre configuration

## Ressources

- [Documentation API Codebeamer](https://pp-2510281248me.portal.ptc.io:9443/cb/api)
- [Script d'upload](.github/scripts/upload-to-codebeamer.sh)
- [Workflow GitHub Actions](.github/workflows/ci.yml)
