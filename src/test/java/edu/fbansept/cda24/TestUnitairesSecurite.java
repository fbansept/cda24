package edu.fbansept.cda24;

import edu.fbansept.cda24.model.EtatProduit;
import edu.fbansept.cda24.model.Produit;
import edu.fbansept.cda24.model.Utilisateur;
import edu.fbansept.cda24.security.AppUserDetails;
import edu.fbansept.cda24.security.JwtUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TestUnitairesSecurite {

    private JwtUtils jwtUtils;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        jwtUtils.secretJwt = "mySecretKey";  // Assigner directement pour le test

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("a@a.com");
        utilisateur.setMotDePasse("root");
        utilisateur.setAdministrateur(true);

        userDetails = new AppUserDetails(utilisateur);
    }

    @Test
    void testGenerateTokenContainsCorrectSubject() {
        String token = jwtUtils.generateToken(userDetails);
        String subject = jwtUtils.getSubjectFromJwt(token);
        assertEquals("a@a.com", subject);
    }

    @Test
    void testTokenHandlingWithInvalidSignature() {
        String invalidToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VybmFtZSJ9.InvalidSignature";
        assertThrows(Exception.class, () -> jwtUtils.getSubjectFromJwt(invalidToken));
    }


}
