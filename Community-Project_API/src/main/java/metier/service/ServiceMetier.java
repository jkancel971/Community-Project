/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

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
import metier.modele.Service;
import metier.modele.Template;
import metier.modele.Utilisateur;

/**
 *
 * @author hjebalia
 */
public class ServiceMetier {
    UtilisateurDao utilisateur_dao = new UtilisateurDao();
    TemplateDao template_dao = new TemplateDao();
    ServiceDao service_dao = new ServiceDao();
    OptionDao option_dao = new OptionDao();
    CommunauteDao communaute_dao = new CommunauteDao();
    CategorieDao categorie_dao = new CategorieDao();
    
    public Utilisateur connecter_utilisateur(String mail, String mot_de_passe) throws Throwable {
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        Utilisateur utilisateur = utilisateur_dao.findByMail(mail);
        if(utilisateur != null){
            System.out.println("utilisateur trouvé grace à son mail");
            if(utilisateur.getMot_de_passe().equals(mot_de_passe)) 
                System.out.println("mot de passe correct");
        }
        JpaUtil.fermerEntityManager();
        return utilisateur;
    }
    
    public Utilisateur inscrire_utilisateur(Utilisateur utilisateur_) throws Throwable{
        try {          
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            Utilisateur utilisateur = utilisateur_dao.findByMail(utilisateur_.getMail());
            if(utilisateur == null){
                utilisateur_dao.create(utilisateur_);
                JpaUtil.validerTransaction();
                System.out.println("utilisateur crée: " + utilisateur.toString());
                JpaUtil.fermerEntityManager();
                return utilisateur_;
            }
            JpaUtil.fermerEntityManager();
            return utilisateur;
            } catch (Exception e) {
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                System.err.println("Error while creating a new user");
                System.err.println(e);
            }
        return null;
    }
        
    public Service creer_service(String nom, String description, String adresse, int code_postale, String ville, int nb_jeton_proposes, Date date_realisation, Template template, Utilisateur demandeur, Communaute communaute) throws Throwable {
        Service service = new Service(nom, description, adresse, code_postale, ville, nb_jeton_proposes, date_realisation, template, demandeur, communaute);
            try {
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                service_dao.create(service);
                JpaUtil.validerTransaction();
                System.out.println("service crée: " + service.toString());
                JpaUtil.fermerEntityManager();
                return service;
            } catch (Exception e) {
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                System.err.println("Error while creating a new service");
                System.err.println(e);
            }
        return null;
    }
    
    public void postuler(Service service, Utilisateur utilisateur){
       service.addPrestataire(utilisateur);
    }
    
    public Communaute creer_communaute(String nom_, Utilisateur createur_) throws Throwable {
            try {
                Communaute communaute = new Communaute(nom_,createur_);
                communaute.addMembre(createur_);
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                communaute_dao.create(communaute);
                JpaUtil.validerTransaction();
                System.out.println("communaute crée: " + communaute.toString());
                JpaUtil.fermerEntityManager();
                return communaute;
            } catch (Exception e) {
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                System.err.println("Error while creating a new community");
                System.err.println(e);
            }
        return null;
    }
    
    public Template creer_template(String nom_, Categorie cat_,Communaute communaute_) throws Throwable {
            try {
                Template template = new Template(nom_);
                template.setCategorie(cat_);
                communaute_.addTemplate(template);
                JpaUtil.creerEntityManager();
                JpaUtil.ouvrirTransaction();
                template_dao.create(template);
                communaute_dao.update(communaute_);
                JpaUtil.validerTransaction();
                System.out.println("template crée: " + template.toString());
                JpaUtil.fermerEntityManager();
                return template;
            } catch (Exception e) {
                JpaUtil.annulerTransaction();
                JpaUtil.fermerEntityManager();
                System.err.println("Error while creating a new template");
                System.err.println(e);
            }
        return null;
    }
}
