package com.exam.project.controller;

import com.exam.project.model.Account;
import com.exam.project.model.AccountType;
import com.exam.project.service.CollectivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests d'intégration pour le contrôleur des collectivités
 * Tests les endpoints : GET /collectivities/{id}/financialAccounts et GET /collectivities/{id}
 */
@WebMvcTest(CollectivityController.class)
@DisplayName("Collectivity Controller Integration Tests")
public class CollectivityControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CollectivityService collectivityService;

    private List<Account> mockAccounts;
    private Account cashAccount;
    private Account bankAccount;
    private Account mobileMoneyAccount;

    @BeforeEach
    void setUp() {
        // Initialiser les comptes mock
        cashAccount = new Account();
        cashAccount.setId("ACC-001");
        cashAccount.setType(AccountType.CASH);
        cashAccount.setBalance(50000.0);
        cashAccount.setOwnerId("COL-001");

        bankAccount = new Account();
        bankAccount.setId("ACC-002");
        bankAccount.setType(AccountType.BANK);
        bankAccount.setBalance(100000.0);
        bankAccount.setOwnerId("COL-001");

        mobileMoneyAccount = new Account();
        mobileMoneyAccount.setId("ACC-003");
        mobileMoneyAccount.setType(AccountType.MOBILE_MONEY);
        mobileMoneyAccount.setBalance(25000.0);
        mobileMoneyAccount.setOwnerId("COL-001");

        mockAccounts = Arrays.asList(cashAccount, bankAccount, mobileMoneyAccount);
    }

    // =============== Tests pour GET /collectivities/{id}/financialAccounts ===============

    @Test
    @DisplayName("Devrait retourner 200 et la liste des comptes sans paramètre de date")
    void testGetFinancialAccountsWithoutDate() throws Exception {
        // Arrange
        when(collectivityService.getAccountsWithBalanceAt(eq("COL-001"), any(LocalDate.class)))
                .thenReturn(mockAccounts);

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", equalTo("ACC-001")))
                .andExpect(jsonPath("$[0].type", equalTo("CASH")))
                .andExpect(jsonPath("$[0].balance", equalTo(50000.0)))
                .andExpect(jsonPath("$[1].type", equalTo("BANK")))
                .andExpect(jsonPath("$[1].balance", equalTo(100000.0)))
                .andExpect(jsonPath("$[2].type", equalTo("MOBILE_MONEY")))
                .andExpect(jsonPath("$[2].balance", equalTo(25000.0)));

        verify(collectivityService, times(1))
                .getAccountsWithBalanceAt(eq("COL-001"), any(LocalDate.class));
    }

    @Test
    @DisplayName("Devrait retourner 200 et les comptes avec soldes à la date donnée")
    void testGetFinancialAccountsWithSpecificDate() throws Exception {
        // Arrange
        LocalDate specificDate = LocalDate.of(2024, 4, 15);
        List<Account> accountsAtDate = new ArrayList<>();
        Account historicalAccount = new Account();
        historicalAccount.setId("ACC-001");
        historicalAccount.setType(AccountType.CASH);
        historicalAccount.setBalance(30000.0); // Solde différent
        accountsAtDate.add(historicalAccount);

        when(collectivityService.getAccountsWithBalanceAt("COL-001", specificDate))
                .thenReturn(accountsAtDate);

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts")
                .param("at", "2024-04-15")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].balance", equalTo(30000.0)));

        verify(collectivityService, times(1))
                .getAccountsWithBalanceAt("COL-001", specificDate);
    }

    @Test
    @DisplayName("Devrait retourner 200 avec liste vide si aucun compte")
    void testGetFinancialAccountsEmptyList() throws Exception {
        // Arrange
        when(collectivityService.getAccountsWithBalanceAt(eq("COL-002"), any(LocalDate.class)))
                .thenReturn(new ArrayList<>());

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-002/financialAccounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Devrait valider le format de date ISO-8601")
    void testGetFinancialAccountsWithFormattedDate() throws Exception {
        // Arrange
        LocalDate testDate = LocalDate.of(2024, 1, 1);
        when(collectivityService.getAccountsWithBalanceAt("COL-001", testDate))
                .thenReturn(mockAccounts);

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts")
                .param("at", "2024-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Devrait retourner 400 pour une date au format invalide")
    void testGetFinancialAccountsWithInvalidDateFormat() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts")
                .param("at", "15-04-2024")) // Format invalide
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Devrait gérer les collectivités avec plusieurs comptes")
    void testGetFinancialAccountsMultipleTypes() throws Exception {
        // Arrange
        List<Account> complexAccounts = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Account acc = new Account();
            acc.setId("ACC-" + i);
            acc.setType(i % 2 == 0 ? AccountType.CASH : AccountType.BANK);
            acc.setBalance(10000.0 * (i + 1));
            complexAccounts.add(acc);
        }
        when(collectivityService.getAccountsWithBalanceAt(eq("COL-001"), any(LocalDate.class)))
                .thenReturn(complexAccounts);

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[*].balance", hasItems(10000.0, 20000.0, 30000.0, 40000.0, 50000.0)));
    }

    // =============== Tests pour GET /collectivities/{id} ===============

    @Test
    @DisplayName("Devrait retourner 200 et les détails d'une collectivité")
    void testGetCollectivityById() throws Exception {
        // Ce test dépend de l'implémentation de la méthode GET /collectivities/{id}
        // Pour l'instant, il est laissé en tant que placeholder

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.members").isArray());
    }

    @Test
    @DisplayName("Devrait inclure le tableau des membres dans la réponse")
    void testGetCollectivityIncludesMembers() throws Exception {
        // Ce test vérifie que la réponse inclut bien les membres

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.members", notNullValue()))
                .andExpect(jsonPath("$.members", instanceOf(List.class)));
    }

    @Test
    @DisplayName("Devrait retourner 404 pour une collectivité inexistante")
    void testGetCollectivityNotFound() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/collectivities/INVALID-ID"))
                .andExpect(status().isNotFound());
    }

    // =============== Tests de validation et gestion d'erreurs ===============

    @Test
    @DisplayName("Devrait accepter les IDs de collectivité avec différents formats")
    void testGetFinancialAccountsWithDifferentIdFormats() throws Exception {
        // Test avec ID numérique
        when(collectivityService.getAccountsWithBalanceAt(eq("123"), any(LocalDate.class)))
                .thenReturn(mockAccounts);

        mockMvc.perform(get("/api/collectivities/123/financialAccounts"))
                .andExpect(status().isOk());

        // Test avec ID UUID-like
        when(collectivityService.getAccountsWithBalanceAt(
                eq("550e8400-e29b-41d4-a716-446655440000"), any(LocalDate.class)))
                .thenReturn(mockAccounts);

        mockMvc.perform(get("/api/collectivities/550e8400-e29b-41d4-a716-446655440000/financialAccounts"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Devrait gérer les soldes négatifs (comptes débiteurs)")
    void testGetFinancialAccountsWithNegativeBalance() throws Exception {
        // Arrange
        Account negativeAccount = new Account();
        negativeAccount.setId("ACC-NEG");
        negativeAccount.setType(AccountType.CASH);
        negativeAccount.setBalance(-5000.0); // Solde négatif

        when(collectivityService.getAccountsWithBalanceAt(eq("COL-001"), any(LocalDate.class)))
                .thenReturn(Arrays.asList(negativeAccount));

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance", equalTo(-5000.0)));
    }

    @Test
    @DisplayName("Devrait gérer les très grandes valeurs de solde")
    void testGetFinancialAccountsWithLargeBalance() throws Exception {
        // Arrange
        Account largeAccount = new Account();
        largeAccount.setId("ACC-LARGE");
        largeAccount.setType(AccountType.BANK);
        largeAccount.setBalance(999999999999.99); // Très grande valeur

        when(collectivityService.getAccountsWithBalanceAt(eq("COL-001"), any(LocalDate.class)))
                .thenReturn(Arrays.asList(largeAccount));

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].balance", equalTo(999999999999.99)));
    }

    @Test
    @DisplayName("Devrait gérer les dates historiques")
    void testGetFinancialAccountsWithHistoricalDate() throws Exception {
        // Arrange
        LocalDate historicalDate = LocalDate.of(2020, 1, 1);
        when(collectivityService.getAccountsWithBalanceAt("COL-001", historicalDate))
                .thenReturn(Arrays.asList(cashAccount));

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts")
                .param("at", "2020-01-01"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @DisplayName("Devrait gérer les dates futures")
    void testGetFinancialAccountsWithFutureDate() throws Exception {
        // Arrange
        LocalDate futureDate = LocalDate.of(2025, 12, 31);
        when(collectivityService.getAccountsWithBalanceAt("COL-001", futureDate))
                .thenReturn(mockAccounts);

        // Act & Assert
        mockMvc.perform(get("/api/collectivities/COL-001/financialAccounts")
                .param("at", "2025-12-31"))
                .andExpect(status().isOk());
    }
}
