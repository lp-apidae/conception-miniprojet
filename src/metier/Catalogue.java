package metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.GestionnaireCategorie;

public class Catalogue implements I_Catalogue
{
	
	private String nom;
	private List<I_Produit> lesProduits;
	private static Catalogue catalogue;
	
	public static Catalogue getCatalogue() 
	{
		if(catalogue == null) {
			catalogue = new Catalogue();
		}
		return catalogue;
	}
	
	public static Catalogue getCatalogue(String nomCatalogue) 
	{
		catalogue = new Catalogue(nomCatalogue);
		return catalogue;
	}
	
	public Catalogue() {
		this.lesProduits = new ArrayList<I_Produit>();
	}
	
	public Catalogue(String nom) {
		this.nom =  nom.replaceAll("\t", " ").trim();
		this.lesProduits = new ArrayList<I_Produit>();
	}

	@Override
	public boolean addProduit(I_Produit nouveauProduit) 
	{
		if (nouveauProduit == null || nouveauProduit.getPrixUnitaireHT() <= 0 || nouveauProduit.getQuantite() < 0)
			return false;		
		
		if(chercherProduit(nouveauProduit.getNom()) != -1)
			return false;
		else
			return this.lesProduits.add(nouveauProduit);
	}

	@Override
	public boolean addProduit(String nomProduit, double prixProduit, int qteProduit, String nomCategorie) 
	{
		try {
			I_Categorie categorie = GestionnaireCategorie.getCategory(nomCategorie);
			I_Produit nouveauProduit = new Produit(nomProduit, prixProduit, qteProduit, Catalogue.getCatalogue().getNom(), categorie);
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
		int indexProduitASupprimer = chercherProduit(nomProduitASupprimer);
		
		if(indexProduitASupprimer >= 0) {
			this.lesProduits.remove(indexProduitASupprimer);
			return true;	
		}else
			return false;
	}

	@Override
	public boolean acheterStock(String nomProduit, int qteAchetee) 
	{
		if(qteAchetee <= 0) 
			return false;
		
		int indexProduit = chercherProduit(nomProduit);
		
		if(indexProduit >= 0) 
			return this.lesProduits.get(indexProduit).ajouter(qteAchetee);
		else
			return false;
	}

	@Override
	public boolean vendreStock(String nomProduit, int qteVendue) 
	{
		if(qteVendue <= 0) 
			return false;
		
		int indexProduit = chercherProduit(nomProduit);
		
		if(indexProduit >= 0){
			if(this.lesProduits.get(indexProduit).getQuantite() < qteVendue) 
				return false;
			else 
				return this.lesProduits.get(indexProduit).enlever(qteVendue);
		}else
			return false;
	}

	@Override
	public String[] getNomProduits() 
	{
		String[] nomProduits = new String[this.lesProduits.size()];
		int index = 0;
		
		for(I_Produit produitCatalogue : this.lesProduits) {
			nomProduits[index] = produitCatalogue.getNom();
			index++;
		}
		 Arrays.sort(nomProduits);
		 return nomProduits;
	}

	@Override
	public double getMontantTotalTTC() 
	{
		double montantTotalTTC = 0;
		
		for(I_Produit produitCatalogue : this.lesProduits) {
			montantTotalTTC += produitCatalogue.getPrixStockTTC();
		}
		return Math.arrondir(montantTotalTTC);
	}

	@Override
	public String toString() 
	{
		String catalogueToString = "";
		
		for(I_Produit produitCatalogue : this.lesProduits) {
			catalogueToString += produitCatalogue.toString();
		}
		catalogueToString += "\nMontant total TTC du stock : " + Math.formatDouble(this.getMontantTotalTTC()) + " €";
		return catalogueToString;
	}
	
	public void clear() 
	{
		this.lesProduits.clear();
	}
	
	@Override
	public I_Produit getProduit(String nomProduit)
	{
		for(I_Produit produitCatalogue : this.lesProduits) {
			if(produitCatalogue.getNom().equals(nomProduit)) {
				return produitCatalogue;
			}
		}
		return null;
	}
	
	private int chercherProduit(String nomProduit) 
	{
		int index = 0;
		
		for(I_Produit produitCatalogue : this.lesProduits) {
			if(produitCatalogue.getNom().equals(nomProduit)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

}