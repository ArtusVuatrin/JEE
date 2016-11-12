package com.octest.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.octest.beans.BeanException;
import com.octest.beans.Questionnaire;
import com.octest.dao.DaoException;
import com.octest.dao.DaoFactory;
import com.octest.dao.QuestionnaireDao;
import com.octest.dao.UtilisateurDao;


public class Questionnaires extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private QuestionnaireDao QuestionnaireDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.QuestionnaireDao = daoFactory.getQuestionnaireDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute( "type" );
		if(type != null && type.equals(UtilisateurDao.ADMIN)){
			request = getQuestionnaire(request);
	        this.getServletContext().getRequestDispatcher("/WEB-INF/questionnaires.jsp").forward(request, response);
		}else{
			response.sendRedirect("/JEE/Test");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String typeUser = (String) session.getAttribute( "type" );
		if(typeUser != null && typeUser.equals(UtilisateurDao.ADMIN)){
			Boolean delete = (request.getParameter("delete") != null);
			if(delete){
				try{
					int id = Integer.parseInt(request.getParameter("id"));
					QuestionnaireDao.supprimer(id);
					request.setAttribute("information", "Questionnaire supprimé");
				}catch(DaoException e){
					request.setAttribute("information", "Problème lors de l'ajout en base : "+ e.getMessage());
				}
			}else{			
				Questionnaire newQuestion = new Questionnaire();
				String sujet = request.getParameter("sujet");
				
				Boolean addition = (request.getParameter("addQuestionary") != null);
				if(addition){
					try{
						newQuestion.setSujet(sujet);
						QuestionnaireDao.creer(newQuestion);
					}catch(BeanException e){
						request.setAttribute("information", "Données du questionnaire incorrectes : "+ e.getMessage());
					}catch(DaoException e){
						request.setAttribute("information", "Problème lors de l'ajout en base : "+ e.getMessage());
					}
				}else{
					int id = Integer.parseInt(request.getParameter("id"));
					boolean activity = request.getParameter("activity"  +  id  ).equals("1");
					try{
						newQuestion.setSujet(sujet);
						newQuestion.setActif(activity);
						newQuestion.setId(id);
						QuestionnaireDao.modifier(newQuestion);
					}catch(BeanException e){
						request.setAttribute("information", "Données du questionnaire incorrectes : "+ e.getMessage());
					}catch(DaoException e){
						request.setAttribute("information", "Problème lors de l'ajout en base : "+ e.getMessage());
					}
				}
			}
			request = getQuestionnaire(request);
	        this.getServletContext().getRequestDispatcher("/WEB-INF/questionnaires.jsp").forward(request, response);
		}else{
			response.sendRedirect("/JEE/Test");
		}
	}

	protected HttpServletRequest getQuestionnaire(HttpServletRequest request){
		try {
            List<Questionnaire> Questionnaires = QuestionnaireDao.lister();
            request.setAttribute("Questionnaires", Questionnaires);
        }
        catch (Exception e) {
            request.setAttribute("information", e.getMessage());
        }
		return request;
	}

}
