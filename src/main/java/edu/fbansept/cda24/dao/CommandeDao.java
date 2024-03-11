package edu.fbansept.cda24.dao;

import edu.fbansept.cda24.model.Commande;
import edu.fbansept.cda24.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeDao extends JpaRepository<Commande,Integer> {

    @Query(value = "FROM Commande c JOIN c.statut s WHERE s.designation = :status")
    List<Commande> commandesByStatus(@Param("status") String status);

}
