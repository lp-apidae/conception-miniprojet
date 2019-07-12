package dal;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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
import metier.Catalogue;
import metier.I_Catalogue;

public class CatalogueDAO_XML implements I_CatalogueDAO
{
	private String uri = "Catalogues.xml";
	private Document doc;
	
	public CatalogueDAO_XML() {
		SAXBuilder sdoc = new SAXBuilder();
		try {
			doc = sdoc.build(uri);
		} catch (Exception e) {
			System.out.println("erreur construction arbre JDOM");
		}
	}

	@Override
	public int getNombreCatalogues() {
		Element root = doc.getRootElement();
		return root.getChildren().size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<I_Catalogue> getNomsCatalogues() {
		List<I_Catalogue> l = new ArrayList<I_Catalogue>();
		try {
			Element root = doc.getRootElement();
			List<Element> ecat = root.getChildren("catalogue");

			for (Element cat : ecat) {
				String nomC = cat.getChildText("nom");
				l.add(new Catalogue(nomC));
			}
		} catch (Exception e) {
			System.out.println("erreur getNomsCatalogues tous les catalogues");
		}
		return l;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<I_Catalogue, String> getNomsCataloguesEtProduits() {
		Map<I_Catalogue, String> hm = new HashMap<I_Catalogue, String>();
		try {
			Element root = doc.getRootElement();
			List<Element> ecat = root.getChildren("catalogue");
			
			for(Element cat : ecat) {
				String nomC = cat.getChildText("nom");
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        factory.setNamespaceAware(true); 
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        org.w3c.dom.Document docP = builder.parse(new InputSource("Produits.xml"));
		        XPathFactory xpf = XPathFactory.newInstance();
		        XPath xpath = xpf.newXPath();
		        XPathExpression expr = xpath.compile("count(//produit[nomcatalogue='"+nomC+"'])");
		        Object result = expr.evaluate(docP, XPathConstants.NUMBER);
		        hm.put(new Catalogue(nomC), Integer.toString(((Double) result).intValue()));
			}
		}catch (Exception e) {
				System.out.println("erreur getNomsCataloguesEtProduits");
		}
		return hm;
	}

	@Override
	public boolean delete(I_Catalogue catalogue) {
		try {
			Element root = doc.getRootElement();
			Element cat = chercheCatalogue(catalogue.getNom());
			if (cat != null) {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		        factory.setNamespaceAware(true); 
		        DocumentBuilder builder = factory.newDocumentBuilder();
		        org.w3c.dom.Document docP = builder.parse(new InputSource("Produits.xml"));
		        XPathFactory xpf = XPathFactory.newInstance();
		        XPath xpath = xpf.newXPath();
		        XPathExpression expr = xpath.compile("//produit[nomcatalogue='"+catalogue.getNom()+"']");
		        NodeList nodes = (NodeList) expr.evaluate(docP, XPathConstants.NODESET);
		        for(int i = 0 ; i < nodes.getLength() ; i++) {
		        	nodes.item(i).getParentNode().removeChild(nodes.item(i));
		        }
		        Transformer transformer = TransformerFactory.newInstance().newTransformer();
	        	transformer.transform(new DOMSource(docP), new StreamResult("Produits.xml"));
	        	root.removeContent(cat);
				return sauvegarde();
			}else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("erreur supprimer catalogue");
			return false;
		}
	}

	@Override
	public boolean add(String nomCatalogue) {
		try {
			Element root = doc.getRootElement();
			Element cat = new Element("catalogue");
			Element nom = new Element("nom");
			cat.addContent(nom.setText(nomCatalogue));
			root.addContent(cat);
			return sauvegarde();
		} catch (Exception e) {
			System.out.println("erreur creer catalogue");
			return false;
		}
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
	private Element chercheCatalogue(String nom) {
		Element root = doc.getRootElement();
		List<Element> ecat = root.getChildren("catalogue");
		int i = 0;
		while (i < ecat.size() && !ecat.get(i).getChildText("nom").equals(nom))
			i++;
		if (i < ecat.size())
			return ecat.get(i);
		else
			return null;
	}

}
