package cul_de_chouette.pojo;

import cul_de_chouette.pojo.ActionPK;
import cul_de_chouette.pojo.Interraction;
import cul_de_chouette.pojo.Partie;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-04-26T19:24:26")
@StaticMetamodel(Action.class)
public class Action_ { 

    public static volatile SingularAttribute<Action, Partie> partie;
    public static volatile SingularAttribute<Action, Short> chouette2;
    public static volatile SingularAttribute<Action, Short> chouette1;
    public static volatile SingularAttribute<Action, Interraction> interraction;
    public static volatile SingularAttribute<Action, ActionPK> actionPK;
    public static volatile SingularAttribute<Action, Short> cul;

}