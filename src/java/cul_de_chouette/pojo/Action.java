/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.pojo;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Collection;

/**
 *
 * @author Frozy
 */
@Entity
@Table(name = "action", catalog = "cul_de_chouette", schema = "")
@NamedQueries({
    @NamedQuery(name = "Action.findAll", query = "SELECT a FROM Action a"),
    @NamedQuery(name = "Action.findByIdPartie", query = "SELECT a FROM Action a WHERE a.actionPK.idPartie = :idPartie"),
    @NamedQuery(name = "Action.findByTour", query = "SELECT a FROM Action a WHERE a.actionPK.tour = :tour"),
    @NamedQuery(name = "Action.findByChouette1", query = "SELECT a FROM Action a WHERE a.chouette1 = :chouette1"),
    @NamedQuery(name = "Action.findByChouette2", query = "SELECT a FROM Action a WHERE a.chouette2 = :chouette2"),
    @NamedQuery(name = "Action.findByCul", query = "SELECT a FROM Action a WHERE a.cul = :cul")})
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ActionPK actionPK;
    @Column(name = "chouette1")
    private Short chouette1;
    @Column(name = "chouette2")
    private Short chouette2;
    @Column(name = "cul")
    private Short cul;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "action")
    private Interraction interraction;
    @JoinColumn(name = "id_partie", referencedColumnName = "id_partie", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;

    public Action() {
    }

    public Action(ActionPK actionPK) {
        this.actionPK = actionPK;
    }

    public Action(int idPartie, int tour) {
        this.actionPK = new ActionPK(idPartie, tour);
    }

    public ActionPK getActionPK() {
        return actionPK;
    }

    public void setActionPK(ActionPK actionPK) {
        this.actionPK = actionPK;
    }

    public Short getChouette1() {
        return chouette1;
    }

    public void setChouette1(Short chouette1) {
        this.chouette1 = chouette1;
    }

    public Short getChouette2() {
        return chouette2;
    }

    public void setChouette2(Short chouette2) {
        this.chouette2 = chouette2;
    }

    public Short getCul() {
        return cul;
    }

    public void setCul(Short cul) {
        this.cul = cul;
    }

    public Interraction getInterraction() {
        return interraction;
    }

    public void setInterraction(Interraction interraction) {
        this.interraction = interraction;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }
    
    public Joueur getJoueurCourant() {
        Collection<Resultats> resultats = this.partie.getResultatsCollection();
        
        int indexJoueur = (this.actionPK.getTour() - 1) % resultats.size();
        
        return resultats.toArray(new Resultats[0])[indexJoueur].getJoueur();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (actionPK != null ? actionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Action)) {
            return false;
        }
        Action other = (Action) object;
        return !((this.actionPK == null && other.actionPK != null) || (this.actionPK != null && !this.actionPK.equals(other.actionPK)));
    }

    @Override
    public String toString() {
        return "pojo.Action[ actionPK=" + actionPK + " ]";
    }
    
}
