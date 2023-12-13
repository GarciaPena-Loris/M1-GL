package m1.archi.data;

import m1.archi.domaines.Adresse;
import m1.archi.domaines.Chambre;
import m1.archi.domaines.Hotel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class RandomDonneesHotel {
    // ---- IMAGE HOTEL ----
    // ## france ##
    private static final String[] listeImageFrance = {
            "https://i.imgur.com/ebcI7zy.jpg", "https://i.imgur.com/fwVqYAq.jpg", "https://i.imgur.com/GE7Ywjk.jpg", "https://i.imgur.com/m5D63cl.jpg", "https://i.imgur.com/IDXoEJy.jpg",
            "https://i.imgur.com/uSoX4ii.jpg", "https://i.imgur.com/CNUoLbC.jpg", "https://i.imgur.com/3bnyFeM.jpg", "https://i.imgur.com/7INYUVz.png", "https://i.imgur.com/tU9eQE9.jpg"
    };

    // ## grece ##
    private static final String[] listeImageGrece = {
            "https://i.imgur.com/yYA5KT3.jpg", "https://i.imgur.com/3UqyC5a.jpg", "https://i.imgur.com/kWDOBSw.jpg", "https://i.imgur.com/Aern4Yx.jpg", "https://i.imgur.com/5EYCijX.jpg",
            "https://i.imgur.com/eohYnqq.jpg", "https://i.imgur.com/goctoXp.jpg", "https://i.imgur.com/WJiqPAt.jpg", "https://i.imgur.com/HjW4xPV.jpg", "https://i.imgur.com/pNkuwf4.png",
            "https://i.imgur.com/VFk3dWP.jpg"
    };
    // ## espagne ##
    private static final String[] listeImageEspagne = {
            "https://i.imgur.com/L93Qcjp.jpg", "https://i.imgur.com/x6LTy9x.jpg", "https://i.imgur.com/TiYiIvC.jpg", "https://i.imgur.com/4K0vPpN.jpg", "https://i.imgur.com/JQzdJiz.jpg",
            "https://i.imgur.com/OJvRx0k.jpg"
    };
    // ## canada ##
    private static final String[] listeImageCanada = {
            "https://i.imgur.com/OMAKbuT.jpg", "https://i.imgur.com/Atc86Ni.jpg", "https://i.imgur.com/0qVZPMz.jpg", "https://i.imgur.com/BRZUfHv.jpg", "https://i.imgur.com/5touVxE.jpg",
            "https://i.imgur.com/ExsFui0.jpg", "https://i.imgur.com/xYrwlUm.jpg"
    };

    // ---- IMAGE CHAMBRE ----
    // ## grece ##
    private static final String[] listeChambre1 = {
            "https://i.imgur.com/dJYnFfQ.jpg", "https://i.imgur.com/gd8z1Mi.jpg", "https://i.imgur.com/9ntF2Tb.jpg", "https://i.imgur.com/raYNc4x.jpg", "https://i.imgur.com/MUEgTaS.jpg",
            "https://i.imgur.com/HEqy6Lc.jpg", "https://i.imgur.com/wdImS06.jpg", "https://i.imgur.com/yAmDoED.jpg", "https://i.imgur.com/tHpuyQU.jpg", "https://i.imgur.com/M6DP5GL.jpg",
            "https://i.imgur.com/do6Owkb.jpg", "https://i.imgur.com/rd4Z71M.jpg", "https://i.imgur.com/o4uKP8x.jpg", "https://i.imgur.com/0eD2ApM.jpg", "https://i.imgur.com/QsopCD2.jpg",
            "https://i.imgur.com/eAXoZgz.jpg", "https://i.imgur.com/sgn8j01.jpg", "https://i.imgur.com/qz8pwRR.jpg", "https://i.imgur.com/Iow28Fa.jpg", "https://i.imgur.com/qyCZaVd.jpg",
            "https://i.imgur.com/QS6ZmrF.jpg", "https://i.imgur.com/wYKL8JX.jpg", "https://i.imgur.com/9DqkX4t.jpg", "https://i.imgur.com/R07WP7I.jpg", "https://i.imgur.com/uBvZR1l.jpg",
            "https://i.imgur.com/XHg9qqV.png", "https://i.imgur.com/XOYzPd1.jpg", "https://i.imgur.com/OR7aqkn.png", "https://i.imgur.com/Yo13vvr.jpg", "https://i.imgur.com/CU6IKrN.jpg"
    };
    // ## espagne ##
    private static final String[] listeChambre2 = {
            "https://i.imgur.com/PsdNyjb.jpg", "https://i.imgur.com/GP2UBxZ.jpg", "https://i.imgur.com/cEKi0CN.jpg", "https://i.imgur.com/sKjTbF1.jpg", "https://i.imgur.com/vU8doAB.jpg",
            "https://i.imgur.com/FSNgSS2.jpg", "https://i.imgur.com/ZAI8y9z.jpg", "https://i.imgur.com/b8jHeEc.jpg", "https://i.imgur.com/DwXIg9I.jpg", "https://i.imgur.com/zHy2apT.jpg",
            "https://i.imgur.com/lOqSmAG.jpg", "https://i.imgur.com/a8KJDAe.jpg", "https://i.imgur.com/ieZDNmL.jpg", "https://i.imgur.com/L8WX2IO.jpg", "https://i.imgur.com/gMaYoTt.jpg",
            "https://i.imgur.com/pQj5PaD.jpg", "https://i.imgur.com/HQxP8nd.jpg", "https://i.imgur.com/gDyJbgJ.jpg", "https://i.imgur.com/7mNTXFZ.jpg", "https://i.imgur.com/tJ3Bjof.jpg",
            "https://i.imgur.com/qyQ0ihG.jpg", "https://i.imgur.com/6rlEeMQ.jpg", "https://i.imgur.com/2cghf2p.jpg", "https://i.imgur.com/LCsqvg6.jpg", "https://i.imgur.com/kLNUzK7.jpg",
            "https://i.imgur.com/QQEmWjl.jpg", "https://i.imgur.com/OIrBRdS.jpg", "https://i.imgur.com/9FmUPwv.jpg"
    };
    // ## canada ##
    private static final String[] listeChambre3 = {
            "https://i.imgur.com/6Jm0r2E.jpg", "https://i.imgur.com/BF1Sufr.jpg", "https://i.imgur.com/HEcGQaU.jpg", "https://i.imgur.com/qbWO8fD.jpg", "https://i.imgur.com/0PjG0dp.jpg",
            "https://i.imgur.com/DSHXKIV.jpg", "https://i.imgur.com/iSEojGo.jpg", "https://i.imgur.com/5tFPayU.jpg", "https://i.imgur.com/NrVQcAj.jpg", "https://i.imgur.com/Qnug453.jpg",
            "https://i.imgur.com/ukWAvNJ.jpg", "https://i.imgur.com/pB5MtLq.jpg", "https://i.imgur.com/CaCYbzq.jpg", "https://i.imgur.com/qhEEjnt.jpg", "https://i.imgur.com/Czi5ZLQ.jpg",
            "https://i.imgur.com/KoJud3c.jpg", "https://i.imgur.com/0ClohGG.jpg", "https://i.imgur.com/ZBcIQ3M.jpg", "https://i.imgur.com/18lxqGf.jpg", "https://i.imgur.com/RgZrAcw.jpg",
            "https://i.imgur.com/5lgRXYF.jpg", "https://i.imgur.com/2wpJ10S.jpg", "https://i.imgur.com/9RhiumW.jpg", "https://i.imgur.com/ldhQQkn.jpg", "https://i.imgur.com/xjqSbIW.jpg",
            "https://i.imgur.com/pxGe8w2.jpg", "https://i.imgur.com/PdPQOnI.jpg", "https://i.imgur.com/FSUxwL6.jpg", "https://i.imgur.com/M2gvX7Y.jpg", "https://i.imgur.com/fIKuSSv.jpg",
            "https://i.imgur.com/KUMepik.jpg", "https://i.imgur.com/xDVg3Gk.jpg", "https://i.imgur.com/mb5T6lF.jpg"
    };

    // ## 4 etoile ##
    private static final String[] listeChambre4 = {
            "https://i.imgur.com/pjh1oU6.jpg","https://i.imgur.com/Qjewq9P.jpg","https://i.imgur.com/7inE6jh.jpg","https://i.imgur.com/JhmZHH6.jpg","https://i.imgur.com/UL2aENN.jpg",
            "https://i.imgur.com/0QolnVy.jpg","https://i.imgur.com/EtUSoQy.jpg","https://i.imgur.com/r5EGC5G.jpg","https://i.imgur.com/agsdGme.jpg","https://i.imgur.com/OKS6R3m.jpg",
            "https://i.imgur.com/6i5U0Vc.jpg","https://i.imgur.com/dQ2ACFf.jpg","https://i.imgur.com/h8EpqwZ.jpg","https://i.imgur.com/JVwNCkg.jpg","https://i.imgur.com/jkUUZQ3.jpg",
            "https://i.imgur.com/Ygedniq.jpg","https://i.imgur.com/k4ba7uQ.png","https://i.imgur.com/BlXKhyx.jpg","https://i.imgur.com/a3wCRbO.jpg","https://i.imgur.com/YHY5OeD.jpg",
            "https://i.imgur.com/LWmohV1.jpg", "https://i.imgur.com/T4wzb8Z.jpg", "https://i.imgur.com/nSEwRPQ.jpg", "https://i.imgur.com/el0R9cn.jpg", "https://i.imgur.com/EIvgP47.jpg",
            "https://i.imgur.com/Wfbx9Ll.jpg"
    };
    // ## 5 etoile ##
    private static final String[] listeChambre5 = {
            "https://i.imgur.com/qVtQUsC.jpg","https://i.imgur.com/FBRWAVf.jpg","https://i.imgur.com/ha4IcXv.jpg","https://i.imgur.com/x60IA80.jpg","https://i.imgur.com/8EIOrpR.jpg",
            "https://i.imgur.com/rG8icxr.jpg","https://i.imgur.com/T2bQTF0.jpg","https://i.imgur.com/SQHA79R.jpg","https://i.imgur.com/koGenr1.jpg","https://i.imgur.com/Zrin2le.jpg",
            "https://i.imgur.com/3G8PYy5.jpg","https://i.imgur.com/T2bQTF0.jpg","https://i.imgur.com/SQHA79R.jpg","https://i.imgur.com/koGenr1.jpg","https://i.imgur.com/G67yj1X.jpg",
            "https://i.imgur.com/8LGbthV.jpg","https://i.imgur.com/kaCl80Q.jpg","https://i.imgur.com/dp3i15h.jpg","https://i.imgur.com/zZq5fcv.png","https://i.imgur.com/HPDrbpm.jpg",
            "https://i.imgur.com/cLPyBAK.jpg","https://i.imgur.com/9aM9F3s.jpg","https://i.imgur.com/kG68x8y.jpg","https://i.imgur.com/RXg8U8D.jpg","https://i.imgur.com/mklL5hB.jpg",
            "https://i.imgur.com/LEzGiFy.jpg","https://i.imgur.com/ndVpWrZ.jpg"
    };
    private static final String[] listeRues = {
            "Avenue des Orangers", "Rue des Étoiles Filantes", "Boulevard des Lumières", "Chemin de la Cascade", "Allée des Cerisiers",
            "Rue de la Sérénité", "Avenue des Trois Chênes", "Boulevard de l'Horizon", "Chemin des Roses", "Allée des Mélodies",
            "Rue des Perles", "Avenue du Crépuscule", "Boulevard des Montagnes", "Chemin des Papillons", "Allée des Hibiscus",
            "Rue de la Brise Marine", "Avenue des Oiseaux Chanteurs", "Boulevard des Saveurs", "Chemin de la Fontaine", "Allée des Émeraudes",
            "Rue des Rêves", "Avenue des Amoureux", "Boulevard de l'Enchantement", "Chemin des Arc-en-Ciel", "Allée des Palmiers",
            "Rue des Souvenirs", "Avenue des Poètes", "Boulevard de la Tranquillité", "Chemin des Lucioles", "Allée des Lys",
            "Rue des Illusions", "Avenue des Mirages", "Boulevard de la Douceur", "Chemin des Mélancolies", "Allée des Chansons",
            "Rue des Farfadets", "Avenue des Énigmes", "Boulevard de la Magie", "Chemin des Vignes", "Allée des Artistes",
            "Rue des Bougies", "Avenue des Rires", "Boulevard des Aurores", "Chemin des Étoiles Brillantes", "Allée des Parfums",
            "Rue des Légendes", "Avenue des Secrets", "Boulevard de l'Aventure", "Chemin des Papillons de Nuit", "Allée des Ombres",
            "Rue des Mystères", "Avenue des Contes de Fées", "Boulevard de la Liberté", "Chemin des Arômes", "Allée des Reflets",
            "Rue des Saisons", "Avenue des Embruns", "Boulevard des Merveilles", "Chemin des Sourires", "Allée des Étoiles du Matin"
    };

    private static final String[] listeNomHotel = {
            "Lumière d'Étoile", "Le Château Doré", "Auberge de la Brise Marine", "Lodge en Montagne", "Suites de la Vie Urbaine", "Vue sur la Rivière",
            "Paradis des Palmiers", "Lodge des Plages d'Or", "Couronne Royale", "Auberge des Eaux Azurées", "Manoir de la Vallée", "Retraite au Bord du Lac",
            "Suites Ciel Étoilé", "Spa Sérénité", "Vue sur le Port", "Auberge de l'Oasis Exotique", "Lodge des Pins Tranquilles",
            "Maison du Bois Rouge", "Refuge en Bord de Mer", "Lodge Montagneux", "Auberge des Pins Sereins", "Ciel Azur",
            "Auberge de la Baie de Corail", "Auberge des Rives d'Argent", "Plaza Royale", "Émeraude Île", "Retraite au Bord de la Rivière",
            "Auberge Vue sur le Port", "Palais Doré", "Auberge du Charme Cottage", "Suites Prestige", "Auberge au Bord du Lac",
            "Manoir des Bois Murmureurs", "Porte Impériale", "Auberge de la Brise Côtière", "Villa Étoilée", "Retraite de Plage aux Palmiers",
            "Lodge au Bord du Lac", "Palais de Saphir", "Rive de la Rivière", "Auberge de la Campagne Tranquille", "Port de Lumière",
            "Manoir Clair de Lune Mirage", "Auberge Vue sur le Soleil Couchant", "Lodge Vue sur la Mer", "Auberge au Paradis Tropical",
            "Retraite Impériale", "Majesté Montagnarde", "Suites du Centre-Ville", "Auberge de la Baie de Corail", "Auberge de l'Île d'Émeraude",
            "Auberge au Bord de la Rivière", "Retraite au Port de Marina", "Palais aux Palmiers Dorés", "Retraite au Bord de la Rivière Tranquille",
            "Oasis de Luxe", "Bord du Port Harmonieux", "Printemps de l'Émeraude", "Retraite au Bord du Lac Scintillant", "Vue sur le Soleil Couchant Paradisiaque",
            "Retraite au Bord du Lac Paisible", "Retraite au Port de Marina", "Château d'Émeraude", "Lodge du Charme Cottage", "Plaza Prestige",
            "Auberge de Sérénité au Bord du Lac Tranquille", "Auberge de la Campagne Chuchotante"
    };

    private static final String[] listePays = {
            "France", "Espagne", "Grece", "Canada"
    };

    private static final HashMap<String, ArrayList<String>> listeVillePays = new HashMap<>();

    static {
        listeVillePays.put("France", new ArrayList<>() {{
            add("Paris");
            add("Toulouse");
            add("Nice");
        }});
        listeVillePays.put("Espagne", new ArrayList<>() {{
            add("Madrid");
            add("Barcelone");
            add("Valence");
        }});
        listeVillePays.put("Grece", new ArrayList<>() {{
            add("Athènes");
            add("Rhodes");
        }});
        listeVillePays.put("Canada", new ArrayList<>() {{
            add("Toronto");
            add("Montréal");
        }});
    }

    private static <T> T getElementListeAleatoire(T[] liste) {
        return liste[new Random().nextInt(liste.length)];
    }

    public static String randomNomHotel() {
        return getElementListeAleatoire(listeNomHotel);
    }

    public static String randomRue() {
        return getElementListeAleatoire(listeRues);
    }

    public static String randomPays() {
        return getElementListeAleatoire(listePays);
    }

    public static String randomVille(String pays) {
        ArrayList<String> villesPays = listeVillePays.get(pays);
        return getElementListeAleatoire(villesPays.toArray(new String[0]));
    }

    public static String randomNumero() {
        Random random = new Random();
        String numeroRue = String.valueOf(random.nextInt(100) + 1); // Numéro de rue entre 1 et 100
        if (random.nextInt(8) == 0) {
            numeroRue += "bis"; // Ajoute "bis" avec une chance sur 8
        } else if (random.nextInt(15) == 0) {
            numeroRue += "ter"; // Ajoute "ter" avec une chance sur 15
        } else if (random.nextInt(100) == 0) {
            numeroRue += "shrek"; // Ajoute "shrek" avec une chance sur 100
        }
        return numeroRue;
    }

    public static String randomPositionGPS() {
        return new Random().nextDouble() * 180 - 90 + "," + (new Random().nextDouble() * 360 - 180); // Position GPS aléatoire
    }

    public static int randomNombreEtoiles() {
        return new Random().nextInt(5) + 1; // Nombre d'étoiles entre 1 et 5
    }

    public static int randomPrix(int nombreEtoile, int nombreLits) {
        switch (nombreEtoile) {
            case 1:
                return new Random().nextInt(10) + 30 + (nombreLits * 10); // Prix entre 50 et 80 + 5€ par lit
            case 2:
                return new Random().nextInt(20) + 55 + (nombreLits * 12); // Prix entre 55 et 85 + 5€ par lit
            case 3:
                return new Random().nextInt(40) + 90 + (nombreLits * 15); // Prix entre 60 et 90 + 5€ par lit
            case 4:
                return new Random().nextInt(80) + 130 + (nombreLits * 15); // Prix entre 65 et 95 + 5€ par lit
            case 5:
                return new Random().nextInt(250) + 200 + (nombreLits * 20); // Prix entre 70 et 100 + 5€ par lit
            default:
                return new Random().nextInt(50) + (nombreLits * (nombreEtoile * 5)) + 50 * nombreEtoile; // Prix entre 50 et 130 + 5€ par lit + 50€ par étoile  (prix entre 50 et 130 pour 1 étoile, entre 55 et 135 pour 2 étoiles, etc.)
        }
    }

    public static int randomNombreLits() {
        return new Random().nextInt(4) + 1; // Nombre de personnes entre 1 et 4
    }

    public static String randomImageChambre(int nombreEtoiles) {
        return switch (nombreEtoiles) {
            case 1 -> getElementListeAleatoire(listeChambre1);
            case 2 -> getElementListeAleatoire(listeChambre2);
            case 3 -> getElementListeAleatoire(listeChambre3);
            case 4 -> getElementListeAleatoire(listeChambre4);
            case 5 -> getElementListeAleatoire(listeChambre5);
            default -> null;
        };
    }

    public static String randomImageHotel(String pays) {
        return switch (pays) {
            case "France" -> getElementListeAleatoire(listeImageFrance);
            case "Espagne" -> getElementListeAleatoire(listeImageEspagne);
            case "Grece" -> getElementListeAleatoire(listeImageGrece);
            case "Canada" -> getElementListeAleatoire(listeImageCanada);
            default -> null;
        };
    }

    public static int randomNombreChambres() {
        return new Random().nextInt(50) + 5; // Nombre de chambres entre 1 et 10
    }

    /* Hotel */
    public static Hotel generateRandomHotel(Adresse adresse) {
        String nomHotel = RandomDonneesHotel.randomNomHotel();
        int nombreEtoiles = RandomDonneesHotel.randomNombreEtoiles();
        String imageHotel = RandomDonneesHotel.randomImageHotel(adresse.getPays());

        return new Hotel(null, nomHotel, adresse, nombreEtoiles, imageHotel, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public static Adresse generateRandomAdresse() {
        String pays = RandomDonneesHotel.randomPays();
        String ville = RandomDonneesHotel.randomVille(pays);
        String rue = RandomDonneesHotel.randomRue();
        String numero = RandomDonneesHotel.randomNumero();
        String position = RandomDonneesHotel.randomPositionGPS();

        return new Adresse(null, pays, ville, rue, numero, position);
    }

    public static Chambre generateRandomChambres(Hotel hotel, int numero) {
        int nombreLits = RandomDonneesHotel.randomNombreLits();
        int prix = RandomDonneesHotel.randomPrix(hotel.getNombreEtoiles(), nombreLits);
        String imageChambre = RandomDonneesHotel.randomImageChambre(hotel.getNombreEtoiles());

        return new Chambre(null, numero, prix, nombreLits, hotel.getIdHotel(), imageChambre);
    }
}
