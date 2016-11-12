package com.octest.beans;

import java.io.Serializable;
import java.sql.Date;

import com.octest.dao.UtilisateurDao;

/* Classe abstraite Utilisateur dont héritent les classes Administrateur et Stagiaire */

public class Utilisateur implements Serializable {

	private static final long serialVersionUID = -2251602482188547493L;
	/* Propriétés du bean */
    protected Integer id;
    protected String email;
    protected String mdp;
    protected String nom;
    protected String societe;
    protected String tel;
    protected Date dateCreation;
    protected Boolean actif;
    protected String typeUtilisateur;

    public void setId( Integer id ) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setEmail( String email ) throws BeanException {
    	if(email.length() < 10)
    		throw new BeanException("Email invalide");
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setMdp( String mdp ) {
        this.mdp = mdp;
    }

    public String getMdp() {
        return mdp;
    }

    public void setNom( String nom ) throws BeanException {
    	if(nom.length() < 5)
    		throw new BeanException("Nom trop court");
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setSociete ( String societe ) {
        this.societe = societe;
    }

    public String getSociete() {
        return societe;
    }

    public void setTel( String tel ) {
        this.tel = tel;
    }

    public String getTel() {
        return tel;
    }

    public void setDateCreation( Date dateCreation ) {
        this.dateCreation = dateCreation;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setActif( Boolean actif ) {
        this.actif = actif;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setTypeUtilisateur( String typeUtilisateur ) throws BeanException {
    	if(!typeUtilisateur.equals(UtilisateurDao.STAGIAIRE) && !typeUtilisateur.equals(UtilisateurDao.ADMIN))
    		throw new BeanException("Type d'utilisateur invalide");
        this.typeUtilisateur = typeUtilisateur;
    }

    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }

}