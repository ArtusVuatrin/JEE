package com.octest.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.octest.beans.BeanException;
import com.octest.beans.Question;
import com.octest.beans.Reponse;

import com.octest.dao.DaoException;
import com.octest.dao.DaoFactory;
import com.octest.dao.QuestionDao;
import com.octest.dao.ReponseDao;
import com.octest.dao.UtilisateurDao;


public class Parcours extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private QuestionDao questionDao;
    private ReponseDao reponseDao;
    private UtilisateurDao utilisateurDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.questionDao = daoFactory.getQuestionDao();
        this.reponseDao = daoFactory.getReponseDao();
        this.utilisateurDao = daoFactory.getUtilisateurDao();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String type = (String) session.getAttribute( "type" );
		
		if(type != null && type.equals(utilisateurDao.STAGIAIRE)){
			
			Integer idQuestionnaire = Integer.parseInt(request.getParameter("questionnaire"));

			request = getQuestionsReponses(request, idQuestionnaire);

	        this.getServletContext().getRequestDispatcher("/WEB-INF/parcours.jsp").forward(request, response);
		
		}else{
	        this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		String typeUser = (String) session.getAttribute( "type" );

		if(typeUser != null && typeUser.equals(utilisateurDao.STAGIAIRE)){
			
			Integer idStagiaire = (Integer) session.getAttribute("idUser");

			Enumeration<String> questions = request.getParameterNames();
			while (questions.hasMoreElements()) {
				String question = questions.nextElement();
				Integer reponse = Integer.parseInt(request.getParameter(question));
				
				Random rand = new Random();
				Integer duree = rand.nextInt(200) + 5; // entre 5 et 200 secondes
				
				try{
					utilisateurDao.ajouterReponseStagiaire(idStagiaire, reponse, duree);
				}catch(DaoException e){
					request.setAttribute("information", "Problème lors de l'ajout en base : "+ e.getMessage());
				}
			}
			response.sendRedirect("/JEE/Stagiaire");

		}else{
			response.sendRedirect("/JEE/Test");
		
		}
	}

	protected HttpServletRequest getQuestionsReponses(HttpServletRequest request, Integer idQuestionnaire){
		try {
            List<Question> questions = questionDao.lister(idQuestionnaire);
            request.setAttribute("questions", questions);
            
    		List<List<Reponse>> reponsesQuestions = new ArrayList<List<Reponse>>();

            for (int i = 0; i < questions.size() ; i++){
            	List<Reponse> reponses = reponseDao.lister(questions.get(i).getId());
            	reponsesQuestions.add(reponses);
            }
            
            request.setAttribute("reponses", reponsesQuestions);
        }
        catch (Exception e) {
            request.setAttribute("information", e.getMessage());
        }
		return request;
	}

}