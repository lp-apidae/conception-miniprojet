# Projet en couches
Application Java simpliste permettant la gestion de catalogues & de produits dans le cadre du cours "Architecture logicielle" en licence professionnelle API-DAE à l'IUT de Montpellier.

### Contenu
  - Chaque partie du TP (parties 1 et 2, partie 3 et partie 4) est séparée dans un répertoire qui lui est propre
  - À l'intérieur du répertoire de chaque partie se trouve un répertoire "Sources" contenu le code Java de la partie, ainsi qu'un répertoire "Dosssier" contenant les différents fichiers demandés (diagrammes, script de création de la base de données relationnelle, fichiers XML...). Un rendu sous cette forme-ci a été préféré à un fichier pdf pour des raisons de lisibilité.

### Installation
- Créer sur votre SGBD favori la base de données grâce au fichier .sql fourni
- Importer sur votre EDI préféré la partie choisie
- Entrez les paramètres de votre base de données dans la classe dal.ProduitDAORelationnel pour les parties 1-2, ou dans la classe dal.BDRFactory pour les parties 3 et 4
- Compilez, lancez et profitez :)

### Utilisation
- Si vous souhaitez changer la méthode de stockage de données, pour passer d'un SGBD à un fichier XML, ou l'inverse, modifiez le code en conséquence dans la classe dal.StorageFactory pour les parties 1-2, ou dans la classe dal.BDManagerAbstractFactory pour les parties 3 et 4.

### Crédits
Bryan NICOT
Benjamin SUREAU

### License
(CC BY-NC-SA 3.0 FR)
https://creativecommons.org/licenses/by-nc-sa/3.0/fr/

----
> Keep it simple, stupid
