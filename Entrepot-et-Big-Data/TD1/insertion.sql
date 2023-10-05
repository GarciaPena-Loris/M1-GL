-- Table des Utilisateur 
INSERT INTO Utilisateur VALUES('utilisateur1@example.com', 'Utilisateur 1', 'Adresse 1');
INSERT INTO Utilisateur VALUES('utilisateur2@example.com', 'Utilisateur 2', 'Adresse 2');
INSERT INTO Utilisateur VALUES('utilisateur3@example.com', 'Utilisateur 3', 'Adresse 3');
INSERT INTO Utilisateur VALUES('utilisateur4@example.com', 'Utilisateur 4', 'Adresse 4');
INSERT INTO Utilisateur VALUES('utilisateur5@example.com', 'Utilisateur 5', 'Adresse 5');
INSERT INTO Utilisateur VALUES('utilisateur6@example.com', 'Utilisateur 6', 'Adresse 6');
INSERT INTO Utilisateur VALUES('utilisateur7@example.com', 'Utilisateur 7', 'Adresse 7');

-- Table des Photographie 
INSERT INTO Photographie VALUES(1, 'Montpellier', TO_DATE('2023-09-19', 'YYYY-MM-DD'), 1, 1, 1);
INSERT INTO Photographie VALUES(2, 'Lieu 2', TO_DATE('2023-09-18', 'YYYY-MM-DD'), 2, 2, 2);
INSERT INTO Photographie VALUES(3, 'Lieu 3', TO_DATE('2023-09-17', 'YYYY-MM-DD'), 1, 3, 1);
INSERT INTO Photographie VALUES(4, 'Lieu 4', TO_DATE('2023-09-16', 'YYYY-MM-DD'), 3, 2, 3);
INSERT INTO Photographie VALUES(5, 'Lieu 5', TO_DATE('2023-09-15', 'YYYY-MM-DD'), 2, 1, 2);
INSERT INTO Photographie VALUES(6, 'Lieu 6', TO_DATE('2023-09-14', 'YYYY-MM-DD'), 1, 2, 1);
INSERT INTO Photographie VALUES(7, 'Lieu 7', TO_DATE('2023-09-13', 'YYYY-MM-DD'), 1, 3, 2);


-- Table des Configurations
INSERT INTO AppareilPhoto VALUES(1, 'Modèle 1');
INSERT INTO AppareilPhoto VALUES(2, 'Modèle 2');
INSERT INTO AppareilPhoto VALUES(3, 'Modèle 3');
INSERT INTO AppareilPhoto VALUES(4, 'Modèle 4');
INSERT INTO AppareilPhoto VALUES(5, 'Modèle 5');


-- Table des Configurations
INSERT INTO Configurations VALUES (1, 2.8, 1/250, 1, 50.0);
INSERT INTO Configurations VALUES (2, 2.0, 1/500, 0, 35.0);
INSERT INTO Configurations VALUES (3, 4.0, 1/125, 1, 85.0);
INSERT INTO Configurations VALUES (4, 1.8, 1/1000, 0, 24.0);
INSERT INTO Configurations VALUES (5, 3.5, 1/60, 1, 70.0);

-- Table des LicenceDeDistribution
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES (1, 'Tous droits réservés', 0, 0);
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES (2, 'Attribution uniquement', 1, 0);
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES (3, 'Partage à l identique', 1, 1);
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES (4, 'Utilisation non commerciale', 0, 1);
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES (5, 'Domaine public', 1, 1);
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES (6, 'Tous droits réservés', 1, 0);
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES  (7, 'Tous droits réservés', 0, 1);
INSERT INTO LicenceDeDistribution (licence_id, droit_reserves, utilisation_commerciale, modification_autorisee) VALUES (8, 'Tous droits réservés', 1, 1);

-- Table des Publication
INSERT INTO Publication (email, photo_id, date_publication) VALUES
	('utilisateur1@example.com', 1, TO_DATE('2023-09-19', 'YYYY-MM-DD'));
