package metier;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class EnsembleCataloguesTest {

		I_EnsembleCatalogues ensembleCat;
		
		@Before
		public void setUp() {
			ensembleCat = EnsembleCatalogues.getInstance();
			ensembleCat.clear();
		}
		
		@Test
		public void testAddCatalogueICatalogue_null() {
			I_Catalogue c1 = null;
			assertFalse("ajout catalogue null", ensembleCat.addCatalogue(c1));
		}
		
		@Test
		public void testAddProduitICatalogue_unCatalogue() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			assertTrue("ajout un catalogue", ensembleCat.addCatalogue(c1));
		}
		
		@Test
		public void testAddProduitIProduit_deuxProduits() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			ensembleCat.addCatalogue(c1);
			I_Catalogue c2 = new Catalogue("Apprendre Window - la méthode XP");
			assertTrue("ajout deux catalogues", ensembleCat.addCatalogue(c2));
		}
		
		@Test
		public void testAddCatalogueICatalogue_deuxFoisMemeCatalogueConsecutif() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			ensembleCat.addCatalogue(c1);
			I_Catalogue c2 = new Catalogue("Apprendre Window - la méthode XP");
			ensembleCat.addCatalogue(c2);
			assertFalse("ajout deux fois même catalogue consécutif", ensembleCat.addCatalogue(c2));
		}
		@Test
		public void testAddCatalogueICatalogue_deuxFoisCatalogueMemeNomEspacesAuDebut() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			ensembleCat.addCatalogue(c1);
			I_Catalogue c2 = new Catalogue(" Window pour les nuls");
			ensembleCat.addCatalogue(c2);
			assertFalse("ajout deux catalogues avec même nom mais un avec des espaces au début", ensembleCat.addCatalogue(c2));
		}
		
		@Test
		public void testAddCatalogueICatalogue_deuxFoisCatalogueMemeNomTabulationsAuDebut() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			ensembleCat.addCatalogue(c1);
			I_Catalogue c2 = new Catalogue("	Window pour les nuls");
			ensembleCat.addCatalogue(c2);
			assertFalse("ajout deux catalogues avec même nom mais un avec des tabulations au début", ensembleCat.addCatalogue(c2));
		}
		
		@Test
		public void testAddCatalogueICatalogue_deuxFoisCatalogueMemeNomEspacesALaFin() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			ensembleCat.addCatalogue(c1);
			I_Catalogue c2 = new Catalogue("Window pour les nuls ");
			ensembleCat.addCatalogue(c2);
			assertFalse("ajout deux catalogues avec même nom mais un avec des espaces à la fin", ensembleCat.addCatalogue(c2));
		}
		
		@Test
		public void testAddProduitICatalogue_deuxFoisCatalogueMemeNomTabulationsALaFin() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			ensembleCat.addCatalogue(c1);
			I_Catalogue c2 = new Catalogue("Window pour les nuls	");
			ensembleCat.addCatalogue(c2);
			assertFalse("ajout deux catalogues avec même nom mais un avec des tabulations à la fin", ensembleCat.addCatalogue(c2));
		}
	
		@Test
		public void testAddCatalogue_null() {
			List<I_Catalogue> cat = null;
			assertEquals("ajout catalogue null", 0, ensembleCat.addCatalogue(cat));
		}
		
		@Test
		public void testAddProduits_vide() {
			List<I_Catalogue> cat = new ArrayList<I_Catalogue>();
			assertEquals("ajout catalogue vide", 0, ensembleCat.addCatalogue(cat));
		}	
		
		@Test
		public void testAddCatalogues_cataloguesAvecUnSeulDoublon() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			I_Catalogue c2 = new Catalogue("Window pour les nuls - la methode XP");
			ensembleCat.addCatalogue(c1);
			ensembleCat.addCatalogue(c2);
			List<I_Catalogue> cat = new ArrayList<I_Catalogue>();
			I_Catalogue c3 = new Catalogue("Window pour les nuls");
			cat.add(c3);
			assertEquals("ajout catalogue avec un seul des catalogues déjà dans l'ensemble",0, ensembleCat.addCatalogue(cat));
		}
		
		@Test
		public void testAddProduits_produitsAvecDoublonsNomProduitsEspacesAuDebut() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			I_Catalogue c2 = new Catalogue("Window pour les nuls - la methode XP");
			ensembleCat.addCatalogue(c1);
			ensembleCat.addCatalogue(c2);
			List<I_Catalogue> cat = new ArrayList<I_Catalogue>();
			I_Catalogue c3 = new Catalogue(" Window pour les nuls");
			cat.add(c3);
			assertEquals("ajout catalogue avec catalogue espaces au début du nom",0, ensembleCat.addCatalogue(cat));
		}
			
		@Test
		public void testAddProduits_produitsAvecDoublonsNomProduitsTabulationsAuDebut() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			I_Catalogue c2 = new Catalogue("Window pour les nuls - la methode XP");
			ensembleCat.addCatalogue(c1);
			ensembleCat.addCatalogue(c2);
			List<I_Catalogue> cat = new ArrayList<I_Catalogue>();
			I_Catalogue c3 = new Catalogue("	Window pour les nuls");
			cat.add(c3);
			assertEquals("ajout catalogue avec catalogue tabulation au début du nom",0, ensembleCat.addCatalogue(cat));
		}	
		
		@Test
		public void testAddProduits_produitsAvecDoublonsNomProduitsEspacesALaFin() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			I_Catalogue c2 = new Catalogue("Window pour les nuls - la methode XP");
			ensembleCat.addCatalogue(c1);
			ensembleCat.addCatalogue(c2);
			List<I_Catalogue> cat = new ArrayList<I_Catalogue>();
			I_Catalogue c3 = new Catalogue("Window pour les nuls ");
			cat.add(c3);
			assertEquals("ajout catalogue avec catalogue espaces à la fin du nom",0, ensembleCat.addCatalogue(cat));
		}
		
		@Test
		public void testAddProduits_produitsAvecDoublonsNomProduitsTabulationsALaFin() {
			I_Catalogue c1 = new Catalogue("Window pour les nuls");
			I_Catalogue c2 = new Catalogue("Window pour les nuls - la methode XP");
			ensembleCat.addCatalogue(c1);
			ensembleCat.addCatalogue(c2);
			List<I_Catalogue> cat = new ArrayList<I_Catalogue>();
			I_Catalogue c3 = new Catalogue("Window pour les nuls	");
			cat.add(c3);
			assertEquals("ajout catalogue avec catalogue tabulation à la fin du nom",0, ensembleCat.addCatalogue(cat));
		}

		@Test
		public void testRemoveProduit_existe() {
			ensembleCat.addCatalogue("Window pour les nuls");
			ensembleCat.addCatalogue("Window pour les nuls - la methode XP");
			assertTrue("suppression catalogue existant", ensembleCat.removeCatalogue("Window pour les nuls"));
		}	
		
		@Test
		public void testRemoveProduit_existePas() {
			assertFalse("suppression catalogue qui n'existe pas", ensembleCat.removeCatalogue("riezourioez"));
		}
			
		@Test
		public void testRemoveProduit_null() {
			ensembleCat.addCatalogue("Window pour les nuls");
			assertFalse("suppression avec un nom null", ensembleCat.removeCatalogue(null));
		}	
		
		@Test
		public void testGetNomCatalogues_vide() {
			String[] tab0 = new String[0];
			assertArrayEquals("recupère nom produits catalogue vide", tab0, ensembleCat.getNomCatalogues());
		}
		
		@Test
		public void testGetNomCatalogues_unCatalogue() {
			String[] tab = {"Window pour les nuls"};
			ensembleCat.addCatalogue("Window pour les nuls");
			assertArrayEquals("recupère nom produits avec un seul produit", tab, ensembleCat.getNomCatalogues());
		}
		
		@Test
		public void testGetNomCatalogues_unCatalogueAvecNomEspacesAuDebut() {
			String[] tab = {"Window pour les nuls"};
			ensembleCat.addCatalogue(" Window pour les nuls");
			assertArrayEquals("recupère nom produits avec avec espace au début du nom", tab, ensembleCat.getNomCatalogues());
		}
		
		@Test
		public void testGetNomCatalogues_unCatalogueAvecNomEspacesALaFin() {
			String[] tab = {"Window pour les nuls"};
			ensembleCat.addCatalogue("Window pour les nuls ");
			assertArrayEquals("recupère nom produit avec espace fin ; les espaces à la fin ne doivent pas être stockés", tab, ensembleCat.getNomCatalogues());
		}
		
		@Test
		public void testGetNomCatalogues_unCatalogueAvecNomTabulationsAuDebut() {
			String[] tab = {"Window pour les nuls"};
			ensembleCat.addCatalogue("	Window pour les nuls");
			assertArrayEquals("recupère nom produit avec tabulation debut ; les tabulations au début ne doivent pas être stockés", tab, ensembleCat.getNomCatalogues());
		}
		
		@Test
		public void testGetNomCatalogues_unCatalogueAvecNomTabulationsALaFin() {
			String[] tab = {"Window pour les nuls"};
			ensembleCat.addCatalogue("Window pour les nuls	");
			assertArrayEquals("recupère nom produit avec tabulation fin ; les tabulations à la fin ne doivent pas être stockés", tab, ensembleCat.getNomCatalogues());
		}
			
		@Test
		public void testGetNomCatalogues_plusieursCataloguesInseresOrdreAlphabetique() {
			String[] tab = {"Window pour les nuls", "Window pour les nuls - la méthode XP", "ZOZO"};
			ensembleCat.addCatalogue("Window pour les nuls");
			ensembleCat.addCatalogue("Window pour les nuls - la méthode XP");
			ensembleCat.addCatalogue("ZOZO");
			assertArrayEquals("recupère nom de plusieurs produits ajoutés dans ordre alphabétique", tab, ensembleCat.getNomCatalogues());
		}	
		
		@Test
		public void testToString_EnsembleCatalogueVide() {
			String resultatAttendu = "Liste des catalogues : \n" +
									"Aucun catalogue n'est enregistré";
			assertEquals("toString catalogue vide", resultatAttendu, ensembleCat.toString());
		}
		
		@Test	
		public void testToString_EnsembleCatalogueAvecDesCatalogues() {
			String resultatAttendu = "Liste des catalogues : \n" +
									 "Catalogue " + "Window pour les nuls" + "\n" +
									 "Catalogue " + "Window pour les nuls - la méthode XP" + "\n";
			ensembleCat.addCatalogue("Window pour les nuls");
			ensembleCat.addCatalogue("Window pour les nuls - la méthode XP");
			assertEquals("toString catalogue sans virgule", resultatAttendu, ensembleCat.toString());
		}
}
