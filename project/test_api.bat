@echo off
REM Script de test complet pour l'API Collectivity Management (Windows)
REM Usage: test_api.bat

setlocal enabledelayedexpansion

set BASE_URL=http://localhost:8080/api
set COLLECTIVITY_ID=1
set MEMBER_ID=1

echo ========================================
echo   Collectivity Management API Tests
echo ========================================
echo.

REM Test 1: Créer collectivités
echo Testing: 1. POST /collectivities - Créer collectivités
set collectivities_data={^
  "name": "Collectivité Test Alpha",^
  "description": "Test collectivity"^
}

curl -X POST %BASE_URL%/collectivities ^
  -H "Content-Type: application/json" ^
  -d "%collectivities_data%"
echo.
echo.

REM Test 2: Récupérer collectivité
echo Testing: 2. GET /collectivities/{id} - Récupérer collectivité
curl -X GET "%BASE_URL%/collectivities/%COLLECTIVITY_ID%"
echo.
echo.

REM Test 3: Récupérer comptes financiers (NEW) - Sans date
echo Testing: 3. GET /collectivities/{id}/financialAccounts - Sans date
curl -X GET "%BASE_URL%/collectivities/%COLLECTIVITY_ID%/financialAccounts"
echo.
echo.

REM Test 4: Récupérer comptes financiers (NEW) - Avec date
echo Testing: 4. GET /collectivities/{id}/financialAccounts - Avec date
curl -X GET "%BASE_URL%/collectivities/%COLLECTIVITY_ID%/financialAccounts?at=2024-04-15"
echo.
echo.

REM Test 5: Créer membres
echo Testing: 5. POST /members - Créer membres
set members_data={^
  "firstName": "Jean",^
  "lastName": "Dupont",^
  "phoneNumber": "+223 75 12 34 56",^
  "email": "jean@example.com"^
}

curl -X POST "%BASE_URL%/members" ^
  -H "Content-Type: application/json" ^
  -d "%members_data%"
echo.
echo.

REM Test 6: Enregistrer paiements
echo Testing: 6. POST /members/{id}/payments - Enregistrer paiements
set payments_data={^
  "amount": 5000,^
  "paymentDate": "2024-04-20",^
  "paymentMethod": "CASH"^
}

curl -X POST "%BASE_URL%/members/%MEMBER_ID%/payments" ^
  -H "Content-Type: application/json" ^
  -d "%payments_data%"
echo.
echo.

echo ========================================
echo Tests terminated
echo ========================================

REM Afficher les commandes utiles
echo.
echo Commandes utiles:
echo   - Compiler: mvn clean compile
echo   - Tester: mvn clean test
echo   - Lancer: mvn spring-boot:run
echo   - Importer Postman: Collectivity_API_Tests.postman_collection.json
echo.

pause
