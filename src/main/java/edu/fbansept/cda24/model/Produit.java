package edu.fbansept.cda24.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.view.CommandeView;
import edu.fbansept.cda24.view.EtatProduitView;
import edu.fbansept.cda24.view.ProduitView;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Produit  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(ProduitView.class)
    protected Integer id;

    @NotBlank(message = "Le nom ne peut être vide")
    @Size(max = 100, message = "Nom à maximum 100 caractères") //ConstraintViolationException en cas d'erreur
    @Column(length = 100) //MysqlDataTruncation en cas d'erreur
    @JsonView({ProduitView.class, EtatProduitView.class, CommandeView.class})
    protected String nom;

    @NotBlank(message = "Le code ne peut être vide")
    @Size(max = 50, message = "Code à maximum 50 caractères")
    @Column(unique = true, length = 50)
    protected String code;

    @Column(columnDefinition = "TEXT")
    @JsonView(ProduitView.class)
    protected String description;

    @Min(value = 0, message = "Le prix doit être supérieur à 0")
    @JsonView(ProduitView.class)
    protected float prix;

    @ManyToOne(optional = false)
    @JsonView(ProduitView.class)
    protected EtatProduit etat;

    @ManyToMany
    @JoinTable(
            name = "etiquette_produit",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id")
    )
    @JsonView(ProduitView.class)
    protected List<EtiquetteProduit> listeEtiquettes;

    @JsonView({ProduitView.class, CommandeView.class})
    protected String nomImage;
}