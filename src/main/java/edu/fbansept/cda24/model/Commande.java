package edu.fbansept.cda24.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected LocalDate date;

    @ManyToOne(optional = false)
    protected Utilisateur client;

}








