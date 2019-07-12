package metier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnsembleCatalogues implements I_EnsembleCatalogues
{
	
	private static EnsembleCatalogues ensembleCatalogues;
	private List<I_Catalogue> lesCatalogues;
	
	private EnsembleCatalogues() 
	{
		lesCatalogues = new ArrayList<I_Catalogue>();
	}
	
	public static EnsembleCatalogues getInstance() 
	{
		if(ensembleCatalogues == null) {
			ensembleCatalogues = new EnsembleCatalogues();
		}
		return ensembleCatalogues;
	}

	@Override
	public boolean addCatalogue(String nomCatalogue) 
	{
		try {
			I_Catalogue nouveauCatalogue = new Catalogue(nomCatalogue);
			return addCatalogue(nouveauCatalogue);
		}
		catch (IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public boolean addCatalogue(I_Catalogue nouveauCatalogue) 
	{
		if (nouveauCatalogue == null)
			return false;		
		
		if(chercherCatalogue(nouveauCatalogue.getNom()) != -1)
			return false;
		else
			return this.lesCatalogues.add(nouveauCatalogue);
	}

	@Override
	public int addCatalogue(List<I_Catalogue> listeNouveauxCatalogues) 
	{
		int NombreDeCataloguesAjoutes = 0;
		try {
			for(I_Catalogue nouveauCatalogue : listeNouveauxCatalogues) {
				if(addCatalogue(nouveauCatalogue) == true) 
					NombreDeCataloguesAjoutes++;
			}
			return NombreDeCataloguesAjoutes;
		}catch(NullPointerException e) {
			return NombreDeCataloguesAjoutes;
		}
	}

	@Override
	public boolean removeCatalogue(String nomCatalogue) 
	{
		int indexCatalogueASupprimer = chercherCatalogue(nomCatalogue);
		
		if(indexCatalogueASupprimer >= 0) {
			this.lesCatalogues.remove(indexCatalogueASupprimer);
			return true;	
		}else
			return false;
	}

	@Override
	public int chercherCatalogue(String nomCatalogue) 
	{
		int index = 0;
		for(I_Catalogue catalogue : this.lesCatalogues) {
			if(catalogue.getNom().equals(nomCatalogue)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	@Override
	public I_Catalogue getCatalogue(String nomCatalogue) 
	{
		for(I_Catalogue catalogue : this.lesCatalogues) {
			if(catalogue.getNom().equals(nomCatalogue)) {
				return catalogue;
			}
		}
		return null;
	}
	
	@Override
	public String[] getNomCatalogues() {
		String[] nomCatalogues = new String[this.lesCatalogues.size()];
		int index = 0;
		
		for(I_Catalogue catalogue : this.lesCatalogues) {
			nomCatalogues[index] = catalogue.getNom();
			index++;
		}
		 Arrays.sort(nomCatalogues);
		 return nomCatalogues;
	}
	
	@Override
	public String toString() {
		String result = new String();
		String catalogues = new String("");
		result = "Liste des catalogues : \n";
		for(I_Catalogue catalogue : this.lesCatalogues) {
			catalogues = catalogues + "Catalogue " + catalogue.getNom() + "\n";
		}
		if(catalogues.equals(""))
			return result + "Aucun catalogue n'est enregistré";
		else
			return result + catalogues;
	}
	
	public void clear() 
	{
		this.lesCatalogues.clear();
	}
}