package interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import app.GestionnaireCatalogue;
import app.GestionnaireCategorie;


public class FenetrePrincipale extends JFrame implements ActionListener,
		WindowListener {

	private static final long serialVersionUID = 1L;
	private JButton btAfficher;
	private JButton btNouveauProduit;
	private JButton btSupprimerProduit;
	private JButton btNouvelleCategorie;
	private JButton btSupprimerCategorie;
	private JButton btAchat;
	private JButton btVente;
	private JButton btQuitter;

	
	public FenetrePrincipale(String nomCatalogue) {
		setTitle(nomCatalogue);
		setBounds(500, 500, 320, 250);
		JPanel panAffichage = new JPanel();
		JPanel panNouveauSupprimerProduit = new JPanel();
		JPanel panNouveauSupprimerCategorie = new JPanel();
		JPanel panAchatVente = new JPanel();
		JPanel panQuitter = new JPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btAfficher = new JButton("Quantités en stock");
		btNouveauProduit = new JButton("Nouveau Produit");
		btSupprimerProduit = new JButton("Supprimer Produit");
		btNouvelleCategorie = new JButton("Nouvelle Categorie");
		btSupprimerCategorie = new JButton("Supprimer Categorie");
		btAchat = new JButton("Achat Produits");
		btVente = new JButton("Vente Produits");
		btQuitter = new JButton("Quitter");
		panAffichage.add(btAfficher);
		panNouveauSupprimerProduit.add(btNouveauProduit); 
		panNouveauSupprimerProduit.add(btSupprimerProduit);
		panNouveauSupprimerCategorie.add(btNouvelleCategorie); 
		panNouveauSupprimerCategorie.add(btSupprimerCategorie);
		panAchatVente.add(btAchat); 
		panAchatVente.add(btVente);  
		panQuitter.add(btQuitter);

		contentPane.add(panAffichage);
		contentPane.add(panNouveauSupprimerCategorie);
		contentPane.add(panNouveauSupprimerProduit);
		contentPane.add(panAchatVente);
		contentPane.add(panQuitter);

		btAfficher.addActionListener(this);
		btNouveauProduit.addActionListener(this);
		btSupprimerProduit.addActionListener(this);
		btNouvelleCategorie.addActionListener(this);
		btSupprimerCategorie.addActionListener(this);
		btAchat.addActionListener(this);
		btVente.addActionListener(this);
		btQuitter.addActionListener(this);
		
		addWindowListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btAfficher) {
			String contenuCatalogue = GestionnaireCatalogue.afficherEtatDesStocks();
			if(contenuCatalogue == null) {
				JOptionPane.showMessageDialog(this, "Un problème a été détecté lors de la connexion à la base de données.", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
			}else {
				new FenetreAffichage(contenuCatalogue);
			}
		}
		if (e.getSource() == btNouveauProduit)
			new FenetreNouveauProduit(GestionnaireCategorie.getNomsCategories());
		if (e.getSource() == btSupprimerProduit) {
			String[] nomsProduits = GestionnaireCatalogue.getNomsProduits();
			if(nomsProduits.length == 0)
				JOptionPane.showMessageDialog(this, "Impossible de supprimer un produit. Le catalogue est vide.", "Catalogue vide", JOptionPane.INFORMATION_MESSAGE);
			else
				new FenetreSuppressionProduit(nomsProduits);
		}
		if (e.getSource() == btNouvelleCategorie)
			new FenetreNouvelleCategorie();
		if (e.getSource() == btSupprimerCategorie) {
			if(GestionnaireCategorie.getNomsCategories().length > 0)
				new FenetreSuppressionCategorie(GestionnaireCategorie.getNomsCategories());
		}
		if (e.getSource() == btAchat) {
			String[] nomsProduits = GestionnaireCatalogue.getNomsProduits();
			if(nomsProduits.length == 0)
				JOptionPane.showMessageDialog(this, "Le catalogue est vide.", "Catalogue vide", JOptionPane.INFORMATION_MESSAGE);
			else
				new FenetreAchat(GestionnaireCatalogue.getNomsProduits());
		}
		if (e.getSource() == btVente) {
			String[] nomsProduits = GestionnaireCatalogue.getNomsProduits();
			if(nomsProduits.length == 0)
				JOptionPane.showMessageDialog(this, "Le catalogue est vide.", "Catalogue vide", JOptionPane.INFORMATION_MESSAGE);
			else
				new FenetreVente(GestionnaireCatalogue.getNomsProduits());
		}
		if (e.getSource() == btQuitter){
			close();
		}	
	}

	public void windowClosing(WindowEvent arg0) {
		close();
	}
	
	public void close() {
		new FenetreAccueil();
		this.dispose();
	}

	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
}
