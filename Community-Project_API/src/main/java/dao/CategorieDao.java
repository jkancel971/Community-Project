/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Categorie;

/**
 *
 * @author hjebalia
 */
public class CategorieDao {
    public void create(Categorie categorie) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(categorie);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Categorie update(Categorie categorie) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            categorie = em.merge(categorie);
        }
        catch(Exception e){
            throw e;
        }
        return categorie;
    }
    
    public Categorie findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Categorie categorie = null;
        try {
            categorie = em.find(Categorie.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return categorie;
    }
    
    public Categorie findByName(String nom) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Categorie> categorie = null;
        try {
            Query q = em.createQuery("SELECT c FROM Categorie c Where c.nom = :nom");   
            categorie = (List<Categorie>) q.setParameter("nom", nom).getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return categorie.get(0);
    }
    
    public List<Categorie> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Categorie> categories = null;
        try {
            Query q = em.createQuery("SELECT c FROM Categorie c ORDER BY c.nom");
            categories = (List<Categorie>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return categories;
    }
}
