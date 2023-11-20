package m1.archi.resthotel.controllers;

import m1.archi.resthotel.exceptions.HotelNotFoundException;
import m1.archi.resthotel.models.Hotel;
import m1.archi.resthotel.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HotelController {
    /* Attributs */
    @Autowired
    private HotelRepository repository;

    /* Méthodes */
    @GetMapping("${base-uri}/hotels")
    public List<Hotel> getAllHotels() {
        return repository.findAll();
    }

    @GetMapping("${base-uri}/hotels/count")
    public String count() {
        return String.format("{\"%s\": %d}", "count", repository.count());
    }

    @GetMapping("${base-uri}/hotels/{id}")
    public Hotel getHotelById(@PathVariable long id) throws HotelNotFoundException {
        return repository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("${base-uri}/hotels")
    public Hotel createHotel(@RequestBody Hotel hotel) {
        return repository.save(hotel);
    }

    @PutMapping("${base-uri}/hotels/{id}")
    public Hotel updateHotel(@RequestBody Hotel newHotel, @PathVariable long id) throws HotelNotFoundException {
        return repository.findById(id).map(hotel -> {
            hotel.setNom(newHotel.getNom());
            hotel.setAdresse(newHotel.getAdresse());
            hotel.setNombreEtoiles(newHotel.getNombreEtoiles());
            hotel.setImageHotel(newHotel.getImageHotel());
            hotel.setChambres(newHotel.getChambres());
            hotel.setReservations(newHotel.getReservations());
            hotel.setOffres(newHotel.getOffres());
            return repository.save(hotel);
        }).orElseGet(() -> repository.save(newHotel));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("${base-uri}/hotels/{id}")
    public void deteleHotel(@PathVariable long id) throws HotelNotFoundException {
        Hotel hotel = repository.findById(id).orElseThrow(() -> new HotelNotFoundException("Hotel not found with id " + id));
        repository.delete(hotel);
    }

}
