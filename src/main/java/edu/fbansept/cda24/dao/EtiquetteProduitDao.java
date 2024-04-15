package edu.fbansept.cda24.dao;

import edu.fbansept.cda24.model.EtatProduit;
import edu.fbansept.cda24.model.EtiquetteProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EtiquetteProduitDao extends JpaRepository<EtiquetteProduit,Integer> {

}
