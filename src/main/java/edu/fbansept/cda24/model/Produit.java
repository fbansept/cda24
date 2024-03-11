package edu.fbansept.cda24.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    protected Integer id;

    @NotBlank(message = "Le nom ne peut être vide")
    @Size(max = 100, message = "Nom à maximum 100 caractères") //ConstraintViolationException en cas d'erreur
    @Column(length = 100) //MysqlDataTruncation en cas d'erreur
    protected String nom;

    @NotBlank(message = "Le code ne peut être vide")
    @Size(max = 50, message = "Code à maximum 50 caractères")
    @Column(unique = true, length = 50)
    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;

    @Min(value = 0, message = "Le prix doit être supérieur à 0")
    protected float prix;

    @ManyToOne(optional = false)
    protected EtatProduit etat;

    @ManyToMany
    @JoinTable(
            name = "etiquette_produit",
            joinColumns = @JoinColumn(name = "produit_id"),
            inverseJoinColumns = @JoinColumn(name = "etiquette_id")
    )
    protected List<EtiquetteProduit> listeEtiquettes = new ArrayList<>();
}