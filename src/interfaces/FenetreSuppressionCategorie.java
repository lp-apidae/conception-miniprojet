package interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import app.GestionnaireCategorie;

public class FenetreSuppressionCategorie extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btSupprimer;
	private JComboBox<String> combo;

	
	public FenetreSuppressionCategorie(String lesCategories[]) {
		
		setTitle("Suppression categorie");
		setBounds(500, 500, 200, 105);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());
		btSupprimer = new JButton("Supprimer");

		combo = new JComboBox<String>(lesCategories);
		combo.setPreferredSize(new Dimension(100, 20));
		contentPane.add(new JLabel("Categorie"));
		contentPane.add(combo);
		contentPane.add(btSupprimer);

		btSupprimer.addActionListener(this);

		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String texteSupprime = combo.getSelectedItem().toString();
		if(GestionnaireCategorie.checkIfCategoryHasProductsAssociated(texteSupprime) == true)
			JOptionPane.showMessageDialog(this, "Impossible de supprimer la catégorie !\nVeuillez d'abord supprimer les produits associés.", "Erreur", JOptionPane.OK_OPTION);
		else {
			if(GestionnaireCategorie.delete(texteSupprime) == true) {
				JOptionPane.showMessageDialog(this, "La catégorie " + texteSupprime + " a bien été supprimée !", "Succès", JOptionPane.INFORMATION_MESSAGE);
				modifierListesCategories(GestionnaireCategorie.getNomsCategories());
				if(combo.getItemCount() == 0)
					this.dispose();
			}
			else
				JOptionPane.showMessageDialog(this, "Une erreur est survenues avec la base de données", "Erreur", JOptionPane.OK_OPTION);
		}
	}
	
	private void modifierListesCategories(String[] nomsCategories) {
		combo.removeAllItems();
		if (nomsCategories != null)
			for (int i=0 ; i<nomsCategories.length; i++)
			{
				combo.addItem(nomsCategories[i]);
			}
	}

}
