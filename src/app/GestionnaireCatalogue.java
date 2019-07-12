package app;

import dal.BDManagerAbstractFactory;
import dal.I_BDManagerFactory;
import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Categorie;
import metier.I_Produit;
import metier.Produit;

public final class GestionnaireCatalogue 
{
	public static String[] getNomsProduits() 
	{
		I_Catalogue catalogue = Catalogue.getCatalogue();
		return catalogue.getNomProduits();
	}
	
	public static boolean chargerProduitsDansCatalogue(String nomCatalogue)
	{
		boolean resultat = true;
		I_Catalogue catalogue = Catalogue.getCatalogue(nomCatalogue);
		I_BDManagerFactory storageFactory = BDManagerAbstractFactory.getBDManagerFactory();
		
		if(storageFactory.createProduitDAO() == null)
			resultat = false;
		else 
			catalogue.addProduits(storageFactory.createProduitDAO().findAll(nomCatalogue));

		return resultat;
	}
	
	public static boolean enregistrerAchat(String nomProduit, String quantite) 
	{
		boolean result;
		I_Catalogue catalogue = Catalogue.getCatalogue();
		int intQuantite = Integer.parseInt(quantite);

		if(intQuantite <= 0)
			throw new NumberFormatException();

		I_BDManagerFactory storageFactory = BDManagerAbstractFactory.getBDManagerFactory();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			I_Produit produit = catalogue.getProduit(nomProduit);
			I_Categorie categorie = GestionnaireCategorie.getCategory(produit.getNomCategorie());
			I_Produit produitAvecNouveauStock = new Produit(produit.getNom(), produit.getPrixUnitaireHT(), produit.getQuantite() + intQuantite, Catalogue.getCatalogue().getNom(), categorie);
			result = storageFactory.createProduitDAO().buyStock(produitAvecNouveauStock);

			if(!catalogue.acheterStock(nomProduit, intQuantite))
				throw new IllegalArgumentException();
		}
		
		return result;
	}
	
	public static boolean enregistrerVente(String nomProduit, String quantite) 
	{
		boolean result;
		int intQuantite = Integer.parseInt(quantite);
		I_Catalogue catalogue = Catalogue.getCatalogue();
		
		if(intQuantite <= 0)
			throw new NumberFormatException();

		I_BDManagerFactory storageFactory = BDManagerAbstractFactory.getBDManagerFactory();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			I_Produit produit = catalogue.getProduit(nomProduit);
			if(produit.getQuantite() < intQuantite) {
				throw new IllegalArgumentException();
			}else {
				I_Categorie categorie = GestionnaireCategorie.getCategory(produit.getNomCategorie());
				I_Produit produitAvecNouveauStock = new Produit(produit.getNom(), produit.getPrixUnitaireHT(), produit.getQuantite() - intQuantite, Catalogue.getCatalogue().getNom(), categorie);
				result = storageFactory.createProduitDAO().sellStock(produitAvecNouveauStock);
				catalogue.vendreStock(nomProduit, intQuantite);
			}
		}
		
		return result;
	}

	public static boolean supprimerProduit(String nomProduit) 
	{
		boolean result;
		I_BDManagerFactory storageFactory = BDManagerAbstractFactory.getBDManagerFactory();
		I_Catalogue catalogue = Catalogue.getCatalogue();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			I_Produit produit = catalogue.getProduit(nomProduit);
			result = storageFactory.createProduitDAO().delete(produit);
			catalogue.removeProduit(nomProduit);
		}
		
		return result;
	}
	
	public static boolean ajouterProduit(String nomProduit, String prixHT, String quantite, String nomCategorie) 
	{
		boolean result = true; 
		I_Catalogue catalogue = Catalogue.getCatalogue();
		
		if(nomProduit.isEmpty() || prixHT.isEmpty()|| quantite.isEmpty())
			throw new NullPointerException();
		
		double prix = Double.parseDouble(prixHT);
		int intQuantite = Integer.parseInt(quantite);
		
		if(prix < 0 || intQuantite < 0)
			throw new NumberFormatException();

		I_BDManagerFactory storageFactory = BDManagerAbstractFactory.getBDManagerFactory();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			if(catalogue.getProduit(nomProduit) != null) {
				throw new IllegalArgumentException("Erreur: Le produit est déjà présent dans le catalogue !");
			}
			else {
				I_Categorie categorie = GestionnaireCategorie.getCategory(nomCategorie);
				I_Produit produit = new Produit(nomProduit, prix, intQuantite, Catalogue.getCatalogue().getNom(), categorie);
				result = storageFactory.createProduitDAO().create(produit);
				catalogue.addProduit(nomProduit, prix, intQuantite, nomCategorie);
			}
		}
		
		return result;
	}
	
	public static String afficherEtatDesStocks() 
	{
		I_Catalogue catalogue = Catalogue.getCatalogue();
		return catalogue.toString();
	}
}