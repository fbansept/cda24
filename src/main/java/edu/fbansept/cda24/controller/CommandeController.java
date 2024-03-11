package edu.fbansept.cda24.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.dao.CommandeDao;
import edu.fbansept.cda24.model.Commande;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class CommandeController {

    @Autowired
    CommandeDao commandeDao;

    @GetMapping("/commande/liste")
    public List<Commande> liste() {

        return commandeDao.findAll();

    }

    @GetMapping("/commande/{id}")
    public ResponseEntity<Commande> get(@PathVariable int id) {

        Optional<Commande> commandeOptional = commandeDao.findById(id);

        if(commandeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commandeOptional.get(),HttpStatus.OK);
    }


    @PostMapping("/commande")
    public ResponseEntity<Commande> add(@Valid @RequestBody Commande nouveauCommande) {

        //C'est une mise à jour
        if(nouveauCommande.getId() != null) {

            Optional<Commande> commandeOptional = commandeDao.findById(nouveauCommande.getId());

            //l'utilisateur tente de modifier un commande qui n'existe pas/plus
            if(commandeOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            commandeDao.save(nouveauCommande);

            return new ResponseEntity<>(commandeOptional.get(), HttpStatus.OK);
        }

        commandeDao.save(nouveauCommande);

        return new ResponseEntity<>(nouveauCommande, HttpStatus.CREATED);
    }

//    @PostMapping("/commande")
//    public ResponseEntity<Commande> add (@RequestBody Commande nouveauCommande) {
//        nouveauCommande.setId(null);
//        commandeDao.save(nouveauCommande);
//        return new ResponseEntity<>(nouveauCommande, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/commande/{id}")
//    public ResponseEntity<Commande> update (@RequestBody Commande commandeAmodifier, @PathVariable int id) {
//        commandeAmodifier.setId(id);
//
//        Optional<Commande> commandeOptional = commandeDao.findById(commandeAmodifier.getId());
//
//        //l'utilisateur tente de modifier un commande qui n'existe pas/plus
//        if(commandeOptional.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        commandeDao.save(commandeAmodifier);
//        return new ResponseEntity<>(commandeOptional.get(), HttpStatus.OK);
//    }

    @DeleteMapping("/commande/{id}")
    public ResponseEntity<Commande> delete (@PathVariable int id) {

        Optional<Commande> commandeOptional = commandeDao.findById(id);

        if(commandeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commandeDao.deleteById(id);

        return new ResponseEntity<>(commandeOptional.get(),HttpStatus.OK);
    }
}