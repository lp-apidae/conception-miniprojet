package dal;

public interface I_BDManagerFactory {
	public abstract I_ProduitDAO createProduitDAO();
	public abstract I_CatalogueDAO createCatalogueDAO();
	public abstract I_CategorieDAO createCategorieDAO();
}
