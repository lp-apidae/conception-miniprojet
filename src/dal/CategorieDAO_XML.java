package dal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import metier.Categorie;
import metier.I_Categorie;

public class CategorieDAO_XML implements I_CategorieDAO {

	private String uri = "Categories.xml";
	private Document doc;
	
	public CategorieDAO_XML() {
		SAXBuilder sdoc = new SAXBuilder();
		try {
			doc = sdoc.build(uri);
		} catch (Exception e) {
			System.out.println("erreur construction arbre JDOM");
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean exist(String nomCategorie) {
		try {
			Element root = doc.getRootElement();
			Element cat = chercheCategorie(nomCategorie);
			if (cat != null) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			System.out.println("erreur supprimer produit");
			return false;
		}
	}

	@Override
	public boolean add(I_Categorie categorie) {
		try {
			Element root = doc.getRootElement();
			Element cat = new Element("categorie");
			cat.setAttribute("nom", categorie.getNom());
			Element tx = new Element("tauxtva");
			cat.addContent(tx.setText(String.valueOf(categorie.getTauxTVA())));
			root.addContent(cat);
			return sauvegarde();
		} catch (Exception e) {
			System.out.println("erreur creer produit");
			return false;
		}
	}

	@Override
	public boolean delete(String nomCategorie) {
		try {
			Element root = doc.getRootElement();
			Element cat = chercheCategorie(nomCategorie);
			if (cat != null) {
				root.removeContent(cat);
				return sauvegarde();
			} else
				return false;
		} catch (Exception e) {
			System.out.println("erreur supprimer produit");
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<I_Categorie> getCategories() {
		try {
			Element root = doc.getRootElement();
			List<Element> cat = root.getChildren("categorie");
			List<I_Categorie> l = new ArrayList<I_Categorie>();
			int i = 0;
			while (i < cat.size()) {
				String nom = cat.get(i).getAttributeValue("nom");
				float tva = Float.valueOf(cat.get(i).getChildText("tauxtva"));
				l.add(new Categorie(nom, tva));
				i++;
			}
			return l;
		} catch (Exception e) {
			System.out.println("erreur lireTous tous les produits");
			System.out.println(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean checkIfCategoryHasProductsAssociated(String nomCategorie) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setNamespaceAware(true); 
	        DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			org.w3c.dom.Document docP = builder.parse(new InputSource("Produits.xml"));
	        XPathFactory xpf = XPathFactory.newInstance();
	        XPath xpath = xpf.newXPath();
	        XPathExpression expr = xpath.compile("//produit[nomcategorie='"+nomCategorie+"']");
	        NodeList nodes = (NodeList) expr.evaluate(docP, XPathConstants.NODESET);
	        System.out.println(nodes.getLength());
	        if(nodes.getLength() > 0)
	        	return true;
	        return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public I_Categorie getCategory(String nomCategorie) {
		Element e = chercheCategorie(nomCategorie);
		if (e != null) {
			return new Categorie(e.getAttributeValue("nom"), Float.parseFloat(e.getChildText("tauxtva")));
		}
		else
			return null;
	}
	
	private boolean sauvegarde() {
		System.out.println("Sauvegarde");
		XMLOutputter out = new XMLOutputter();
		try {
			out.output(doc, new PrintWriter(uri));
			return true;
		} catch (Exception e) {
			System.out.println("erreur sauvegarde dans fichier XML");
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Element chercheCategorie(String nom) {
		Element root = doc.getRootElement();
		List<Element> cat = root.getChildren("categorie");
		int i = 0;
		while (i < cat.size() && !cat.get(i).getAttribute("nom").getValue().equals(nom))
			i++;
		if (i < cat.size())
			return cat.get(i);
		else
			return null;
	}

}
