package dal;

import java.util.List;

import metier.I_Categorie;

public interface I_CategorieDAO {
	
	public boolean exist(String nomCategorie);
	public boolean add(I_Categorie categorie);
	public boolean delete(String nomCategorie);
	public List<I_Categorie> getCategories();
	public I_Categorie getCategory(String nomCategorie);
	public boolean checkIfCategoryHasProductsAssociated(String nomCategorie);
	
}
