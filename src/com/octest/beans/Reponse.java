package com.octest.beans;

import java.io.Serializable;

public class Reponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/* Propriétés du bean */
    private Integer id;
    private Integer question;  // references Question.id
    private Integer position;
    private String intitule;
    private Boolean valide;
    private Boolean actif;

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setQuestion( Integer question ) {
        this.question = question;
    }

    public Integer getQuestion() {
        return question;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition( Integer position ) {
        this.position = position;
    }

    public void setIntitule( String intitule ) {
        this.intitule = intitule;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setValide( Boolean valide ) {
        this.valide = valide;
    }

    public Boolean getValide() {
        return valide;
    }

    public void setActif( Boolean actif ) {
        this.actif = actif;
    }

    public Boolean getActif() {
        return actif;
    }

}