package m1.archi.model;

import m1.archi.exception.*;
import m1.archi.exception.DateNonValideException;
import m1.archi.hotel.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Agence {
    private String identifiant;
    private String nom;
    private HashMap<String, Integer> mapIdentifiantsHotelsPartenairesReduction;
    private ArrayList<User> listeUsers;

    public Agence() {
    }

    public Agence(String identifiant, String nom) {
        this.identifiant = identifiant;
        this.nom = nom;
        this.mapIdentifiantsHotelsPartenairesReduction = new HashMap<String, Integer>();
        this.listeUsers = new ArrayList<User>();
    }

    public User inscriptionUser(String login, String motDePasse) throws UserAlreadyExistsException {
        // Check if user already exists
        for (User u : listeUsers) {
            if (u.getLogin().equals(login)) {
                throw new UserAlreadyExistsException("Error: User " + login + " already exists");
            }
        }
        try {
            User user = new User(login, motDePasse);
            this.addUser(user);
            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public User connectionUser(String login, String motDePasse) throws UserNotFoundException {
        for (User u : listeUsers) {
            if (u.getLogin().equals(login) && u.getMotDePasse().equals(motDePasse)) {
                return u;
            }
        }
        throw new UserNotFoundException("Error: User " + login + " not found");
    }

    public boolean addReservation(String login, String motDePasse, Reservation reservation) throws UserNotFoundException {
        // Essayer de se connecter
        connectionUser(login, motDePasse);

        for (User u : listeUsers) {
            if (u.getLogin().equals(login)) {
                u.addReservation(reservation);
                return true;
            }
        }
        return false;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public HashMap<String, Integer> getIdentifiantHotelPartenaire() {
        return mapIdentifiantsHotelsPartenairesReduction;
    }

    public void setMapIdentifiantsHotelsPartenairesReduction(HashMap<String, Integer> mapIdentifiantsHotelsPartenairesReduction) {
        this.mapIdentifiantsHotelsPartenairesReduction = mapIdentifiantsHotelsPartenairesReduction;
    }

    public void addIdentifiantHotelPartenaire(String identifiantHotelPartenaire, int reduction) throws IdentifiantAlreadyExistsException {
        if (this.mapIdentifiantsHotelsPartenairesReduction.containsKey(identifiantHotelPartenaire))
            throw new IdentifiantAlreadyExistsException("Error: Hotel " + identifiantHotelPartenaire + " already exists");
        this.mapIdentifiantsHotelsPartenairesReduction.put(identifiantHotelPartenaire, reduction);
    }

    public void removeIdentifiantHotelPartenaire(String identifiantHotelPartenaire) throws IdentifiantNotfoundException {
        if (!this.mapIdentifiantsHotelsPartenairesReduction.containsKey(identifiantHotelPartenaire))
            throw new IdentifiantNotfoundException("Error: Hotel " + identifiantHotelPartenaire + " not found");
        this.mapIdentifiantsHotelsPartenairesReduction.remove(identifiantHotelPartenaire);
    }

    public ArrayList<User> getlisteUsers() {
        return listeUsers;
    }

    public void setlisteUsers(ArrayList<User> listeUsers) {
        this.listeUsers = listeUsers;
    }

    public void addUser(User user) throws IdentifiantAlreadyExistsException {
        if (this.listeUsers.contains(user))
            throw new IdentifiantAlreadyExistsException("Error: User " + user.getLogin() + " already exists");
        this.listeUsers.add(user);
    }

    public void removeUser(User user) throws UserNotFoundException {
        if (!this.listeUsers.contains(user))
            throw new UserNotFoundException("Error: User " + user.getLogin() + " not found");
        this.listeUsers.remove(user);
    }

    /* FONCTION */
    public ArrayList<Offres> listeChoisOffresHotelCriteres(String login, String motDePasse, String ville, Date dateArrivee, Date dateDepart, int prixMin, int prixMax, int nombreEtoiles, int nombrePersonne) throws DateNonValideException, UserNotFoundException {
        // Essayer de se connecter
        connectionUser(login, motDePasse);

        ArrayList<Offres> listeChoisOffresHotel = new ArrayList<Offres>();
        for (String identifiantHotel : mapIdentifiantsHotelsPartenairesReduction.keySet()) {
            try {
                URL url = new URL("http://localhost:8080/hotelservice/" + identifiantHotel + "/consultation");
                HotelServiceConsultationImplService hotelServiceConsultation = new HotelServiceConsultationImplService(url);
                HotelServiceConsultation proxy = hotelServiceConsultation.getHotelServiceConsultationImplPort();

                DatatypeFactory df = DatatypeFactory.newInstance();
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(dateArrivee);
                XMLGregorianCalendar xmlDateArrivee = df.newXMLGregorianCalendar(gc);
                gc.setTime(dateDepart);
                XMLGregorianCalendar xmlDateDepart = df.newXMLGregorianCalendar(gc);

                Offres offres = new Offres();
                ArrayList<Offre> listeOffres = (ArrayList<Offre>) proxy.getChambreDisponibleCriteres(ville, xmlDateArrivee, xmlDateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);

                // Pour chaque offres de la liste on change le momant total avec la reduction de l'hotel
                for (Offre offre : listeOffres) {
                    int reduction = mapIdentifiantsHotelsPartenairesReduction.get(identifiantHotel);
                    offre.setPrix(Math.round(offre.getPrix() * (1 - reduction / 100.0) * 100.0) / 100.0);
                }

                offres.setOffres(listeOffres);

                if (!listeOffres.isEmpty())
                    listeChoisOffresHotel.add(offres);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (DatatypeConfigurationException | DateNonValideException_Exception e) {
                throw new DateNonValideException(e);
            }
        }
        return listeChoisOffresHotel;

    }

    public Reservation reserverChambresHotel(String login, String motDePasse, Offre offre, boolean petitDejeuner, String nomClient, String prenomClient, String email, String telephone, String nomCarte, String numeroCarte, String expirationCarte, String CCVCarte) throws DateNonValideException, UserNotFoundException, ReservationProblemeException {
        // Essayer de se connecter
        connectionUser(login, motDePasse);

        try {
            URL url = new URL("http://localhost:8080/hotelservice/" + offre.getIdHotel() + "/reservation");
            HotelServiceReservationImplService hotelServiceReservation = new HotelServiceReservationImplService(url);
            HotelServiceReservation proxy = hotelServiceReservation.getHotelServiceReservationImplPort();

            return proxy.reserverChambres(offre, petitDejeuner, nomClient, prenomClient, email, telephone, nomCarte, numeroCarte, expirationCarte, CCVCarte);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (DateNonValideException_Exception e) {
            throw new DateNonValideException(e);
        } catch (Exception e) {
            throw new ReservationProblemeException("Error: Probleme lors de la reservation");
        }

    }

    public String getAgenceInfo() {
        String res = "L'agence '" + this.nom + "' (" + this.identifiant + ") possède " + this.mapIdentifiantsHotelsPartenairesReduction.keySet().size() + " hotels partenaires :\n";

        int compteur = 1;
        for (String hotel : this.mapIdentifiantsHotelsPartenairesReduction.keySet()) {
            res += "\t" + compteur + "- L'hotel (" + hotel + ") avec une reduction de " + this.mapIdentifiantsHotelsPartenairesReduction.get(hotel) + "% sur les chambres.\n";
            compteur++;
        }
        return res;
    }
}
