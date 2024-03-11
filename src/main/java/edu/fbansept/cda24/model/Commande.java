package edu.fbansept.cda24.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.view.CommandeView;
import edu.fbansept.cda24.view.UtilisateurView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({UtilisateurView.class, CommandeView.class})
    protected Integer id;

    @JsonView({UtilisateurView.class, CommandeView.class})
    protected LocalDate date;

    @ManyToOne(optional = false)
    @JsonView(CommandeView.class)
    protected Utilisateur client;

    @ManyToOne(optional = false)
    @JsonView(CommandeView.class)
    protected StatutCommande statut;

    @OneToMany(mappedBy = "commande")
    @JsonView(CommandeView.class)
    protected List<LigneCommande> listeLigneCommande;

}








