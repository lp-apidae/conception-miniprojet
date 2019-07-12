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
	private static double tauxTVA = 0.2;
	private String nomCatalogue;
	
	public Produit(String nom, double prixUnitaireHT, int qte, String nomCatalogue) 
	{
		this.nom = nom.replaceAll("\t", " ").trim();
		if(prixUnitaireHT < 0)
			throw new IllegalArgumentException("Le prix du produit doit être un nombre positif");
		this.prixUnitaireHT = prixUnitaireHT;
		if(quantiteStock < 0)
			throw new IllegalArgumentException("Le stock disponible du produit doit être un nombre positif");
		this.quantiteStock = qte;
		
		this.nomCatalogue = nomCatalogue;
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
		return this.prixUnitaireHT * (1 + Produit.tauxTVA);
	}

	@Override
	public double getPrixStockTTC() 
	{
		return (this.quantiteStock * this.prixUnitaireHT) * (1 + Produit.tauxTVA);
	}
	
	@Override
	public String toString() 
	{
		return	getNom() + " - " +
				"prix HT : " + Math.formatDouble(getPrixUnitaireHT()) + " € - " +
				"prix TTC : " + Math.formatDouble(getPrixUnitaireTTC()) + " € - " +
				"quantité en stock : " + getQuantite() + " - " +
				"catalogue : " + getNomCatalogue() + "\n";
	}

	@Override
	public String getNomCatalogue() {
		return this.nomCatalogue;
	}
}