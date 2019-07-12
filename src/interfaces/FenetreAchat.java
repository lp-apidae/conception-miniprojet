package interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import app.GestionnaireCatalogue;


public class FenetreAchat extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btAchat;
	private JTextField txtQuantite;
	private JComboBox<String> combo;

	public FenetreAchat(String[] lesProduits) {

		setTitle("Achat");
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btAchat = new JButton("Achat");
		txtQuantite = new JTextField(5);
		txtQuantite.setText("0");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(new JLabel("Quantité achetée"));
		contentPane.add(txtQuantite);
		contentPane.add(btAchat);

		btAchat.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if(GestionnaireCatalogue.enregistrerAchat((String)combo.getSelectedItem(), txtQuantite.getText()) == false) {
				JOptionPane.showMessageDialog(this, "Un problème a été détecté lors de la connexion à la base de données.", "Erreur de connexion",
						JOptionPane.ERROR_MESSAGE);
			}else {
				this.dispose();
				JOptionPane.showMessageDialog(this, "L'achat a bien été enregistré !", "Achat enregistré",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}catch(NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "La quantité saisie doit être un entier positif", "Le formulaire contient des erreurs",
					JOptionPane.WARNING_MESSAGE);
		}
		
	}

}
