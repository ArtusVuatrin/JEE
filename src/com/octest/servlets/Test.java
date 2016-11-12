package com.octest.servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.octest.beans.Utilisateur;
import com.octest.dao.*;

/**
 * Servlet implementation class Test
 */
public class Test extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
    private UtilisateurDao utilisateurDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
    }
    
    
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        request.setAttribute("admin", UtilisateurDao.ADMIN);
        request.setAttribute("stagiaire", UtilisateurDao.STAGIAIRE);
    	try {
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setEmail(request.getParameter("email"));
            utilisateur.setMdp(request.getParameter("password"));
            
            Utilisateur User = utilisateurDao.verifier(utilisateur);
            if( User.getId() == null ){
                request.setAttribute("erreur", "Identifiants incorrects");
            	this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
            }else{
            	
                HttpSession session = request.getSession();
                session.setAttribute("email", User.getEmail());
                session.setAttribute("type", User.getTypeUtilisateur());
                session.setAttribute("idUser", User.getId());
                
                request.setAttribute("User", User);
            	this.getServletContext().getRequestDispatcher("/WEB-INF/accueil.jsp").forward(request, response);
            }
        }
        catch (Exception e) {
            request.setAttribute("erreur", e.getMessage());
            this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
        }
        
    }
    
    

}