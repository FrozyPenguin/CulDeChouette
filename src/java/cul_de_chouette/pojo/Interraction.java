/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Frozy
 */
@Entity
@Table(name = "interraction", catalog = "cul_de_chouette", schema = "")
@NamedQueries({
    @NamedQuery(name = "Interraction.findAll", query = "SELECT i FROM Interraction i"),
    @NamedQuery(name = "Interraction.findByIdPartie", query = "SELECT i FROM Interraction i WHERE i.interractionPK.idPartie = :idPartie"),
    @NamedQuery(name = "Interraction.findByTour", query = "SELECT i FROM Interraction i WHERE i.interractionPK.tour = :tour"),
    @NamedQuery(name = "Interraction.findByEffet", query = "SELECT i FROM Interraction i WHERE i.effet = :effet")})
public class Interraction implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected InterractionPK interractionPK;
    @Column(name = "effet")
    private Character effet;
    @JoinColumns({
        @JoinColumn(name = "id_partie", referencedColumnName = "id_partie", insertable = false, updatable = false),
        @JoinColumn(name = "tour", referencedColumnName = "tour", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Action action;
    @JoinColumn(name = "joueur_affecte", referencedColumnName = "pseudonyme")
    @ManyToOne
    private Joueur joueurAffecte;

    public Interraction() {
    }

    public Interraction(InterractionPK interractionPK) {
        this.interractionPK = interractionPK;
    }

    public Interraction(int idPartie, int tour) {
        this.interractionPK = new InterractionPK(idPartie, tour);
    }

    public InterractionPK getInterractionPK() {
        return interractionPK;
    }

    public void setInterractionPK(InterractionPK interractionPK) {
        this.interractionPK = interractionPK;
    }

    public Character getEffet() {
        return effet;
    }

    public void setEffet(Character effet) {
        this.effet = effet;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Joueur getJoueurAffecte() {
        return joueurAffecte;
    }

    public void setJoueurAffecte(Joueur joueurAffecte) {
        this.joueurAffecte = joueurAffecte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (interractionPK != null ? interractionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Interraction)) {
            return false;
        }
        Interraction other = (Interraction) object;
        return !((this.interractionPK == null && other.interractionPK != null) || (this.interractionPK != null && !this.interractionPK.equals(other.interractionPK)));
    }

    @Override
    public String toString() {
        return "pojo.Interraction[ interractionPK=" + interractionPK + " ]";
    }
    
}
