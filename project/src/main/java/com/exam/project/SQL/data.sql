INSERT INTO collectivity (id, location, specialty, name, federation_approval)
VALUES ('COLL-001', 'Antananarivo', 'Riziculture', 'Fédér. Analamanga', true);

INSERT INTO member (id, first_name, last_name, occupation, joining_date, collectivity_id)
VALUES
('M-SENIOR-1', 'Jean', 'Rabe', 'SENIOR', '2025-01-01', 'COLL-001'),
('M-SENIOR-2', 'Marie', 'Sitraka', 'SENIOR', '2025-01-01', 'COLL-001'),
('M-SENIOR-3', 'Luc', 'Andria', 'SENIOR', '2025-01-01', 'COLL-001'),
('M-SENIOR-4', 'Faly', 'Ranaivo', 'SENIOR', '2025-01-01', 'COLL-001'),
('M-SENIOR-5', 'Hery', 'Rakoto', 'SENIOR', '2025-01-01', 'COLL-001');

INSERT INTO member (id, first_name, last_name, occupation, joining_date, collectivity_id)
VALUES
('M-JUNIOR-1', 'Solo', 'Razafy', 'JUNIOR', CURRENT_DATE, 'COLL-001'),
('M-JUNIOR-2', 'Lova', 'Rivo', 'JUNIOR', CURRENT_DATE, 'COLL-001');

INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, joining_date) VALUES
('C1-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'Président', 'col-1', '2025-01-01'),
('C1-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'Vice président', 'col-1', '2025-01-01'),
('C1-M3', 'Prénom membre 3', 'Nom membre 3', '1992-03-10', 'MALE', 'Lot II J Ambato', 'Collecteur', '0331234567', 'member.3@fed-agrimg', 'Secrétaire', 'col-1', '2025-01-01'),
('C1-M4', 'Prénom membre 4', 'Nom membre 4', '1988-05-22', 'FEMALE', 'Lot A K 50 Ambato', 'Distributeur', '0381234567', 'member.4@fed-agri.mg', 'Trésorier', 'col-1', '2025-01-01'),
('C1-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'Confirmé', 'col-1', '2025-01-01'),
('C1-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'Confirmé', 'col-1', '2025-01-01'),
('C1-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'Confirmé', 'col-1', '2025-01-01'),
('C1-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'Confirmé', 'col-1', '2025-01-01');

INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, joining_date) VALUES
('C2-M1', 'Prénom membre 1', 'Nom membre 1', '1980-02-01', 'MALE', 'Lot II V M Ambato', 'Riziculteur', '0341234567', 'member.1@fed-agri.mg', 'Confirmé', 'col-2', '2025-01-01'),
('C2-M2', 'Prénom membre 2', 'Nom membre 2', '1982-03-05', 'MALE', 'Lot II F Ambato', 'Agriculteur', '0321234567', 'member.2@fed-agri.mg', 'Confirmé', 'col-2', '2025-01-01'),
('C2-M5', 'Prénom membre 5', 'Nom membre 5', '1999-08-21', 'MALE', 'Lot UV 80 Ambato', 'Riziculteur', '0373434567', 'member.5@fed-agri.mg', 'Président', 'col-2', '2025-01-01'),
('C2-M6', 'Prénom membre 6', 'Nom membre 6', '1998-08-22', 'FEMALE', 'Lot UV 6 Ambato', 'Riziculteur', '0372234567', 'member.6@fed-agri.mg', 'Vice président', 'col-2', '2025-01-01'),
('C2-M7', 'Prénom membre 7', 'Nom membre 7', '1998-01-31', 'MALE', 'Lot UV 7 Ambato', 'Riziculteur', '0374234567', 'member.7@fed-agri.mg', 'Secrétaire', 'col-2', '2025-01-01'),
('C2-M8', 'Prénom membre 8', 'Nom membre 8', '1975-08-20', 'MALE', 'Lot UV 8 Ambato', 'Riziculteur', '0370234567', 'member.8@fed-agri.mg', 'Trésorier', 'col-2', '2025-01-01');

INSERT INTO member (id, first_name, last_name, birth_date, gender, address, profession, phone_number, email, occupation, collectivity_id, joining_date) VALUES
('C3-M1', 'Prénom membre 9', 'Nom membre 9', '1985-06-15', 'MALE', 'Lot IV G Brickaville', 'Apiculteur', '0340012345', 'member.9@fed-agri.mg', 'Président', 'col-3', '2025-02-10'),
('C3-M2', 'Prénom membre 10', 'Nom membre 10', '1990-11-20', 'FEMALE', 'Lot IV H Brickaville', 'Apicultrice', '0320012345', 'member.10@fed-agri.mg', 'Trésorier', 'col-3', '2025-02-10');

INSERT INTO account (id, type, owner_id, balance, holder_name, phone_number, mobile_service) VALUES
('C1-A-CASH', 'CASH', 'col-1', 0.00, '-', NULL, NULL),
('C1-A-MOBILE-1', 'MOBILE_MONEY', 'col-1', 0.00, 'Mpanorina', '0370489612', 'ORANGE_MONEY'),
('C2-A-CASH', 'CASH', 'col-2', 0.00, '-', NULL, NULL),
('C2-A-MOBILE-1', 'MOBILE_MONEY', 'col-2', 0.00, 'Dobo voalohany', '0320489612', 'ORANGE_MONEY'),
('C3-A-CASH', 'CASH', 'col-3', 0.00, '-', NULL, NULL);

INSERT INTO contribution (id, collectivity_id, amount, date, description) VALUES
('cot-1', 'col-1', 100000, '2026-01-01', 'Cotisation annuelle ACTIVE ANNUALLY'),
('cot-2', 'col-2', 100000, '2026-01-01', 'Cotisation annuelle ACTIVE ANNUALLY'),
('cot-3', 'col-3', 50000, '2026-01-01', 'Cotisation annuelle ACTIVE ANNUALLY');


-- Collectivité 1
INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES
('P1-1', 'C1-M1', 'col-1', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P1-2', 'C1-M2', 'col-1', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P1-3', 'C1-M3', 'col-1', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P1-4', 'C1-M4', 'col-1', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P1-5', 'C1-M5', 'col-1', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P1-6', 'C1-M6', 'col-1', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P1-7', 'C1-M7', 'col-1', 60000, '2026-01-01', 'CASH', 'Paiement partiel'),
('P1-8', 'C1-M8', 'col-1', 90000, '2026-01-01', 'CASH', 'Paiement partiel');

-- Collectivité 2
INSERT INTO contribution (id, member_id, collectivity_id, amount, date, payment_method, description) VALUES
('P2-1', 'C2-M1', 'col-2', 60000, '2026-01-01', 'CASH', 'Paiement partiel'),
('P2-2', 'C2-M2', 'col-2', 90000, '2026-01-01', 'CASH', 'Paiement partiel'),
('P2-5', 'C2-M5', 'col-2', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P2-6', 'C2-M6', 'col-2', 100000, '2026-01-01', 'CASH', 'Paiement intégral'),
('P2-7', 'C2-M7', 'col-2', 40000, '2026-01-01', 'MOBILE_MONEY', 'Paiement partiel'),
('P2-8', 'C2-M8', 'col-2', 60000, '2026-01-01', 'MOBILE_MONEY', 'Paiement partiel');

INSERT INTO payment (id, member_id, collectivity_id, amount, account_id, payment_method, payment_date) VALUES
('PAY-C1-001', 'C1-M1', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('PAY-C1-002', 'C1-M2', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('PAY-C1-003', 'C1-M3', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('PAY-C1-004', 'C1-M4', 'col-1', 100000, '2026-01-01', 'C1-A-CASH', 'CASH'),
('PAY-C1-005', 'C1-M5', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('PAY-C1-006', 'C1-M6', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('PAY-C1-007', 'C1-M7', 'col-1', 60000,  'C1-A-CASH', 'CASH', '2026-01-01'),
('PAY-C1-008', 'C1-M8', 'col-1', 90000,  'C1-A-CASH', 'CASH', '2026-01-01');

INSERT INTO transaction (id, member_id, collectivity_id, amount, account_id, payment_method, creation_date) VALUES
('TR-C1-001', 'C1-M1', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('TR-C1-002', 'C1-M2', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('TR-C1-003', 'C1-M3', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('TR-C1-004', 'C1-M4', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('TR-C1-005', 'C1-M5', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('TR-C1-006', 'C1-M6', 'col-1', 100000, 'C1-A-CASH', 'CASH', '2026-01-01'),
('TR-C1-007', 'C1-M7', 'col-1', 60000,  'C1-A-CASH', 'CASH', '2026-01-01'),
('TR-C1-008', 'C1-M8', 'col-1', 90000,  'C1-A-CASH', 'CASH', '2026-01-01');

INSERT INTO payment (id, member_id, collectivity_id, amount, account_id, payment_method, payment_date) VALUES
('PAY-C2-001', 'C2-M1', 'col-2', 60000,  'C2-A-CASH', 'CASH', '2026-01-01'),
('PAY-C2-002', 'C2-M2', 'col-2', 90000,  'C2-A-CASH', 'CASH', '2026-01-01'),
('PAY-C2-003', 'C2-M3', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('PAY-C2-004', 'C2-M4', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('PAY-C2-005', 'C2-M5', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('PAY-C2-006', 'C2-M6', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('PAY-C2-007', 'C2-M7', 'col-2', 40000,  'C2-A-MOBILE-1', 'MOBILE_MONEY', '2026-01-01'),
('PAY-C2-008', 'C2-M8', 'col-2', 60000,  'C2-A-MOBILE-1', 'MOBILE_MONEY', '2026-01-01');

INSERT INTO transaction (id, member_id, collectivity_id, amount, account_id, payment_method, creation_date) VALUES
('TR-C2-001', 'C2-M1', 'col-2', 60000,  'C2-A-CASH', 'CASH', '2026-01-01'),
('TR-C2-002', 'C2-M2', 'col-2', 90000,  'C2-A-CASH', 'CASH', '2026-01-01'),
('TR-C2-003', 'C2-M3', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('TR-C2-004', 'C2-M4', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('TR-C2-005', 'C2-M5', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('TR-C2-006', 'C2-M6', 'col-2', 100000, 'C2-A-CASH', 'CASH', '2026-01-01'),
('TR-C2-007', 'C2-M7', 'col-2', 40000,  'C2-A-MOBILE-1', 'MOBILE_MONEY', '2026-01-01'),
('TR-C2-008', 'C2-M8', 'col-2', 60000,  'C2-A-MOBILE-1', 'MOBILE_MONEY', '2026-01-01');