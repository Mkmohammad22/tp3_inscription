package com.sdzee.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.sdzee.beans.Client;
public class CreationClientForm {
	
	    public static final String Nom_client       = "nom";
	    private static final String CHAMP_PRENOM    = "prenomClient";
	    private static final String CHAMP_ADRESSE   = "adresseClient";
	    private static final String CHAMP_TELEPHONE = "telephoneClient";
	    private static final String CHAMP_EMAIL     = "emailClient";

	    private String              resultat;
	    private Map<String, String> erreurs         = new HashMap<String, String>();

	    public Map<String, String> getErreurs() {
	        return erreurs;
	    }

	    public String getResultat() {
	        return resultat;
	    }
	    Client client = new Client();
	    public Client creerClient( HttpServletRequest request ) {
	        String nom = getValeurChamp( request, Nom_client );
	        String prenom = getValeurChamp( request, CHAMP_PRENOM );
	        String adresse = getValeurChamp( request, CHAMP_ADRESSE );
	        String telephone = getValeurChamp( request, CHAMP_TELEPHONE );
	        String email = getValeurChamp( request, CHAMP_EMAIL );

	       

	       
	        client.setnomClient( nom );

	        try {
	            validationPrenom( prenom );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_PRENOM, e.getMessage() );
	        }
	        client.setprenomClient( prenom );

	        try {
	            validationAdresse( adresse );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_ADRESSE, e.getMessage() );
	        }
	        client.setadresseClient( adresse );

	        try {
	            validationTelephone( telephone );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_TELEPHONE, e.getMessage() );
	        }
	        client.settelephoneClient( telephone );

	        try {
	            validationEmail( email );
	        } catch ( Exception e ) {
	            setErreur( CHAMP_EMAIL, e.getMessage() );
	        }
	        client.setemailClient( email );

	        if ( erreurs.isEmpty() ) {
	            resultat = "Succès de la création du client.";
	        } else {
	            resultat = "Échec de la création du client.";
	        }

	        return client;
	    }

	  

	    private void validationPrenom( String prenom ) throws Exception {
	        if ( prenom != null && prenom.length() < 2 ) {
	            throw new Exception( "Le prénom d'utilisateur doit contenir au moins 2 caractères." );
	        }
	    }

	    private void validationAdresse( String adresse ) throws Exception {
	        if ( adresse != null ) {
	            if ( adresse.length() < 10 ) {
	                throw new Exception( "L'adresse de livraison doit contenir au moins 10 caractères." );
	            }
	        } else {
	            throw new Exception( "Merci d'entrer une adresse de livraison." );
	        }
	    }

	    private void validationTelephone( String telephone ) throws Exception {
	        if ( telephone != null ) {
	            if ( !telephone.matches( "^\\d+$" ) ) {
	                throw new Exception( "Le numéro de téléphone doit uniquement contenir des chiffres." );
	            } else if ( telephone.length() < 4 ) {
	                throw new Exception( "Le numéro de téléphone doit contenir au moins 4 chiffres." );
	            }
	        } else {
	            throw new Exception( "Merci d'entrer un numéro de téléphone." );
	        }
	    }

	    private void validationEmail( String email ) throws Exception {
	        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
	            throw new Exception( "Merci de saisir une adresse mail valide." );
	        }
	    }

	    /*
	     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
	     */
	    private void setErreur( String champ, String message ) {
	        erreurs.put( champ, message );
	    }

	    /*
	     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
	     * sinon.
	     */
	    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
	        String valeur = request.getParameter( nomChamp );
	        if ( valeur == null || valeur.trim().length() == 0 ) {
	            return null;
	        } else {
	            return valeur;
	        }
	    }

}
