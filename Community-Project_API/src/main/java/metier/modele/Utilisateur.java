/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 *
 * @author hjebalia
 */
@Entity
public class Utilisateur implements Serializable {
    //nombre de jetons offerts suite à l'inscription à l'application
    @Transient
    private final int NB_JETON_INIT = 3;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nom;
    private String prenom;
    private String adresse;
    private int code_postale;
    private String ville;
    
    @Column(unique=true)
    private String mail;
    
    private String mot_de_passe;
    private int nb_jeton;
    private int nb_jetons_offerts;
    private int nb_jetons_recus;
    private int nb_services_demandes;
    private int nb_services_realises;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "demandeur")
    private List<Service> services_demandes;
    
    @ManyToMany
        @JoinTable(
            name="UTILISATEUR_SERVICE",
            joinColumns=@JoinColumn(name="UTILISATEUR_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="SERVICE_ID", referencedColumnName="ID"))
    private List<Service> services_postules;
    
    @OneToMany(mappedBy = "prestataire")
    private List<Service> services_realises;
    
    @ManyToMany
        @JoinTable(
            name="UTILISATEUR_COMMUNAUTE",
            joinColumns=@JoinColumn(name="UTILISATEUR_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="COMMUNAUTE_ID", referencedColumnName="ID"))
    private List<Communaute> communautes;
    
    @OneToMany(mappedBy = "createur")
    private List<Communaute> communautes_crees;
    
    public Utilisateur() {
        services_demandes = new ArrayList<Service>();
        services_postules = new ArrayList<Service>();
        services_realises = new ArrayList<Service>();
        communautes = new ArrayList<Communaute>();
        communautes_crees = new ArrayList<Communaute>();
    }
    
    public Utilisateur(String nom_, String prenom_, String adresse_, int code_postale_, String ville_, String mail_, String mot_de_passe_) {
        nom = nom_;
        prenom = prenom_;
        adresse = adresse_;
        code_postale = code_postale_;
        ville = ville_;
        mail = mail_;
        mot_de_passe = mot_de_passe_;
        nb_jeton = NB_JETON_INIT;
        nb_jetons_offerts = 0;
        nb_jetons_recus = 0;
        nb_services_demandes = 0;
        nb_services_realises = 0;
        services_demandes = new ArrayList<Service>();
        services_postules = new ArrayList<Service>();
        services_realises = new ArrayList<Service>();
        communautes = new ArrayList<Communaute>();
        communautes_crees = new ArrayList<Communaute>();
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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

    public String getMot_de_passe() {
        return mot_de_passe;
    }

    public void setMot_de_passe(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }
    
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getNb_jeton() {
        return nb_jeton;
    }

    public void setNb_jeton(int nb_jeton) {
        this.nb_jeton = nb_jeton;
    }

    public int getNb_jetons_offerts() {
        return nb_jetons_offerts;
    }

    public void setNb_jetons_offerts(int nb_jetons_offerts) {
        this.nb_jetons_offerts = nb_jetons_offerts;
    }

    public int getNb_jetons_recus() {
        return nb_jetons_recus;
    }

    public void setNb_jetons_recus(int nb_jetons_recus) {
        this.nb_jetons_recus = nb_jetons_recus;
    }

    public int getNb_services_demandes() {
        return nb_services_demandes;
    }

    public void setNb_services_demandes(int nb_services_demandes) {
        this.nb_services_demandes = nb_services_demandes;
    }

    public int getNb_services_realises() {
        return nb_services_realises;
    }

    public void setNb_services_realises(int nb_services_realises) {
        this.nb_services_realises = nb_services_realises;
    }

    public List<Service> getServices_demandes() {
        return services_demandes;
    }

    public void addServie_demande(Service service_demande) {
        this.services_demandes.add(service_demande);
        if (service_demande.getDemandeur() != this)
            service_demande.setDemandeur(this);
    }

    public List<Service> getServices_postules() {
        return services_postules;
    }

    public void addService_postule(Service service_postule) {
        this.services_postules.add(service_postule);
        if (!service_postule.getPrestataires().contains(this))
            service_postule.addPrestataire(this);
    }

    public List<Service> getServices_realises() {
        return services_realises;
    }

    public void addServie_realise(Service service_realise) {
        this.services_realises.add(service_realise);
        if (service_realise.getPrestataire() != this)
            service_realise.setPrestataire(this);
    }

    public List<Communaute> getCommunautes() {
        return communautes;
    }

    public void addCommunaute(Communaute communaute) {
        this.communautes.add(communaute);
        if (!communaute.getMembres().contains(this))
            communaute.addMembre(this);
    }

    public List<Communaute> getCommunautes_crees() {
        return communautes_crees;
    }

    public void addCommunautes_crees(Communaute communaute_cree) {
        this.communautes_crees.add(communaute_cree);
        if (communaute_cree.getCreateur() != this)
            communaute_cree.setCreateur(this);
    }

    @Override
    public String toString() {
        return "Utilisateur{" +  "nom=" + nom + ", prenom=" + prenom + ", nb_jeton=" + nb_jeton + ", nb_jetons_offerts=" + nb_jetons_offerts + ", nb_jetons_recus=" + nb_jetons_recus + ", nb_services_demandes=" + nb_services_demandes + ", nb_services_realises=" + nb_services_realises;
    }
}
