package edu.fbansept.cda24.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Produit  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Size(max = 100) //ConstraintViolationException
    @Column(length = 100) //MysqlDataTruncation
    protected String nom;

    @Column(unique = true)
    protected String code;

    @Column(columnDefinition = "TEXT")
    protected String description;

    protected float prix;


}