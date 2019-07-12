package interfaces;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import app.GestionnaireSelectionCatalogue;

public class FenetreAccueil extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btAjouter, btSupprimer, btSelectionner;
	private JTextField txtAjouter;
	private JLabel lbNbCatalogues;
	private JComboBox<String> cmbSupprimer, cmbSelectionner;
	private TextArea taDetailCatalogues;

	public FenetreAccueil() {
		setTitle("Catalogues");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(500, 500, 200, 125);
		Container contentPane = getContentPane();
		JPanel panInfosCatalogues = new JPanel();
		JPanel panNbCatalogues = new JPanel();
		JPanel panDetailCatalogues = new JPanel();
		JPanel panGestionCatalogues = new JPanel();
		JPanel panAjouter = new JPanel();
		JPanel panSupprimer = new JPanel();
		JPanel panSelectionner = new JPanel();
		panNbCatalogues.setBackground(Color.white);
		panDetailCatalogues.setBackground(Color.white);
		panAjouter.setBackground(Color.gray);
		panSupprimer.setBackground(Color.lightGray);
		panSelectionner.setBackground(Color.gray);
		
		panNbCatalogues.add(new JLabel("Nous avons actuellement : "));
		lbNbCatalogues = new JLabel();
		panNbCatalogues.add(lbNbCatalogues);
		
		taDetailCatalogues = new TextArea();
		taDetailCatalogues.setEditable(false);
		new JScrollPane(taDetailCatalogues);
		taDetailCatalogues.setPreferredSize(new Dimension(300, 100));
		panDetailCatalogues.add(taDetailCatalogues);

		panAjouter.add(new JLabel("Ajouter un catalogue : "));
		txtAjouter = new JTextField(10);
		panAjouter.add(txtAjouter);
		btAjouter = new JButton("Ajouter");
		panAjouter.add(btAjouter);
		
		panSupprimer.add(new JLabel("Supprimer un catalogue : "));
		cmbSupprimer = new JComboBox<String>();
		cmbSupprimer.setPreferredSize(new Dimension(100, 20));
		panSupprimer.add(cmbSupprimer);
		btSupprimer = new JButton("Supprimer");
		panSupprimer.add(btSupprimer);

		panSelectionner.add(new JLabel("Selectionner un catalogue : "));
		cmbSelectionner = new JComboBox<String>();
		cmbSelectionner.setPreferredSize(new Dimension(100, 20));
		panSelectionner.add(cmbSelectionner);
		btSelectionner = new JButton("Selectionner");
		panSelectionner.add(btSelectionner);
		
		panGestionCatalogues.setLayout (new BorderLayout());
		panGestionCatalogues.add(panAjouter, "North");
		panGestionCatalogues.add(panSupprimer);
		panGestionCatalogues.add(panSelectionner, "South");
		
		panInfosCatalogues.setLayout(new BorderLayout());
		panInfosCatalogues.add(panNbCatalogues, "North");
		panInfosCatalogues.add(panDetailCatalogues, "South");
				
		contentPane.add(panInfosCatalogues, "North");
		contentPane.add(panGestionCatalogues, "South");
		pack();

		btAjouter.addActionListener(this);
		btSupprimer.addActionListener(this);
		btSelectionner.addActionListener(this);
		
		setVisible(true);
		this.modifierDetailCatalogues(GestionnaireSelectionCatalogue.getNomsCataloguesEtProduits());
		this.modifierListesCatalogues(GestionnaireSelectionCatalogue.getNomsCatalogues());
		this.modifierNbCatalogues(GestionnaireSelectionCatalogue.getNombreCatalogues());
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btAjouter)
		{
			String texteAjout = txtAjouter.getText();
			if (!texteAjout.equals(""))
			{
				boolean resultat = GestionnaireSelectionCatalogue.ajouterCatalogue(texteAjout);
				if(resultat == true) {
					JOptionPane.showMessageDialog(this, "Le catalogue " + texteAjout + " a bien été ajouté !", "Ajout catalogue", JOptionPane.INFORMATION_MESSAGE);
					modifierListesCatalogues(GestionnaireSelectionCatalogue.getNomsCatalogues());
					modifierNbCatalogues(GestionnaireSelectionCatalogue.getNombreCatalogues());
					modifierDetailCatalogues(GestionnaireSelectionCatalogue.getNomsCataloguesEtProduits());
				}else {
					JOptionPane.showMessageDialog(this, "Le catalogue " + texteAjout + " existe déjà !", "Ajout catalogue", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		if(e.getSource() == btSupprimer)
		{
			String texteSupprime = (String)cmbSupprimer.getSelectedItem();
			if (texteSupprime != null) {
				int choice = JOptionPane.showConfirmDialog(this, "Souhaitez-vous réellement supprimer le catalogue " + texteSupprime + " ? \nTous les produits associés seront également supprimés !", "Suppression catalogue", JOptionPane.YES_NO_CANCEL_OPTION);
				if(choice == JOptionPane.YES_OPTION) {
					int index = cmbSupprimer.getSelectedIndex();
					GestionnaireSelectionCatalogue.supprimerCatalogue(texteSupprime);
					cmbSupprimer.removeItemAt(index);
					modifierListesCatalogues(GestionnaireSelectionCatalogue.getNomsCatalogues());
					modifierNbCatalogues(GestionnaireSelectionCatalogue.getNombreCatalogues());
					modifierDetailCatalogues(GestionnaireSelectionCatalogue.getNomsCataloguesEtProduits());
					JOptionPane.showMessageDialog(this, "Le catalogue " + texteSupprime + " et ses produits ont bien été supprimés !", "Confirmation suppression", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
		if(e.getSource() == btSelectionner)
		{
			String texteSelection = (String)cmbSelectionner.getSelectedItem();
			if (texteSelection != null) 
			{
				this.dispose();
				GestionnaireSelectionCatalogue.chargerCatalogue(texteSelection);
			}
		}
	}

	private void modifierListesCatalogues(String[] nomsCatalogues) {
		cmbSupprimer.removeAllItems();
		cmbSelectionner.removeAllItems();
		if (nomsCatalogues != null)
			for (int i=0 ; i<nomsCatalogues.length; i++)
			{
				cmbSupprimer.addItem(nomsCatalogues[i]);
				cmbSelectionner.addItem(nomsCatalogues[i]);
			}
	}
	
	private void modifierNbCatalogues(int nb)
	{
		if(nb > 1)
			lbNbCatalogues.setText(nb + " catalogues");
		else
			lbNbCatalogues.setText(nb + " catalogue");
	}
	
	private void modifierDetailCatalogues(String detailCatalogues) {
		taDetailCatalogues.setText(detailCatalogues);
	}
	
	public static void main(String[] args) {
		new FenetreAccueil();
		
	}
}
