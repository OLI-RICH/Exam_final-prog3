#!/bin/bash
# Script de test complet pour l'API Collectivity Management
# Usage: bash test_api.sh [option]

set -e

BASE_URL="http://localhost:8080/api"
COLLECTIVITY_ID="1"
MEMBER_ID="1"

# Couleurs pour l'output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}========================================${NC}"
echo -e "${BLUE}  Collectivity Management API Tests${NC}"
echo -e "${BLUE}========================================${NC}\n"

# Fonction pour afficher le résultat d'un test
test_endpoint() {
    local method=$1
    local endpoint=$2
    local data=$3
    local expected_code=$4
    local description=$5

    echo -e "${YELLOW}Testing:${NC} ${description}"
    echo -e "  ${method} ${BASE_URL}${endpoint}"

    if [ -z "$data" ]; then
        response=$(curl -s -w "\n%{http_code}" -X ${method} "${BASE_URL}${endpoint}" \
            -H "Content-Type: application/json")
    else
        response=$(curl -s -w "\n%{http_code}" -X ${method} "${BASE_URL}${endpoint}" \
            -H "Content-Type: application/json" \
            -d "$data")
    fi

    http_code=$(echo "$response" | tail -n 1)
    body=$(echo "$response" | head -n -1)

    if [ "$http_code" -eq "$expected_code" ]; then
        echo -e "  ${GREEN}✓ Status ${http_code} (Expected: ${expected_code})${NC}"
    else
        echo -e "  ${RED}✗ Status ${http_code} (Expected: ${expected_code})${NC}"
        echo -e "  Response: ${body}"
        return 1
    fi
    echo ""
}

# ========== TESTS COLLECTIVITÉS ==========

echo -e "${BLUE}--- Testing Collectivities Endpoints ---${NC}\n"

# Test 1: Créer collectivités
collectivities_data='[
  {
    "name": "Collectivité Test Alpha",
    "description": "Test 1",
    "region": "Région A"
  },
  {
    "name": "Collectivité Test Beta",
    "description": "Test 2",
    "region": "Région B"
  }
]'

test_endpoint "POST" "/collectivities" "$collectivities_data" 201 \
    "1. POST /collectivities - Créer collectivités"

# Test 2: Récupérer collectivité
test_endpoint "GET" "/collectivities/${COLLECTIVITY_ID}" "" 200 \
    "2. GET /collectivities/{id} - Récupérer collectivité"

# Test 3: Attribuer informations uniques
info_data='{
  "number": "COL-2024-001",
  "name": "Collectivité Officielle"
}'

test_endpoint "PUT" "/collectivities/${COLLECTIVITY_ID}/informations" "$info_data" 200 \
    "3. PUT /collectivities/{id}/informations - Attribuer informations"

# Test 4: Créer cotisations
fees_data='[
  {
    "amount": 5000,
    "frequency": "MONTHLY",
    "startDate": "2024-04-01",
    "description": "Cotisation mensuelle"
  }
]'

test_endpoint "POST" "/collectivities/${COLLECTIVITY_ID}/membershipFees" "$fees_data" 201 \
    "4. POST /collectivities/{id}/membershipFees - Créer cotisations"

# Test 5: Récupérer cotisations
test_endpoint "GET" "/collectivities/${COLLECTIVITY_ID}/membershipFees" "" 200 \
    "5. GET /collectivities/{id}/membershipFees - Récupérer cotisations"

# Test 6: Récupérer transactions
test_endpoint "GET" "/collectivities/${COLLECTIVITY_ID}/transactions?startDate=2024-01-01&endDate=2024-04-30" "" 200 \
    "6. GET /collectivities/{id}/transactions - Récupérer transactions"

# Test 7: Récupérer comptes financiers (NEW) - Sans date
test_endpoint "GET" "/collectivities/${COLLECTIVITY_ID}/financialAccounts" "" 200 \
    "7. GET /collectivities/{id}/financialAccounts - Sans paramètre de date"

# Test 8: Récupérer comptes financiers (NEW) - Avec date
test_endpoint "GET" "/collectivities/${COLLECTIVITY_ID}/financialAccounts?at=2024-04-15" "" 200 \
    "8. GET /collectivities/{id}/financialAccounts - Avec paramètre 'at'"

# ========== TESTS MEMBRES ==========

echo -e "${BLUE}--- Testing Members Endpoints ---${NC}\n"

# Test 9: Créer membres
members_data='[
  {
    "firstName": "Jean",
    "lastName": "Dupont",
    "phoneNumber": "+223 75 12 34 56",
    "email": "jean@example.com",
    "collectivityId": "'${COLLECTIVITY_ID}'"
  },
  {
    "firstName": "Marie",
    "lastName": "Martin",
    "phoneNumber": "+223 76 98 76 54",
    "email": "marie@example.com",
    "collectivityId": "'${COLLECTIVITY_ID}'"
  }
]'

test_endpoint "POST" "/members" "$members_data" 201 \
    "9. POST /members - Créer membres"

# Test 10: Enregistrer paiements
payments_data='[
  {
    "amount": 5000,
    "paymentDate": "2024-04-20",
    "paymentMethod": "CASH",
    "reference": "PAY-001"
  }
]'

test_endpoint "POST" "/members/${MEMBER_ID}/payments" "$payments_data" 201 \
    "10. POST /members/{id}/payments - Enregistrer paiements"

# ========== TESTS D'ERREUR ==========

echo -e "${BLUE}--- Testing Error Handling ---${NC}\n"

# Test 11: Collectivité inexistante
test_endpoint "GET" "/collectivities/999999" "" 404 \
    "11. GET /collectivities/{id} - ID inexistant (404)"

# Test 12: Données invalides
invalid_data='{
  "name": ""
}'

test_endpoint "POST" "/collectivities" "$invalid_data" 400 \
    "12. POST /collectivities - Données invalides (400)"

# Test 13: Date invalide
test_endpoint "GET" "/collectivities/${COLLECTIVITY_ID}/financialAccounts?at=invalid-date" "" 400 \
    "13. GET /collectivities/{id}/financialAccounts - Date invalide (400)"

# ========== RÉSUMÉ ==========

echo -e "${BLUE}========================================${NC}"
echo -e "${GREEN}✓ Tous les tests ont été exécutés${NC}"
echo -e "${BLUE}========================================${NC}\n"

echo -e "${YELLOW}Commandes utiles:${NC}"
echo "  - Compiler: mvn clean compile"
echo "  - Tester: mvn clean test"
echo "  - Lancer: mvn spring-boot:run"
echo "  - Importer Postman: Collectivity_API_Tests.postman_collection.json"
echo ""
