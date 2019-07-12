package interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import app.GestionnaireCatalogue;

public class FenetreSuppressionProduit extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton btSupprimer;
	private JComboBox<String> combo;
	
	public FenetreSuppressionProduit(String lesProduits[]) {
		
		setTitle("Suppression produit");
		setBounds(500, 500, 200, 105);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btSupprimer = new JButton("Supprimer");

		combo = new JComboBox<String>(lesProduits);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Produit"));
		contentPane.add(combo);
		contentPane.add(btSupprimer);

		btSupprimer.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(GestionnaireCatalogue.supprimerProduit((String)combo.getSelectedItem()) == false) {
			JOptionPane.showMessageDialog(null, "Un problème a été détecté lors de la connexion à la base de données.", "Erreur de connexion", 
					JOptionPane.ERROR_MESSAGE);
		}else {		
			this.dispose();
			JOptionPane.showMessageDialog(this, "Le produit a bien été supprimé", "Produit supprimé",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
