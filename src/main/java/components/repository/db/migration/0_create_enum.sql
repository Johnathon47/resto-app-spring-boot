-- Type pour le statut des plats dans une commande
CREATE TYPE order_dish_status AS ENUM ('CREATED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED');

-- Type pour les numéros de table
CREATE TYPE table_number AS ENUM ('TABLE1', 'TABLE2', 'TABLE3'); -- Ajoute d'autres numéros de table si nécessaire

-- Type pour le mouvement des stocks (par exemple : ajout, retrait)
CREATE TYPE movement_type AS ENUM ('ENTRY', 'EXIT');
