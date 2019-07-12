package app;

import dal.StorageFactory;
import metier.Catalogue;
import metier.I_Catalogue;
import metier.I_Produit;
import metier.Produit;

public final class GestionnaireCatalogue 
{
	public static String[] getNomsProduits() 
	{
		I_Catalogue catalogue = Catalogue.getCatalogue();
		return catalogue.getNomProduits();
	}
	
	public static boolean chargerProduitsDansCatalogue() 
	{
		boolean resultat = true;
		I_Catalogue catalogue = Catalogue.getCatalogue();
		StorageFactory storageFactory = StorageFactory.getStorageFactory();
		
		if(storageFactory.createProduitDAO() == null)
			resultat = false;
		else 
			catalogue.addProduits(storageFactory.createProduitDAO().findAll());

		return resultat;
	}
	
	public static boolean enregistrerAchat(String nomProduit, String quantite) 
	{
		boolean result;
		int intQuantite = Integer.parseInt(quantite);
		
		if(intQuantite <= 0)
			throw new NumberFormatException();
		
		I_Catalogue catalogue = Catalogue.getCatalogue();
		StorageFactory storageFactory = StorageFactory.getStorageFactory();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			I_Produit produit = Catalogue.getCatalogue().getProduit(nomProduit);
			I_Produit produitAvecNouveauStock = new Produit(produit.getNom(), produit.getPrixUnitaireHT(), produit.getQuantite() + intQuantite);
			result = storageFactory.createProduitDAO().buyStock(produitAvecNouveauStock);

			if(catalogue.acheterStock(nomProduit, intQuantite) == false)
				throw new IllegalArgumentException();
		}
		
		return result;
	}
	
	public static boolean enregistrerVente(String nomProduit, String quantite) 
	{
		boolean result;
		int intQuantite = Integer.parseInt(quantite);
		
		if(intQuantite <= 0)
			throw new NumberFormatException();
		
		I_Catalogue catalogue = Catalogue.getCatalogue();
		StorageFactory storageFactory = StorageFactory.getStorageFactory();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			I_Produit produit = Catalogue.getCatalogue().getProduit(nomProduit);
			if(produit.getQuantite() < intQuantite) {
				result = false;
			}else {
				I_Produit produitAvecNouveauStock = new Produit(produit.getNom(), produit.getPrixUnitaireHT(), produit.getQuantite() - intQuantite);
				result = storageFactory.createProduitDAO().sellStock(produitAvecNouveauStock);
			
				if(catalogue.vendreStock(nomProduit, intQuantite) == false)
					throw new IllegalArgumentException();
			}
		}
		
		return result;
	}
	
	public static boolean supprimerProduit(String nomProduit) 
	{
		boolean result;
		I_Catalogue catalogue = Catalogue.getCatalogue();
		StorageFactory storageFactory = StorageFactory.getStorageFactory();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			I_Produit produit = Catalogue.getCatalogue().getProduit(nomProduit);
			result = storageFactory.createProduitDAO().delete(produit);
			catalogue.removeProduit(nomProduit);
		}
		
		return result;
	}
	
	public static boolean ajouterProduit(String nomProduit, String prixHT, String quantite) 
	{
		boolean result; 
		
		if(nomProduit.isEmpty() || prixHT.isEmpty()|| quantite.isEmpty())
			throw new NullPointerException();
		
		double prix = Double.parseDouble(prixHT);
		int intQuantite = Integer.parseInt(quantite);
		
		if(prix < 0 || intQuantite < 0)
			throw new NumberFormatException();
		
		I_Catalogue catalogue = Catalogue.getCatalogue();
		
		StorageFactory storageFactory = StorageFactory.getStorageFactory();
		
		if(storageFactory.createProduitDAO() == null)
			result = false;
		else {
			if(Catalogue.getCatalogue().getProduit(nomProduit) != null) {
				throw new IllegalArgumentException("Erreur: Le produit est déjà présent dans le catalogue !");
			}
			else {
				I_Produit produit = new Produit(nomProduit, prix, intQuantite);
				result = storageFactory.createProduitDAO().create(produit);
				catalogue.addProduit(nomProduit, prix, intQuantite);
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