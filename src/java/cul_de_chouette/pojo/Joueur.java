/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cul_de_chouette.pojo;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Frozy
 */
@Entity
@Table(name = "joueur", catalog = "cul_de_chouette", schema = "")
@NamedQueries({
    @NamedQuery(name = "Joueur.findAll", query = "SELECT j FROM Joueur j"),
    @NamedQuery(name = "Joueur.findByPseudonyme", query = "SELECT j FROM Joueur j WHERE j.pseudonyme = :pseudonyme"),
    @NamedQuery(name = "Joueur.findByEmail", query = "SELECT j FROM Joueur j WHERE j.email = :email"),
    @NamedQuery(name = "Joueur.findByMotDePasse", query = "SELECT j FROM Joueur j WHERE j.motDePasse = :motDePasse"),
    @NamedQuery(name = "Joueur.findByDateNaissance", query = "SELECT j FROM Joueur j WHERE j.dateNaissance = :dateNaissance"),
    @NamedQuery(name = "Joueur.findBySexe", query = "SELECT j FROM Joueur j WHERE j.sexe = :sexe"),
    @NamedQuery(name = "Joueur.findByVille", query = "SELECT j FROM Joueur j WHERE j.ville = :ville")})
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "pseudonyme")
    private String pseudonyme;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "mot_de_passe")
    private String motDePasse;
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    @Column(name = "sexe")
    private String sexe;
    @Column(name = "ville")
    private String ville;
    @OneToMany(mappedBy = "vainqueur")
    private Collection<Partie> partiesGagnees;
    @OneToMany(mappedBy = "hote")
    private Collection<Partie> partiesHote;
    @OneToMany(mappedBy = "joueurAffecte")
    private Collection<Interraction> interractionCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "joueur")
    private Collection<Resultats> resultatsCollection;

    public Joueur() {
    }

    public Joueur(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public Joueur(String pseudonyme, String email, String motDePasse) {
        this.pseudonyme = pseudonyme;
        this.email = email;
        this.motDePasse = motDePasse;
    }

    public String getPseudonyme() {
        return pseudonyme;
    }

    public void setPseudonyme(String pseudonyme) {
        this.pseudonyme = pseudonyme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Collection<Partie> getPartiesGagnees() {
        return partiesGagnees;
    }

    public void setPartiesGagnees(Collection<Partie> partieCollection) {
        this.partiesGagnees = partieCollection;
    }

    public Collection<Partie> getPartiesHote() {
        return partiesHote;
    }

    public void setPartiesHote(Collection<Partie> partieCollection1) {
        this.partiesHote = partieCollection1;
    }

    public Collection<Interraction> getInterractionCollection() {
        return interractionCollection;
    }

    public void setInterractionCollection(Collection<Interraction> interractionCollection) {
        this.interractionCollection = interractionCollection;
    }

    public Collection<Resultats> getResultatsCollection() {
        return resultatsCollection;
    }

    public void setResultatsCollection(Collection<Resultats> resultatsCollection) {
        this.resultatsCollection = resultatsCollection;
    }
    
    public int getNbPartiesJouee() {
        return this.resultatsCollection.size();
    }
    
    public int getNbPartiesGagnee() {
        return this.partiesGagnees.size();
    }
    
    public float getMoySuiteGagnee() {
        int suitesGagnee = 0;
        
        suitesGagnee = this.resultatsCollection.stream().map(resultat -> resultat.getSuitesGagnees()).reduce(suitesGagnee, Integer::sum);
        
        return this.resultatsCollection.isEmpty() ? 0 : (float) suitesGagnee / (float) this.resultatsCollection.size();
    }
    
    public float getMoyChouetteVeluesPerdu() {
        int chouetteVeluesPerdu = 0;
        
        chouetteVeluesPerdu = this.resultatsCollection.stream().map(resultat -> resultat.getChouettesVeluesPerdues()).reduce(chouetteVeluesPerdu, Integer::sum);
        
        return this.resultatsCollection.isEmpty() ? 0 : (float) chouetteVeluesPerdu / (float) this.resultatsCollection.size();
    }
    
    public float getMoyScore() {
        int score = 0;
        
        score = this.resultatsCollection.stream().map(resultat -> resultat.getScore()).reduce(score, Integer::sum);
        
        return this.resultatsCollection.isEmpty() ? 0 : (float) score / (float) this.resultatsCollection.size();
    }
    
    public int getAge() {
        LocalDate now = LocalDate.now();
        Period diff = Period.between(this.dateNaissance, now);
        
        return diff.getYears();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pseudonyme != null ? pseudonyme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        return !((this.pseudonyme == null && other.pseudonyme != null) || (this.pseudonyme != null && !this.pseudonyme.equals(other.pseudonyme)));
    }

    @Override
    public String toString() {
        return "pojo.Joueur[ pseudonyme=" + pseudonyme + " ]";
    }
}
