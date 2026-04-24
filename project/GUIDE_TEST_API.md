# Guide de Test des Endpoints API

## 📋 Résumé des Endpoints

### Collectivités
- `POST /api/collectivities` - Créer une liste de collectivités
- `PUT /api/collectivities/{id}/informations` - Attribuer numéro et nom unique
- `POST /api/collectivities/{id}/membershipFees` - Créer cotisations
- `GET /api/collectivities/{id}/membershipFees` - Récupérer cotisations
- `GET /api/collectivities/{id}/transactions` - Récupérer transactions (avec période)
- **NEW**: `GET /api/collectivities/{id}/financialAccounts` - Récupérer comptes avec soldes
- **NEW**: `GET /api/collectivities/{id}` - Récupérer détails collectivité avec membres

### Membres
- `POST /api/members` - Admettre nouveaux membres
- `POST /api/members/{id}/payments` - Enregistrer paiements

---

## 🚀 Méthodes de Test

### Option 1 : Tests avec cURL (Console)

#### 1.1 Créer une collectivité
```bash
curl -X POST http://localhost:8080/api/collectivities \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Collectivité A",
    "description": "Une collectivité test"
  }'
```

#### 1.2 Récupérer les comptes financiers avec soldes (NEW)
```bash
# Sans date (date actuelle par défaut)
curl -X GET http://localhost:8080/api/collectivities/1/financialAccounts

# Avec une date spécifique
curl -X GET "http://localhost:8080/api/collectivities/1/financialAccounts?at=2024-01-15"
```

#### 1.3 Récupérer détails collectivité avec membres (NEW)
```bash
curl -X GET http://localhost:8080/api/collectivities/1
```

#### 1.4 Créer membres
```bash
curl -X POST http://localhost:8080/api/members \
  -H "Content-Type: application/json" \
  -d '[
    {
      "firstName": "Jean",
      "lastName": "Dupont",
      "phoneNumber": "1234567890",
      "email": "jean@example.com"
    },
    {
      "firstName": "Marie",
      "lastName": "Martin",
      "phoneNumber": "0987654321",
      "email": "marie@example.com"
    }
  ]'
```

#### 1.5 Enregistrer paiement membre
```bash
curl -X POST http://localhost:8080/api/members/1/payments \
  -H "Content-Type: application/json" \
  -d '[
    {
      "amount": 5000,
      "date": "2024-04-20",
      "method": "CASH"
    }
  ]'
```

---

### Option 2 : Tests avec Postman (Recommandé pour une évaluation)

#### Étapes :
1. **Installer Postman** : https://www.postman.com/downloads/
2. **Créer une collection** nommée "Collectivity API"
3. **Ajouter les requêtes suivantes** :

#### 2.1 Variables d'environnement Postman
```
base_url: http://localhost:8080/api
collectivity_id: 1
member_id: 1
```

#### 2.2 Requête : GET Collectivités
```
GET {{base_url}}/collectivities/{{collectivity_id}}

Params:
- (Optionnel) members: true
```

#### 2.3 Requête : GET Comptes Financiers
```
GET {{base_url}}/collectivities/{{collectivity_id}}/financialAccounts

Params:
- at: 2024-04-20  (optionnel)

Expected Response:
[
  {
    "id": "...",
    "type": "CASH|BANK|MOBILE_MONEY",
    "balance": 50000,
    "lastUpdated": "2024-04-20T10:30:00"
  }
]
```

---

### Option 3 : Tests Automatisés en Java (Integration Tests)

Créer le fichier: `src/test/java/com/exam/project/controller/CollectivityControllerTest.java`

