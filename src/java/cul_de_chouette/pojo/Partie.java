/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Frozy
 */
@Entity
@Table(name = "partie", catalog = "cul_de_chouette", schema = "")
@NamedQueries({
    @NamedQuery(name = "Partie.findAll", query = "SELECT p FROM Partie p"),
    @NamedQuery(name = "Partie.findByIdPartie", query = "SELECT p FROM Partie p WHERE p.idPartie = :idPartie"),
    @NamedQuery(name = "Partie.findByObjectif", query = "SELECT p FROM Partie p WHERE p.objectif = :objectif"),
    @NamedQuery(name = "Partie.findByDateDebut", query = "SELECT p FROM Partie p WHERE p.dateDebut = :dateDebut"),
    @NamedQuery(name = "Partie.findByDuree", query = "SELECT p FROM Partie p WHERE p.duree = :duree")})
public class Partie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_partie")
    private Integer idPartie;
    @Basic(optional = false)
    @Column(name = "objectif")
    private int objectif;
    @Column(name = "date_debut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "duree")
    private Float duree;
    @JoinColumn(name = "vainqueur", referencedColumnName = "pseudonyme")
    @ManyToOne
    private Joueur vainqueur;
    @JoinColumn(name = "hote", referencedColumnName = "pseudonyme")
    @ManyToOne
    private Joueur hote;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<Resultats> resultatsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Collection<Action> actionCollection;

    public Partie() {
    }

    public Partie(Integer idPartie) {
        this.idPartie = idPartie;
    }

    public Partie(Integer idPartie, int objectif) {
        this.idPartie = idPartie;
        this.objectif = objectif;
    }

    public Integer getIdPartie() {
        return idPartie;
    }

    public void setIdPartie(Integer idPartie) {
        this.idPartie = idPartie;
    }

    public int getObjectif() {
        return objectif;
    }

    public void setObjectif(int objectif) {
        this.objectif = objectif;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Float getDuree() {
        return duree;
    }

    public void setDuree(Float duree) {
        this.duree = duree;
    }

    public Joueur getVainqueur() {
        return vainqueur;
    }

    public void setVainqueur(Joueur vainqueur) {
        this.vainqueur = vainqueur;
    }

    public Joueur getHote() {
        return hote;
    }

    public void setHote(Joueur hote) {
        this.hote = hote;
    }

    public Collection<Resultats> getResultatsCollection() {
        return resultatsCollection;
    }

    public void setResultatsCollection(Collection<Resultats> resultatsCollection) {
        this.resultatsCollection = resultatsCollection;
    }

    public Collection<Action> getActionCollection() {
        return actionCollection;
    }

    public void setActionCollection(Collection<Action> actionCollection) {
        this.actionCollection = actionCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPartie != null ? idPartie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partie)) {
            return false;
        }
        Partie other = (Partie) object;
        return !((this.idPartie == null && other.idPartie != null) || (this.idPartie != null && !this.idPartie.equals(other.idPartie)));
    }

    @Override
    public String toString() {
        return "pojo.Partie[ idPartie=" + idPartie + " ]";
    }
    
}
