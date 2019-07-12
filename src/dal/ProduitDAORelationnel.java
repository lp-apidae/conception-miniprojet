package dal;

import metier.Catalogue;
import metier.I_Categorie;
import metier.I_Produit;
import metier.Produit;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import app.GestionnaireCategorie;

public class ProduitDAORelationnel implements I_ProduitDAO 
{

	private Connection cn;
	private PreparedStatement pst;
	private ResultSet rs;

	public ProduitDAORelationnel(Connection connection)  
	{
		cn = connection;
	}
	

	@Override
	public boolean create(I_Produit produit)
	{
		boolean result;
		try {
			pst = cn.prepareStatement("CALL ajouterProduit(?, ?, ?, ?, ?)");
			pst.setString(1, produit.getNom());
			pst.setDouble(2, produit.getPrixUnitaireHT());
			pst.setInt(3, produit.getQuantite());
			pst.setString(4, Catalogue.getCatalogue().getNom());
			pst.setString(5, produit.getNomCategorie());
			pst.execute();
			result = true;
		} catch(SQLException e) {
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
	public List<I_Produit> findAll(String nomCatalogue)
	{
		List<I_Produit> produits;
		try{
			pst = cn.prepareStatement("SELECT * FROM PRODUITS WHERE NOMCATALOGUE IN (\n" + 
					"SELECT * FROM CATALOGUES WHERE NOM = ?)");
			pst.setString(1, nomCatalogue);
			rs =  pst.executeQuery();
			produits = hydrateProducts(rs);
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
		boolean result = true;
		try {
			pst = cn.prepareStatement("DELETE FROM PRODUITS WHERE NOM = ?");
			pst.setString(1,  produit.getNom());
			pst.execute();
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
		String nomCatalogue;
		String nomCategorie;
		
		while (rs.next()) {
			nom = rs.getString("NOM");
			prixUnitaireHT = rs.getDouble("PRIXHT");
			qte = rs.getInt("QUANTITE");
			nomCatalogue = rs.getString("NOMCATALOGUE");
			nomCategorie = rs.getString("NOMCATEGORIE");
			I_Categorie categorie = GestionnaireCategorie.getCategory(nomCategorie);
			I_Produit produit = new Produit(nom, prixUnitaireHT, qte, nomCatalogue, categorie);
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
		String nomCatalogue;
		String nomCategorie;
		
		if(rs.next()) {
			nom = rs.getString("NOM");
			prixUnitaireHT = rs.getDouble("PRIXHT");
			qte = rs.getInt("QUANTITE");
			nomCatalogue = rs.getString("NOMCATALOGUE");
			nomCategorie = rs.getString("NOMCATEGORIE");
			I_Categorie categorie = GestionnaireCategorie.getCategory(nomCategorie);
			produit = new Produit(nom, prixUnitaireHT, qte, nomCatalogue, categorie);
		}
		
		return produit;
	}
}