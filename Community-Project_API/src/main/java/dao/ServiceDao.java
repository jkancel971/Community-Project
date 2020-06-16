/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Service;

/**
 *
 * @author hjebalia
 */
public class ServiceDao {
    public void create(Service service) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            em.persist(service);
        }
        catch(Exception e) {
            throw e;
        }
    }
    
    public Service update(Service service) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        try {
            service = em.merge(service);
        }
        catch(Exception e){
            throw e;
        }
        return service;
    }
    
    public Service findById(Long id) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Service service = null;
        try {
            service = em.find(Service.class, id);
        }
        catch(Exception e) {
            throw e;
        }
        return service;
    }
    
    public List<Service> findByName(String nom) throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Service> service = null;
        try {
            Query q = em.createQuery("SELECT s FROM Service s Where s.nom = :nom");   
            service = (List<Service>) q.setParameter("nom", nom).getResultList();
        }
        catch(Exception e) {
            throw e;
        }
        return service;
    }
    
    public List<Service> findAll() throws Throwable {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Service> services = null;
        try {
            Query q = em.createQuery("SELECT s FROM Service s ORDER BY s.nom");
            services = (List<Service>) q.getResultList();
        }
        catch(Exception e) {
            throw e;
        }     
        return services;
    }
}
