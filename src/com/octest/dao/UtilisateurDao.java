package com.octest.dao;

import java.util.List;

import com.octest.beans.Parcours;
import com.octest.beans.Utilisateur;

public interface UtilisateurDao {
	final String ADMIN = "administrateur";
	final String STAGIAIRE = "stagiaire";
	
	void ajouter( Utilisateur utilisateur ) throws DaoException;
	List<Utilisateur> lister() throws DaoException;
	Utilisateur verifier(Utilisateur utilisateur) throws DaoException;
	void modifier(Utilisateur Utilisateur) throws DaoException;
	void supprimer(int idUtilisateur, String typeUtilisateur) throws DaoException;

	List<Parcours> listerParcoursStagiaire(Integer idStagiaire) throws DaoException;
    void ajouterReponseStagiaire(Integer idStagiaire, Integer idReponse, Integer duree) throws DaoException;
    void supprimerParcoursStagiaire(Integer idStagiaire, Integer idQuestionnaire) throws DaoException;
	
}