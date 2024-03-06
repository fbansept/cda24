package edu.fbansept.cda24.controller;


import edu.fbansept.cda24.dao.ProduitDao;
import edu.fbansept.cda24.model.Produit;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
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
    public ResponseEntity<Produit> add(@Valid @RequestBody Produit nouveauProduit) {

        //C'est une mise à jour
        if(nouveauProduit.getId() != null) {

            Optional<Produit> produitOptional = produitDao.findById(nouveauProduit.getId());

            //l'utilisateur tente de modifier un produit qui n'existe pas/plus
            if(produitOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            produitDao.save(nouveauProduit);

            return new ResponseEntity<>(produitOptional.get(), HttpStatus.OK);
        }

        produitDao.save(nouveauProduit);

        return new ResponseEntity<>(nouveauProduit, HttpStatus.CREATED);
    }

//    @PostMapping("/produit")
//    public ResponseEntity<Produit> add (@RequestBody Produit nouveauProduit) {
//        nouveauProduit.setId(null);
//        produitDao.save(nouveauProduit);
//        return new ResponseEntity<>(nouveauProduit, HttpStatus.CREATED);
//    }
//
//    @PutMapping("/produit/{id}")
//    public ResponseEntity<Produit> update (@RequestBody Produit produitAmodifier, @PathVariable int id) {
//        produitAmodifier.setId(id);
//
//        Optional<Produit> produitOptional = produitDao.findById(produitAmodifier.getId());
//
//        //l'utilisateur tente de modifier un produit qui n'existe pas/plus
//        if(produitOptional.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        produitDao.save(produitAmodifier);
//        return new ResponseEntity<>(produitOptional.get(), HttpStatus.OK);
//    }

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