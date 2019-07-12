DROP TABLE PRODUITS;
DROP TABLE CATALOGUES;

CREATE TABLE CATALOGUES(
	nom VARCHAR(50),
	CONSTRAINT pk_catalogue PRIMARY KEY (nom)
);

CREATE TABLE PRODUITS (
	numproduit NUMBER(10),
	nom VARCHAR(50) CONSTRAINT nn_nom_produit NOT NULL,
	prixht NUMBER(12, 2) CONSTRAINT nn_prixht_produit NOT NULL,
	quantite NUMBER(10, 0) CONSTRAINT nn_quantite_produit NOT NULL,
	nomcatalogue VARCHAR(50) CONSTRAINT nn_nomcatalogue NOT NULL, 
	CONSTRAINT pk_produits PRIMARY KEY (numproduit),
	CONSTRAINT prixht_produit CHECK (prixht > 0),
	CONSTRAINT quantite_produit CHECK (quantite >= 0),
	CONSTRAINT fk_nomcatalogue FOREIGN KEY(nomcatalogue) REFERENCES CATALOGUES(nom) ON DELETE CASCADE
);

GRANT ALL ON PRODUITS TO nicotb;
GRANT ALL ON CATALOGUES TO nicotb;

CREATE SEQUENCE sq_produits START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE PROCEDURE ajouterCatalogue(p_nomCatalogue IN CATALOGUES.nom%TYPE) IS
BEGIN 
	INSERT INTO CATALOGUES(nom) VALUES (p_nomCatalogue);
END;

CREATE OR REPLACE PROCEDURE ajouterProduit(p_nomProduit IN PRODUITS.nom%TYPE, p_prixht IN PRODUITS.prixht%TYPE, p_quantite IN PRODUITS.quantite%TYPE, p_nomCatalogue IN PRODUITS.nomcatalogue%TYPE) IS
BEGIN 
	INSERT INTO PRODUITS(numproduit, nom, prixht, quantite, nomcatalogue) VALUES (sq_produits.NEXTVAL, p_nomProduit, p_prixht, p_quantite, p_nomCatalogue);
END;

CALL ajouterCatalogue('BibliothequeEtudiant');
CALL ajouterProduit('XPallejing', 10000, 1, 'BibliothequeEtudiant');
COMMIT;

