package interfaces;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import app.GestionnaireCategorie;

public class FenetreNouvelleCategorie extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtTaux;
	private JTextField txtNom;
	private JButton btValider;

	public FenetreNouvelleCategorie() {

		setTitle("Creation Categorie");
		setBounds(500, 500, 200, 210);
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());

		JLabel labNom = new JLabel("Nom categorie");
		JLabel labTaux = new JLabel("Taux TVA");
		contentPane.add(labNom);
		txtNom = new JTextField(15);
		contentPane.add(txtNom);
		contentPane.add(labTaux);
		txtTaux = new JTextField(15);
		contentPane.add(txtTaux);

		btValider = new JButton("Valider");
		contentPane.add(btValider);

		btValider.addActionListener(this);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(!txtTaux.getText().isEmpty() && !txtNom.getText().isEmpty()) {
			if(Float.valueOf(txtTaux.getText()) < 0)
				JOptionPane.showMessageDialog(this, "Le taux de TVA ne peut être négatif", "Erreur", JOptionPane.OK_OPTION);
			else if(GestionnaireCategorie.exist(txtNom.getText()) == true)
				JOptionPane.showMessageDialog(this, "Cette catégorie existe déjà", "Erreur", JOptionPane.OK_OPTION);
			else {
				if(GestionnaireCategorie.add(txtNom.getText(), Float.valueOf(txtTaux.getText())) == true) {
					JOptionPane.showMessageDialog(this, "La catégorie " + txtNom.getText() + " a bien été ajoutée !", "Succès", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				}
			}
		}
		
	}
}