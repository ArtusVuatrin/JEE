package com.octest.dao;

import java.util.List;

import com.octest.beans.Questionnaire;

public interface QuestionnaireDao {

	List<Questionnaire> lister() throws DaoException;
	Questionnaire trouver(Integer idQuestionnaire) throws DaoException;
	void creer(Questionnaire questionnaire) throws DaoException;
	void modifier(Questionnaire questionnaire) throws DaoException;
	void supprimer(Integer idQuestionnaire) throws DaoException;

}