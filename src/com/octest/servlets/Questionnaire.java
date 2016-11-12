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
import com.octest.beans.Question;
import com.octest.beans.Reponse;
import com.octest.beans.Utilisateur;
import com.octest.dao.DaoException;
import com.octest.dao.DaoFactory;
import com.octest.dao.QuestionDao;
import com.octest.dao.QuestionnaireDao;
import com.octest.dao.ReponseDao;
import com.octest.dao.UtilisateurDao;

public class Questionnaire extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private QuestionDao questionDao;
    private QuestionnaireDao questionnaireDao;
    private ReponseDao reponseDao;
    
    public void init() throws ServletException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        this.questionDao = daoFactory.getQuestionDao();
        this.questionnaireDao = daoFactory.getQuestionnaireDao();
        this.reponseDao = daoFactory.getReponseDao();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		String type = (String) session.getAttribute( "type" );
		if(type != null && type.equals(UtilisateurDao.ADMIN) && id != null){
			int idQuestionnaire = Integer.parseInt(id);
			com.octest.beans.Questionnaire Q = new com.octest.beans.Questionnaire();
			try{
				Q = questionnaireDao.trouver(idQuestionnaire);
			}catch(DaoException e){
		           request.setAttribute("information", "Communication avec la base impossible");
			}
			if(Q.isValid()){
				request.setAttribute("Questionnaire", Q);
				request = getQuestions(request, idQuestionnaire);
			}else{
	           request.setAttribute("information", "Ce questionnaire n'existe pas");
			}
	        this.getServletContext().getRequestDispatcher("/WEB-INF/questionnaire.jsp").forward(request, response);
		}else{
	        this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		HttpSession session = request.getSession();
		String typeUser = (String) session.getAttribute( "type" );
		if(typeUser.equals(UtilisateurDao.ADMIN)){
			int idQuestionnaire = Integer.parseInt(id);
			com.octest.beans.Questionnaire Q = new com.octest.beans.Questionnaire();
			try{
				Q = questionnaireDao.trouver(idQuestionnaire);
			}catch(DaoException e){
		           request.setAttribute("information", "Communication avec la base impossible");
			}
			if(!Q.isValid()){
		           request.setAttribute("information", "Ce questionnaire n'existe pas");
			}else{
				request.setAttribute("Questionnaire", Q);
				Boolean addQuestion = request.getParameter("addQuestion") != null;
				Boolean addReponse = request.getParameter("addReponse") != null;
				Boolean deleteQuestion = request.getParameter("deleteQuestion") != null;
				Boolean deleteReponse = request.getParameter("deleteReponse") != null;
				Boolean modifyQuestion = request.getParameter("modifyQuestion") != null;
				Boolean modifyReponse = request.getParameter("modifyReponse") != null;
				Boolean modifyOrder = request.getParameter("modifyOrder") != null;
				request = getQuestions(request, idQuestionnaire);

				if(modifyOrder){
					int idQuestion = Integer.parseInt(request.getParameter("idQuestion"));
					int activated = Integer.parseInt(request.getParameter("actif"+idQuestion));
					try{
						reponseDao.activer(activated);
					}catch(DaoException e){
				           request.setAttribute("information", "Activation de la réponse impossible : "+e.getMessage());
					}
				}else if(addQuestion){
					String newQuestion = request.getParameter("newQuestion");
					int position = Integer.parseInt(request.getParameter("position"));
					Question Quest = new Question();
					Quest.setIntitule(newQuestion);
					Quest.setQuestionnaire(idQuestionnaire);
					Quest.setActif(true);
					Quest.setPosition(position);
					try{
						questionDao.creer(Quest);
					}catch(DaoException e){
				           request.setAttribute("information", "Ajout impossible"+e.getMessage());
					}
							
				}else if(addReponse){
					String newReponse = request.getParameter("newReponse");
					int position = Integer.parseInt(request.getParameter("position"));
					int question = Integer.parseInt(request.getParameter("question"));
					boolean valide = request.getParameter("valide").equals("1");
					Reponse rep = new Reponse();
					rep.setIntitule(newReponse);
					rep.setPosition(position);
					rep.setQuestion(question);
					rep.setActif(true);
					rep.setValide(valide);
					try{
						reponseDao.creer(rep);
					}catch(DaoException e){
				           request.setAttribute("information", "Ajout impossible : "+e.getMessage());
					}
				}else if(deleteQuestion){
					int idQuestion = Integer.parseInt(request.getParameter("idQuestion"));
					try{
						questionDao.supprimer(idQuestion);
					}catch(DaoException e){
				        request.setAttribute("information", "Suppression impossible : "+e.getMessage());
					}
				}else if(deleteReponse){
					int idReponse = Integer.parseInt(request.getParameter("idReponse"));
					try{
						reponseDao.supprimer(idReponse);
					}catch(DaoException e){
				        request.setAttribute("information", "Suppression impossible :"+e.getMessage());
					}
				}else if(modifyQuestion){
					int idQuestion = Integer.parseInt(request.getParameter("idQuestion"));
					String intitule = request.getParameter("intituleQuestion");
					Question Quest = new Question();
					Quest.setIntitule(intitule);
					Quest.setId(idQuestion);
					try{
						questionDao.modifier(Quest);
					}catch(DaoException e){
				        request.setAttribute("information", "Modification impossible :"+e.getMessage());
					}
				}else if(modifyReponse){
					int idReponse = Integer.parseInt(request.getParameter("idReponse"));
					String intitule = request.getParameter("intituleReponse");
					Reponse Reponse = new Reponse();
					Reponse.setIntitule(intitule);
					Reponse.setId(idReponse);
					try{
						reponseDao.modifier(Reponse);
					}catch(DaoException e){
				        request.setAttribute("information", "Modification impossible :"+e.getMessage());
					}
				}
				request = getQuestions(request, idQuestionnaire);
			}
	        this.getServletContext().getRequestDispatcher("/WEB-INF/questionnaire.jsp").forward(request, response);
		}else{
	        this.getServletContext().getRequestDispatcher("/WEB-INF/bonjour.jsp").forward(request, response);
		}
	}
	
	protected HttpServletRequest getQuestions(HttpServletRequest request, int idQuestionnaire){
		try {
            List<Question> Questions = questionDao.lister(idQuestionnaire);
            request.setAttribute("Questions", Questions);
        }
        catch (Exception e) {
            request.setAttribute("information", e.getMessage());
        }
		return request;
	}

}
