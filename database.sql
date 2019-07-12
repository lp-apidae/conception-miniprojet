DROP TABLE PRODUITS;

CREATE TABLE PRODUITS (
	numproduit NUMBER(10),
	nom VARCHAR(50) CONSTRAINT nn_nom_produit NOT NULL,
	prixht NUMBER(12, 2) CONSTRAINT nn_prixht_produit NOT NULL,
	quantite NUMBER(10, 0) CONSTRAINT nn_quantite_produit NOT NULL,
	CONSTRAINT pk_produits PRIMARY KEY (numproduit),
	CONSTRAINT u_nom_produit UNIQUE(nom),
	CONSTRAINT prixht_produit CHECK (prixht > 0),
	CONSTRAINT quantite_produit CHECK (quantite >= 0)
);

GRANT ALL ON PRODUITS TO nicotb


CREATE SEQUENCE sq_produits START WITH 1 INCREMENT BY 1;


CREATE OR REPLACE PROCEDURE ajouterProduit(p_nomProduit IN PRODUITS.nom%TYPE, p_prixht IN PRODUITS.prixht%TYPE, p_quantite IN PRODUITS.quantite%TYPE) IS

BEGIN 
	INSERT INTO PRODUITS(numproduit, nom, prixht, quantite) VALUES (sq_produits.NEXTVAL, p_nomProduit, p_prixht, p_quantite);
END;

CALL ajouterProduit('XPallejing', 10000, 1);
COMMIT;

