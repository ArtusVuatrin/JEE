package com.octest.dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.octest.beans.BeanException;
import com.octest.beans.Parcours;
import com.octest.beans.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {
    private DaoFactory daoFactory;

    UtilisateurDaoImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    @Override
    public void ajouter(Utilisateur Utilisateur) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String sqlStatement = new String();
        
        try {
            connexion = daoFactory.getConnection();
            if(Utilisateur.getTypeUtilisateur().equals(ADMIN)){
            	sqlStatement = "INSERT INTO administrateur ";
            }else{
            	sqlStatement = "INSERT INTO stagiaire ";
            }
            sqlStatement += " (email, mdp, nom, societe, tel, actif) " +
            				" VALUES (?,?,?,?,?,TRUE)";
            preparedStatement = connexion.prepareStatement(sqlStatement);
            preparedStatement.setString(1, Utilisateur.getEmail());
            preparedStatement.setString(2, Utilisateur.getMdp());
            preparedStatement.setString(3, Utilisateur.getNom());
            preparedStatement.setString(4, Utilisateur.getSociete());
            preparedStatement.setString(5, Utilisateur.getTel());

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

    public List<Utilisateur> lister() throws DaoException {
        List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        Connection connexion = null;
        Statement statement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            resultat = statement.executeQuery("SELECT * FROM utilisateur;");

            while (resultat.next()) {
            	try{
	                Utilisateur utilisateur = new Utilisateur();
	                utilisateur.setEmail(resultat.getString("email"));
	                utilisateur.setNom(resultat.getString("nom"));
	                utilisateur.setTypeUtilisateur(resultat.getString("typeUtilisateur"));
	                utilisateur.setActif(resultat.getBoolean("actif"));
	                utilisateur.setId(resultat.getInt("id"));
	                utilisateurs.add(utilisateur);
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
        return utilisateurs;

    }
    
	@Override
	public Utilisateur verifier(Utilisateur utilisateur) throws DaoException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
    	Utilisateur Connected = new Utilisateur();

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement(
            		"SELECT * " +
            		"FROM utilisateur " +
            		"WHERE email = ? AND mdp = ?;"
            		);
            preparedStatement.setString(1, utilisateur.getEmail());
            preparedStatement.setString(2, utilisateur.getMdp());

            ResultSet resultat = preparedStatement.executeQuery();
            if(resultat.next()){
            	try{
	            	Connected.setTypeUtilisateur(resultat.getString("typeUtilisateur"));
	                Connected.setEmail(resultat.getString("email"));
	                Connected.setNom(resultat.getString("nom"));
	                Connected.setActif(resultat.getBoolean("actif"));
	                Connected.setId(resultat.getInt("id"));
            	}catch(BeanException e){
            		
            	}
            }
            
            
            
        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données : " + e.getMessage());
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données.");
            }
        }
        return Connected;
		
	}
	@Override
	public void modifier(Utilisateur Utilisateur) throws DaoException {
		Connection connexion = null;
        PreparedStatement preparedStatement = null;
        String sqlStatement = new String();

        try {
            connexion = daoFactory.getConnection();
            if(Utilisateur.getTypeUtilisateur().equals(ADMIN)){
            	sqlStatement = "UPDATE administrateur ";
            }else{
            	sqlStatement = "UPDATE stagiaire ";
            }
            sqlStatement += "SET email=?, nom=?, actif=? WHERE id=?;";
            preparedStatement = connexion.prepareStatement(sqlStatement);
            
            preparedStatement.setString(1, Utilisateur.getEmail());
            preparedStatement.setString(2, Utilisateur.getNom());
            preparedStatement.setBoolean(3, Utilisateur.getActif());
            preparedStatement.setInt(4, Utilisateur.getId());

            preparedStatement.executeUpdate();
            connexion.commit();
        } catch (SQLException e) {
            try {
                if (connexion != null) {
                    connexion.rollback();
                }
            } catch (SQLException e2) {
            }
            throw new DaoException("Impossible de communiquer avec la base de données : " + e.getMessage() + sqlStatement);
        }
        finally {
            try {
                if (connexion != null) {
                    connexion.close();  
                }
            } catch (SQLException e) {
                throw new DaoException("Impossible de communiquer avec la base de données.");
            }
        }
		
	}
	@Override
	public void supprimer(int idUtilisateur, String typeUtilisateur) throws DaoException {

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            String sqlStatement = new String();
            if(typeUtilisateur.equals(ADMIN)){
            	sqlStatement = "DELETE FROM administrateur ";
            }else{
            	sqlStatement = "DELETE FROM stagiaire ";
            }
            preparedStatement = connexion.prepareStatement(sqlStatement+" WHERE id = ?;");
            preparedStatement.setInt(1, idUtilisateur);

            preparedStatement.execute();
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

    /******************************************************************************************************************/
    /*********************************************** PARCOURS STAGIAIRE ***********************************************/
    /******************************************************************************************************************/

    @Override
    public List<Parcours> listerParcoursStagiaire(Integer idStagiaire) throws DaoException{

        List<Parcours> parcourss = new ArrayList<Parcours>();
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultat = null;

        try {
            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("SELECT DISTINCT Qr.id AS questionnaire, Qr.sujet AS sujetQuestionnaire, SUM(RS.duree) AS duree, SUM(R.valide) AS score, COUNT(*) AS nb_questions " +
                                                            "FROM reponse_stagaire RS, reponse R, question Q, questionnaire Qr " +
                                                            "WHERE RS.stagiaire = ? " +
                                                            "AND RS.reponse = R.id " +
                                                            "AND R.question = Q.id " +
                                                            "AND Q.questionnaire = Qr.id " +
                                                            "GROUP BY Q.questionnaire;");
            preparedStatement.setInt(1, idStagiaire);

            resultat = preparedStatement.executeQuery();

            while (resultat.next()) {
                Integer questionnaire = resultat.getInt("questionnaire");
                String sujetQuestionnaire = resultat.getString("sujetQuestionnaire");
                Integer score = resultat.getInt("score");
                Integer duree = resultat.getInt("duree");
                Integer nbQuestions = resultat.getInt("nb_questions");

                Parcours parcours = new Parcours();

                parcours.setStagiaire(idStagiaire);
                parcours.setQuestionnaire(questionnaire);
                parcours.setsujetQuestionnaire(sujetQuestionnaire);
                parcours.setScore(score);
                parcours.setDuree(duree);
                parcours.setNbQuestions(nbQuestions);
                
                parcourss.add(parcours);
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
        return parcourss;
    }

    @Override
    public void ajouterReponseStagiaire(Integer idStagiaire, Integer idReponse, Integer duree) throws DaoException{

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("INSERT INTO reponse_stagaire (stagiaire, reponse, duree) VALUES(?, ?, ?);");
            preparedStatement.setInt(1, idStagiaire);
            preparedStatement.setInt(2, idReponse);
            preparedStatement.setInt(3, duree);

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
    public void supprimerParcoursStagiaire(Integer idStagiaire, Integer idQuestionnaire) throws DaoException{

        Connection connexion = null;
        PreparedStatement preparedStatement = null;

        try {

            connexion = daoFactory.getConnection();
            preparedStatement = connexion.prepareStatement("DELETE FROM reponse_stagaire WHERE stagiaire = ? AND get_questionnaire_of_reponse(reponse) = ?;");
            preparedStatement.setInt(1, idStagiaire);
            preparedStatement.setInt(2, idQuestionnaire);

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