```java
package com.exam.project.controller;

import com.exam.project.model.Account;
import com.exam.project.service.CollectivityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CollectivityController.class)
public class CollectivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectivityService collectivityService;

    @Test
    public void testGetFinancialAccountsWithoutDate() throws Exception {
        // Arrange
        List<Account> mockAccounts = Arrays.asList(
            createAccount("1", "CASH", 50000),
            createAccount("2", "BANK", 100000),
            createAccount("3", "MOBILE_MONEY", 25000)
        );
        when(collectivityService.getAccountsWithBalanceAt(eq("1"), any(LocalDate.class)))
            .thenReturn(mockAccounts);

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/1/financialAccounts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].type", equalTo("CASH")))
            .andExpect(jsonPath("$[0].balance", equalTo(50000)));
    }

    @Test
    public void testGetFinancialAccountsWithDate() throws Exception {
        // Arrange
        LocalDate testDate = LocalDate.of(2024, 4, 15);
        List<Account> mockAccounts = Arrays.asList(
            createAccount("1", "CASH", 30000)
        );
        when(collectivityService.getAccountsWithBalanceAt("1", testDate))
            .thenReturn(mockAccounts);

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/1/financialAccounts")
                .param("at", "2024-04-15"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].balance", equalTo(30000)));
    }

    private Account createAccount(String id, String type, double balance) {
        Account account = new Account();
        account.setId(id);
        account.setType(type);
        account.setBalance(balance);
        return account;
    }
}
```

---

## 🔍 Plan de Test Détaillé

### Phase 1 : Test des Endpoints Existants
| Endpoint | Méthode | Status | Notes |
|----------|---------|--------|-------|
| POST /collectivities | Test JSON valide | 201 | Créer 2-3 collectivités |
| PUT /collectivities/{id}/informations | Test avec ID valide | 200 | Vérifier numéro unique |
| POST /collectivities/{id}/membershipFees | Test avec frais | 201 | Valider périodicité |
| GET /collectivities/{id}/membershipFees | Récupérer après création | 200 | Liste non-vide |
| GET /collectivities/{id}/transactions | Avec date_start/date_end | 200 | Filtrer par période |
| POST /members | Test JSON valide | 201 | Créer 2-3 membres |
| POST /members/{id}/payments | Test paiement | 201 | Enregistrer versement |

### Phase 2 : Test des Nouveaux Endpoints (NEW)
| Endpoint | Test Case | Expected |
|----------|-----------|----------|
| GET /collectivities/{id}/financialAccounts | Sans paramètre | Soldes actuels (3 types) |
| GET /collectivities/{id}/financialAccounts?at=DATE | Avec date passée | Soldes historiques |
| GET /collectivities/{id} | Récupérer collectivité | Inclut liste members |
| GET /collectivities/{id} | ID invalide | 404 Not Found |

---

## ✅ Checklist d'Évaluation

- [ ] Tous les endpoints retournent le code HTTP correct
- [ ] Les dates sont correctement traitées (format ISO-8601)
- [ ] Les balances sont calculées correctement pour la date donnée
- [ ] La liste des membres est complète pour une collectivité
- [ ] Les erreurs retournent des messages explicites
- [ ] La validation des données d'entrée fonctionne
- [ ] Les performances sont acceptables (< 500ms)

---

## 🛠️ Configuration de Base pour les Tests

### Variables d'environnement à configurer :
```yaml
# application-test.yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/collectivity_test
    username: test_user
    password: test_password
  jpa:
    hibernate:
      ddl-auto: create-drop
```

### Commandes utiles :
```bash
# Compiler et tester
mvn clean test

# Tester un endpoint spécifique
mvn test -Dtest=CollectivityControllerTest#testGetFinancialAccountsWithoutDate

# Lancer l'application
mvn spring-boot:run

# Vérifier la compilation
mvn compile
```

---

## 📊 Ordre d'Exécution Recommandé

1. **Démarrer l'application** : `mvn spring-boot:run`
2. **Créer une collectivité** (POST) → récupérer l'ID
3. **Créer des membres** (POST)
4. **Attribuer des comptes** avec soldes
5. **Tester GET /financialAccounts** sans date
6. **Tester GET /financialAccounts** avec dates variées
7. **Récupérer collectivité** (GET /collectivities/{id})
8. **Vérifier** que members est inclus dans la réponse
