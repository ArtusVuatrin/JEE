package com.octest.beans;

import java.io.Serializable;

public class Parcours implements Serializable {
    
    /* Propriétés du bean */
    private Integer stagiaire;      // references Stagiaire.id
    private Integer questionnaire;  // references Questionnaire.id
    private String sujetQuestionnaire; //references Questionnaire.sujet
    private Integer score;
    private Integer duree;
    private Integer nbQuestions;

    public void setStagiaire( Integer stagiaire ) {
        this.stagiaire = stagiaire;
    }

    public Integer getStagiaire() {
        return stagiaire;
    }

    public void setQuestionnaire( Integer questionnaire ) {
        this.questionnaire = questionnaire;
    }

    public String getsujetQuestionnaire() {
        return sujetQuestionnaire;
    }

    public void setsujetQuestionnaire( String sujetQuestionnaire ) {
        this.sujetQuestionnaire = sujetQuestionnaire;
    }

    public Integer getQuestionnaire() {
        return questionnaire;
    }

    public void setScore( Integer score ) {
        this.score = score;
    }

    public Integer getScore() {
        return score;
    }

    public void setDuree( Integer duree ) {
        this.duree = duree;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setNbQuestions( Integer nbQuestions ) {
        this.nbQuestions = nbQuestions;
    }

    public Integer getNbQuestions() {
        return nbQuestions;
    }

}