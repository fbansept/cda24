package edu.fbansept.cda24.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.dao.CommandeDao;
import edu.fbansept.cda24.model.Commande;
import edu.fbansept.cda24.model.LigneCommande;
import edu.fbansept.cda24.view.CommandeView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class CommandeController {

    @Autowired
    CommandeDao commandeDao;

    @GetMapping("/chiffre-affaire")
    @JsonView(CommandeView.class)
    public Map<String, Object> chiffreAffaire()  {

//        List<Commande> listeCommande = commandeDao.findAll();
//
//        float ca = 0;
//
//        for (Commande commande : listeCommande) {
//
//            if(commande.getStatut().getDesignation().equals("Validée")
//                    || commande.getStatut().getDesignation().equals("Expédiée")) {
//
//                for (LigneCommande ligne : commande.getListeLigneCommande()) {
//                    ca += ligne.getPrixDeVente() * ligne.getQuantite();
//                }
//            }
//        }
//
//        return Map.of("ca", ca);

        List<Commande> listeCommande = commandeDao.findAll();

        Double ca =  listeCommande.stream()
                .filter(commande -> commande.getStatut().getDesignation().equals("Validée")
                        || commande.getStatut().getDesignation().equals("Expédiée"))
                .map(commande -> commande.getListeLigneCommande())
                .flatMap(listeLigneCommande -> listeLigneCommande.stream())
                .mapToDouble(ligneCommande -> ligneCommande.getQuantite() * ligneCommande.getPrixDeVente())
                .sum();

        return Map.of("ca", ca);
    }

    @GetMapping("/commande-by-status/{status}")
    @JsonView(CommandeView.class)
    public List<Commande> listeCommandeValide(@PathVariable String status) throws UnsupportedEncodingException {

        String statusPlainText = java.net.URLDecoder.decode(status, StandardCharsets.UTF_8.name());
        return commandeDao.commandesByStatus(statusPlainText);

       //----- methode 1 -----

//        List<Commande> listeCommande = commandeDao.findAll();
//
//        List<Commande> listeCommandeValide = new ArrayList<>();
//
//        for (Commande commande : listeCommande) {
//            if(commande.getStatut().getDesignation().equals("Validée")) {
//                listeCommandeValide.add(commande);
//            }
//        }
//
//        return listeCommandeValide;

        //----- methode 2 -----

//        List<Commande> listeCommande = commandeDao.findAll();
//
//        return listeCommande.stream()
//                .filter(commande -> commande.getStatut().getDesignation().equals("Validée"))
//                .collect(Collectors.toList());



    }

    @GetMapping("/commande/liste")
    @JsonView(CommandeView.class)
    public List<Commande> liste() {

        return commandeDao.findAll();

    }

    @GetMapping("/commande/{id}")
    @JsonView(CommandeView.class)
    public ResponseEntity<Commande> get(@PathVariable int id) {

        Optional<Commande> commandeOptional = commandeDao.findById(id);

        if(commandeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(commandeOptional.get(),HttpStatus.OK);
    }


    @PostMapping("/commande")
    @JsonView(CommandeView.class)
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
    @JsonView(CommandeView.class)
    public ResponseEntity<Commande> delete (@PathVariable int id) {

        Optional<Commande> commandeOptional = commandeDao.findById(id);

        if(commandeOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        commandeDao.deleteById(id);

        return new ResponseEntity<>(commandeOptional.get(),HttpStatus.OK);
    }
}