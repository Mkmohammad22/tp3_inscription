package com.sdzee.beans;

public class Client {

	  public String nom;
      private String prenomClient;
      private String adresseClient;
      private String telephoneClient;
      private String emailClient;

      public void setnomClient(String nom) {
      this.nom = nom;
      }
      public String getnomClient() {
      return nom;
      }

      public void setadresseClient(String adresseClient) {
          this.adresseClient = adresseClient;
          }
          public String getadresseClient() {
          return adresseClient;
          }

      
      public void setprenomClient(String prenomClient) {
      this.prenomClient = prenomClient;
      }
      public String getprenomClient() {
      return prenomClient;
      }

      public void settelephoneClient(String telephoneClient) {
      this.telephoneClient = telephoneClient;
      }
      public String gettelephoneClient() {
      return telephoneClient;
      }
      
      public void setemailClient(String emailClient) {
          this.emailClient = emailClient;
          }
          public String getemailClient() {
          return emailClient;
          }
}
