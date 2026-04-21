
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