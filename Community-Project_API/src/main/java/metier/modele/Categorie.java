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
import javax.persistence.OneToMany;

/**
 *
 * @author hjebalia
 */
@Entity
public class Categorie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(unique = true)
    private String nom;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categorie")
    private List<Template> templates;
    
    public Categorie() {
        templates = new ArrayList<Template>();
    }
    
    public Categorie(String nom_){
        nom = nom_;
        templates = new ArrayList<Template>();
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

    public List<Template> getTemplates() {
        return templates;
    }
    
    public void addTemplate(Template template_) {
        this.templates.add(template_);
        if (template_.getCategorie()!= this)
            template_.setCategorie(this);
    }

    @Override
    public String toString() {
        return "Categorie{" + "nom=" + nom + '}';
    }
}
