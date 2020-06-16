/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele_de_donnees;

import dao.CategorieDao;
import dao.CommunauteDao;
import dao.JpaUtil;
import dao.OptionDao;
import dao.ServiceDao;
import dao.TemplateDao;
import dao.UtilisateurDao;
import java.util.Date;
import metier.modele.Categorie;
import metier.modele.Communaute;
import metier.modele.OptionService;
import metier.modele.Service;
import metier.modele.Template;
import metier.modele.Utilisateur;
import metier.service.ServiceMetier;
import metier.service.ServiceTechnique;

/**
 *
 * @author hjebalia
 */
public class InitBDD {
    public static void main(String[] args) throws Throwable {
        System.out.println("=================== Début test ) ===================");
        JpaUtil.creerEntityManager();
        UtilisateurDao utilisateur_dao = new UtilisateurDao();
        TemplateDao template_dao = new TemplateDao();
        ServiceDao service_dao = new ServiceDao();
        OptionDao option_dao = new OptionDao();
        CommunauteDao communaute_dao = new CommunauteDao();
        CategorieDao categorie_dao = new CategorieDao();
        
        Utilisateur utilisateur1 = new Utilisateur("MER", "Louis", "20 AV Albert EINSTEIN", 69621, "Villeurbanne", "louis.mer@insa-lyon.fr", "lmer");
        System.out.println("Objet créé: " + utilisateur1);
        JpaUtil.ouvrirTransaction();
        try{
            utilisateur_dao.create(utilisateur1);
            System.out.println("utilisateur 1 en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting user:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Utilisateur utilisateur2 = new Utilisateur("KANCEL", "justin", "20 AV Albert EINSTEIN", 69621, "Villeurbanne", "justin.kancel@insa-lyon.fr", "jkancel");
        System.out.println("Objet créé: " + utilisateur2);
        JpaUtil.ouvrirTransaction();
        try{
            utilisateur_dao.create(utilisateur2);
            System.out.println("utilisateur 2 en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting user:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Categorie categorie1 = new Categorie("cuisine");
        System.out.println("Objet créé: " + categorie1);
        JpaUtil.ouvrirTransaction();
        try{
            categorie_dao.create(categorie1);
            System.out.println("categorie en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting categorie:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction(); 
        
        Template template1 = new Template("Barbecue");
        template1.setCategorie(categorie1);
        System.out.println("Objet créé: " + template1);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template1);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
     
        Template template2 = new Template("préparation de repas");
        template2.setCategorie(categorie1);
        System.out.println("Objet créé: " + template2);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template2);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
 
        Categorie categorie2 = new Categorie("étude");
        System.out.println("Objet créé: " + categorie2);
        JpaUtil.ouvrirTransaction();
        try{
            categorie_dao.create(categorie2);
            System.out.println("categorie en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting categorie:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction(); 
                
        Template template3 = new Template("révision math");
        template3.setCategorie(categorie2);
        System.out.println("Objet créé: " + template3);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template3);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Template template4 = new Template("aide anglais");
        template4.setCategorie(categorie2);
        System.out.println("Objet créé: " + template4);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template4);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        OptionService option = new OptionService("nombre de repas");
        option.addTemplate(template1);
        System.out.println("Objet créé: " + option);
        JpaUtil.ouvrirTransaction();
        try{
            option_dao.create(option);
            System.out.println("option en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting option:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Communaute communaute = new Communaute("INSA", utilisateur1);
        communaute.addMembre(utilisateur1);
        communaute.addMembre(utilisateur2);
        communaute.addTemplate(template1);
        communaute.addTemplate(template2);
        communaute.addTemplate(template3);
        communaute.addTemplate(template4);
        System.out.println("Objet créé: " + communaute);
        JpaUtil.ouvrirTransaction();
        try{
            communaute_dao.create(communaute);
            System.out.println("communaute en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting caummunity:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Service service = new Service("Aide préparation Barbecue IF", "Nous cherchons de l'aide pour organiser un barbecue (courses, préparations, rangement)", "20 Av Albert Einstein", 69621, "Villeurbanne", 2, new Date(),template1, utilisateur2, communaute);
        System.out.println("Objet créé: " + service);
        JpaUtil.ouvrirTransaction();
        try{
            service_dao.create(service);
            System.out.println("service en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting service:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Categorie categorie3 = new Categorie("jardinage");
        System.out.println("Objet créé: " + categorie3);
        JpaUtil.ouvrirTransaction();
        try{
            categorie_dao.create(categorie3);
            System.out.println("categorie en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting categorie:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction(); 

        Template template5 = new Template("Tondre la pelouse");
        template5.setCategorie(categorie3);
        System.out.println("Objet créé: " + template5);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template5);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
     
        Template template6 = new Template("Potager");
        template6.setCategorie(categorie3);
        System.out.println("Objet créé: " + template6);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template6);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Categorie categorie4 = new Categorie("plomberie");
        System.out.println("Objet créé: " + categorie4);
        JpaUtil.ouvrirTransaction();
        try{
            categorie_dao.create(categorie4);
            System.out.println("categorie en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting categorie:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction(); 
        
        Template template7 = new Template("fuite d'eau");
        template7.setCategorie(categorie4);
        System.out.println("Objet créé: " + template7);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template7);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
     
        Template template8 = new Template("installation");
        template8.setCategorie(categorie4);
        System.out.println("Objet créé: " + template8);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template8);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        Communaute communaute2 = new Communaute("LYON",utilisateur1);
        communaute2.setCreateur(utilisateur1);
        communaute2.addMembre(utilisateur1);
        communaute2.addTemplate(template5);
        communaute2.addTemplate(template6);
        communaute2.addTemplate(template7);
        communaute2.addTemplate(template8);
        System.out.println("Objet créé: " + communaute2);
        JpaUtil.ouvrirTransaction();
        try{
            communaute_dao.create(communaute2);
            System.out.println("communaute en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting caummunity:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
        
        JpaUtil.fermerEntityManager();
        System.out.println("=================== Fin test =====================");
    }
}

