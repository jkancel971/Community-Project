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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
public class MddTestsMain {
    public static void main(String[] args) throws Throwable {
        System.out.println("=================== Début test ) ===================");
        /*JpaUtil.creerEntityManager();
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
        
        Communaute communaute = new Communaute("INSA", utilisateur1);
        communaute.addMembre(utilisateur1);
        communaute.addMembre(utilisateur2);
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
        
        Categorie categorie = new Categorie("jardinage");
        System.out.println("Objet créé: " + categorie);
        JpaUtil.ouvrirTransaction();
        try{
            categorie_dao.create(categorie);
            System.out.println("categorie en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting categorie:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction(); 
        
        Template template = new Template("jardinage");
        System.out.println("Objet créé: " + template);
        JpaUtil.ouvrirTransaction();
        try{
            template_dao.create(template);
            System.out.println("template en cours de persistance");
        } catch (Exception e)
        {
            JpaUtil.annulerTransaction();
            System.err.println("Error while persisting template:");
            System.err.println(e);
        }
        JpaUtil.validerTransaction();
 
        OptionService option = new OptionService("surface du jardin");
        option.addTemplate(template);
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
        
        Service service = new Service("arroser les plantes", "j'ai besoin de qqun pour m'aider a arroser les plantes chez moi", 2, new Date(),template, utilisateur2, communaute);
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
        JpaUtil.fermerEntityManager();
        */
        //Test connect
        //ServiceMetier serviceMetier = new ServiceMetier();
        //System.out.println(serviceMetier.connecter_utilisateur("louis.mer@insa-lyon.fr", "lmer"));
        //System.out.println(serviceMetier.connecter_utilisateur("louis.mer@insa-lyon.fr", "lmer"));
        //System.out.println(serviceMetier.connecter_utilisateur("louis.mer@insa-lyon.fr", "lmer"));
        
        //Test recuperer_communaute_par_utilisateur
        /*ServiceTechnique serviceTechnique = new ServiceTechnique();
        UtilisateurDao utilisateur_dao = new UtilisateurDao();
        JpaUtil.creerEntityManager();
        long x = 1;
        Utilisateur u = utilisateur_dao.findById(x);
        JpaUtil.fermerEntityManager();
        
        System.out.println(serviceTechnique.recuperer_communaute_par_utilisateur(u));
        */
        
        //Test recuperer communautes categories services
        /*ServiceTechnique serviceTechnique = new ServiceTechnique();
        UtilisateurDao utilisateur_dao = new UtilisateurDao();
        JpaUtil.creerEntityManager();
        long x = 1;
        Utilisateur u = utilisateur_dao.findById(x);
        JpaUtil.fermerEntityManager();
        System.out.println(serviceTechnique.recuperer_communautes_categories(u));
        System.out.println(serviceTechnique.recuperer_categories_templates(u));
        */
        
        //Test ceration service
        /*ServiceMetier serviceMetier = new ServiceMetier();
        ServiceTechnique serviceTechnique = new ServiceTechnique();
        UtilisateurDao utilisateur_dao = new UtilisateurDao();
        JpaUtil.creerEntityManager();
        long x = 1;
        Utilisateur u = utilisateur_dao.findById(x);
        JpaUtil.fermerEntityManager();
        
        Communaute communaute = serviceTechnique.recuperer_communaute(u.getCommunautes().get(0).getNom());
        Template template = serviceTechnique.recuperer_template(communaute, communaute.getTemplates().get(0).getNom());
        System.out.println(communaute.getNom());
        System.out.println(template);
        
        
        SimpleDateFormat simple_date_format = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(serviceMetier.creer_service("nom", "description", "20 Av Albert Einstein", 69621, "Villeurbanne", 3, simple_date_format.parse("12-04-2018"), communaute.getTemplates().get(0), u, communaute));
        */
        ServiceTechnique serviceTechnique = new ServiceTechnique();
        System.out.println(serviceTechnique.recuperer_communaute_par_ville("Villeurbanne"));
        
        System.out.println("=================== Fin test) =====================");
    }
}
