package dal;

//import java.sql.SQLException;

public class StorageFactory 
{

	private static StorageFactory storageFactory;
	
	private StorageFactory()
	{
	}
	
	public static synchronized StorageFactory getStorageFactory() 
	{
		if(storageFactory == null)
			storageFactory = new StorageFactory();
		return storageFactory;
	}
	
	public I_ProduitDAO createProduitDAO()
	{
		/*try {
			return new ProduitDAORelationnel();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}*/
		
		return new ProduitDAO_XML_Adapter();
	}
	
}