DROP TABLE PRODUITS;
DROP TABLE CATEGORIES;
DROP TABLE CATALOGUES;

CREATE TABLE CATALOGUES(
	nom VARCHAR(50),
	tauxtva NUMBER(4, 2) CONSTRAINT nn_tx_tva NOT NULL,
	CONSTRAINT pk_catalogue PRIMARY KEY (nom)
);

CREATE TABLE CATEGORIES(
	nom VARCHAR(50),
	CONSTRAINT pk_categories PRIMARY KEY (nom)
);

CREATE TABLE PRODUITS (
	numproduit NUMBER(10),
	nom VARCHAR(50) CONSTRAINT nn_nom_produit NOT NULL,
	prixht NUMBER(12, 2) CONSTRAINT nn_prixht_produit NOT NULL,
	quantite NUMBER(10, 0) CONSTRAINT nn_quantite_produit NOT NULL,
	nomcatalogue VARCHAR(50) CONSTRAINT nn_nomcatalogue NOT NULL, 
	nomcategorie VARCHAR(50) CONSTRAINT nn_nomcategorie NOT NULL,
	CONSTRAINT pk_produits PRIMARY KEY (numproduit),
	CONSTRAINT prixht_produit CHECK (prixht > 0),
	CONSTRAINT quantite_produit CHECK (quantite >= 0),
	CONSTRAINT fk_nomcatalogue FOREIGN KEY(nomcatalogue) REFERENCES CATALOGUES(nom) ON DELETE CASCADE, 
	CONSTRAINT fk_nomcategorie FOREIGN KEY(nomcategorie) REFERENCES CATEGORIES(nom)
);

GRANT ALL ON CATALOGUES TO nicotb;
GRANT ALL ON CATEGORIES TO nicotb;
GRANT ALL ON PRODUITS TO nicotb;

CREATE SEQUENCE sq_produits START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE PROCEDURE ajouterCatalogue(p_nomCatalogue IN CATALOGUES.nom%TYPE) IS
BEGIN 
	INSERT INTO CATALOGUES(nom) VALUES (p_nomCatalogue);
END;

CREATE OR REPLACE PROCEDURE ajouterCategorie(p_nomCategorie IN CATEGORIES.nom%TYPE, p_tauxTva IN CATEGORIES.tauxtva%TYPE) IS
BEGIN 
	INSERT INTO CATEGORIES(nom, tauxtva) VALUES (p_nomCategorie, p_tauxTva);
END;

CREATE OR REPLACE PROCEDURE ajouterProduit(p_nomProduit IN PRODUITS.nom%TYPE, p_prixht IN PRODUITS.prixht%TYPE, p_quantite IN PRODUITS.quantite%TYPE, p_nomCatalogue IN PRODUITS.nomcatalogue%TYPE, p_nomCategorie IN PRODUITS.nomcategorie%TYPE) IS
BEGIN 
	INSERT INTO PRODUITS(numproduit, nom, prixht, quantite, nomcatalogue, nomcategorie) VALUES (sq_produits.NEXTVAL, p_nomProduit, p_prixht, p_quantite, p_nomCatalogue, p_nomCategorie);
END;

CALL ajouterCatalogue('BibliothequeEtudiant');
CALL ajouterCategorie('Livres de collection', 5.5);
CALL ajouterProduit('XPallejing', 10000, 1, 'BibliothequeEtudiant', 'Livres de collection');
COMMIT;

