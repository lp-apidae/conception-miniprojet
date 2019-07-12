package metier;

public class Categorie implements I_Categorie {
	
	private String nom;
	private float tva;
	
	public Categorie(String nom, float tva)
	{
		this.nom = nom;
		if(tva < 0)
			throw new IllegalArgumentException("Le taux de TVA doit être un nombre positif");
		this.tva = tva;
	}

	@Override
	public String getNom() {
		return this.nom.replaceAll("\t", " ").trim();
	}

	@Override
	public float getTauxTVA() {
		return this.tva;
	}
}
