package m1.archi.service;

import m1.archi.model.Chambre;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class RandomDonneStockage {
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
            "Hôtel Lumière d'Étoile", "Le Château Doré", "Auberge de la Brise Marine", "Lodge en Montagne", "Suites de la Vie Urbaine", "Hôtel de la Rivière",
            "Hôtel Paradis des Palmiers", "Lodge des Plages d'Or", "Couronne Royale", "Auberge des Eaux Azurées", "Manoir de la Vallée", "Retraite au Bord du Lac",
            "Suites Ciel Étoilé", "Hôtel Spa Sérénité", "Hôtel Vue sur le Port", "Auberge de l'Oasis Exotique", "Lodge des Pins Tranquilles",
            "Maison du Bois Rouge", "Refuge en Bord de Mer", "Lodge Montagneux", "Auberge des Pins Sereins", "Hôtel Ciel Azur",
            "Auberge de la Baie de Corail", "Auberge des Rives d'Argent", "Plaza Royale", "Hôtel de l'Émeraude Île", "Retraite au Bord de la Rivière",
            "Auberge Vue sur le Port", "Palais Doré", "Auberge du Charme Cottage", "Suites Prestige", "Auberge au Bord du Lac",
            "Manoir des Bois Murmureurs", "Hôtel de la Porte Impériale", "Auberge de la Brise Côtière", "Villa Étoilée", "Retraite de Plage aux Palmiers",
            "Lodge au Bord du Lac", "Palais de Saphir", "Hôtel de la Rive de la Rivière", "Auberge de la Campagne Tranquille", "Hôtel Port de Lumière",
            "Manoir Clair de Lune Mirage", "Auberge Vue sur le Soleil Couchant", "Lodge Vue sur la Mer", "Auberge au Paradis Tropical",
            "Retraite Impériale", "Hôtel de la Majesté Montagnarde", "Suites du Centre-Ville", "Auberge de la Baie de Corail", "Auberge de l'Île d'Émeraude",
            "Auberge au Bord de la Rivière", "Retraite au Port de Marina", "Palais aux Palmiers Dorés", "Retraite au Bord de la Rivière Tranquille",
            "Oasis de Luxe", "Hôtel au Bord du Port Harmonieux", "Printemps de l'Émeraude", "Retraite au Bord du Lac Scintillant", "Vue sur le Soleil Couchant Paradisiaque",
            "Retraite au Bord du Lac Paisible", "Retraite au Port de Marina", "Château d'Émeraude", "Lodge du Charme Cottage", "Plaza Prestige",
            "Auberge de Sérénité au Bord du Lac Tranquille", "Auberge de la Campagne Chuchotante"
    };

    private static final String[] listePays = {
            "France", "Espagne", "Italie", "Allemagne", "Royaume-Uni",
            "Portugal", "Grèce", "Suisse", "Belgique", "Canada"
    };

    private static final HashMap<String, ArrayList<String>> listeVillePays = new HashMap<>();

    static {
        listeVillePays.put("France", new ArrayList<String>() {{
            add("Paris");
            add("Marseille");
            add("Lyon");
            add("Toulouse");
            add("Nice");
        }});
        listeVillePays.put("Espagne", new ArrayList<String>() {{
            add("Madrid");
            add("Barcelone");
            add("Valence");
            add("Séville");
            add("Bilbao");
        }});
        listeVillePays.put("Italie", new ArrayList<String>() {{
            add("Rome");
            add("Milan");
            add("Naples");
            add("Turin");
            add("Florence");
        }});
        listeVillePays.put("Allemagne", new ArrayList<String>() {{
            add("Berlin");
            add("Munich");
            add("Hambourg");
            add("Cologne");
            add("Francfort");
        }});
        listeVillePays.put("Royaume-Uni", new ArrayList<String>() {{
            add("Londres");
            add("Manchester");
            add("Birmingham");
            add("Glasgow");
            add("Édimbourg");
        }});
        listeVillePays.put("Portugal", new ArrayList<String>() {{
            add("Lisbonne");
            add("Porto");
            add("Coimbra");
            add("Faro");
            add("Braga");
        }});
        listeVillePays.put("Grèce", new ArrayList<String>() {{
            add("Athènes");
            add("Thessalonique");
            add("Héraklion");
            add("Rhodes");
            add("Patras");
        }});
        listeVillePays.put("Suisse", new ArrayList<String>() {{
            add("Zurich");
            add("Genève");
            add("Bâle");
            add("Berne");
            add("Lausanne");
        }});
        listeVillePays.put("Belgique", new ArrayList<String>() {{
            add("Bruxelles");
            add("Anvers");
            add("Gand");
            add("Liège");
            add("Bruges");
        }});
        listeVillePays.put("Canada", new ArrayList<String>() {{
            add("Toronto");
            add("Montréal");
            add("Vancouver");
            add("Ottawa");
            add("Québec");
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
        return getElementListeAleatoire(villesPays.toArray(new String[villesPays.size()]));
    }

    public static String randomNumero() {
        Random random = new Random();
        String numeroRue = String.valueOf(random.nextInt(100) + 1); // Numéro de rue entre 1 et 100
        if (random.nextInt(4) == 0) {
            numeroRue += "bis"; // Ajoute "bis" avec une chance sur 4
        }
        else if (random.nextInt(10) == 0) {
            numeroRue += "ter"; // Ajoute "ter" avec une chance sur 10
        }
        else if (random.nextInt(100) == 0) {
            numeroRue += "shrek";
        }
        return String.valueOf(numeroRue);
    }

    public static String randomPositionGPS() {
        return String.valueOf(new Random().nextDouble() * 180 - 90) + "," + String.valueOf(new Random().nextDouble() * 360 - 180); // Position GPS aléatoire
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

    public static String randomIdentifiantHotel() {
        Date date = new Date();
        return "H" + date.getTime();

    }
    public static int randomNombreChambres() {
        return new Random().nextInt(50) + 1; // Nombre de chambres entre 1 et 10
    }

}
