package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class GestionnaireCatalogueTest {
	
	@Before
	public void setUp() {
		GestionnaireCatalogue.chargerProduitsDansCatalogue("BibliothequeIUT");
		GestionnaireCatalogue.supprimerProduit("ProduitTest");
	}
	
	@Test
	public void chargerProduitsDansCatalogue() {
		assertTrue("Chargement des produits dans le catalogue", GestionnaireCatalogue.chargerProduitsDansCatalogue("BibliothequeIUT"));
	}
	
	@Test
	public void testCreate() {
		assertTrue("Ajout d'un nouveau produit", GestionnaireCatalogue.ajouterProduit("ProduitTest", "10.28", "10"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testCreateWithEmptyFields() {
		GestionnaireCatalogue.ajouterProduit("", "10.28", "10");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testCreateWithNegativePrice() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "10.28", "-10");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testCreateWithNegativeQuantity() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "-10.28", "10");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCreateProductThatAlreadyExist() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
	}
	
	@Test
	public void testEnregistrerAchat() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		assertTrue("Enregistrement d'un achat", GestionnaireCatalogue.enregistrerAchat("ProduitTest", "1"));
	}
	
	@Test(expected = NumberFormatException.class)
	public void testEnregistrerAchatAvecQuantiteNegative() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		assertFalse("Enregistrement d'un achat avec quantité négative", GestionnaireCatalogue.enregistrerAchat("ProduitTest", "-1"));
	}
	
	@Test(expected = NumberFormatException.class)
	public void testEnregistrerAchatAvecDonneesErronees() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		GestionnaireCatalogue.enregistrerAchat("ProduitTest", "rezireziroez");
	}
	
	@Test 
	public void testEnregistrerVente() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		assertTrue("Enregistrement d'une vente", GestionnaireCatalogue.enregistrerVente("ProduitTest", "1"));
	}
	
	@Test(expected = NumberFormatException.class)
	public void testEnregistrerVenteAvecQuantiteNegative() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		GestionnaireCatalogue.enregistrerVente("ProduitTest", "-10");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testEnregistrerVenteAvecQuantiteVenteSuperieureAQuantiteDisponible() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		GestionnaireCatalogue.enregistrerVente("ProduitTest", "100");
	}
	
	@Test(expected = NumberFormatException.class)
	public void testEnregistrerVenteAvecDonneesErronees() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		GestionnaireCatalogue.enregistrerVente("ProduitTest", "rezireziroez");
	}
	
	@Test 
	public void testSupprimerProduit() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "11", "3");
		assertTrue("Suppression d'un produit", GestionnaireCatalogue.supprimerProduit("ProduitTest"));
	}
	
	@Test 
	public void testSupprimerProduitNonExistant() {
		assertFalse("Suppression d'un produit", GestionnaireCatalogue.supprimerProduit("ProduitTest"));
	}
	
	@Test 
	public void testAfficherEtatDesStocks() {
		GestionnaireCatalogue.ajouterProduit("ProduitTest", "1", "1");
		String resultatAttendu = 
				"ProduitTest - prix HT : 1,00 € - prix TTC : 1,20 € - quantité en stock : 1" + "\n" +
				 "\n" +
				 "Montant total TTC du stock : 1,20 €";
		assertEquals("Afficher Etat des stocks", resultatAttendu, GestionnaireCatalogue.afficherEtatDesStocks());
	}
}