INSERT INTO Publication (email, photo_id, date_publication) VALUES
	('utilisateur2@example.com', 2, TO_DATE('2023-09-18', 'YYYY-MM-DD'));
INSERT INTO Publication (email, photo_id, date_publication) VALUES
	('utilisateur3@example.com', 3, TO_DATE('2023-09-17', 'YYYY-MM-DD'));
INSERT INTO Publication (email, photo_id, date_publication) VALUES
	('utilisateur4@example.com', 4, TO_DATE('2023-09-16', 'YYYY-MM-DD'));
INSERT INTO Publication (email, photo_id, date_publication) VALUES
	('utilisateur5@example.com', 5, TO_DATE('2023-09-15', 'YYYY-MM-DD'));

-- Table des CollectionDePhotos
INSERT INTO CollectionDePhotos (collection_id, description, email) VALUES (1, 'Collection 1', 'utilisateur1@example.com');
INSERT INTO CollectionDePhotos (collection_id, description, email) VALUES (2, 'Collection 2', 'utilisateur2@example.com');
INSERT INTO CollectionDePhotos (collection_id, description, email) VALUES (3, 'Collection 3', 'utilisateur3@example.com');
INSERT INTO CollectionDePhotos (collection_id, description, email) VALUES (4, 'Collection 4', 'utilisateur4@example.com');
INSERT INTO CollectionDePhotos (collection_id, description, email) VALUES (5, 'Collection 5', 'utilisateur5@example.com');

-- Table d'association entre les CollectionsDePhotos et les Photographies
INSERT INTO Contient (collection_id, photo_id) VALUES (1, 1);
INSERT INTO Contient (collection_id, photo_id) VALUES (1, 2);
INSERT INTO Contient (collection_id, photo_id) VALUES (2, 3);
INSERT INTO Contient (collection_id, photo_id) VALUES (3, 4);
INSERT INTO Contient (collection_id, photo_id) VALUES (4, 5);

-- Table des Albums
INSERT INTO Album VALUES (1);
INSERT INTO Album VALUES (2);
INSERT INTO Album VALUES (3);
INSERT INTO Album VALUES (4);
INSERT INTO Album VALUES (5);

-- Table des Galerie
INSERT INTO Galerie VALUES (1);
INSERT INTO Galerie VALUES (2);
INSERT INTO Galerie VALUES (3);
INSERT INTO Galerie VALUES (4);
INSERT INTO Galerie VALUES (5);

-- Table d'association Galerie - Utilisateurs
INSERT INTO GaleriePublicateur (galerie_id, email) VALUES (1, 'utilisateur1@example.com');
INSERT INTO GaleriePublicateur (galerie_id, email) VALUES (2, 'utilisateur2@example.com');
INSERT INTO GaleriePublicateur (galerie_id, email) VALUES (3, 'utilisateur3@example.com');
INSERT INTO GaleriePublicateur (galerie_id, email) VALUES (4, 'utilisateur4@example.com');
INSERT INTO GaleriePublicateur (galerie_id, email) VALUES (5, 'utilisateur5@example.com');

-- Table des Abonnements
INSERT INTO Abonnement (email, email_abonnement) VALUES ('utilisateur1@example.com', 'utilisateur2@example.com');
INSERT INTO Abonnement (email, email_abonnement) VALUES ('utilisateur1@example.com', 'utilisateur3@example.com');
INSERT INTO Abonnement (email, email_abonnement) VALUES ('utilisateur2@example.com', 'utilisateur4@example.com');
INSERT INTO Abonnement (email, email_abonnement) VALUES ('utilisateur3@example.com', 'utilisateur5@example.com');
INSERT INTO Abonnement (email, email_abonnement) VALUES ('utilisateur4@example.com', 'utilisateur1@example.com');

