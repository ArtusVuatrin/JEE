package com.octest.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.octest.beans.BeanException;
import com.octest.beans.Utilisateur;
import com.octest.dao.DaoException;
import com.octest.dao.DaoFactory;
import com.octest.dao.UtilisateurDao;

/**
 * Servlet implementation class Utilisateurs
 */
public class Utilisateurs extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute( "type" );
		if(type.equals(UtilisateurDao.ADMIN)){
			request = getUsers(request);
	        this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs.jsp").forward(request, response);
		}else{
	        this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String typeUser = (String) session.getAttribute( "type" );
		if(typeUser.equals(UtilisateurDao.ADMIN)){
			Boolean delete = (request.getParameter("delete") != null);
			if(delete){
				try{
					int id = Integer.parseInt(request.getParameter("id"));
					utilisateurDao.supprimer(id, request.getParameter("type"));
					request.setAttribute("information", "Utilisateur supprimé");
				}catch(DaoException e){
					request.setAttribute("information", "Problème lors de la supression en base : "+ e.getMessage());
				}
			}else{
				Utilisateur newUser = new Utilisateur();
				Boolean addition = request.getParameter("addUser") != null;
				String nom = request.getParameter("name");
				String email = request.getParameter("email");
				String type = request.getParameter("type");
				
				if(addition){
					String societe = request.getParameter("societe");
					String password = request.getParameter("password");
					String tel = request.getParameter("tel");
					try{
						newUser.setEmail(email);
						newUser.setTypeUtilisateur(type);
						newUser.setNom(nom);
						newUser.setMdp(password);
						newUser.setSociete(societe);
						newUser.setTel(tel);
						utilisateurDao.ajouter(newUser);
						request.setAttribute("information", "Utilisateur "+ nom +" ajouté");
					}catch(BeanException e){
						request.setAttribute("information", "Données d'utilisateur incorrectes : "+ e.getMessage());
					}catch(DaoException e){
						request.setAttribute("information", "Problème lors de l'ajout en base : "+ e.getMessage());
					}
				}else{
					int id = Integer.parseInt(request.getParameter("id"));
					boolean activity = request.getParameter("activity"  +  id  +  request.getParameter("type") ).equals("1");
					
					try{
						newUser.setId(id);
						newUser.setActif(activity);
						newUser.setEmail(email);
						newUser.setTypeUtilisateur(type);
						newUser.setNom(nom);
						utilisateurDao.modifier(newUser);
						request.setAttribute("information", "Utilisateur "+ nom +" modifié");
					}catch(BeanException e){
						request.setAttribute("information", "Données d'utilisateur incorrectes : "+ e.getMessage());
					}catch(DaoException e){
						request.setAttribute("information", "Problème lors de la mise à jour en base : "+ e.getMessage());
					}
				}
			}
			request = getUsers(request);
	        this.getServletContext().getRequestDispatcher("/WEB-INF/utilisateurs.jsp").forward(request, response);
		}else{
	        this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
		}
	}
	
	protected HttpServletRequest getUsers(HttpServletRequest request){
		try {
            List<Utilisateur> Users = utilisateurDao.lister();
            request.setAttribute("Users", Users);
        }
        catch (Exception e) {
            request.setAttribute("information", e.getMessage());
        }
		return request;
	}

}
