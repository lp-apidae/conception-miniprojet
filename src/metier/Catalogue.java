package metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Catalogue implements I_Catalogue
{
	
	private static Catalogue catalogue;
	private List<I_Produit> lesProduits;

	private Catalogue() 
	{
		lesProduits = new ArrayList<I_Produit>();
	}
	
	public static Catalogue getCatalogue() 
	{
		if(catalogue == null) {
			catalogue = new Catalogue();
		}
		return catalogue;
	}

	@Override
	public boolean addProduit(I_Produit nouveauProduit) 
	{
		Catalogue catalogue = getCatalogue();

		if (nouveauProduit == null || nouveauProduit.getPrixUnitaireHT() <= 0 || nouveauProduit.getQuantite() < 0)
			return false;		
		
		if(chercherProduit(nouveauProduit.getNom()) != -1)
			return false;
		else
			return catalogue.lesProduits.add(nouveauProduit);
	}

	@Override
	public boolean addProduit(String nomProduit, double prixProduit, int qteProduit) 
	{
		try {
			I_Produit nouveauProduit = new Produit(nomProduit, prixProduit, qteProduit);
			return addProduit(nouveauProduit);
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public int addProduits(List<I_Produit> listeNouveauxProduits) 
	{
		int NombreDeProduitsAjoutes = 0;
		try {
			for(I_Produit nouveauProduit : listeNouveauxProduits) {
				if(addProduit(nouveauProduit) == true) 
					NombreDeProduitsAjoutes++;
			}
			return NombreDeProduitsAjoutes;
		}catch(NullPointerException e) {
			return NombreDeProduitsAjoutes;
		}
	}

	@Override
	public boolean removeProduit(String nomProduitASupprimer) 
	{
		Catalogue catalogue = getCatalogue();
		int indexProduitASupprimer = chercherProduit(nomProduitASupprimer);
		
		if(indexProduitASupprimer >= 0) {
			catalogue.lesProduits.remove(indexProduitASupprimer);
			return true;	
		}else
			return false;
	}

	@Override
	public boolean acheterStock(String nomProduit, int qteAchetee) 
	{
		if(qteAchetee <= 0) 
			return false;
		
		Catalogue catalogue = getCatalogue();
		
		int indexProduit = chercherProduit(nomProduit);
		
		if(indexProduit >= 0) 
			return catalogue.lesProduits.get(indexProduit).ajouter(qteAchetee);
		else
			return false;
	}

	@Override
	public boolean vendreStock(String nomProduit, int qteVendue) 
	{
		if(qteVendue <= 0) 
			return false;
		
		Catalogue catalogue = getCatalogue();
		int indexProduit = chercherProduit(nomProduit);
		
		if(indexProduit >= 0){
			if(catalogue.lesProduits.get(indexProduit).getQuantite() < qteVendue) 
				return false;
			else 
				return catalogue.lesProduits.get(indexProduit).enlever(qteVendue);
		}else
			return false;
	}

	@Override
	public String[] getNomProduits() 
	{
		Catalogue catalogue = getCatalogue();
		String[] nomProduits = new String[catalogue.lesProduits.size()];
		int index = 0;
		
		for(I_Produit produitCatalogue : catalogue.lesProduits) {
			nomProduits[index] = produitCatalogue.getNom();
			index++;
		}
		 Arrays.sort(nomProduits);
		 return nomProduits;
	}

	@Override
	public double getMontantTotalTTC() 
	{
		Catalogue catalogue = getCatalogue();
		double montantTotalTTC = 0;
		
		for(I_Produit produitCatalogue : catalogue.lesProduits) {
			montantTotalTTC += produitCatalogue.getPrixStockTTC();
		}
		return Math.arrondir(montantTotalTTC);
	}

	@Override
	public String toString() 
	{
		Catalogue catalogue = getCatalogue();

		String catalogueToString = "";
		
		for(I_Produit produitCatalogue : catalogue.lesProduits) {
			catalogueToString += produitCatalogue.toString();
		}
		catalogueToString += "\nMontant total TTC du stock : " + Math.formatDouble(catalogue.getMontantTotalTTC()) + " €";
		return catalogueToString;
	}
	
	public void clear() 
	{
		Catalogue catalogue = getCatalogue();
		catalogue.lesProduits.clear();
	}
	
	public I_Produit getProduit(String nomProduit)
	{
		for(I_Produit produitCatalogue : catalogue.lesProduits) {
			if(produitCatalogue.getNom().equals(nomProduit)) {
				return produitCatalogue;
			}
		}
		return null;
	}
	
	private int chercherProduit(String nomProduit) 
	{
		int index = 0;
		
		for(I_Produit produitCatalogue : catalogue.lesProduits) {
			if(produitCatalogue.getNom().equals(nomProduit)) {
				return index;
			}
			index++;
		}
		return -1;
	}
}