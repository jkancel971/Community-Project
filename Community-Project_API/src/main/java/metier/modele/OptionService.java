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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author hjebalia
 */
@Entity
public class OptionService implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nom;
    
    @ManyToMany(mappedBy="options")
    private List<Template> templates;
    
    public OptionService() {
        templates = new ArrayList<Template>();
    }
    
    public OptionService(String nom_){
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
        if(!template_.getOptions().contains(this))
            template_.getOptions().add(this);
    }

    @Override
    public String toString() {
        return "Option{" + "nom=" + nom + '}';
    }
}
