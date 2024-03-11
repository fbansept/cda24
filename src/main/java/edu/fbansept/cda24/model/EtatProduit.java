package edu.fbansept.cda24.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.view.EtatProduitView;
import edu.fbansept.cda24.view.ProduitView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "etat")
public class EtatProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({ProduitView.class, EtatProduitView.class})
    protected Integer id;

    @JsonView({ProduitView.class, EtatProduitView.class})
    protected String designation;

    @OneToMany(mappedBy = "etat")
    @JsonView(EtatProduitView.class)
    protected List<Produit> listeProduits = new ArrayList<>();
}
