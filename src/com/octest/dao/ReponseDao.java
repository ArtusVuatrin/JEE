package com.octest.dao;

import java.util.List;

import com.octest.beans.Reponse;

public interface ReponseDao {

	List<Reponse> lister(Integer idQuestion) throws DaoException;
	Reponse trouver(Integer idReponse) throws DaoException;
	void creer(Reponse reponse) throws DaoException;
	void modifier(Reponse reponse) throws DaoException;
	void supprimer(Integer idReponse) throws DaoException;
	void activer(int idReponse) throws DaoException;

}