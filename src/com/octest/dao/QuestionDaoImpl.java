package com.octest.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.octest.beans.BeanException;
import com.octest.beans.Question;
import com.octest.beans.Reponse;


public class QuestionDaoImpl implements QuestionDao {

    private DaoFactory daoFactory;

    QuestionDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// GET /////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Question> lister(Integer idQuestionnaire) throws DaoException {

        List<Question> questions = new ArrayList<Question>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM question WHERE questionnaire = ?;");
            preparedStatement.setInt(1, idQuestionnaire);

            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
            	
                Integer id = resultat.getInt("id");
                Integer questionnaire = resultat.getInt("questionnaire");
                Integer position = resultat.getInt("position");
                String intitule = resultat.getString("intitule");
                Boolean actif = resultat.getBoolean("actif");

                Question question = new Question();
                question.setId(id);
                question.setQuestionnaire(questionnaire);
                question.setPosition(position);
                question.setIntitule(intitule);
                question.setActif(actif);

                try {
	                PreparedStatement preparedStatementReponse = null;
	                ResultSet resultatReponse = null;
	                preparedStatementReponse = connexion.prepareStatement("SELECT * FROM reponse WHERE question = ? ORDER BY position;");
	                preparedStatementReponse.setInt(1, id);
	                resultatReponse = preparedStatementReponse.executeQuery();

	                while(resultatReponse.next()){

	                    Integer idR = resultatReponse.getInt("id");
	                    Integer questionR = resultatReponse.getInt("question");
	                    Integer positionR = resultatReponse.getInt("position");
	                    String intituleR = resultatReponse.getString("intitule");
	                    Boolean actifR = resultatReponse.getBoolean("actif");
	                    Boolean valideR = resultatReponse.getBoolean("valide");
	
	                    Reponse reponse = new Reponse();
	                    reponse.setId(idR);
	                    reponse.setQuestion(questionR);
	                    reponse.setPosition(positionR);
	                    reponse.setIntitule(intituleR);
	                    reponse.setValide(valideR);
	                    reponse.setActif(actifR);
	                    
	                    question.addReponse(reponse);

	                }
                }catch(SQLException e){
                    throw new DaoException("Impossible de communiquer avec la base de données 5"+ e.getMessage());
	            }
                questions.add(question);
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
        return questions;
    }

    @Override
    public Question trouver(Integer idQuestion) throws DaoException {

        Question question = new Question();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM question WHERE id = ?;");
            preparedStatement.setInt(1, idQuestion);

            resultat = preparedStatement.executeQuery();

            if ( resultat.next() ) { 
                Integer id = resultat.getInt("id");
                Integer questionnaire = resultat.getInt("questionnaire");
                Integer position = resultat.getInt("position");
                String intitule = resultat.getString("intitule");
                Boolean actif = resultat.getBoolean("actif");

                question.setId(id);
                question.setQuestionnaire(questionnaire);
                question.setPosition(position);
                question.setIntitule(intitule);
                question.setActif(actif);
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
        return question;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// ADD /////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void creer(Question question) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement(
            			"INSERT INTO question (questionnaire, position, intitule, actif) " +
            			"VALUES(?, " +
            			"	(SELECT COALESCE(MAX(position)+1, 1) " +
            			"	FROM (SELECT * FROM question) AS questions" +
            			"	WHERE questionnaire = ?)" +
            			", ?, ?);");
            preparedStatement.setInt(1, question.getQuestionnaire());
            preparedStatement.setInt(2, question.getQuestionnaire());
            preparedStatement.setString(3, question.getIntitule());
            preparedStatement.setBoolean(4, question.getActif());

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
    public void modifier(Question question) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE question SET intitule = ? WHERE id = ?;");
            preparedStatement.setString(1, question.getIntitule());
            preparedStatement.setInt(2, question.getId());

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
    public void supprimer(Integer idQuestion) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM question WHERE id = ?;");
            preparedStatement.setInt(1, idQuestion);

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