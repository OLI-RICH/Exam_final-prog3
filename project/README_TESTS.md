# 🚀 Guide Rapide de Test de l'API

## 📌 Prérequis
- Java 24+
- Maven 3.8+
- PostgreSQL (ou base de données configurée)
- Postman (optionnel mais recommandé)
- curl ou Insomnia (optionnel)

---

## ⚡ Démarrage Rapide (5 minutes)

### 1️⃣ Démarrer l'application
```bash
# Depuis le répertoire racine du projet
mvn spring-boot:run

# L'API sera accessible à: http://localhost:8080/api
```

### 2️⃣ Importer la collection Postman
**Fichier à importer:** `Collectivity_API_Tests.postman_collection.json`

**Étapes:**
1. Ouvrir Postman
2. Cliquer sur "Import" en haut à gauche
3. Sélectionner le fichier `Collectivity_API_Tests.postman_collection.json`
4. Les variables d'environnement seront automatiquement configurées
5. Commencer par tester les requêtes dans l'ordre

### 3️⃣ Ou tester rapidement via terminal

#### Sur Windows (PowerShell):
```powershell
# Lancer le script de test
.\test_api.bat

# Ou des commandes individuelles
$body = @{
    name = "Collectivité Test"
    description = "Test"
} | ConvertTo-Json

Invoke-WebRequest -Uri "http://localhost:8080/api/collectivities" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body $body
```

#### Sur Linux/Mac:
```bash
# Rendre le script exécutable
chmod +x test_api.sh

# Lancer les tests
./test_api.sh
```

#### Avec curl (tous les OS):
```bash
# Récupérer les comptes financiers (NEW)
curl -X GET "http://localhost:8080/api/collectivities/1/financialAccounts"

# Avec paramètre de date
curl -X GET "http://localhost:8080/api/collectivities/1/financialAccounts?at=2024-04-15"

# Récupérer une collectivité avec ses membres (NEW)
curl -X GET "http://localhost:8080/api/collectivities/1"
```

---

## 🧪 Stratégies de Test Complètes

### Option A: Tests Automatisés (Recommandé pour l'évaluation)

```bash
# Lancer TOUS les tests unitaires et d'intégration
mvn clean test

# Lancer un test spécifique
mvn test -Dtest=CollectivityControllerIntegrationTest#testGetFinancialAccountsWithoutDate

# Lancer avec verbosité
mvn clean test -X
```

**Fichiers de test:**
- `src/test/java/com/exam/project/controller/CollectivityControllerIntegrationTest.java`

### Option B: Tests Manuels avec Postman

**Collection importée:** `Collectivity_API_Tests.postman_collection.json`

**Ordre recommandé:**
1. ✅ **Créer collectivités** (POST)
2. ✅ **Récupérer collectivité** (GET /collectivities/{id}) - **NEW**
3. ✅ **Attribuer informations**  (PUT)
4. ✅ **Créer cotisations** (POST)
5. ✅ **Récupérer cotisations** (GET)
6. ✅ **Créer membres** (POST)
7. ✅ **Récupérer comptes financiers** (GET /financialAccounts) - **NEW**
8. ✅ **Récupérer comptes financiers avec date** (GET /financialAccounts?at=DATE) - **NEW**

### Option C: Tests avec un Outil de Debugging HTTP

```bash
# Avec Insomnia, HTTPie, ou Thunder Client:
# 1. Ouvrir l'outil
# 2. Créer une nouvelle requête
# 3. URL: http://localhost:8080/api/collectivities/1/financialAccounts
# 4. Méthode: GET
# 5. Ajouter paramètre: at=2024-04-15
# 6. Envoyer
```

---

## 📊 Endpoints à Tester Prioritairement

### NEW Endpoints (À ajouter/vérifier)

#### 1. GET /collectivities/{id}/financialAccounts
**Description:** Récupère les comptes financiers avec soldes

**Sans date (soldes actuels):**
```bash
GET http://localhost:8080/api/collectivities/1/financialAccounts

Réponse (200):
[
  {
    "id": "ACC-001",
    "type": "CASH",
    "balance": 50000.0,
    "lastUpdated": "2024-04-23T10:30:00"
  },
  {
    "id": "ACC-002",
    "type": "BANK",
    "balance": 100000.0,
    "lastUpdated": "2024-04-23T10:30:00"
  },
  {
    "id": "ACC-003",
    "type": "MOBILE_MONEY",
    "balance": 25000.0,
    "lastUpdated": "2024-04-23T10:30:00"
  }
]
```

