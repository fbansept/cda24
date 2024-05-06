package edu.fbansept.cda24.controller;


import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.dao.ProduitDao;
import edu.fbansept.cda24.model.Produit;
import edu.fbansept.cda24.security.IsAdmin;
import edu.fbansept.cda24.security.IsUser;
import edu.fbansept.cda24.services.FichierService;
import edu.fbansept.cda24.view.ProduitView;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
public class ProduitController {

    @Autowired
    ProduitDao produitDao;

    @Autowired
    FichierService fichierService;

    @GetMapping("/produit/liste")
    @IsUser
    @JsonView(ProduitView.class)
    public List<Produit> liste() {

        return produitDao.findAll();

    }

    @GetMapping("/produit/{id}")
    @JsonView(ProduitView.class)
    public ResponseEntity<Produit> get(@PathVariable int id) {

        Optional<Produit> produitOptional = produitDao.findById(id);

        if(produitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(produitOptional.get(),HttpStatus.OK);
    }


//    @PostMapping ("/sauvegarde-article-avec-fichier" )
//    public ResponseEntity<Article> sauvegardeArticle (
//            @Nullable @RequestParam ("fichier" ) MultipartFile fichier ,
//            @RequestPart ("article" ) Article article) {
//        return new ResponseEntity<>(article , HttpStatus. OK);
//    }


    @PostMapping("/produit")
    @JsonView(ProduitView.class)
    @IsAdmin
    public ResponseEntity<Produit> add(
            @Valid @RequestPart("produit") Produit nouveauProduit,
            @Nullable @RequestParam("image") MultipartFile image) throws IOException {

        if(image != null) {

            String nouveauNomFichier = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
                    .format(Calendar.getInstance().getTime()) + "-" + image.getOriginalFilename();

            fichierService.uploadToLocalFileSystem(image, nouveauNomFichier);

            nouveauProduit.setNomImage(nouveauNomFichier);

        }

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
    @JsonView(ProduitView.class)
    public ResponseEntity<Produit> delete (@PathVariable int id) {

        Optional<Produit> produitOptional = produitDao.findById(id);

        if(produitOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        produitDao.deleteById(id);

        return new ResponseEntity<>(produitOptional.get(),HttpStatus.OK);
    }

    @ResponseBody
    @IsUser
    @GetMapping("/download/{nomDeFichier}")
    public ResponseEntity<byte[]> getImageAsResource(@PathVariable String nomDeFichier) {
        try {
            String mimeType = "";
            if(!fichierService.nomFichierValide(nomDeFichier)) {
                return ResponseEntity.notFound().build();
            }
            try {
                mimeType = Files.probeContentType(new File(nomDeFichier).toPath());
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            if(mimeType != null) {
                headers.setContentType(MediaType.valueOf(mimeType));
            }
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());
            byte[] media = fichierService.getFileFromUploadFolder(nomDeFichier);
            return new ResponseEntity<>(media, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}