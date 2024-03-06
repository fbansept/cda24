package edu.fbansept.cda24.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Produit  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String nom;

    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;

    protected float prix;


}