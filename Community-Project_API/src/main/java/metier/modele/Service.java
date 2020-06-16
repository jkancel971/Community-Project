/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author hjebalia
 */
@Entity
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nom;
    private String description;
    private String adresse;
    private int code_postale;
    private String ville;
    private int nb_jeton_proposes;
    
    public enum STATUS{OUVERT, POURVU, REALISE, CLOTURE, ANNULE};
    @Enumerated(EnumType.STRING)
    private STATUS status;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_demande;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_realisation;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date date_cloture;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="TEMPLATE_ID")
    private Template template;
    
    @ManyToOne
    private Utilisateur demandeur;
    
    @ManyToOne
    private Communaute communaute;
    
    @ManyToMany(mappedBy = "services_postules")
    private List<Utilisateur> prestataires;
    
    @ManyToOne
    private Utilisateur prestataire;
    
    public Service() {
        prestataires = new ArrayList<Utilisateur>();
    }
    
    public Service(String nom_, String description_, String adresse_, int cp_, String ville_, int nb_jeton_proposes_, Date date_realisation_,Template template_, Utilisateur demandeur_, Communaute communaute_){
        nom = nom_;
        description = description_;
        adresse = adresse_;
        code_postale = cp_;
        ville = ville_;
        nb_jeton_proposes = nb_jeton_proposes_;
        date_realisation = date_realisation_;
        status = status.OUVERT;
        date_demande = new Date();
        template = template_;
        demandeur = demandeur_;
        demandeur_.setNb_services_demandes(demandeur_.getNb_services_demandes()+1);
        communaute = communaute_;
        prestataires = new ArrayList<Utilisateur>();
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getCode_postale() {
        return code_postale;
    }

    public void setCode_postale(int code_postale) {
        this.code_postale = code_postale;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public int getNb_jeton_proposes() {
        return nb_jeton_proposes;
    }

    public void setNb_jeton_proposes(int nb_jeton_proposes) {
        this.nb_jeton_proposes = nb_jeton_proposes;
    }
    
    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public Date getDate_demande() {
        return date_demande;
    }

    public void setDate_demande(Date date_demande) {
        this.date_demande = date_demande;
    }

    public Date getDate_realisation() {
        return date_realisation;
    }

    public void setDate_realisation(Date date_realisation) {
        this.date_realisation = date_realisation;
    }

    public Date getDate_cloture() {
        return date_cloture;
    }

    public void setDate_cloture(Date date_cloture) {
        this.date_cloture = date_cloture;
    }

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Utilisateur getDemandeur() {
        return demandeur;
    }

    public void setDemandeur(Utilisateur demandeur) {
        this.demandeur = demandeur;
        if (!this.demandeur.getServices_demandes().contains(this))
            this.demandeur.addServie_demande(this);
    }

    public Communaute getCommunaute() {
        return communaute;
    }

    public void setCommunaute(Communaute communaute) {
        this.communaute = communaute;
        if (!this.communaute.getServices_demandes().contains(this))
            this.communaute.addServie_demande(this);
    }

    public List<Utilisateur> getPrestataires() {
        return prestataires;
    }

    public void addPrestataire(Utilisateur prestataire_) {
        this.prestataire = prestataire_;
        if (!prestataire_.getServices_realises().contains(this))
            prestataire_.addServie_realise(this);
    }

    public Utilisateur getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Utilisateur prestataire_) {
        this.prestataire = prestataire_;
        if (this.prestataire.getServices_realises() != this)
            this.prestataire.addServie_realise(this);
    }

    @Override
    public String toString() {
        return "Service{" + "nom=" + nom + ", description=" + description + ", adresse=" + adresse + ", code_postale=" + code_postale + ", ville=" + ville + ", status=" + status + ", date_demande=" + date_demande + ", date_realisation=" + date_realisation + ", date_cloture=" + date_cloture + '}';
    }
}
