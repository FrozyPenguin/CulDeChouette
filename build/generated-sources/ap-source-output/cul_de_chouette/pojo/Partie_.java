package cul_de_chouette.pojo;

import cul_de_chouette.pojo.Action;
import cul_de_chouette.pojo.Joueur;
import cul_de_chouette.pojo.Resultats;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-04-26T19:24:26")
@StaticMetamodel(Partie.class)
public class Partie_ { 

    public static volatile SingularAttribute<Partie, Integer> idPartie;
    public static volatile SingularAttribute<Partie, Joueur> vainqueur;
    public static volatile SingularAttribute<Partie, Date> dateDebut;
    public static volatile SingularAttribute<Partie, Integer> objectif;
    public static volatile SingularAttribute<Partie, Joueur> hote;
    public static volatile SingularAttribute<Partie, Float> duree;
    public static volatile CollectionAttribute<Partie, Action> actionCollection;
    public static volatile CollectionAttribute<Partie, Resultats> resultatsCollection;

}