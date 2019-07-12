package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import metier.Categorie;
import metier.I_Categorie;

public class CategorieDAO_Relationnel implements I_CategorieDAO {
	
	private Connection cn;
	private PreparedStatement pst;
	private ResultSet rs;
	
	public CategorieDAO_Relationnel(Connection connection) 
	{	
		cn = connection;
	}
	
	@Override
	public boolean exist(String nomCategorie) {
		try {
			pst = cn.prepareStatement("SELECT * FROM CATEGORIES WHERE NOM = ?");
			pst.setString(1, nomCategorie);
			rs = pst.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean add(I_Categorie categorie) {
		try {
			pst = cn.prepareStatement("INSERT INTO CATEGORIES(NOM, TAUXTVA) VALUES(?, ?)");
			pst.setString(1, categorie.getNom());
			pst.setFloat(2, categorie.getTauxTVA());
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public List<I_Categorie> getCategories() {
		try {
			pst = cn.prepareStatement("SELECT * FROM CATEGORIES");
			rs = pst.executeQuery();
			return hydrateCategories(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean checkIfCategoryHasProductsAssociated(String nomCategorie)
	{
		try {
			pst = cn.prepareStatement("SELECT * FROM PRODUITS p JOIN CATEGORIES c ON c.nom = p.nomcategorie WHERE C.NOM = ?");
			pst.setString(1, nomCategorie);
			rs = pst.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private List<I_Categorie> hydrateCategories(ResultSet rs) throws SQLException
	{
		List<I_Categorie> l = new ArrayList<I_Categorie>();
		while(rs.next()) {
			String nom = rs.getString("NOM");
			float tva = rs.getFloat("tauxtva");
			l.add(new Categorie(nom, tva));
		}
		return l;
	}
	
	private I_Categorie hydrateCategory(ResultSet rs) throws SQLException
	{
		I_Categorie categorie = null;
		while(rs.next()) {
			String nom = rs.getString("NOM");
			float tva = rs.getFloat("tauxtva");
			categorie = new Categorie(nom, tva);
		}
		return categorie;
	}

	@Override
	public boolean delete(String nomCategorie) {
		try {
			pst = cn.prepareStatement("DELETE FROM CATEGORIES WHERE NOM = ?");
			pst.setString(1, nomCategorie);
			pst.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public I_Categorie getCategory(String nomCategorie) {
		try {
			pst = cn.prepareStatement("SELECT * FROM CATEGORIES WHERE NOM = ?");
			pst.setString(1, nomCategorie);
			rs = pst.executeQuery();
			return hydrateCategory(rs);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
