/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Frozy
 */
@Entity
@Table(name = "resultats", catalog = "cul_de_chouette", schema = "")
@NamedQueries({
    @NamedQuery(name = "Resultats.findAll", query = "SELECT r FROM Resultats r"),
    @NamedQuery(name = "Resultats.findByIdPartie", query = "SELECT r FROM Resultats r WHERE r.resultatsPK.idPartie = :idPartie"),
    @NamedQuery(name = "Resultats.findByPseudonyme", query = "SELECT r FROM Resultats r WHERE r.resultatsPK.pseudonyme = :pseudonyme"),
    @NamedQuery(name = "Resultats.findByOrdre", query = "SELECT r FROM Resultats r WHERE r.ordre = :ordre"),
    @NamedQuery(name = "Resultats.findByScore", query = "SELECT r FROM Resultats r WHERE r.score = :score"),
    @NamedQuery(name = "Resultats.findBySuitesGagnees", query = "SELECT r FROM Resultats r WHERE r.suitesGagnees = :suitesGagnees"),
    @NamedQuery(name = "Resultats.findByChouettesVeluesPerdues", query = "SELECT r FROM Resultats r WHERE r.chouettesVeluesPerdues = :chouettesVeluesPerdues")})
public class Resultats implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ResultatsPK resultatsPK;
    @Column(name = "ordre")
    private Integer ordre;
    @Basic(optional = false)
    @Column(name = "score")
    private int score;
    @Column(name = "suites_gagnees")
    private Integer suitesGagnees;
    @Column(name = "chouettes_velues_perdues")
    private Integer chouettesVeluesPerdues;
    @JoinColumn(name = "pseudonyme", referencedColumnName = "pseudonyme", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Joueur joueur;
    @JoinColumn(name = "id_partie", referencedColumnName = "id_partie", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;

    public Resultats() {
    }

    public Resultats(ResultatsPK resultatsPK) {
        this.resultatsPK = resultatsPK;
    }

    public Resultats(ResultatsPK resultatsPK, int score) {
        this.resultatsPK = resultatsPK;
        this.score = score;
    }

    public Resultats(int idPartie, String pseudonyme) {
        this.resultatsPK = new ResultatsPK(idPartie, pseudonyme);
    }

    public ResultatsPK getResultatsPK() {
        return resultatsPK;
    }

    public void setResultatsPK(ResultatsPK resultatsPK) {
        this.resultatsPK = resultatsPK;
    }

    public Integer getOrdre() {
        return ordre;
    }

    public void setOrdre(Integer ordre) {
        this.ordre = ordre;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Integer getSuitesGagnees() {
        return suitesGagnees;
    }

    public void setSuitesGagnees(Integer suitesGagnees) {
        this.suitesGagnees = suitesGagnees;
    }

    public Integer getChouettesVeluesPerdues() {
        return chouettesVeluesPerdues;
    }

    public void setChouettesVeluesPerdues(Integer chouettesVeluesPerdues) {
        this.chouettesVeluesPerdues = chouettesVeluesPerdues;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (resultatsPK != null ? resultatsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Resultats)) {
            return false;
        }
        Resultats other = (Resultats) object;
        if ((this.resultatsPK == null && other.resultatsPK != null) || (this.resultatsPK != null && !this.resultatsPK.equals(other.resultatsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Resultats[ resultatsPK=" + resultatsPK + " ]";
    }
}
