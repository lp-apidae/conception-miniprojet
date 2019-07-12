package metier;


import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CategorieTest {

	@Test
	public void testConstructeurCategorieOk() {
		new Categorie("test", 5.5f);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testConstructeurCategorieEntierNegatif() {
		new Categorie("test", -10);
	}
	
	@Test
	public void testConstructeurCategorieNomEspaceAvant() {
		I_Categorie cat = new Categorie(" test", 20);
		assertEquals("test", cat.getNom());
	}
	
	@Test
	public void testConstructeurCategorieNomEspaceApres() {
		I_Categorie cat = new Categorie("test ", 20);
		assertEquals("test", cat.getNom());
	}
	
	@Test
	public void testConstructeurCategorieNomTabAvant() {
		I_Categorie cat = new Categorie("	test", 20);
		assertEquals("test", cat.getNom());
	}
	
	@Test
	public void testConstructeurCategorieNomTabApres() {
		I_Categorie cat = new Categorie("test	", 20);
		assertEquals("test", cat.getNom());
	}
	
}
