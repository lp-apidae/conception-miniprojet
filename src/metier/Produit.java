package metier;

public class Produit implements I_Produit
{
	
	private String nom;
	private int quantiteStock;
	/**
	 * @param quantiteStock the quantiteStock to set
	 */
	public void setQuantiteStock(int quantiteStock) {
		this.quantiteStock = quantiteStock;
	}

	private double prixUnitaireHT;
	private float tauxTVA;
	private String nomCatalogue;
	private String nomCategorie;
	
	public Produit(String nom, double prixUnitaireHT, int qte, String nomCatalogue, I_Categorie categorie) 
	{
		this.nom = nom.replaceAll("\t", " ").trim();
		if(prixUnitaireHT < 0)
			throw new IllegalArgumentException("Le prix du produit doit être un nombre positif");
		this.prixUnitaireHT = prixUnitaireHT;
		if(quantiteStock < 0)
			throw new IllegalArgumentException("Le stock disponible du produit doit être un nombre positif");
		this.quantiteStock = qte;
		
		this.nomCatalogue = nomCatalogue;
		this.tauxTVA = categorie.getTauxTVA() / 100;
		this.nomCategorie = categorie.getNom();
	}

	@Override
	public boolean ajouter(int qteAchetee) 
	{
		this.quantiteStock += qteAchetee;
		return true;
	}

	@Override
	public boolean enlever(int qteVendue) 
	{
		if(qteVendue > getQuantite())
			return false;
		this.quantiteStock -= qteVendue;
		return true;
	}

	@Override
	public String getNom() 
	{
		return this.nom;
	}
	
	
	
	@Override
	public String getNomCategorie() 
	{
		return this.nomCategorie;
	}

	@Override
	public int getQuantite() 
	{
		return this.quantiteStock;
	}

	@Override
	public double getPrixUnitaireHT() 
	{
		return this.prixUnitaireHT;
	}

	@Override
	public double getPrixUnitaireTTC() 
	{
		return this.prixUnitaireHT * (1 + this.tauxTVA);
	}

	@Override
	public double getPrixStockTTC() 
	{
		return (this.quantiteStock * this.prixUnitaireHT) * (1 + this.tauxTVA);
	}
	
	@Override
	public String toString() 
	{
		return	getNom() + " - " +
				"prix HT : " + Math.formatDouble(getPrixUnitaireHT()) + " € - " +
				"prix TTC : " + Math.formatDouble(getPrixUnitaireTTC()) + " € - " +
				"quantité en stock : " + getQuantite() + " - " + 
				"nom catalogue : " + getNomCatalogue() + " - " + 
				"nom catégorie : " + getNomCategorie() + "\n";
	}

	@Override
	public String getNomCatalogue() {
		return this.nomCatalogue;
	}
}