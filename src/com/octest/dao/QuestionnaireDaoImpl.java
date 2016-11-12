package com.octest.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.octest.beans.BeanException;
import com.octest.beans.Questionnaire;


public class QuestionnaireDaoImpl implements QuestionnaireDao {

    private DaoFactory daoFactory;

    QuestionnaireDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// GET /////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Questionnaire> lister() throws DaoException {

        List<Questionnaire> questionnaires = new ArrayList<Questionnaire>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM questionnaire;");

            while (resultat.next()) {
            	try{
	                Integer id = resultat.getInt("id");
	                String sujet = resultat.getString("sujet");
	                Boolean actif = resultat.getBoolean("actif");
	                Questionnaire questionnaire = new Questionnaire();
	                questionnaire.setId(id);
	                questionnaire.setSujet(sujet);
	                questionnaire.setActif(actif);
	                
	                questionnaires.add(questionnaire);
            	}catch(BeanException e){
            		
            	}
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données 3"+ e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données 4");
            }
        }
        return questionnaires;
    }

    @Override
    public Questionnaire trouver(Integer idQuestionnaire) throws DaoException {

        Questionnaire questionnaire = new Questionnaire();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM questionnaire WHERE id = ?;");
            preparedStatement.setInt(1, idQuestionnaire);

            resultat = preparedStatement.executeQuery();

            if ( resultat.next() ) { 
            	try{
	                Integer id = resultat.getInt("id");
	                String sujet = resultat.getString("sujet");
	                Boolean actif = resultat.getBoolean("actif");
	
	                questionnaire.setId(id);
	                questionnaire.setSujet(sujet);
	                questionnaire.setActif(actif);
            	}catch(BeanException e){
            		
            	}
            }
        } catch (SQLException e) {
            throw new DaoException("Impossible de communiquer avec la base de données 3"+ e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données 4");
            }
        }
        return questionnaire;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// ADD /////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void creer(Questionnaire questionnaire) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO questionnaire(sujet, actif) VALUES(?, TRUE);");
            preparedStatement.setString(1, questionnaire.getSujet());

            preparedStatement.executeUpdate();
            connexion.commit();

        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données 1" + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données 2");
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////// UPDATE ////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void modifier(Questionnaire questionnaire) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE questionnaire SET sujet = ?, actif = ? WHERE id = ?;");
            preparedStatement.setString(1, questionnaire.getSujet());
            preparedStatement.setBoolean(2, questionnaire.getActif());
            preparedStatement.setInt(3, questionnaire.getId());

            preparedStatement.executeUpdate();
            connexion.commit();

        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données 1" + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données 2");
            }
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////// DELETE ////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void supprimer(Integer idQuestionnaire) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM questionnaire WHERE id = ?;");
            preparedStatement.setInt(1, idQuestionnaire);

            preparedStatement.executeUpdate();
            connexion.commit();

        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données 1" + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données 2");
            }
        }

    }

}