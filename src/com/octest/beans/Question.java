package com.octest.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

import com.octest.dao.DaoException;

public class Question implements Serializable {
    
	private static final long serialVersionUID = 1L;
	/* Propriétés du bean */
    private Integer id;
    private Integer questionnaire;  // references Questionnaire.id
    private Integer position;
    private String intitule;
    private Boolean actif;
    private ArrayList<Reponse> Reponses;

    public Question(){
    	Reponses = new ArrayList<Reponse>();
    }
    
    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setQuestionnaire( Integer questionnaire ) {
        this.questionnaire = questionnaire;
    }

    public Integer getQuestionnaire() {
        return questionnaire;
    }

    public void setPosition( Integer position ) {
        this.position = position;
    }

    public Integer getPosition() {
        return position;
    }

    public void setIntitule( String intitule ) {
        this.intitule = intitule;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setActif( Boolean actif ) {
        this.actif = actif;
    }

    public Boolean getActif() {
        return actif;
    }

    public void addReponse(Reponse Reponse){
        this.Reponses.add(Reponse);
    }
    
    public ArrayList<Reponse> getReponses() {
        return this.Reponses;
    }

}