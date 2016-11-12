package com.octest.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.octest.beans.BeanException;
import com.octest.beans.Reponse;


public class ReponseDaoImpl implements ReponseDao {

    private DaoFactory daoFactory;

    ReponseDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// GET /////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public List<Reponse> lister(Integer idQuestion) throws DaoException {

        List<Reponse> reponses = new ArrayList<Reponse>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM reponse WHERE question = ?;");
            preparedStatement.setInt(1, idQuestion);

            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                Integer id = resultat.getInt("id");
                Integer question = resultat.getInt("question");
                Integer position = resultat.getInt("position");
                String intitule = resultat.getString("intitule");
                Boolean valide = resultat.getBoolean("valide");
                Boolean actif = resultat.getBoolean("actif");

                Reponse reponse = new Reponse();
                reponse.setId(id);
                reponse.setQuestion(question);
                reponse.setPosition(position);
                reponse.setIntitule(intitule);
                reponse.setValide(valide);
                reponse.setActif(actif);
                
                reponses.add(reponse);
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
        return reponses;
    }

    @Override
    public Reponse trouver(Integer idReponse) throws DaoException {

        Reponse reponse = new Reponse();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT * FROM reponse WHERE id = ?;");
            preparedStatement.setInt(1, idReponse);

            resultat = preparedStatement.executeQuery();

            if ( resultat.next() ) { 
                Integer id = resultat.getInt("id");
                Integer question = resultat.getInt("question");
                Integer position = resultat.getInt("position");
                String intitule = resultat.getString("intitule");
                Boolean valide = resultat.getBoolean("valide");
                Boolean actif = resultat.getBoolean("actif");

                reponse.setId(id);
                reponse.setQuestion(question);
                reponse.setPosition(position);
                reponse.setIntitule(intitule);
                reponse.setValide(valide);
                reponse.setActif(actif);
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
        return reponse;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////// ADD /////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void creer(Reponse reponse) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO reponse (question, position, intitule, valide, actif) " +
            		"VALUES(?, " +
            		"	(SELECT COALESCE(MAX(position) + 1, 1) " +
            		"	FROM (SELECT * FROM reponse) AS reponses" +
            		"	WHERE question = ? )" +
            		"	, ?, ?, ?);");
            preparedStatement.setInt(1, reponse.getQuestion());
            preparedStatement.setInt(2, reponse.getQuestion());
            preparedStatement.setString(3, reponse.getIntitule());
            preparedStatement.setBoolean(4, reponse.getValide());
            preparedStatement.setBoolean(5, reponse.getActif());

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
    public void modifier(Reponse reponse) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE reponse SET intitule = ? WHERE id = ?;");
            preparedStatement.setString(1, reponse.getIntitule());
            preparedStatement.setInt(2, reponse.getId());

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
    public void supprimer(Integer idReponse) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM reponse WHERE id = ?;");
            preparedStatement.setInt(1, idReponse);

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

	@Override
	public void activer(int idReponse) throws DaoException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("UPDATE reponse SET valide = 1 WHERE id = ?; ");
            preparedStatement.setInt(1, idReponse);

            preparedStatement.executeUpdate();

            preparedStatement = connexion.prepareStatement("UPDATE reponse SET valide = 0 WHERE question = " +
            		"											(SELECT question FROM " +
            		"												(SELECT * FROM reponse) AS reponses " +
            		"											WHERE id=?)" +
            		"										 AND id != ?; ");
            preparedStatement.setInt(1, idReponse);
            preparedStatement.setInt(2, idReponse);

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