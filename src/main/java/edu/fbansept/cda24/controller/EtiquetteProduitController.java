package edu.fbansept.cda24.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.dao.EtiquetteProduitDao;
import edu.fbansept.cda24.model.EtiquetteProduit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EtiquetteProduitController {

    @Autowired
    EtiquetteProduitDao etiquetteProduitDao;

    @GetMapping("/etiquette-produit/liste")
    public List<EtiquetteProduit> liste() {

        return etiquetteProduitDao.findAll();

    }

    @GetMapping("/etiquette-produit/{id}")
    public ResponseEntity<EtiquetteProduit> get(@PathVariable int id) {

        Optional<EtiquetteProduit> etiquetteProduitOptional = etiquetteProduitDao.findById(id);

        if(etiquetteProduitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(etiquetteProduitOptional.get(),HttpStatus.OK);
    }


    @PostMapping("/etiquette-produit")
    public ResponseEntity<EtiquetteProduit> add(@Valid @RequestBody EtiquetteProduit nouveauEtiquetteProduit) {

        //C'est une mise à jour
        if(nouveauEtiquetteProduit.getId() != null) {

            Optional<EtiquetteProduit> etiquetteProduitOptional = etiquetteProduitDao.findById(nouveauEtiquetteProduit.getId());

            //l'utilisateur tente de modifier un etiquetteProduit qui n'existe pas/plus
            if(etiquetteProduitOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            etiquetteProduitDao.save(nouveauEtiquetteProduit);

            return new ResponseEntity<>(etiquetteProduitOptional.get(), HttpStatus.OK);
        }

        etiquetteProduitDao.save(nouveauEtiquetteProduit);

        return new ResponseEntity<>(nouveauEtiquetteProduit, HttpStatus.CREATED);
    }

//    @PostMapping("/etiquetteProduit")
//    public ResponseEntity<EtiquetteProduit> add (@RequestBody EtiquetteProduit nouveauEtiquetteProduit) {
//        nouveauEtiquetteProduit.setId(null);
//        etiquetteProduitDao.save(nouveauEtiquetteProduit);
//        return new ResponseEntity<>(nouveauEtiquetteProduit, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/etiquetteProduit/{id}")
//    public ResponseEntity<EtiquetteProduit> update (@RequestBody EtiquetteProduit etiquetteProduitAmodifier, @PathVariable int id) {
//        etiquetteProduitAmodifier.setId(id);
//
//        Optional<EtiquetteProduit> etiquetteProduitOptional = etiquetteProduitDao.findById(etiquetteProduitAmodifier.getId());
//
//        //l'utilisateur tente de modifier un etiquetteProduit qui n'existe pas/plus
//        if(etiquetteProduitOptional.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        etiquetteProduitDao.save(etiquetteProduitAmodifier);
//        return new ResponseEntity<>(etiquetteProduitOptional.get(), HttpStatus.OK);
//    }

    @DeleteMapping("/etiquette-produit/{id}")
    public ResponseEntity<EtiquetteProduit> delete (@PathVariable int id) {

        Optional<EtiquetteProduit> etiquetteProduitOptional = etiquetteProduitDao.findById(id);

        if(etiquetteProduitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        etiquetteProduitDao.deleteById(id);

        return new ResponseEntity<>(etiquetteProduitOptional.get(),HttpStatus.OK);
    }
}