package dal;

import java.util.List;

import metier.I_Produit;

public class ProduitDAO_XML_Adapter implements I_ProduitDAO {
	
	private ProduitDAO_XML produitDAOXML;
	
	public ProduitDAO_XML_Adapter() {
		produitDAOXML = new ProduitDAO_XML();
	}

	@Override
	public boolean create(I_Produit produit) 
	{
		return produitDAOXML.creer(produit);
	}

	@Override
	public I_Produit findByName(String nomProduit) 
	{
		return produitDAOXML.lire(nomProduit);
	}

	@Override
	public List<I_Produit> findAll() 
	{
		return produitDAOXML.lireTous();
	}

	@Override
	public boolean sellStock(I_Produit produit) 
	{
		return produitDAOXML.maj(produit);
	}

	@Override
	public boolean buyStock(I_Produit produit) 
	{
			return produitDAOXML.maj(produit);
	}

	@Override
	public boolean delete(I_Produit produit) 
	{
		return produitDAOXML.supprimer(produit);
	}
}