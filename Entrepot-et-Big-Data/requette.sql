-- Sélectionner les photographie de montpellier avec le flash depuis la base de données
SELECT *
FROM Photographie
WHERE lieu = 'Montpellier' AND configuration_id IN (
    SELECT configuration_id
    FROM Configurations
    WHERE flash = 1
);

-- Sélectionner toutes les collections d'un utilisateur spécifique
SELECT collection_id, description
FROM CollectionDePhotos
WHERE email = 'utilisateur1@example.com';


-- Sélectionner la liste des utilisateurs ayant le plus grand nombre de likes sur leurs photos
SELECT U.nom, U.email, MAX(L.nombre_like) AS nombre_likes_max
FROM Utilisateur U
JOIN Likes L ON U.email = L.email
GROUP BY U.nom, U.email
ORDER BY nombre_likes_max DESC
FETCH FIRST 2 ROWS ONLY;





-- Sélectionner les photos les plus appréciées avec la licence 'tous droits réservés'
SELECT P.photo_id, L.nombre_like
FROM Photographie P
JOIN Likes L ON P.photo_id = L.photo_id
JOIN LicenceDeDistribution LD ON P.licence_id = LD.licence_id
WHERE LD.droit_reserves = 'Tous droits réservés'
GROUP BY P.photo_id, L.nombre_like
ORDER BY nombre_like DESC
FETCH FIRST 2 ROWS ONLY;


-- Sélectionner les photos incluses dans le plus grand nombre de galeries
SELECT P.photo_id, COUNT(GP.galerie_id) AS nombre_de_galeries
FROM Photographie P
JOIN Contient C ON P.photo_id = C.photo_id
JOIN GaleriePublicateur GP ON C.collection_id = GP.galerie_id
GROUP BY P.photo_id
ORDER BY nombre_de_galeries DESC
FETCH FIRST 2 ROWS ONLY;
