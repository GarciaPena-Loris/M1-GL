package m1.archi.resthotel.controllers;

import m1.archi.resthotel.exceptions.DateNonValideException;
import m1.archi.resthotel.exceptions.HotelException;
import m1.archi.resthotel.exceptions.HotelNotFoundException;
import m1.archi.resthotel.exceptions.OffreNotFoundException;
import m1.archi.resthotel.models.*;
import m1.archi.resthotel.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class HotelController {
    /* Attributs */
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private OffreRepository offreRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CarteRepository carteRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    /* Méthodes */
    @GetMapping("${base-uri}/hotels")
    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    @GetMapping("${base-uri}/idHotels")
    public List<Long> getAllIdHotels() {
        return hotelRepository.findAll().stream().map(Hotel::getIdHotel).collect(Collectors.toList());
    }

    @GetMapping("${base-uri}/hotels/{id}/image")
    public String getHotelImage(@PathVariable long id) throws HotelNotFoundException {
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id)).getImageHotel();
    }

    @GetMapping("${base-uri}/hotels/{id}/chambres")
    public List<Chambre> getHotelChambres(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        return new ArrayList<>(hotel.getChambres());
    }

    @GetMapping("${base-uri}/hotels/{id}/offres")
    public List<Offre> getHotelOffres(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        return new ArrayList<>(hotel.getOffres());
    }

    @GetMapping("${base-uri}/hotels/{id}/reservations")
    public List<Reservation> getHotelReservations(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        return new ArrayList<>(hotel.getReservations());
    }

    @GetMapping("${base-uri}/hotels/count")
    public String count() {
        return String.format("{\"%s\": %d}", "count", hotelRepository.count());
    }

    @GetMapping("${base-uri}/hotels/{id}")
    public Hotel getHotelById(@PathVariable long id) throws HotelNotFoundException {
        return hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
    }

    @GetMapping("${base-uri}/hotels/{id}/recherche")
    public List<Offre> rechercheChambreById(@PathVariable long id, @RequestParam String ville, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime dateArrivee,
                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime dateDepart, @RequestParam int prixMin,
                                            @RequestParam int prixMax, @RequestParam int nombreEtoiles, @RequestParam int nombrePersonne) throws HotelNotFoundException, DateNonValideException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        String villeDecode = URLDecoder.decode(ville, StandardCharsets.UTF_8);
        List<Offre> offers = hotel.rechercheChambres(villeDecode, dateArrivee, dateDepart, prixMin, prixMax, nombreEtoiles, nombrePersonne);
        if (offers.isEmpty()) {
            return offers;
        }
        for (Offre offre : offers) {
            offreRepository.save(offre);
            hotel.addOffre(offre);
        }
        hotelRepository.save(hotel);
        return offers;
    }

    @PostMapping("${base-uri}/hotels/{id}/reservation")
    @Transactional
    public Reservation reserverChambreById(@PathVariable long id, @RequestParam long idOffre, @RequestParam boolean petitDejeuner, @RequestParam String nomClient,
                                           @RequestParam String prenomClient, @RequestParam String email, @RequestParam String telephone, @RequestParam String nomCarte,
                                           @RequestParam String numeroCarte, @RequestParam String expirationCarte, @RequestParam String CCVCarte) throws HotelException, DateNonValideException, OffreNotFoundException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        Offre storedOffre = offreRepository.findById(idOffre).orElseThrow(() -> new OffreNotFoundException("Offre not found"));
        try {
            if (storedOffre.getDateExpiration().isBefore(LocalDateTime.now())) {
                throw new DateNonValideException("Offre expired");
            }

            // Créer une carte si elle n'existe pas déjà
            Carte carte = carteRepository.findByNumero(numeroCarte).orElseGet(() -> carteRepository.save(new Carte(nomCarte, numeroCarte, expirationCarte, CCVCarte)));
            carteRepository.save(carte);

            // Créer un client si il n'existe pas déjà
            Client clientPrincipal = clientRepository.findByEmail(email).orElseGet(() -> clientRepository.save(new Client(nomClient, prenomClient, email, telephone, carte)));
            clientRepository.save(clientPrincipal);

            // Créer la réservation
            double montantReservation = storedOffre.getPrixAvecReduction();
            if (petitDejeuner) {
                int nombreNuits = (int) (storedOffre.getDateDepart().toLocalDate().toEpochDay() - storedOffre.getDateArrivee().toLocalDate().toEpochDay());
                double montantPetitDejeuner = (storedOffre.getHotel().getNombreEtoiles() * 4) * storedOffre.getNombreLitsTotal() * nombreNuits;
                montantReservation += montantPetitDejeuner;
            }
            Reservation reservation = new Reservation(hotel, clientPrincipal, storedOffre.getDateArrivee(), storedOffre.getDateDepart(), storedOffre.getNombreLitsTotal(), (double) Math.round(montantReservation * 10) / 10, petitDejeuner);
            reservationRepository.save(reservation);

            for (Chambre chambre : storedOffre.getChambres()) {
                reservation.addChambreReservee(chambre);
            }
            reservationRepository.save(reservation);

            // Ajouter la réservation à l'hotel et a l'historique du client
            hotel.addReservation(reservation);
            hotelRepository.save(hotel);

            clientPrincipal.addReservationToHistorique(reservation);
            clientRepository.save(clientPrincipal);

            return reservation;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new HotelException("Une erreur est survenue lors de la réservation " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("${base-uri}/hotels")
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @PutMapping("${base-uri}/hotels/{id}")
    public Hotel updateHotel(@RequestBody Hotel newHotel, @PathVariable long id) {
        return hotelRepository.findById(id).map(hotel -> {
            hotel.setNom(newHotel.getNom());
            hotel.setAdresse(newHotel.getAdresse());
            hotel.setNombreEtoiles(newHotel.getNombreEtoiles());
            hotel.setImageHotel(newHotel.getImageHotel());
            hotel.setChambres(newHotel.getChambres());
            hotel.setReservations(newHotel.getReservations());
            return hotelRepository.save(hotel);
        }).orElseGet(() -> hotelRepository.save(newHotel));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("${base-uri}/hotels/{id}")
    public void deteleHotel(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        hotelRepository.delete(hotel);
    }

    // Modifier une offre
    @PutMapping("${base-uri}/hotels/{id}/offres/{idOffre}")
    public Offre updateOffre(@RequestBody Offre newOffre, @PathVariable long id, @PathVariable long idOffre) throws HotelNotFoundException, OffreNotFoundException {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        return offreRepository.findById(idOffre).map(offre -> {
            offre.setNombreLitsTotal(newOffre.getNombreLitsTotal());
            offre.setPrix(newOffre.getPrix());
            offre.setPrixAvecReduction(newOffre.getPrixAvecReduction());
            offre.setDateArrivee(newOffre.getDateArrivee());
            offre.setDateDepart(newOffre.getDateDepart());
            offre.setDateExpiration(newOffre.getDateExpiration());
            offre.setChambres(newOffre.getChambres());
            offre.setHotel(newOffre.getHotel());
            return offreRepository.save(offre);
        }).orElseGet(() -> offreRepository.save(newOffre));
    }

}
