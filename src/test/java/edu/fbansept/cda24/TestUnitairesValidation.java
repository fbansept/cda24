package edu.fbansept.cda24;

import edu.fbansept.cda24.model.EtatProduit;
import edu.fbansept.cda24.model.Produit;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class TestUnitairesValidation {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void creationNouveauProduit_listeEtiquetteDoitEtreInitialisee() {
        Produit produit = new Produit();
        assertNotNull(produit.getListeEtiquettes());
    }

    @Test
    public void creationNouveauProduitValide_aucuneViolationDeRegle() {
        Produit produit = new Produit();
        produit.setNom("Nom produit");
        produit.setCode("CODE123");
        produit.setDescription("Une description.");
        produit.setPrix(10.0f);

        EtatProduit etatNeuf = new EtatProduit();
        etatNeuf.setId(1);

        produit.setEtat(etatNeuf);

        Set<ConstraintViolation<Produit>> violations = validator.validate(produit);
        assertEquals(0, violations.size());
    }

    @Test
    public void creationNouveauProduitSansNom_violationDeLaRegleNotBlank() {
        Produit produit = new Produit();
        produit.setNom("");
        produit.setCode("CODE123");
        produit.setDescription("Une description.");
        produit.setPrix(10.0f);

        EtatProduit etatNeuf = new EtatProduit();
        etatNeuf.setId(1);

        produit.setEtat(etatNeuf);

        Set<ConstraintViolation<Produit>> violations = validator.validate(produit);

        List<String> listeMessage = violations.stream().map(violation -> violation.getMessage()).toList();
        assertTrue(listeMessage.contains("Le nom ne peut être vide"));
    }

}
