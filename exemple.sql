-- ce fichier contient des exemples pour les syntaxes

-- creation de base de donnee
create database test 

-- suppression de base de donnees
drop database test

-- utilisation de base de donnee
use database test

-- creation table 
    -- creation de table personne 
    create table personne with idPersonne:int nom:varchar prenom:varchar
    -- creation de table emp
    create table emp with idEmp:int nom:varchar prenom:varchar salaire:double
    -- creation de table etudiant
    create table etudiant with idEtudiant:int nom:varchar prenom:varchar

-- suppression de table
drop table personne

-- insertion de donnees
    -- insertion dans la table personne 
    insert into personne values idPersonne:1 nom:Razanamamy prenom:Jean
    insert into personne values idPersonne:2 nom:Razaka prenom:Johany
    -- insertion dans la table emp
    insert into emp values idEmp:1 nom:Andriamanana prenom:Jean salaire:2000.500

-- selection de donnees
select * from personne where prenom = Jean
select * from personne where prenom like a -- les prenoms qui contiennent la lettre 'a'

-- suppression de donnees
    -- tout effacer
    delete from personne
    -- selectionner les lignes a effacer
    delete from personne where nom = Razaka
    delete from personne where nom like a

-- projection
select nom prenom from personne

-- jointure 
select * from personne emp join on prenom

-- intersection
select * from etudiant personne intersect

-- union
select * from etudiant personne union

-- produit cartesien
select * from etudiant emp prod

-- soustraction 
select * from personne etudiant soustraction

