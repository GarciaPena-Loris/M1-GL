package m1.archi.restagence.controllers;

import m1.archi.restagence.exceptions.UtilisateurNotFoundException;
import m1.archi.restagence.exceptions.UtilisateurWrongPasswordException;
import m1.archi.restagence.models.Utilisateur;
import m1.archi.restagence.repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${base-uri}/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @GetMapping("/ids")
    public List<Long> getAllUtilisateurIds() {
        return utilisateurRepository.findAll().stream().map(Utilisateur::getIdUtilisateur).collect(Collectors.toList());
    }

    @GetMapping("/count")
    public String count() {
        return String.format("{\"%s\": %d}", "count", utilisateurRepository.count());
    }

    @GetMapping("/{id}")
    public Utilisateur getUtilisateurById(@PathVariable long id) throws UtilisateurNotFoundException {
        return utilisateurRepository.findById(id).orElseThrow(() -> new UtilisateurNotFoundException("User not found with id " + id));
    }

    @GetMapping("/email")
    public Utilisateur getUtilisateurByEmailMotDePasse(@RequestParam String email, @RequestParam String motDePasse) throws UtilisateurWrongPasswordException, UtilisateurNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElseThrow(() -> new UtilisateurNotFoundException("Utilisateur not found with email " + email));
        if (!utilisateur.getMotDePasse().equals(motDePasse)) {
            throw new UtilisateurWrongPasswordException("Wrong password");
        }
        return utilisateur;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Optional<Utilisateur> user = utilisateurRepository.findByEmail(utilisateur.getEmail());
        return user.orElseGet(() -> utilisateurRepository.save(utilisateur));
    }

    @PutMapping("/{id}")
    public Utilisateur updateUtilisateur(@RequestBody Utilisateur newUtilisateur, @PathVariable long id) {
        return utilisateurRepository.findById(id).map(utilisateur -> {
            utilisateur.setEmail(newUtilisateur.getEmail());
            utilisateur.setMotDePasse(newUtilisateur.getMotDePasse());
            utilisateur.setNom(newUtilisateur.getNom());
            utilisateur.setPrenom(newUtilisateur.getPrenom());
            utilisateur.setIdReservations(newUtilisateur.getIdReservations());

            return utilisateurRepository.save(utilisateur);
        }).orElseGet(() -> utilisateurRepository.save(newUtilisateur));
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUtilisateur(@PathVariable long id) throws UtilisateurNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findById(id).orElseThrow(() -> new UtilisateurNotFoundException("User not found with id " + id));
        utilisateurRepository.delete(utilisateur);
    }
}
