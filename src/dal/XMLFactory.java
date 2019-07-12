package dal;

public class XMLFactory implements I_BDManagerFactory
{

	@Override
	public I_ProduitDAO createProduitDAO() 
	{
		return new ProduitDAO_XML_Adapter();
	}

	@Override
	public I_CatalogueDAO createCatalogueDAO() 
	{
		return new CatalogueDAO_XML();
	}
	
	@Override
	public I_CategorieDAO createCategorieDAO() {
		return new CategorieDAO_XML();
	}

}
