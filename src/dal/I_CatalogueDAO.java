package dal;

import java.util.List;
import java.util.Map;
import metier.I_Catalogue;

public interface I_CatalogueDAO 
{
	public abstract int getNombreCatalogues(); 
	public abstract List<I_Catalogue> getNomsCatalogues();
	public abstract Map<I_Catalogue, String> getNomsCataloguesEtProduits();
	public abstract boolean delete(I_Catalogue catalogue);
	public abstract boolean add(String nomCatalogue);
}