**Avec date (soldes historiques):**
```bash
GET http://localhost:8080/api/collectivities/1/financialAccounts?at=2024-04-15

Réponse (200):
[
  {
    "id": "ACC-001",
    "type": "CASH",
    "balance": 30000.0,  # Solde différent à cette date
    "lastUpdated": "2024-04-15T00:00:00"
  }
]
```

#### 2. GET /collectivities/{id}
**Description:** Récupère les détails d'une collectivité avec la liste de ses membres

```bash
GET http://localhost:8080/api/collectivities/1

Réponse (200):
{
  "id": "1",
  "name": "Collectivité Alpha",
  "number": "COL-2024-001",
  "description": "Description",
  "region": "Région A",
  "createdAt": "2024-04-20T10:00:00",
  "members": [
    {
      "id": "MEM-001",
      "firstName": "Jean",
      "lastName": "Dupont",
      "phoneNumber": "+223 75 12 34 56",
      "email": "jean@example.com"
    },
    {
      "id": "MEM-002",
      "firstName": "Marie",
      "lastName": "Martin",
      "phoneNumber": "+223 76 98 76 54",
      "email": "marie@example.com"
    }
  ]
}
```

---

## ✅ Checklist de Validation

### Endpoints Existants
- [ ] `POST /collectivities` - Crée collectivités (201)
- [ ] `PUT /collectivities/{id}/informations` - Attribue info (200)
- [ ] `POST /collectivities/{id}/membershipFees` - Crée cotisations (201)
- [ ] `GET /collectivities/{id}/membershipFees` - Récupère cotisations (200)
- [ ] `GET /collectivities/{id}/transactions` - Récupère transactions (200)
- [ ] `POST /members` - Crée membres (201)
- [ ] `POST /members/{id}/payments` - Enregistre paiements (201)

### NEW Endpoints
- [ ] `GET /collectivities/{id}/financialAccounts` - Récupère comptes (200)
- [ ] `GET /collectivities/{id}/financialAccounts?at=DATE` - Avec date (200)
- [ ] `GET /collectivities/{id}` - Récupère collectivité (200)
- [ ] `GET /collectivities/{id}` - Contient `members` array (200)

### Gestion d'Erreurs
- [ ] ID inexistant → 404
- [ ] Données invalides → 400
- [ ] Date format invalide → 400
- [ ] Pas de paramètres optionnels → Fonctionne (par défaut)

---

## 🔧 Commandes Utiles Maven

```bash
# Nettoyer et compiler
mvn clean compile

# Compiler + Tests unitaires + Tests d'intégration
mvn clean test

# Compiler + Tests + Package JAR
mvn clean package

# Lancer l'application directement
mvn spring-boot:run

# Vérifier les dépendances
mvn dependency:tree

# Générer la documentation
mvn javadoc:javadoc

# Vérifier la qualité du code (si sonar configuré)
mvn sonar:sonar
```

---

## 📚 Fichiers de Documentation

| Fichier | Description |
|---------|-------------|
| `GUIDE_TEST_API.md` | Guide détaillé avec exemples complets |
| `Collectivity_API_Tests.postman_collection.json` | Collection Postman prête à l'emploi |
| `src/test/java/com/exam/project/controller/CollectivityControllerIntegrationTest.java` | Tests automatisés |
| `test_api.sh` | Script de test (Linux/Mac) |
| `test_api.bat` | Script de test (Windows) |

---

## 💡 Conseils pour l'Évaluation

1. **Démarrer l'app:** `mvn spring-boot:run`
2. **Importer Postman:** Utiliser le fichier `.postman_collection.json`
3. **Lancer les tests:** `mvn clean test`
4. **Documenter:** Chaque test dans Postman montre le comportement attendu
5. **Vérifier la couverture:** Les 2 nouveaux endpoints doivent retourner les bons codes HTTP

---

## ❓ Dépannage

### L'app ne démarre pas
```bash
# Vérifier que PostgreSQL est en cours d'exécution
# Vérifier les logs
mvn spring-boot:run -X
```

### Tests échouent
```bash
# Nettoyer et relancer
mvn clean test -DskipTests=false -X

# Vérifier la configuration de la BD
cat src/main/resources/application.yaml
```

### Postman montre 404
```bash
# Vérifier que l'app est bien en cours d'exécution
curl http://localhost:8080/api/collectivities

# Vérifier l'URL de base dans Postman (Variables)
# Doit être: http://localhost:8080/api
```

---

**Bon test! 🎯**
