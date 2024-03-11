package edu.fbansept.cda24.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.view.CommandeView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @JsonView(CommandeView.class)
    protected float prixDeVente;

    @JsonView(CommandeView.class)
    protected int quantite;

    @ManyToOne(optional = false)
    protected Commande commande;

    @ManyToOne(optional = false)
    @JsonView(CommandeView.class)
    protected Produit produit;
}