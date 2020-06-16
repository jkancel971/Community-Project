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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author hjebalia
 */
@Entity
public class Communaute implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique = true)
    private String nom;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CREATEUR_ID")
    private Utilisateur createur;
    
    @ManyToMany(mappedBy = "communautes" )
    private List<Utilisateur> membres;
    
    @ManyToMany
        @JoinTable(
            name="COMMUNAUTE_TEMPLATE",
            joinColumns=@JoinColumn(name="COMMUNAUTE_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="TEMPLATE_ID", referencedColumnName="ID"))
    private List<Template> templates;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "communaute")
    private List<Service> services_demandes;
    
    public Communaute() {
        membres = new ArrayList<Utilisateur>();
        templates = new ArrayList<Template>();
        services_demandes = new ArrayList<Service>();
    }
    
    public Communaute(String nom_, Utilisateur createur_){
        nom = nom_;
        createur = createur_;
        membres = new ArrayList<Utilisateur>();
        //membres.add(createur_);
        createur_.addCommunaute(this);
        templates = new ArrayList<Template>();
        services_demandes = new ArrayList<Service>();
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

    public Utilisateur getCreateur() {
        return createur;
    }

    public void setCreateur(Utilisateur createur) {
        this.createur = createur;
        if (!this.createur.getCommunautes_crees().contains(this))
            this.createur.addCommunautes_crees(this);
    }
    
    public List<Utilisateur> getMembres() {
        return membres;
    }

    public void addMembre(Utilisateur membre) {
        this.membres.add(membre);
        if (!membre.getCommunautes().contains(this))
            membre.addCommunaute(this);
    }

    public List<Template> getTemplates() {
        return templates;
    }

    public void addTemplate(Template template) {
        this.templates.add(template);
        if (!template.getCommunautes().contains(this))
            template.addCommunaute(this);
    }

    public List<Service> getServices_demandes() {
        return services_demandes;
    }

    public void addServie_demande(Service service_demande) {
        this.services_demandes.add(service_demande);
        if (service_demande.getCommunaute() != this)
            service_demande.setCommunaute(this);
    }

    @Override
    public String toString() {
        return "Communaute{" + "nom=" + nom + '}';
    }
}
