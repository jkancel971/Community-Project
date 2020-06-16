/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author hjebalia
 */
@Entity
/* IMPORTANT: Add unique constraint for (categorie_name, communaute) 
    on n'autorise pas d'avoir deux templates ayant meme nom de categorie dans la meme communaute
*/
public class Template implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nom;
    
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CATEGORIE_ID")
    private Categorie categorie;
    
    @ManyToMany
        @JoinTable(
            name="TEMPLATE_OPTION",
            joinColumns=@JoinColumn(name="TEMPLATE_ID", referencedColumnName="ID"),
            inverseJoinColumns=@JoinColumn(name="OPTION_ID", referencedColumnName="ID"))
    private List<OptionService> options;
    
    @ManyToMany(mappedBy = "templates")
    private List<Communaute> communautes;

    public Template() {
        options = new ArrayList<OptionService>();
        communautes = new ArrayList<Communaute>();
    }
    
    public Template(String nom_){
        nom = nom_;
        options = new ArrayList<OptionService>();
        communautes = new ArrayList<Communaute>();
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
    
    public Categorie getCategorie() {
        return categorie;
    }
    
    public void setCategorie(Categorie categorie_) {
        this.categorie = categorie_;
        if (!categorie.getTemplates().contains(this))
            categorie.addTemplate(this);
    }

    public List<OptionService> getOptions() {
        return options;
    }

    public void addOption(OptionService option_) {
        this.options.add(option_);
        if(!option_.getTemplates().contains(this))
            option_.addTemplate(this);
    }

    public List<Communaute> getCommunautes() {
        return communautes;
    }

    public void addCommunaute(Communaute communaute_) {
        this.communautes.add(communaute_);
        if(!communaute_.getTemplates().contains(this))
            communaute_.addTemplate(this);
    }

    @Override
    public String toString() {
        return "Template{" + "nom=" + nom + ", categorie=" + categorie + '}';
    }
}
