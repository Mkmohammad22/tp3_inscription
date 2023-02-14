package com.sdzee.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sdzee.beans.Client;
import com.sdzee.beans.Commande;
public class CreationCommandeForm {

	
	private static final String CHAMP_DATE             = "dateCommande";
    private static final String CHAMP_MONTANT          = "montantCommande";
    private static final String CHAMP_MODE_PAIEMENT    = "modePaiementCommande";
    private static final String CHAMP_STATUT_PAIEMENT  = "statutPaiementCommande";
    private static final String CHAMP_MODE_LIVRAISON   = "modeLivraisonCommande";
    private static final String CHAMP_STATUT_LIVRAISON = "statutLivraisonCommande";

    private static final String FORMAT_DATE            = "dd/MM/yyyy HH:mm:ss";

    private String              resultat;
    private Map<String, String> erreurs                = new HashMap<String, String>();

    public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Commande creerCommande( HttpServletRequest request ) {
        /*
         * L'objet métier pour valider la création d'un client existe déjà, il
         * est donc déconseillé de dupliquer ici son contenu ! À la place, il
         * suffit de passer la requête courante à l'objet métier existant et de
         * récupérer l'objet Client créé.
         */
        CreationClientForm clientForm = new CreationClientForm();
        Client client = clientForm.creerClient( request );

        /*
         * Et très important, il ne faut pas oublier de récupérer le contenu de
         * la map d'erreurs créée par l'objet métier CreationClientForm dans la
         * map d'erreurs courante, actuellement vide.
         */
        erreurs = clientForm.getErreurs();

        /*
         * Ensuite, il suffit de procéder normalement avec le reste des champs
         * spécifiques à une commande.
         */

        /*
         * Récupération et conversion de la date en String selon le format
         * choisi.
         */
        DateTime dt = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern( FORMAT_DATE );
        String date = dt.toString( formatter );

        String montant = getValeurChamp( request, CHAMP_MONTANT );
        String modePaiement = getValeurChamp( request, CHAMP_MODE_PAIEMENT );
        String statutPaiement = getValeurChamp( request, CHAMP_STATUT_PAIEMENT );
        String modeLivraison = getValeurChamp( request, CHAMP_MODE_LIVRAISON );
        String statutLivraison = getValeurChamp( request, CHAMP_STATUT_LIVRAISON );

        Commande commande = new Commande();

        commande.setClient( client );

        commande.setdate( date );

      
        commande.setMontantCommande( montant );

        try {
            validationModePaiement( modePaiement );
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_PAIEMENT, e.getMessage() );
        }
        commande.setModePaiementCommande( modePaiement );

        try {
            validationStatutPaiement( statutPaiement );
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_PAIEMENT, e.getMessage() );
        }
        commande.setStatutPaiementCommande( statutPaiement );

        try {
            validationModeLivraison( modeLivraison );
        } catch ( Exception e ) {
            setErreur( CHAMP_MODE_LIVRAISON, e.getMessage() );
        }
        commande.setModeLivraisonCommande( modeLivraison );

        try {
            validationStatutLivraison( statutLivraison );
        } catch ( Exception e ) {
            setErreur( CHAMP_STATUT_LIVRAISON, e.getMessage() );
        }
        commande.setStatutLivraisonCommande( statutLivraison );

        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création de la commande.";
        } else {
            resultat = "Échec de la création de la commande.";
        }
        return commande;
    }

  

    private void validationModePaiement( String modePaiement ) throws Exception {
        if ( modePaiement != null ) {
            if ( modePaiement.length() < 2 ) {
                throw new Exception( "Le mode de paiement doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de paiement." );
        }
    }

    private void validationStatutPaiement( String statutPaiement ) throws Exception {
        if ( statutPaiement != null && statutPaiement.length() < 2 ) {
            throw new Exception( "Le statut de paiement doit contenir au moins 2 caractères." );
        }
    }

    private void validationModeLivraison( String modeLivraison ) throws Exception {
        if ( modeLivraison != null ) {
            if ( modeLivraison.length() < 2 ) {
                throw new Exception( "Le mode de livraison doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mode de livraison." );
        }
    }

    private void validationStatutLivraison( String statutLivraison ) throws Exception {
        if ( statutLivraison != null && statutLivraison.length() < 2 ) {
            throw new Exception( "Le statut de livraison doit contenir au moins 2 caractères." );
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
