package dal;

import metier.I_Produit;
import java.util.List;

public interface I_ProduitDAO {

	public boolean create(I_Produit produit);
	
	public abstract I_Produit findByName(String nomProduit);

    public abstract List<I_Produit> findAll(String nomCatalogue);
    
    public abstract boolean sellStock(I_Produit produit);
    
    public abstract boolean buyStock(I_Produit produit);

    public abstract boolean delete(I_Produit produit);
}