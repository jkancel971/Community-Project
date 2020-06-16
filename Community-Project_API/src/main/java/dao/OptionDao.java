/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.OptionService;

/**
 *
 * @author hjebalia
 */
public class OptionDao {
    public void create(OptionService option) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(option);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public OptionService update(OptionService option) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            option = em.merge(option);
        }
        catch(Exception e){
            throw e;
        }
        return option;
    }
    
    public OptionService findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        OptionService option = null;
        try {
            option = em.find(OptionService.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return option;
    }
    
    public List<OptionService> findByName(String nom) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<OptionService> option = null;
        try {
            Query q = em.createQuery("SELECT o FROM Option o Where o.nom = :nom");   
            option = (List<OptionService>) q.setParameter("nom", nom).getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return option;
    }
    
    public List<OptionService> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<OptionService> options = null;
        try {
            Query q = em.createQuery("SELECT o FROM Option o ORDER BY o.nom");
            options = (List<OptionService>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return options;
    }
}
