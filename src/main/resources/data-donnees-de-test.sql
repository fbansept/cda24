INSERT INTO etat (designation) VALUES ("Neuf"),("Bon état"), ("Mauvais état");

INSERT INTO etiquette (designation) VALUES ("Best seller"),("En Promo"), ("Saint Valentin"), ("A la une");

INSERT INTO produit (prix, nom, code, description, etat_id) VALUES
(20.99, 'Ecouteurs Bluetooth sans Fil, AOVOCE Casque Bluetooth 5.3 avec 4 ENC Réduction Bruit', 'AOVOCEA60B', 'la nouvelle génération de puce Bluetooth 5.3 personnalisée du écouteurs Bluetooth sans fil A60Pro est deux fois plus rapide que la génération précédente (Bluetooth 5.2) et a une distance de transmission quatre fois plus longue. Audio de haute qualité plus rapide et plus stable (portée de transmission jusqu\'à 15 m). Grâce à la fonction mémoire, le casque se connecte automatiquement à votre téléphone (après la première connexion).',1),

(11.99, 'Chargeur USB C 20W Rapide Prise', 'ChrgUSBC20WAnlikool', 'L\'adaptateur de multiple prise USB est fabriqué en matériau ignifuge PC et fournit des protections multiples, y compris un court-circuit anti-sortie, une surintensité intégrée, une surtension et une protection contre la surchauffe. Assure votre sécurité et celle de vos appareils. Portables Telephone Caricatore usb c rapide et sûr.',1);

INSERT INTO etiquette_produit (produit_id, etiquette_id) VALUES
(1,1), (1,4), (2,1), (2,2);

INSERT INTO utilisateur (email, mot_de_passe, administrateur) VALUES
("bansept.franck@gmail.com","root",1), ("a@a.com","root",1), ("b@b.com","root",0);

INSERT INTO commande (date, client_id) VALUES
("2024-01-03",1), ("2024-01-04",1), (NOW(),3);