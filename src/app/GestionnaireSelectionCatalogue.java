package app;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import dal.BDManagerAbstractFactory;
import dal.I_BDManagerFactory;
import interfaces.FenetrePrincipale;
import metier.EnsembleCatalogues;
import metier.I_Catalogue;
import metier.I_CatalogueComparator;

public final class GestionnaireSelectionCatalogue 
{
	
	public static int getNombreCatalogues() 
	{
		return BDManagerAbstractFactory.getBDManagerFactory().createCatalogueDAO().getNombreCatalogues();
	}
	
	public static String[] getNomsCatalogues() 
	{
		List<I_Catalogue> catalogues = BDManagerAbstractFactory.getBDManagerFactory().createCatalogueDAO().getNomsCatalogues();
		EnsembleCatalogues.getInstance().addCatalogue(catalogues);
		
		String[] nomsCatalogues = new String[catalogues.size()];
		
		int iterator = 0;
		for(I_Catalogue cat : catalogues) {
			nomsCatalogues[iterator] = cat.getNom();
			iterator++;
		}
		 Arrays.sort(nomsCatalogues);
		 return nomsCatalogues;
	}
	
	public static String getNomsCataloguesEtProduits() 
	{
		String print="";
		Map<I_Catalogue, String> hm = BDManagerAbstractFactory.getBDManagerFactory().createCatalogueDAO().getNomsCataloguesEtProduits();
		Map<I_Catalogue, String> sortedmap = hm.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey(new I_CatalogueComparator()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		for (Map.Entry<I_Catalogue, String> entry : sortedmap.entrySet()) {
			if(entry.getValue().equals("0") || entry.getValue().equals("1"))
				print = print + entry.getKey().getNom() + " : " + entry.getValue() + " produit\n";
			else
				print = print + entry.getKey().getNom() + " : " + entry.getValue() + " produits\n";
		}
		
		return print;
	}
	
	public static boolean ajouterCatalogue(String texteAjout) {
		boolean result;
		if(EnsembleCatalogues.getInstance().chercherCatalogue(texteAjout) != -1){
			result = false;
		}else{
			I_BDManagerFactory storageFactory = BDManagerAbstractFactory.getBDManagerFactory();
			
			if(storageFactory.createCatalogueDAO() == null)
				result = false;
			else {
				result = storageFactory.createCatalogueDAO().add(texteAjout);
				EnsembleCatalogues.getInstance().addCatalogue(texteAjout);
				result = true;
			}
		}
		return result;
	}
	
	public static boolean supprimerCatalogue(String nomCatalogue) 
	{
		boolean result;
		I_BDManagerFactory storageFactory = BDManagerAbstractFactory.getBDManagerFactory();
		
		if(storageFactory.createCatalogueDAO() == null)
			result = false;
		else {
			I_Catalogue catalogue = EnsembleCatalogues.getInstance().getCatalogue(nomCatalogue);
			result = storageFactory.createCatalogueDAO().delete(catalogue);
			EnsembleCatalogues.getInstance().removeCatalogue(nomCatalogue);
		}
		return result;
	}
	
	public static boolean chargerCatalogue(String nomCatalogue) {
		if(!GestionnaireCatalogue.chargerProduitsDansCatalogue(nomCatalogue)) {
			return false;
		}
		new FenetrePrincipale(nomCatalogue);
		return true;
	}
	
}