package interfaces;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import app.GestionnaireCatalogue;


public class FenetreNouveauProduit extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextField txtPrixHT;
	private JTextField txtNom;
	private JTextField txtQte;
//	private JComboBox<String> combo;
	private JButton btValider;

//	public FenetreNouveauProduit(String[] lesCategories) {
	public FenetreNouveauProduit() {	

		setTitle("Creation Produit");
		setBounds(500, 500, 200, 250);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());

		JLabel labNom = new JLabel("Nom produit");
		JLabel labPrixHT = new JLabel("Prix Hors Taxe");
		JLabel labQte = new JLabel("Quantité en stock");
//		JLabel labCategorie = new JLabel("Categorie");
		contentPane.add(labNom);
		txtNom = new JTextField(15);
		contentPane.add(txtNom);
		contentPane.add(labPrixHT);
		txtPrixHT = new JTextField(15);
		contentPane.add(txtPrixHT);
		contentPane.add(labQte);
		txtQte = new JTextField(15);
		contentPane.add(txtQte);

//		combo = new JComboBox<String>(lesCategories);
//		combo.setPreferredSize(new Dimension(100, 20));
//		contentPane.add(labCategorie);
//		contentPane.add(combo);

		
		btValider = new JButton("Valider");
		contentPane.add(btValider);

		btValider.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		try{
			boolean resultat = GestionnaireCatalogue.ajouterProduit(txtNom.getText(), txtPrixHT.getText(), txtQte.getText());
			
			if(resultat == true) {
				this.dispose();
				JOptionPane.showMessageDialog(this, "Le produit a bien été ajouté !", "Produit ajouté",
						JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Un problème est survenu lors de l'ajout à la base de données.", "Erreur de connexion",
						JOptionPane.ERROR_MESSAGE);
			}
		}catch(NullPointerException e2){
			e2.printStackTrace();
			JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs du formulaire.", "Le formulaire contient des erreurs", 
					JOptionPane.ERROR_MESSAGE);
		}catch(NumberFormatException e3){
			e3.printStackTrace();
			JOptionPane.showMessageDialog(this, "Le prix et la quantité du produit doivent être des nombres positifs.", "Le formulaire contient des erreurs", 
					JOptionPane.ERROR_MESSAGE);
		}catch(IllegalArgumentException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(this, "Le produit existe déjà !", "Le formulaire contient des erreurs",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}