-- Cachier des charges #1

-- Table des Utilisateur
CREATE TABLE Utilisateur (
    email VARCHAR(255) PRIMARY KEY,
    nom VARCHAR(255),
    adresse VARCHAR(255)
);


-- Table des Appareils Photo
CREATE TABLE AppareilPhoto (
    appareil_id INT PRIMARY KEY,
    modele VARCHAR(255)
);

-- Table des Configurationss
CREATE TABLE Configurations (
    configuration_id INT PRIMARY KEY,
    ouverture_focale DECIMAL(4, 2),
    temps_exposition DECIMAL(4, 2),
    flash NUMBER(1),
    distance_focale DECIMAL(4, 2)
);

-- Table des LicenceDeDistribution
CREATE TABLE LicenceDeDistribution (
    licence_id INT PRIMARY KEY,
    droit_reserves VARCHAR(255),
    utilisation_commerciale NUMBER(1),
    modification_autorisee NUMBER(1)
);

-- Table des Photographie
CREATE TABLE Photographie (
    photo_id INT PRIMARY KEY,
    lieu VARCHAR(255),
    date_prise DATE,
    appareil_id INT,
    configuration_id INT,
    licence_id INT,
    FOREIGN KEY (photo_id) REFERENCES ContenueNumerique (contenue_id),
    FOREIGN KEY (appareil_id) REFERENCES AppareilPhoto (appareil_id),
    FOREIGN KEY (configuration_id) REFERENCES Configurations (configuration_id),
    FOREIGN KEY (licence_id) REFERENCES LicenceDeDistribution (licence_id)
);

-- Table associant les Photographie aux Utilisateur
CREATE TABLE Publication (
    email VARCHAR(255),
    photo_id INT,
    date_publication DATE,
    PRIMARY KEY (email, photo_id, date_publication),
    FOREIGN KEY (email) REFERENCES Utilisateur (email),
    FOREIGN KEY (photo_id) REFERENCES Photographie (photo_id)
);


-- ############################################################################################################
-- Cachier des charges #2

-- Table des CollectionsDePhotos
CREATE TABLE CollectionDePhotos (
    collection_id INT PRIMARY KEY,
    description VARCHAR(255),
    email VARCHAR(255),
    FOREIGN KEY (email) REFERENCES Utilisateur (email)
);

-- Table d'association entre les CollectionsDePhotos et les Photographie
CREATE TABLE Contient (
    collection_id INT,
    photo_id INT,
    PRIMARY KEY (collection_id, photo_id),
    FOREIGN KEY (collection_id) REFERENCES CollectionDePhotos (collection_id),
    FOREIGN KEY (photo_id) REFERENCES Photographie (photo_id)
);

-- Table des Albums
CREATE TABLE Album (
    albums_id INT,   
    PRIMARY KEY (albums_id),
    FOREIGN KEY (albums_id) REFERENCES CollectionDePhotos (collection_id)
);

-- Table des Galerie
CREATE TABLE Galerie (
    galerie_id INT,
    PRIMARY KEY (galerie_id),
    FOREIGN KEY (galerie_id) REFERENCES CollectionDePhotos (collection_id)
);

-- Table d'association Galerie - Utilisateur
CREATE TABLE GaleriePublicateur (
    galerie_id INT,
    email VARCHAR(255),
    PRIMARY KEY (galerie_id, email),
    FOREIGN KEY (galerie_id) REFERENCES Galerie (galerie_id),
    FOREIGN KEY (email) REFERENCES Utilisateur (email)
);

-- ############################################################################################################
-- Cachier des charges #3

-- Table des Abonnements
CREATE TABLE Abonnement (
    email VARCHAR(255),
    email_abonnement VARCHAR(255),
    PRIMARY KEY (email, email_abonnement),
    FOREIGN KEY (email) REFERENCES Utilisateur (email),
    FOREIGN KEY (email_abonnement) REFERENCES Utilisateur (email)
);

-- Table des Likes
CREATE TABLE Likes (
    email VARCHAR(255),
    photo_id INT,SEL
    nombre_like INT,
    PRIMARY KEY (email, photo_id),
    FOREIGN KEY (email) REFERENCES Utilisateur (email),
    FOREIGN KEY (photo_id) REFERENCES Photographie (photo_id)
);

-- Table des Marques
CREATE TABLE Marques (
    email VARCHAR(255),
    photo_id INT,
    tag VARCHAR(255),
    mots_cles VARCHAR(255),
    PRIMARY KEY (email, photo_id),
    FOREIGN KEY (email) REFERENCES Utilisateur (email),
    FOREIGN KEY (photo_id) REFERENCES Photographie (photo_id)
);

-- Table des Vues
CREATE TABLE Vue (
    email VARCHAR(255),
    photo_id INT,
    nombre_vues INT,
    PRIMARY KEY (email, photo_id),
    FOREIGN KEY (email) REFERENCES Utilisateur (email),
    FOREIGN KEY (photo_id) REFERENCES Photographie (photo_id)
);

-- Table des ContenueNumerique
CREATE TABLE ContenueNumerique (
    contenue_id INT PRIMARY KEY
);

-- Table des Discussions
CREATE TABLE Discussion (
    discussion_id Int PRIMARY KEY,
    date_creation DATE,
    photo_id INT,
    FOREIGN KEY (photo_id) REFERENCES Photographie (photo_id) ON DELETE CASCADE
);

-- Tables des Commentaires
CREATE TABLE Commentaire (
    commentaire_id,
    contenue VARCHAR(255),
    email VARCHAR(255), 
    discussion_id INT,
    PRIMARY KEY (commentaire_id),
    FOREIGN KEY (commentaire_id) REFERENCES ContenueNumerique (contenue_id),
    FOREIGN KEY (email) REFERENCES Utilisateur (email),
    FOREIGN KEY (discussion_id) REFERENCES Discussion (discussion_id) ON DELETE CASCADE
);
