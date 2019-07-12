package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//import java.sql.SQLException;

public class BDRFactory implements I_BDManagerFactory
{
	private Connection cn;
	
	public BDRFactory() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@orainfo.iutmontp.univ-montp2.fr:1521:iut";
		String login = "sureaub";
		String mdp = "123";

		cn = DriverManager.getConnection(url, login, mdp);
	}

	@Override
	public I_ProduitDAO createProduitDAO() 
	{
		return new ProduitDAORelationnel(cn);
	}

	@Override
	public I_CatalogueDAO createCatalogueDAO() 
	{
		return new CatalogueDAO_Relationnel(cn);
	}	
}