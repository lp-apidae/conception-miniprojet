package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import metier.Catalogue;
import metier.I_Catalogue;


public class CatalogueDAO_Relationnel implements I_CatalogueDAO
{
	private Connection cn;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public CatalogueDAO_Relationnel(Connection connection) 
	{	
		cn = connection;
	}
	
	public int getNombreCatalogues() 
	{
		try {
			pst = cn.prepareStatement("SELECT COUNT (*) FROM CATALOGUES");
			rs =  pst.executeQuery();
			rs.next();
		    int nbOfRows = rs.getInt(1);
		    return nbOfRows;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<I_Catalogue> getNomsCatalogues()
	{
		try {
			pst = cn.prepareStatement("SELECT NOM FROM CATALOGUES");
			rs =  pst.executeQuery();
			List<I_Catalogue> catalogues = new ArrayList<I_Catalogue>();
			catalogues = hydrateCatalogues(rs);
		    return catalogues;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Map<I_Catalogue, String> getNomsCataloguesEtProduits()
	{
		Map<I_Catalogue, String> hm = new HashMap<I_Catalogue, String>();
		try {
			pst = cn.prepareStatement("SELECT * FROM CATALOGUES");
			rs =  pst.executeQuery();
			while(rs.next()){
				PreparedStatement pst2 = cn.prepareStatement("SELECT COUNT(*) FROM PRODUITS WHERE nomcatalogue = ?");
				pst2.setString(1, rs.getString("nom"));
				ResultSet rs2 =  pst2.executeQuery();
				rs2.next();
				int nbOfRows = rs2.getInt(1);
				I_Catalogue cat = hydrateCatalogue(rs.getString("NOM"));
				hm.put(cat, String.valueOf(nbOfRows));
			}
		    return hm;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private List<I_Catalogue> hydrateCatalogues(ResultSet rs) throws SQLException
	{
		List<I_Catalogue> listeCatalogues = new ArrayList<I_Catalogue>(); 
		String nom;
		
		while (rs.next()) {
			nom = rs.getString("NOM");
			I_Catalogue catalogue = new Catalogue(nom);
			listeCatalogues.add(catalogue);
		}
		return listeCatalogues;
	}
	
	private I_Catalogue hydrateCatalogue(String nom) throws SQLException
	{
		return new Catalogue(nom);
	}
	
	@SuppressWarnings("unused")
	private I_Catalogue hydrateCatalogue(ResultSet rs) throws SQLException
	{
		I_Catalogue catalogue=null; 
		String nom;
		
		if(rs.next()) {
			nom = rs.getString("NOM");
			catalogue = new Catalogue(nom);
		}
		
		return catalogue;
	}

	@Override
	public boolean delete(I_Catalogue catalogue) {
		boolean result = true;
		try {
			pst = cn.prepareStatement("DELETE FROM CATALOGUES WHERE NOM = ?");
			pst.setString(1,  catalogue.getNom());
			pst.execute();
		} catch (SQLException |NullPointerException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	@Override
	public boolean add(String nomCatalogue) {
		boolean result = true;
		try {
			pst = cn.prepareStatement("CALL ajouterCatalogue(?)");
			pst.setString(1, nomCatalogue);
			pst.execute();
		} catch (SQLException |NullPointerException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}
}

