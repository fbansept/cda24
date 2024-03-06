package edu.fbansept.cda24.controller;

import edu.fbansept.cda24.dao.ProduitDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    @GetMapping("/")
    public String hello() {

        return "<h1>Le serveur marche, mais y'a rien a voir ici !</h1>";
    }

}
