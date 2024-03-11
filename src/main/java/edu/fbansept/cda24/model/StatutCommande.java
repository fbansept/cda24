package edu.fbansept.cda24.model;

import com.fasterxml.jackson.annotation.JsonView;
import edu.fbansept.cda24.view.CommandeView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "statut")
public class StatutCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @JsonView(CommandeView.class)
    protected String designation;

}