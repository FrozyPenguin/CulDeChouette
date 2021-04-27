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
public class ResultatsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_partie")
    private int idPartie;
    @Basic(optional = false)
    @Column(name = "pseudonyme")
    private String pseudonyme;

    public ResultatsPK() {
    }

    public ResultatsPK(int idPartie, String pseudonyme) {
        this.idPartie = idPartie;
        this.pseudonyme = pseudonyme;
    }

    public int getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(int idPartie) {
        this.idPartie = idPartie;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idPartie;
        hash += (pseudonyme != null ? pseudonyme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultatsPK)) {
            return false;
        }
        ResultatsPK other = (ResultatsPK) object;
        if (this.idPartie != other.idPartie) {
            return false;
        }
        return !((this.pseudonyme == null && other.pseudonyme != null) || (this.pseudonyme != null && !this.pseudonyme.equals(other.pseudonyme)));
    }

    @Override
    public String toString() {
        return "pojo.ResultatsPK[ idPartie=" + idPartie + ", pseudonyme=" + pseudonyme + " ]";
    }
    
}
