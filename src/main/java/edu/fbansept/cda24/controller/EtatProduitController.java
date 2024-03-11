package edu.fbansept.cda24.controller;


import edu.fbansept.cda24.dao.EtatProduitDao;
import edu.fbansept.cda24.model.EtatProduit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class EtatProduitController {

    @Autowired
    EtatProduitDao etatProduitDao;

    @GetMapping("/etat-produit/liste")
    public List<EtatProduit> liste() {

        return etatProduitDao.findAll();

    }

    @GetMapping("/etat-produit/{id}")
    public ResponseEntity<EtatProduit> get(@PathVariable int id) {

        Optional<EtatProduit> etatProduitOptional = etatProduitDao.findById(id);

        if(etatProduitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(etatProduitOptional.get(),HttpStatus.OK);
    }


    @PostMapping("/etat-produit")
    public ResponseEntity<EtatProduit> add(@Valid @RequestBody EtatProduit nouveauEtatProduit) {

        //C'est une mise à jour
        if(nouveauEtatProduit.getId() != null) {

            Optional<EtatProduit> etatProduitOptional = etatProduitDao.findById(nouveauEtatProduit.getId());

            //l'utilisateur tente de modifier un etatProduit qui n'existe pas/plus
            if(etatProduitOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            etatProduitDao.save(nouveauEtatProduit);

            return new ResponseEntity<>(etatProduitOptional.get(), HttpStatus.OK);
        }

        etatProduitDao.save(nouveauEtatProduit);

        return new ResponseEntity<>(nouveauEtatProduit, HttpStatus.CREATED);
    }

//    @PostMapping("/etatProduit")
//    public ResponseEntity<EtatProduit> add (@RequestBody EtatProduit nouveauEtatProduit) {
//        nouveauEtatProduit.setId(null);
//        etatProduitDao.save(nouveauEtatProduit);
//        return new ResponseEntity<>(nouveauEtatProduit, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/etatProduit/{id}")
//    public ResponseEntity<EtatProduit> update (@RequestBody EtatProduit etatProduitAmodifier, @PathVariable int id) {
//        etatProduitAmodifier.setId(id);
//
//        Optional<EtatProduit> etatProduitOptional = etatProduitDao.findById(etatProduitAmodifier.getId());
//
//        //l'utilisateur tente de modifier un etatProduit qui n'existe pas/plus
//        if(etatProduitOptional.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        etatProduitDao.save(etatProduitAmodifier);
//        return new ResponseEntity<>(etatProduitOptional.get(), HttpStatus.OK);
//    }

    @DeleteMapping("/etat-produit/{id}")
    public ResponseEntity<EtatProduit> delete (@PathVariable int id) {

        Optional<EtatProduit> etatProduitOptional = etatProduitDao.findById(id);

        if(etatProduitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        etatProduitDao.deleteById(id);

        return new ResponseEntity<>(etatProduitOptional.get(),HttpStatus.OK);
    }
}