package cul_de_chouette.pojo;

import cul_de_chouette.pojo.Action;
import cul_de_chouette.pojo.InterractionPK;
import cul_de_chouette.pojo.Joueur;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-04-26T19:24:26")
@StaticMetamodel(Interraction.class)
public class Interraction_ { 

    public static volatile SingularAttribute<Interraction, InterractionPK> interractionPK;
    public static volatile SingularAttribute<Interraction, Action> action;
    public static volatile SingularAttribute<Interraction, Character> effet;
    public static volatile SingularAttribute<Interraction, Joueur> joueurAffecte;

}