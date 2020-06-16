/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Categorie;
import metier.modele.Communaute;
import metier.modele.Template;

/**
 *
 * @author hjebalia
 */
public class TemplateDao {
    public void create(Template template) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(template);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Template update(Template template) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            template = em.merge(template);
        }
        catch(Exception e){
            throw e;
        }
        return template;
    }
    
    public Template findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Template template = null;
        try {
            template = em.find(Template.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return template;
    }
    
    public List<Template> findByName(String nom) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Template> template = null;
        try {
            Query q = em.createQuery("SELECT t FROM Template t Where t.nom = :nom");   
            template = (List<Template>) q.setParameter("nom", nom).getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return template;
    }
    
    public List<Template> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Template> templates = null;
        try {
            Query q = em.createQuery("SELECT t FROM Template t ORDER BY t.nom");
            templates = (List<Template>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return templates;
    }
    
    public Template findByNameCommunity(String template_name, Communaute communaute) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            Query q = em.createQuery("SELECT t FROM Template t Where t.nom = :template_name");
            q.setParameter("template_name", template_name);
            List<Template> templates = (List<Template>) q.getResultList();
            if(!templates.isEmpty()){
                for(int i=0; i<templates.size(); i++){
                    //Ce if contient un bug !!!
                    if(templates.get(i).getCommunautes().contains(communaute)){
                        return templates.get(i);
                    }
                }
            }
            return null;
        }
        catch(Exception e) {
            throw e;
        }
    }
}
