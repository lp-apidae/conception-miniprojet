package interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import app.GestionnaireCatalogue;


public class FenetreVente extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btVente;
	private JTextField txtQuantite;
	private JComboBox<String> combo;

	public FenetreVente(String[] lesProduits) {
		setTitle("Vente");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btVente = new JButton("Vente");
		txtQuantite = new JTextField(5);
		txtQuantite.setText("0");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(new JLabel("Quantité vendue"));
		contentPane.add(txtQuantite);
		contentPane.add(btVente);

		btVente.addActionListener(this);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		
		try {
			if(GestionnaireCatalogue.enregistrerVente((String)combo.getSelectedItem(), txtQuantite.getText()) == false){
				JOptionPane.showMessageDialog(this, "Un problème a été détecté lors de la connexion à la base de données.", "Erreur de connexion",
						JOptionPane.ERROR_MESSAGE);
			}else {
				this.dispose();
				JOptionPane.showMessageDialog(this, "La vente a bien été enregistrée !", "Vente enregistrée",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "La quantité saisie doit être un entier positif.", "Le formulaire contient des erreurs",
					JOptionPane.WARNING_MESSAGE);
		}catch(IllegalArgumentException ex1) {
			JOptionPane.showMessageDialog(this, "La quantité saisie ne peut être supérieure à la quantité disponible en stock.", "Le formulaire contient des erreurs",
					JOptionPane.WARNING_MESSAGE);
		}
	}
}
