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
import com.octest.beans.Parcours;
import com.octest.dao.DaoException;
import com.octest.dao.DaoFactory;
import com.octest.dao.QuestionnaireDao;
import com.octest.dao.UtilisateurDao;


public class Stagiaire extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private QuestionnaireDao questionnaireDao;
    private UtilisateurDao utilisateurDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.questionnaireDao = daoFactory.getQuestionnaireDao();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String type = (String) session.getAttribute( "type" );
		Integer idStagiaire = (Integer) session.getAttribute("idUser");
		
		if(type != null && type.equals(utilisateurDao.STAGIAIRE)){
			
			request = getQuestionnaires(request);
			request = getParcours(request, idStagiaire);
	        this.getServletContext().getRequestDispatcher("/WEB-INF/stagiaire.jsp").forward(request, response);

		}else{
			response.sendRedirect("/JEE/Test");
		}

	}

	protected HttpServletRequest getQuestionnaires(HttpServletRequest request){
		try {
            List<Questionnaire> questionnaires = questionnaireDao.lister();
            request.setAttribute("questionnaires", questionnaires);
        }
        catch (Exception e) {
            request.setAttribute("information", e.getMessage());
        }
		return request;
	}

	protected HttpServletRequest getParcours(HttpServletRequest request, Integer idStagiaire){
		try {
            List<Parcours> parcourss = utilisateurDao.listerParcoursStagiaire(idStagiaire);
            request.setAttribute("parcourss", parcourss);
        }
        catch (Exception e) {
            request.setAttribute("information", e.getMessage());
        }
		return request;
	}

}