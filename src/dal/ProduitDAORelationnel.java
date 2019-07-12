package dal;

import metier.I_Produit;
import metier.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAORelationnel implements I_ProduitDAO 
{

	private Connection cn;
	private PreparedStatement pst;
	private ResultSet rs;

	public ProduitDAORelationnel() throws ClassNotFoundException, SQLException 
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String url = "jdbc:oracle:thin:@orainfo.iutmontp.univ-montp2.fr:1521:iut";
		String login = "sureaub";
		String mdp = "123";

		cn = DriverManager.getConnection(url, login, mdp);
	}

	@Override
	public boolean create(I_Produit produit)
	{
		boolean result;
		try {
			pst = cn.prepareStatement("CALL ajouterProduit(?, ?, ?, ?)");
			pst.setString(1, produit.getNom());
			pst.setDouble(2, produit.getPrixUnitaireHT());
			pst.setInt(3, produit.getQuantite());
			pst.execute();
			cn.close();
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	@Override
	public I_Produit findByName(String nomProduit) 
	{
		I_Produit produit;
		try{
			pst = cn.prepareStatement("SELECT * FROM PRODUITS WHERE NOM = ?");
			pst.setString(1, nomProduit);
			rs =  pst.executeQuery();
			produit = (I_Produit) hydrateProduct(rs);
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return produit;
	}
	
	@Override
	public List<I_Produit> findAll()
	{
		List<I_Produit> produits;
		try{
			pst = cn.prepareStatement("SELECT * FROM PRODUITS");
			rs =  pst.executeQuery();
			produits = hydrateProducts(rs);
			cn.close();
		}catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return produits;
	} 
	
	@Override
	public boolean sellStock(I_Produit produit) 
	{
		boolean result;
		try {
			pst = cn.prepareStatement("UPDATE PRODUITS SET QUANTITE = ? WHERE NOM = ?");
			pst.setInt(1,  produit.getQuantite());
			pst.setString(2, produit.getNom());
			pst.execute();
			cn.close();
			result = true;
		}catch(SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	
	@Override
	public boolean buyStock(I_Produit produit) 
	{
		boolean result;
		try {
			pst = cn.prepareStatement("UPDATE PRODUITS SET QUANTITE = ? WHERE NOM = ?");
			pst.setInt(1,  produit.getQuantite());
			pst.setString(2, produit.getNom());
			pst.execute();
			cn.close();
			result = true;
		}catch(SQLException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
	    
	@Override
	public boolean delete(I_Produit produit) 
	{
		boolean result;
		try {
			pst = cn.prepareStatement("DELETE FROM PRODUITS WHERE NOM = ?");
			pst.setString(1,  produit.getNom());
			pst.execute();
			cn.close();
			result = true;
		} catch (SQLException |NullPointerException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	private List<I_Produit> hydrateProducts(ResultSet rs) throws SQLException
	{
		List<I_Produit> listeProduits = new ArrayList<I_Produit>(); 
		String nom;
		double prixUnitaireHT;
		int qte;
		
		while (rs.next()) {
			nom = rs.getString("NOM");
			prixUnitaireHT = rs.getDouble("PRIXHT");
			qte = rs.getInt("QUANTITE");
			I_Produit produit = new Produit(nom, prixUnitaireHT, qte);
			listeProduits.add(produit);
		}
		
		return listeProduits;
	}
	
	private I_Produit hydrateProduct(ResultSet rs) throws SQLException
	{
		I_Produit produit=null; 
		String nom;
		double prixUnitaireHT;
		int qte;
		
		if(rs.next()) {
			nom = rs.getString("NOM");
			prixUnitaireHT = rs.getDouble("PRIXHT");
			qte = rs.getInt("QUANTITE");
			produit = new Produit(nom, prixUnitaireHT, qte);
		}
		
		return produit;
	}
}