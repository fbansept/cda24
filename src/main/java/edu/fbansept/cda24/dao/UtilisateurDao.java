package edu.fbansept.cda24.dao;

import edu.fbansept.cda24.model.Commande;
import edu.fbansept.cda24.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurDao extends JpaRepository<Utilisateur,Integer> {
    Optional<Utilisateur> findByEmail(String email);
}
