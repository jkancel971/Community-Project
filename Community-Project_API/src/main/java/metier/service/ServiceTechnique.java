/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import dao.CategorieDao;
import dao.CommunauteDao;
import dao.JpaUtil;
import dao.ServiceDao;
import dao.TemplateDao;
import dao.UtilisateurDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import metier.modele.Categorie;
import metier.modele.Communaute;
import metier.modele.Service;
import metier.modele.Template;
import metier.modele.Utilisateur;

/**
 *
 * @author hjebalia
 */
public class ServiceTechnique {
    
    public Utilisateur recuperer_utilisateur(long utilisateur_id) throws Throwable{
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        UtilisateurDao utilisateur_dao = new UtilisateurDao();
        Utilisateur utilisateur = utilisateur_dao.findById(utilisateur_id);
        JpaUtil.fermerEntityManager();
        return utilisateur;
    }
    
    public Service recuperer_service(long service_id) throws Throwable{
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        ServiceDao service_dao = new ServiceDao();
        Service service = service_dao.findById(service_id);
        JpaUtil.fermerEntityManager();
        return service;
    }
    
    public Template recuperer_template(Communaute communaute, String template_nom) throws Throwable{
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        TemplateDao template_dao = new TemplateDao();
        //template_dao.findByNameCommunity contient un bug ! j'ai donc fait autrement
        //Template template = template_dao.findByNameCommunity(template_nom, communaute);
        List<Template> templates = communaute.getTemplates();
        for(int i=0; i<templates.size(); i++){
            if(templates.get(i).getCommunautes().contains(communaute)){
                JpaUtil.fermerEntityManager();
                return templates.get(i);
            }
        }
        JpaUtil.fermerEntityManager();
        return null;
        
    }
    
    public Communaute recuperer_communaute(String communaute_nom) throws Throwable{
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        CommunauteDao communaute_dao = new CommunauteDao();
        Communaute communaute_by_name = communaute_dao.findByName(communaute_nom);
        JpaUtil.fermerEntityManager();
        return communaute_by_name;
    }
    
    public List<Communaute> recuperer_communaute_par_ville(String ville_nom) throws Throwable{
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        CommunauteDao communaute_dao = new CommunauteDao();
        List<Communaute> communaute_by_name = communaute_dao.findByTown(ville_nom);
        JpaUtil.fermerEntityManager();
        return communaute_by_name;
    }
    
    public List<Communaute> recuperer_communautes_par_utilisateur(Utilisateur utilisateur) throws Throwable{
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        CommunauteDao communaute_dao = new CommunauteDao();
        List<Communaute> list = communaute_dao.findByUser(utilisateur);
        JpaUtil.fermerEntityManager();
        return list;
    }
    
    public Map<String, List<String>> recuperer_communautes_categories(Utilisateur utilisateur) throws Throwable{
        Map<String, List<String>> data = new HashMap<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        List<Communaute> list_communautes = utilisateur.getCommunautes();
        if(!list_communautes.isEmpty()){
            for (Communaute com : list_communautes) {
                List<Template> templates_list = com.getTemplates();
                if(!templates_list.isEmpty()){
                    List<String> list_cat = new ArrayList<>();
                    for (Template temp : templates_list) {
                        String cat_nom = temp.getCategorie().getNom();
                        if(!list_cat.contains(cat_nom))
                            list_cat.add(cat_nom);
                    }
                    data.put(com.getNom(), list_cat);
                }
            }
        }
        JpaUtil.fermerEntityManager();
        if(data.isEmpty())
            return null;
        return data;
    }
    
    public Map<String, List<String>> recuperer_categories_templates(Utilisateur utilisateur) throws Throwable{
        Map<String, List<String>> data = new HashMap<>();
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        List<Communaute> list_communautes = utilisateur.getCommunautes();
        if(!list_communautes.isEmpty()){
            for (Communaute com : list_communautes) {
                List<Template> list_temp = com.getTemplates();
                if(!list_temp.isEmpty()){
                    for (Template temp : list_temp){
                        if(data.containsKey(temp.getCategorie().getNom())){
                            data.get(temp.getCategorie().getNom()).add(temp.getNom());
                        }
                        else {
                            List<String> list_serv = new ArrayList<>();
                            list_serv.add(temp.getNom());
                            data.put(temp.getCategorie().getNom(), list_serv);
                        }
                    }
                }
            }
        }
        JpaUtil.fermerEntityManager();
        return data;
    }
    
    public List<Categorie> recuperer_categories() throws Throwable{
        JpaUtil.creerEntityManager();
        JpaUtil.ouvrirTransaction();
        CategorieDao categorie_dao = new CategorieDao();
        List<Categorie> categories = categorie_dao.findAll();
        JpaUtil.fermerEntityManager();
        return categories;
    }
    
    public Categorie recuperer_categorie_by_name(String nom_) throws Throwable{
    JpaUtil.creerEntityManager();
    JpaUtil.ouvrirTransaction();
    CategorieDao categorie_dao = new CategorieDao();
    Categorie categorie = categorie_dao.findByName(nom_);
    JpaUtil.fermerEntityManager();
    return categorie;
    }
}
