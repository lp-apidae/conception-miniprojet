package dal;

//import java.sql.SQLException;

public abstract class BDManagerAbstractFactory implements I_BDManagerFactory
{

	private static I_BDManagerFactory bdManagerFactory;
	
	private BDManagerAbstractFactory() {}
	
	public static synchronized I_BDManagerFactory getBDManagerFactory() 
	{
		/*if(bdManagerFactory == null) {
			try {
				bdManagerFactory = new BDRFactory();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}*/
			bdManagerFactory = new XMLFactory();
			
		return bdManagerFactory;
	}
	
	@Override
	public abstract I_ProduitDAO createProduitDAO();

	@Override
	public abstract I_CatalogueDAO createCatalogueDAO();

}
