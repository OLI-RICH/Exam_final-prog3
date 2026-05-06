DELETE FROM contribution;
DELETE FROM account;
DELETE FROM member;
DELETE FROM collectivity;

INSERT INTO collectivity (id, name, location, specialty, federation_approval) VALUES
('col-1', 'Mpanorina', 'Ambatondrazaka', 'Riziculture', false),
('col-2', 'Dobo voalohany', 'Ambatondrazaka', 'Pisciculture', false),
('col-3', 'Tantely mamy', 'Brickaville', 'Apiculture', false);

-- Membres de la Collectivité 1
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, joining_date) VALUES
('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'Président', 'col-1', '2026-01-01'),
('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'Vice président', 'col-1', '2026-01-01'),
('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg', 'Secrétaire', 'col-1', '2026-01-01'),
('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'Trésorier', 'col-1', '2026-01-01'),
('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'Confirmé', 'col-1', '2026-01-01'),
('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'Confirmé', 'col-1', '2026-01-01'),
('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'Confirmé', 'col-1', '2026-01-01'),
('C1-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'Confirmé', 'col-1', '2026-01-01');

-- Membres de la Collectivité 2
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, joining_date) VALUES
('C2-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'Confirmé', 'col-2', '2026-01-01'),
('C2-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'Confirmé', 'col-2', '2026-01-01'),
('C2-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agri.mg', 'Confirmé', 'col-2', '2026-01-01'),
('C2-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'Confirmé', 'col-2', '2026-01-01'),
('C2-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'Président', 'col-2', '2026-01-01'),
('C2-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'Vice président', 'col-2', '2026-01-01'),
('C2-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'Secrétaire', 'col-2', '2026-01-01'),
('C2-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'Trésorier', 'col-2', '2026-01-01');

-- Membres de la Collectivité 3
INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, joining_date) VALUES
('C3-M1', 'Prénom membre 9', 'Nom membre 9', '1985-06-15', 'MALE', 'Lot IV G Brickaville', 'Apiculteur', '0340012345', 'member.9@fed-agri.mg', 'Président', 'col-3', '2026-01-01'),
('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1990-11-20', 'FEMALE', 'Lot IV H Brickaville', 'Apicultrice', '0320012345', 'member.10@fed-agri.mg', 'Trésorier', 'col-3', '2026-01-01');

INSERT INTO account (id, type, owner_id, balance, holder_name, phone_number, mobile_service) VALUES
('C1-A-CASH', 'CASH', 'col-1', 0.00, '-', NULL, NULL),
('C1-A-MOBILE-1', 'MOBILE_MONEY', 'col-1', 0.00, 'Mpanorina', '0370489612', 'ORANGE_MONEY'),
('C2-A-CASH', 'CASH', 'col-2', 0.00, '-', NULL, NULL),
('C2-A-MOBILE-1', 'MOBILE_MONEY', 'col-2', 0.00, 'Dobo voalohany', '0320489612', 'ORANGE_MONEY'),
('C3-A-CASH', 'CASH', 'col-3', 0.00, '-', NULL, NULL),
('C3-A-BANK-1', 'BANK', 'col-3', 0.00, 'Koto', NULL, NULL),
('C3-A-BANK-2', 'BANK', 'col-3', 0.00, 'Naivo', NULL, NULL),
('C3-A-MOBILE-1', 'MOBILE_MONEY', 'col-3', 0.00, 'Kolo', '0341889612', 'MVOLA');

INSERT INTO contribution (id, collectivity_id, amount, date, description, member_id, payment_method) VALUES
-- Collectivité 1
('cot-1', 'col-1', 200000.00, '2026-01-01', 'Cotisation annuelle ACTIVE ANNUALLY', NULL, NULL),
('cot-2', 'col-1', 20000.00, '2026-04-30', 'Famangiana ACTIVE PUNCTUALLY', NULL, NULL),
-- Collectivité 2
('cot-3', 'col-2', 200000.00, '2026-01-01', 'Cotisation annuelle ACTIVE ANNUALLY', NULL, NULL),
('cot-4', 'col-2', 100000.00, '2025-01-01', 'Cotisation 2025 INACTIVE ANNUALLY', NULL, NULL),
-- Collectivité 3
('cot-5', 'col-3', 25000.00, '2026-04-01', 'Cotisation mensuelle ACTIVE MONTHLY', NULL, NULL);


-- Paiements Collectivité 1 (8 lignes)
INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES
('PAY-C1-01', 'C1-M1', 'col-1', 200000.00, '2026-01-01', 'CASH', 'Paiement cotisation'),
('PAY-C1-02', 'C1-M2', 'col-1', 200000.00, '2026-01-01', 'CASH', 'Paiement cotisation'),
('PAY-C1-03', 'C1-M3', 'col-1', 200000.00, '2026-01-01', 'MOBILE_MONEY', 'Paiement cotisation'),
('PAY-C1-04', 'C1-M4', 'col-1', 200000.00, '2026-01-01', 'MOBILE_MONEY', 'Paiement cotisation'),
('PAY-C1-05', 'C1-M5', 'col-1', 150000.00, '2026-01-01', 'MOBILE_MONEY', 'Paiement partiel'),
('PAY-C1-06', 'C1-M6', 'col-1', 100000.00, '2026-05-01', 'CASH', 'Paiement partiel'),
('PAY-C1-07', 'C1-M7', 'col-1', 60000.00, '2026-05-01', 'CASH', 'Paiement partiel'),
('PAY-C1-08', 'C1-M8', 'col-1', 90000.00, '2026-05-01', 'CASH', 'Paiement partiel');

-- Paiements Collectivité 2 (8 lignes - Réassociés aux membres C2-M1 à C2-M8)
INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES
('PAY-C2-01', 'C2-M1', 'col-2', 120000.00, '2026-01-01', 'CASH', 'Paiement partiel'),
('PAY-C2-02', 'C2-M2', 'col-2', 180000.00, '2026-01-01', 'CASH', 'Paiement partiel'),
('PAY-C2-03', 'C2-M3', 'col-2', 200000.00, '2026-01-01', 'CASH', 'Paiement cotisation'),
('PAY-C2-04', 'C2-M4', 'col-2', 200000.00, '2026-01-01', 'CASH', 'Paiement cotisation'),
('PAY-C2-05', 'C2-M5', 'col-2', 200000.00, '2026-01-01', 'CASH', 'Paiement cotisation'),
('PAY-C2-06', 'C2-M6', 'col-2', 200000.00, '2026-01-01', 'CASH', 'Paiement cotisation'),
('PAY-C2-07', 'C2-M7', 'col-2', 80000.00, '2026-01-01', 'MOBILE_MONEY', 'Paiement partiel'),
('PAY-C2-08', 'C2-M8', 'col-2', 120000.00, '2026-01-01', 'MOBILE_MONEY', 'Paiement partiel');

-- Paiements Collectivité 3 (18 lignes)
INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES
('PAY-C3-01', 'C3-M1', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-02', 'C3-M2', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-03', 'C3-M1', 'col-3', 25000.00, '2026-05-01', 'CASH', 'Mensualité Mai'),
('PAY-C3-04', 'C3-M2', 'col-3', 25000.00, '2026-05-01', 'CASH', 'Mensualité Mai'),
('PAY-C3-05', 'C3-M1', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-06', 'C3-M2', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-07', 'C3-M1', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-08', 'C3-M2', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-09', 'C3-M1', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-10', 'C3-M2', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-11', 'C3-M1', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-12', 'C3-M2', 'col-3', 25000.00, '2026-04-01', 'CASH', 'Mensualité Avril'),
('PAY-C3-13', 'C3-M1', 'col-3', 15000.00, '2026-05-01', 'MOBILE_MONEY', 'Mensualité Mai'),
('PAY-C3-14', 'C3-M2', 'col-3', 15000.00, '2026-05-01', 'MOBILE_MONEY', 'Mensualité Mai'),
('PAY-C3-15', 'C3-M1', 'col-3', 20000.00, '2026-05-01', 'CASH', 'Mensualité Mai'),
('PAY-C3-16', 'C3-M2', 'col-3', 25000.00, '2026-05-01', 'CASH', 'Mensualité Mai'),
('PAY-C3-17', 'C3-M1', 'col-3', 5000.00, '2026-05-01', 'CASH', 'Mensualité Mai'),
('PAY-C3-18', 'C3-M2', 'col-3', 5000.00, '2026-05-01', 'CASH', 'Mensualité Mai');

-- Nouveaux membres Collectivité 1
INSERT INTO member (id, first_name, last_name, occupation, collectivity_id, joining_date) VALUES
('NEW-C1-01', 'Adhérent', 'Nouveau 1', 'Junior', 'col-1', '2026-04-01'),
('NEW-C1-02', 'Adhérent', 'Nouveau 2', 'Junior', 'col-1', '2026-04-01'),
('NEW-C1-03', 'Adhérent', 'Nouveau 3', 'Junior', 'col-1', '2026-05-01'),
('NEW-C1-04', 'Adhérent', 'Nouveau 4', 'Junior', 'col-1', '2026-06-01');

-- Nouveaux membres Collectivité 2
INSERT INTO member (id, first_name, last_name, occupation, collectivity_id, joining_date) VALUES
('NEW-C2-01', 'Adhérent', 'Nouveau 5', 'Junior', 'col-2', '2026-03-01'),
('NEW-C2-02', 'Adhérent', 'Nouveau 6', 'Junior', 'col-2', '2026-03-01'),
('NEW-C2-03', 'Adhérent', 'Nouveau 7', 'Junior', 'col-2', '2026-03-01');

-- Nouveaux membres Collectivité 3
INSERT INTO member (id, first_name, last_name, occupation, collectivity_id, joining_date) VALUES
('NEW-C3-01', 'Adhérent', 'Nouveau 8', 'Junior', 'col-3', '2026-01-01'),
('NEW-C3-02', 'Adhérent', 'Nouveau 9', 'Junior', 'col-3', '2026-02-01'),
('NEW-C3-03', 'Adhérent', 'Nouveau 10', 'Junior', 'col-3', '2026-02-01'),
('NEW-C3-04', 'Adhérent', 'Nouveau 11', 'Junior', 'col-3', '2026-03-01'),
('NEW-C3-05', 'Adhérent', 'Nouveau 12', 'Junior', 'col-3', '2026-03-01'),
('NEW-C3-06', 'Adhérent', 'Nouveau 13', 'Junior', 'col-3', '2026-03-01');


SELECT
    (SELECT COUNT(*) FROM collectivity) AS total_collectivites,
    (SELECT COUNT(*) FROM member) AS total_membres,
    (SELECT COUNT(*) FROM account) AS total_comptes,
    (SELECT COUNT(*) FROM contribution WHERE member_id IS NULL) AS total_exigences_cotisations,
    (SELECT COUNT(*) FROM contribution WHERE member_id IS NOT NULL) AS total_paiements_enregistres;