/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.gson.Gson;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import metier.modele.Categorie;
import metier.modele.Communaute;
import metier.modele.Service;
import metier.modele.Template;
import metier.modele.Utilisateur;
import metier.service.ServiceMetier;
import metier.service.ServiceTechnique;

/**
 *
 * @author hjebalia
 */
@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {
            
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Throwable ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Throwable {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession(true);
        PrintWriter out = response.getWriter();
        Gson json_serializer = new Gson();
        
     
        ServiceMetier service_metier = new ServiceMetier();
        ServiceTechnique service_technique = new ServiceTechnique();
  
        if (request.getParameter("action").equals("check_permission")) {
            try {
                if(session.getAttribute("utilisateur_id") == null)
                    out.print("{\"response\": 401}");
                else
                    out.print("{\"response\": 200}");
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        else if(request.getParameter("action").equals("connect")){
            /* Request parameters: nom_utilisateur, mot_de_passe */
            try {
                if(request.getParameter("nom_utilisateur") == "" || request.getParameter("mot_de_passe") == "")
                    out.print("{\"response\": 401}");
                else{
                    Utilisateur utilisateur = service_metier.connecter_utilisateur(request.getParameter("nom_utilisateur"), request.getParameter("mot_de_passe"));
                    if(utilisateur == null)
                        out.print("{\"response\": 401}");
                    else{
                        session.setAttribute("utilisateur_id", utilisateur.getId());
                        out.print("{\"response\": 200}");
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("disconnect")){
            try {
                session.setAttribute("utilisateur_id", null);
                out.print("{\"response\": 200}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("subscribe_utilisateur")){
            try {
                Utilisateur utilisateur = new Utilisateur(  request.getParameter("nom_utilisateur"),
                                                            request.getParameter("prenom_utilisateur"),
                                                            request.getParameter("adresse"),
                                                            Integer.parseInt(request.getParameter("code_postale")),
                                                            request.getParameter("ville"),
                                                            request.getParameter("mail"),
                                                            request.getParameter("mot_de_passe"));
                Utilisateur utilisateur_exist = service_metier.inscrire_utilisateur(utilisateur);
                if(utilisateur_exist != null){
                    session.setAttribute("utilisateur_id", utilisateur_exist.getId());
                    out.print("{\"response\": 409}");
                    System.out.println("Erreur, user existant");
                }
                else{
					System.out.println("Erreur 2");
                    session.setAttribute("utilisateur_id", utilisateur.getId());
                    out.print("{\"response\": 200}");
                }
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("init_demande")){
            try {
                Utilisateur utilisateur = service_technique.recuperer_utilisateur((long) (session.getAttribute("utilisateur_id")));

                Map<String, List<String>> communautes_categories = service_technique.recuperer_communautes_categories(utilisateur);
                Map<String, List<String>> communautes_services = service_technique.recuperer_categories_templates(utilisateur);
                if(!communautes_categories.isEmpty() && !communautes_services.isEmpty()){
                    javax.json.JsonObjectBuilder data_json = Json.createObjectBuilder();
                    javax.json.JsonObjectBuilder com_cat_json = Json.createObjectBuilder();
                    javax.json.JsonObjectBuilder com_serv_json = Json.createObjectBuilder();
                    
                    for(Map.Entry<String, List<String>> com_cat : communautes_categories.entrySet()){
                        javax.json.JsonArrayBuilder cat_json = Json.createArrayBuilder();
                        for(String cat: com_cat.getValue()){
                            cat_json.add(cat);
                        }
                        com_cat_json.add(com_cat.getKey(),cat_json);
                    }
                    data_json.add("categories", com_cat_json);
                    
                    for(Map.Entry<String, List<String>> com_serv : communautes_services.entrySet()){
                        javax.json.JsonArrayBuilder serv_json = Json.createArrayBuilder();
                        for(String serv: com_serv.getValue()){
                            serv_json.add(serv);
                        }
                        com_serv_json.add(com_serv.getKey(),serv_json);
                    }
                    data_json.add("services", com_serv_json);

                    out.print(data_json.build());
                }
                else
                    out.print("{\"response\": 403}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if (request.getParameter("action").equals("create_service")) {
            try {
                SimpleDateFormat simple_date_format = new SimpleDateFormat("dd-MM-yyyy");
                Utilisateur utilisateur = service_technique.recuperer_utilisateur((long) (session.getAttribute("utilisateur_id")));
                Communaute communaute = service_technique.recuperer_communaute(request.getParameter("communaute_nom"));
                if(communaute != null){
                    Template template = service_technique.recuperer_template(communaute, request.getParameter("template_nom"));
                    if(template != null){
                        service_metier.creer_service(request.getParameter("nom_service"),
                                                    request.getParameter("descrition"),
                                                    request.getParameter("adresse"),
                                                    Integer.parseInt(request.getParameter("code_postale")),
                                                    request.getParameter("ville"),
                                                    Integer.parseInt(request.getParameter("nb_jeton_proposes")),
                                                    simple_date_format.parse(request.getParameter("date_realisation")), 
                                                    template, 
                                                    utilisateur, 
                                                    communaute);
                        out.print("{\"response\": 200}");
                    }
                }
                else
                    out.print("{\"response\": 403}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("init_service")){
            try {
                long id = Long.parseLong(request.getParameter("id"));
                Service service = service_technique.recuperer_service(id);
                out.print("{\"titre\": \""+service.getNom()+"\",\"adresse\": \""+service.getAdresse()+"\",\"CP\": \""+service.getCode_postale()+"\",\"date_demande\": \""+service.getDate_demande()+"\",\"date_realisation\": \""+service.getDate_realisation()+"\",\"description\": \""+service.getDescription()+"\",\"jetons\": \""+service.getNb_jeton_proposes()+"\",\"status\": \""+service.getStatus()+"\",\"ville\": \""+service.getVille()+"\",\"communaute\": \""+service.getCommunaute().getNom()+"\",\"demandeur\": \""+service.getDemandeur().getNom()+"\"}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("postuler")){
            try {
                long id = Long.parseLong(request.getParameter("id"));
                Utilisateur utilisateur = service_technique.recuperer_utilisateur((long) (session.getAttribute("utilisateur_id")));
                Service service = service_technique.recuperer_service(id);
                service_metier.postuler(service, utilisateur);
                out.print("{\"response\": 200}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if (request.getParameter("action").equals("create_communaute")) {
            try {
                Utilisateur utilisateur = service_technique.recuperer_utilisateur((long) (session.getAttribute("utilisateur_id")));
                Communaute communaute = service_technique.recuperer_communaute(request.getParameter("communaute_nom"));
                    if(communaute == null){
                        communaute = service_metier.creer_communaute(request.getParameter("communaute_nom"),utilisateur);
                        String categories = request.getParameter("categories");
                        categories = categories.replace("[", "");
                        categories = categories.replace("]", "");
                        categories = categories.replace("\"", "");
                        String array[] = categories.split(",");
                        for(int i = 0;i<array.length;i++)
                        {
                            service_metier.creer_template(array[i], service_technique.recuperer_categorie_by_name(array[i]),communaute);
                        }
                        out.print("{\"response\": 200,\"com_id\": "+communaute.getId()+"}");
                    }
                    else
                         out.print("{\"response\": 403}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("init_creation_communaute")){
            try {
                List<Categorie> categories = service_technique.recuperer_categories();
                if(!categories.isEmpty()){
                    javax.json.JsonArrayBuilder cat_json = Json.createArrayBuilder();
                    for(Categorie cat: categories){
                        cat_json.add(cat.getNom());
                    }
                    out.print(cat_json.build());
                }
                else
                    out.print("{\"response\": 403}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("init_liste_service")){
            try {
                Utilisateur utilisateur = service_technique.recuperer_utilisateur((long) (session.getAttribute("utilisateur_id")));
                List<Communaute> communautes = service_technique.recuperer_communautes_par_utilisateur(utilisateur);
                List<Service> services;
                if(!communautes.isEmpty()){
                    javax.json.JsonObjectBuilder data_json = Json.createObjectBuilder();
                    javax.json.JsonObjectBuilder temp_json = Json.createObjectBuilder();
                    javax.json.JsonArrayBuilder com_json = Json.createArrayBuilder();
                    javax.json.JsonArrayBuilder service_json = Json.createArrayBuilder();
                    javax.json.JsonArrayBuilder service_postule_json = Json.createArrayBuilder();
                    for(Communaute com: communautes){
                        com_json.add(com.getNom());
                        services = com.getServices_demandes();
                        for(Service ser : services){
                            if(!ser.getStatus().equals("CLOTURE")){
                                    temp_json.add("service_id", ser.getId());
                                    temp_json.add("service_nom", ser.getNom());
                                    temp_json.add("service_cat", ser.getTemplate().getCategorie().getNom().toLowerCase());
                                    temp_json.add("service_demandeur", ser.getDemandeur().getNom().concat(" ").concat(ser.getDemandeur().getPrenom()));
                                    temp_json.add("service_date", ser.getDate_realisation().toString());
                                    temp_json.add("service_jetons", ser.getNb_jeton_proposes());
                                if(!(ser.getDemandeur().getId()!= (long) (session.getAttribute("utilisateur_id")))){
                                    service_json.add(temp_json);
                                }else{
                                    service_postule_json.add(temp_json);
                                }
                                temp_json = Json.createObjectBuilder();
                            }
                        }
                    }
                    data_json.add("communautes_noms",com_json);
                    data_json.add("service_pourvoir",service_json);
                    data_json.add("service_postule",service_postule_json);
                    out.print(data_json.build());
                    }
                else
                    out.print("{\"response\": 403}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("init_profil")){
            try {
                
                Utilisateur user = service_technique.recuperer_utilisateur((long) (session.getAttribute("utilisateur_id")));
                out.print("{\"nom\": \""+user.getNom()+"\",\"prenom\": \""+user.getPrenom()+"\",\"mail\": \""+user.getMail()+"\",\"adresse\": \""+user.getAdresse()+"\",\"ville\": \""+user.getVille()+"\",\"cp\": \""+user.getCode_postale()+"\"}");
                List<Communaute> list = service_technique.recuperer_communautes_par_utilisateur(user);
                if(!list.isEmpty()){
                    javax.json.JsonObjectBuilder data_json = Json.createObjectBuilder();
                    javax.json.JsonObjectBuilder com_cat_json = Json.createObjectBuilder();
                    javax.json.JsonObjectBuilder com_serv_json = Json.createObjectBuilder();
                    
                    
                    
                }
                
                
                
            }catch(Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
            else if(request.getParameter("action").equals("rechercher_communaute_par_nom")){
            try {
                Communaute communaute = service_technique.recuperer_communaute(request.getParameter("nom"));
                if(communaute != null){
                    javax.json.JsonObjectBuilder com_id_nom_json = Json.createObjectBuilder();
                    com_id_nom_json.add(communaute.getNom(), communaute.getId());
                    out.print(com_id_nom_json.build());
                }
                else
                    out.print("{\"response\": 403}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("rechercher_communaute_par_ville")){
            try {
                List<Communaute> communautes = service_technique.recuperer_communaute_par_ville(request.getParameter("ville"));
                if(!communautes.isEmpty()){
                    javax.json.JsonObjectBuilder com_id_nom_json = Json.createObjectBuilder();
                    for(Communaute com: communautes){
                        com_id_nom_json.add(com.getNom(), com.getId());
                    }
                    out.print(com_id_nom_json.build());
                }
                else
                    out.print("{\"response\": 403}");
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
        else if(request.getParameter("action").equals("rechercher_communaute_par_ville")){
            try {
                Utilisateur utilisateur = service_technique.recuperer_utilisateur((long) (session.getAttribute("utilisateur_id")));
                List<Communaute> communautes_inscrits = utilisateur.getCommunautes();
                List<Communaute> communautes_crees = utilisateur.getCommunautes_crees();
                if(!communautes_inscrits.isEmpty()){
                    javax.json.JsonObjectBuilder com_id_nom_json = Json.createObjectBuilder();
                    for(Communaute com: communautes_inscrits){
                        com_id_nom_json.add(com.getNom(), com.getId());
                    }
                    out.print(com_id_nom_json.build());
                }
                if(!communautes_crees.isEmpty()){
                    javax.json.JsonObjectBuilder com_id_nom_json = Json.createObjectBuilder();
                    for(Communaute com: communautes_crees){
                        com_id_nom_json.add(com.getNom(), com.getId());
                    }
                    out.print(com_id_nom_json.build());
                }
            } catch (Exception e) {
                System.out.println(e);
                out.print("{\"response\": 500}");
            }
        }
    }
}
