package com.octest.dao;

import java.util.List;
import com.octest.beans.Question;

public interface QuestionDao {

	List<Question> lister(Integer idQuestionnaire) throws DaoException;
	Question trouver(Integer idQuestion) throws DaoException;
	void creer(Question question) throws DaoException;
	void modifier(Question question) throws DaoException;
	void supprimer(Integer idQuestion) throws DaoException;

}