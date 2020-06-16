/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Utilisateur;

/**
 *
 * @author hjebalia
 */
public class UtilisateurDao {
    public void create(Utilisateur utilisateur) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(utilisateur);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Utilisateur update(Utilisateur utilisateur) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            utilisateur = em.merge(utilisateur);
        }
        catch(Exception e){
            throw e;
        }
        return utilisateur;
    }
    
    public Utilisateur findById(long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Utilisateur utilisateur = null;
        try {
            utilisateur = em.find(Utilisateur.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return utilisateur;
    }
    
    public Utilisateur findByMail(String mail) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            List<Utilisateur> utilisateurs = null;
            Query q = em.createQuery("SELECT u FROM Utilisateur u Where u.mail = :mail");   
            utilisateurs = (List<Utilisateur>) q.setParameter("mail", mail).getResultList();
            if(!utilisateurs.isEmpty())
                return utilisateurs.get(0);
            return null;
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public List<Utilisateur> findByName(String nom) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Utilisateur> utilisateur = null;
        try {
            Query q = em.createQuery("SELECT u FROM Utilisateur u Where u.nom = :nom");   
            utilisateur = (List<Utilisateur>) q.setParameter("nom", nom).getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return utilisateur;
    }
    
    public List<Utilisateur> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Utilisateur> utilisateurs = null;
        try {
            Query q = em.createQuery("SELECT u FROM Utilisateur u ORDER BY u.nom");
            utilisateurs = (List<Utilisateur>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return utilisateurs;
    }
}
