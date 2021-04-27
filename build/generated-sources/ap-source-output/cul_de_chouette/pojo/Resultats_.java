package cul_de_chouette.pojo;

import cul_de_chouette.pojo.Joueur;
import cul_de_chouette.pojo.Partie;
import cul_de_chouette.pojo.ResultatsPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-04-26T19:24:26")
@StaticMetamodel(Resultats.class)
public class Resultats_ { 

    public static volatile SingularAttribute<Resultats, Integer> suitesGagnees;
    public static volatile SingularAttribute<Resultats, Integer> score;
    public static volatile SingularAttribute<Resultats, Partie> partie;
    public static volatile SingularAttribute<Resultats, Integer> ordre;
    public static volatile SingularAttribute<Resultats, Joueur> joueur;
    public static volatile SingularAttribute<Resultats, ResultatsPK> resultatsPK;
    public static volatile SingularAttribute<Resultats, Integer> chouettesVeluesPerdues;

}