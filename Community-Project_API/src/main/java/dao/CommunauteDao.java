/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Communaute;
import metier.modele.Utilisateur;

/**
 *
 * @author hjebalia
 */
public class CommunauteDao {
    public void create(Communaute communaute) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(communaute);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Communaute update(Communaute communaute) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            communaute = em.merge(communaute);
        }
        catch(Exception e){
            throw e;
        }
        return communaute;
    }
    
    public Communaute findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Communaute communaute = null;
        try {
            communaute = em.find(Communaute.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return communaute;
    }
    
    public Communaute findByName(String nom) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Communaute communaute = null;
        try {
            Query q = em.createQuery("SELECT c FROM Communaute c Where c.nom = :nom");   
            List<Communaute> communautes = (List<Communaute>) q.setParameter("nom", nom).getResultList();
            if(!communautes.isEmpty())
                return communautes.get(0);
        }
        catch(Exception e) {
            throw e;
        }
        return communaute;
    }
    
    public List<Communaute> findByTown(String town) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Communaute> communautes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Communaute c Where c.createur.ville = :town");   
            communautes = (List<Communaute>) q.setParameter("town", town).getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return communautes;
    }
    
    public List<Communaute> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Communaute> communautes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Communaute c ORDER BY c.nom");
            communautes = (List<Communaute>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return communautes;
    }
    
    public List<Communaute> findByUser(Utilisateur utilisateur) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Communaute> communautes = null;
        try {
            Query q = em.createQuery("SELECT c FROM Communaute c WHERE :u MEMBER OF (c.membres) ORDER BY c.nom");
            communautes = (List<Communaute>) q.setParameter("u", utilisateur).getResultList();
            return communautes;
        }
        catch(Exception e) {
            throw e;
        }     
    }
}
