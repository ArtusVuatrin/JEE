package com.octest.beans;

import java.io.Serializable;

public class Questionnaire implements Serializable {
    
	private static final long serialVersionUID = 1L;
	/* Propriétés du bean */
    private Integer id;
    private String sujet;
    private Boolean actif;

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setSujet( String sujet ) throws BeanException {
    	if(sujet.length() < 10 )
    		throw new BeanException("Nom du sujet trop court.");
        this.sujet = sujet;
    }

    public String getSujet() {
        return sujet;
    }

    public void setActif( Boolean actif ) {
        this.actif = actif;
    }

    public Boolean getActif() {
        return actif;
    }

    public Boolean isValid() {
        return id != null;
    }

}