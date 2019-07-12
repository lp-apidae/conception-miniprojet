package metier;

import java.util.List;

public interface I_EnsembleCatalogues 
{
	
	public abstract boolean addCatalogue(String nomCatalogue);
	
	public abstract boolean addCatalogue(I_Catalogue catalogue);
	
	public abstract int addCatalogue(List<I_Catalogue> catalogue);
	
	public abstract boolean removeCatalogue(String nomCatalogue);
	
	public abstract String[] getNomCatalogues();
	
	public abstract int chercherCatalogue(String nomCatalogue);
	
	public abstract void clear();

	public abstract I_Catalogue getCatalogue(String nomCatalogue);
}