-- Table des Likes
INSERT INTO Likes (email, photo_id, nombre_like) VALUES ('utilisateur1@example.com', 1, 10);
INSERT INTO Likes (email, photo_id, nombre_like) VALUES ('utilisateur2@example.com', 2, 5);
INSERT INTO Likes (email, photo_id, nombre_like) VALUES ('utilisateur3@example.com', 3, 15);
INSERT INTO Likes (email, photo_id, nombre_like) VALUES ('utilisateur4@example.com', 4, 7);
INSERT INTO Likes (email, photo_id, nombre_like) VALUES ('utilisateur5@example.com', 5, 20);

-- Table des Marques
INSERT INTO Marques (email, photo_id, tag, mots_cles) VALUES ('utilisateur1@example.com', 1, 'Paysage', 'nature, montagnes, ciel');
INSERT INTO Marques (email, photo_id, tag, mots_cles) VALUES ('utilisateur2@example.com', 2, 'Portrait', 'personne, visage, sourire');
INSERT INTO Marques (email, photo_id, tag, mots_cles) VALUES ('utilisateur3@example.com', 3, 'Nourriture', 'repas, cuisine, délicieux');
INSERT INTO Marques (email, photo_id, tag, mots_cles) VALUES ('utilisateur4@example.com', 4, 'Animaux', 'chien, chat, mignon');
INSERT INTO Marques (email, photo_id, tag, mots_cles) VALUES ('utilisateur5@example.com', 5, 'Architecture', 'bâtiment, urbain, design');

-- Table des Vues
INSERT INTO Vue (email, photo_id, nombre_vues) VALUES ('utilisateur1@example.com', 1, 500);
INSERT INTO Vue (email, photo_id, nombre_vues) VALUES ('utilisateur2@example.com', 2, 800);
INSERT INTO Vue (email, photo_id, nombre_vues) VALUES ('utilisateur3@example.com', 3, 300);
INSERT INTO Vue (email, photo_id, nombre_vues) VALUES ('utilisateur4@example.com', 4, 1000);
INSERT INTO Vue (email, photo_id, nombre_vues) VALUES ('utilisateur5@example.com', 5, 600);

-- Table des ContenueNumerique
INSERT INTO ContenueNumerique (contenue_id) VALUES (1);
INSERT INTO ContenueNumerique (contenue_id) VALUES (2);
INSERT INTO ContenueNumerique (contenue_id) VALUES (3);
INSERT INTO ContenueNumerique (contenue_id) VALUES (4);
INSERT INTO ContenueNumerique (contenue_id) VALUES (5);

-- Table des Discussions
INSERT INTO Discussion (discussion_id, date_creation, photo_id) VALUES
	(1, TO_DATE('2023-09-19', 'YYYY-MM-DD'), 1);
INSERT INTO Discussion (discussion_id, date_creation, photo_id) VALUES
	(2, TO_DATE('2023-09-18', 'YYYY-MM-DD'), 2);
INSERT INTO Discussion (discussion_id, date_creation, photo_id) VALUES
	(3, TO_DATE('2023-09-17', 'YYYY-MM-DD'), 3);
INSERT INTO Discussion (discussion_id, date_creation, photo_id) VALUES
	(4, TO_DATE('2023-09-16', 'YYYY-MM-DD'), 4);
INSERT INTO Discussion (discussion_id, date_creation, photo_id) VALUES
	(5, TO_DATE('2023-09-15', 'YYYY-MM-DD'), 5);



-- Tables des Commentaires
INSERT INTO Commentaire (commentaire_id, contenue, email, discussion_id) VALUES (1, 'Très belle photo!', 'utilisateur1@example.com', 1);
INSERT INTO Commentaire (commentaire_id, contenue, email, discussion_id) VALUES (2, 'J adore cette image!', 'utilisateur2@example.com', 2);
INSERT INTO Commentaire (commentaire_id, contenue, email, discussion_id) VALUES (3, 'Superbe cuisine!', 'utilisateur3@example.com', 3);
INSERT INTO Commentaire (commentaire_id, contenue, email, discussion_id) VALUES (4, 'Trop mignon!', 'utilisateur4@example.com', 4);
INSERT INTO Commentaire (commentaire_id, contenue, email, discussion_id) VALUES (5, 'Architecture incroyable!', 'utilisateur5@example.com', 5);
