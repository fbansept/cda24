package edu.fbansept.cda24.model;

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
    protected Integer id;

    protected String designation;

    @OneToMany(mappedBy = "etat")
    protected List<Produit> listeProduits = new ArrayList<>();
}
