/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Frozy
 */
@Embeddable
public class InterractionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_partie")
    private int idPartie;
    @Basic(optional = false)
    @Column(name = "tour")
    private int tour;

    public InterractionPK() {
    }

    public InterractionPK(int idPartie, int tour) {
        this.idPartie = idPartie;
        this.tour = tour;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    public int getTour() {
        return tour;
    }

    public void setTour(int tour) {
        this.tour = tour;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPartie;
        hash += (int) tour;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InterractionPK)) {
            return false;
        }
        InterractionPK other = (InterractionPK) object;
        if (this.idPartie != other.idPartie) {
            return false;
        }
        return this.tour == other.tour;
    }

    @Override
    public String toString() {
        return "pojo.InterractionPK[ idPartie=" + idPartie + ", tour=" + tour + " ]";
    }
    
}
