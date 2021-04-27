package cul_de_chouette.pojo;

import cul_de_chouette.pojo.Interraction;
import cul_de_chouette.pojo.Partie;
import cul_de_chouette.pojo.Resultats;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-04-26T19:24:26")
@StaticMetamodel(Joueur.class)
public class Joueur_ { 

    public static volatile SingularAttribute<Joueur, String> motDePasse;
    public static volatile CollectionAttribute<Joueur, Interraction> interractionCollection;
    public static volatile SingularAttribute<Joueur, String> pseudonyme;
    public static volatile SingularAttribute<Joueur, String> ville;
    public static volatile SingularAttribute<Joueur, Integer> dateNaissance;
    public static volatile SingularAttribute<Joueur, String> sexe;
    public static volatile SingularAttribute<Joueur, String> email;
    public static volatile CollectionAttribute<Joueur, Partie> partiesGagnees;
    public static volatile CollectionAttribute<Joueur, Partie> partiesHote;
    public static volatile CollectionAttribute<Joueur, Resultats> resultatsCollection;

}