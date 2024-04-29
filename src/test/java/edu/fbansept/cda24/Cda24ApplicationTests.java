package edu.fbansept.cda24;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.fbansept.cda24.model.EtatProduit;
import edu.fbansept.cda24.model.Produit;
import edu.fbansept.cda24.model.Utilisateur;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
class Cda24ApplicationTests {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void connexionAvecIdentifiantValide_statut200 () throws Exception {

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("a@a.com");
        utilisateur.setMotDePasse("root");

        String json = mapper.writeValueAsString(utilisateur);

        mvc.perform(
                post("/connexion")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
            .andExpect(status().isOk());
    }

    @Test
    public void connexionAvecEmailInvalide_statut403 () throws Exception {

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("c@c.com");
        utilisateur.setMotDePasse("root");

        String json = mapper.writeValueAsString(utilisateur);

        mvc.perform(
                        post("/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    public void connexionAvecPasswordInvalide_statut403 () throws Exception {

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("a@a.com");
        utilisateur.setMotDePasse("mauvais mot de passe");

        String json = mapper.writeValueAsString(utilisateur);

        mvc.perform(
                        post("/connexion")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(json))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "toto@toto.com", roles={"USER"})
    public void recupererArticleEnEtantUtilisateur_statut200 () throws Exception {
        mvc.perform(get("/produit/liste"))
                .andExpect(status().isOk());
    }

    @Test
    public void recupererArticleEnEtantAnonyme_statut403 () throws Exception {
        mvc.perform(get("/produit/liste"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "toto@toto.com", roles={"ADMIN"})
    public void creationProduit_leProduitAunIdenfiantAutoIncremente () throws Exception {

        Produit produit = new Produit();
        produit.setNom("nouveau produit");
        produit.setCode("AAAAAA");
        produit.setDescription("description produit");
        produit.setPrix(10);
        EtatProduit etatNeuf = new EtatProduit();
        etatNeuf.setId(1);
        produit.setEtat(etatNeuf);

        String json = mapper.writeValueAsString(produit);

        mvc.perform(post("/produit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    @WithMockUser(username = "toto@toto.com", roles={"ADMIN"})
    public void creationProduitAvecLeMemeCode_statut409 () throws Exception {

        Produit produitA = new Produit();
        produitA.setNom("nouveau produit");
        produitA.setCode("AAAAAA");
        produitA.setDescription("description produit");
        produitA.setPrix(10);
        EtatProduit etatNeuf = new EtatProduit();
        etatNeuf.setId(1);
        produitA.setEtat(etatNeuf);

        Produit produitB = new Produit();
        produitB.setNom("nouveau produit");
        produitB.setCode("AAAAAA");
        produitB.setDescription("description produit");
        produitB.setPrix(10);
        produitB.setEtat(etatNeuf);

        String jsonProduitA = mapper.writeValueAsString(produitA);
        String jsonProduitB = mapper.writeValueAsString(produitB);

        mvc.perform(post("/produit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduitA))
                .andExpect(status().isCreated());

        mvc.perform(post("/produit")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonProduitB))
                .andExpect(status().isConflict());
    }

}
