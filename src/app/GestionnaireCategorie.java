package app;

import java.util.List;

import dal.BDManagerAbstractFactory;
import metier.Categorie;
import metier.I_Categorie;

public final class GestionnaireCategorie {

	public static boolean exist(String nomCategorie)
	{
		return BDManagerAbstractFactory.getBDManagerFactory().createCategorieDAO().exist(nomCategorie.replaceAll("\t", " ").trim());
	}
	
	public static boolean add(String nomCategorie, float taux)
	{
		I_Categorie categorie = new Categorie(nomCategorie, taux);
		return BDManagerAbstractFactory.getBDManagerFactory().createCategorieDAO().add(categorie);
	}
	
	public static boolean delete(String nomCategorie)
	{
		return BDManagerAbstractFactory.getBDManagerFactory().createCategorieDAO().delete(nomCategorie);
	}
	
	public static String[] getNomsCategories() {
		List<I_Categorie> categories = BDManagerAbstractFactory.getBDManagerFactory().createCategorieDAO().getCategories();
		String[] nomsCategories = new String[categories.size()];
		for(int i=0 ; i<categories.size() ; i++)
			nomsCategories[i] = categories.get(i).getNom();
		return nomsCategories;
	}
	
	public static I_Categorie getCategory(String nomCategorie) {
		return BDManagerAbstractFactory.getBDManagerFactory().createCategorieDAO().getCategory(nomCategorie);
	}
	
	public static boolean checkIfCategoryHasProductsAssociated(String nomCategorie) {
		return BDManagerAbstractFactory.getBDManagerFactory().createCategorieDAO().checkIfCategoryHasProductsAssociated(nomCategorie);
	}
}
