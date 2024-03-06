package edu.fbansept.cda24.controller;


import edu.fbansept.cda24.dao.ProduitDao;
import edu.fbansept.cda24.model.Produit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProduitController {

    @Autowired
    ProduitDao produitDao;

    @GetMapping("/produit/liste")
    public List<Produit> liste() {

        return produitDao.findAll();

    }

    @GetMapping("/produit/{id}")
    public ResponseEntity<Produit> get(@PathVariable int id) {

        Optional<Produit> produitOptional = produitDao.findById(id);

        if(produitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(produitOptional.get(),HttpStatus.OK);
    }


    @PostMapping("/produit")
    public Produit add(@RequestBody Produit nouveauProduit) {

        produitDao.save(nouveauProduit);

        return nouveauProduit;
    }

    @DeleteMapping("/produit/{id}")
    public ResponseEntity<Produit> delete (@PathVariable int id) {

        Optional<Produit> produitOptional = produitDao.findById(id);

        if(produitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        produitDao.deleteById(id);

        return new ResponseEntity<>(produitOptional.get(),HttpStatus.OK);
    }
}