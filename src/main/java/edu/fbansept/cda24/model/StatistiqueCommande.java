package edu.fbansept.cda24.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StatistiqueCommande {
    protected String nomStatut;
    protected Long nombre;
}
