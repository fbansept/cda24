package edu.fbansept.cda24.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.dao.UtilisateurDao;
import edu.fbansept.cda24.model.Utilisateur;
import edu.fbansept.cda24.security.IsAdmin;
import edu.fbansept.cda24.security.IsUser;
import edu.fbansept.cda24.view.UtilisateurAvecCommandeView;
import edu.fbansept.cda24.view.UtilisateurView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@IsUser
public class UtilisateurController {

    @Autowired
    UtilisateurDao utilisateurDao;

    @GetMapping("/utilisateur/liste")
    @JsonView(UtilisateurView.class)
    public List<Utilisateur> liste() {
        return utilisateurDao.findAll();
    }

    @GetMapping("/utilisateur/{id}")
    @JsonView(UtilisateurAvecCommandeView.class)
    public ResponseEntity<Utilisateur> get(@PathVariable int id) {

        Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(id);

        if(utilisateurOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(utilisateurOptional.get(),HttpStatus.OK);
    }


    @PostMapping("/utilisateur")
    @JsonView(UtilisateurView.class)
    @IsAdmin
    public ResponseEntity<Utilisateur> add(@Valid @RequestBody Utilisateur nouveauUtilisateur) {

        //C'est une mise à jour
        if(nouveauUtilisateur.getId() != null) {

            Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(nouveauUtilisateur.getId());

            //l'utilisateur tente de modifier un utilisateur qui n'existe pas/plus
            if(utilisateurOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            utilisateurDao.save(nouveauUtilisateur);

            return new ResponseEntity<>(utilisateurOptional.get(), HttpStatus.OK);
        }

        utilisateurDao.save(nouveauUtilisateur);

        return new ResponseEntity<>(nouveauUtilisateur, HttpStatus.CREATED);
    }

//    @PostMapping("/utilisateur")
//    public ResponseEntity<Utilisateur> add (@RequestBody Utilisateur nouveauUtilisateur) {
//        nouveauUtilisateur.setId(null);
//        utilisateurDao.save(nouveauUtilisateur);
//        return new ResponseEntity<>(nouveauUtilisateur, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/utilisateur/{id}")
//    public ResponseEntity<Utilisateur> update (@RequestBody Utilisateur utilisateurAmodifier, @PathVariable int id) {
//        utilisateurAmodifier.setId(id);
//
//        Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(utilisateurAmodifier.getId());
//
//        //l'utilisateur tente de modifier un utilisateur qui n'existe pas/plus
//        if(utilisateurOptional.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        utilisateurDao.save(utilisateurAmodifier);
//        return new ResponseEntity<>(utilisateurOptional.get(), HttpStatus.OK);
//    }

    @DeleteMapping("/utilisateur/{id}")
    @JsonView(UtilisateurView.class)
    @IsAdmin
    public ResponseEntity<Utilisateur> delete (@PathVariable int id) {

        Optional<Utilisateur> utilisateurOptional = utilisateurDao.findById(id);

        if(utilisateurOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        utilisateurDao.deleteById(id);

        return new ResponseEntity<>(utilisateurOptional.get(),HttpStatus.OK);
    }
}