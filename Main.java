import relation.Ligne;
import java.util.Vector;
import relation.Relation;
import requete.FunctReq;
import grammaire.Grammaire;
import java.io.File;

import dataBase.Bdd;
import dataBase.Racine;
import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        try {   
            // File file = new File("./toto");
            // System.out.println(file.exists());
            // Bdd data = new Bdd("initial");
            // Relation table = data.getRelation("emp");
            // Relation table2 = data.getRelation("etudiant");
            // FunctReq fonct = new FunctReq();
            // String[] nomCol = new String[2];
            // nomCol[0] = "age";
            // nomCol[1] = "salaire";


            // Relation result = fonct.selection(table, "age", "34", "=");
            // Relation result = fonct.projection(table,nomCol);
            // Relation result = fonct.produitCartesien(table, table2);
            // Relation result = fonct.jointure(table,table2,"age");
            // Relation result = fonct.union(table, table2);
            // Relation result = fonct.intersection(table, table2);
            // System.out.println(result.get(0).get(0));
            // System.out.println(result.get(1).get(0));

            // String request = "select nom, prenom, age from emp";
            // String s="fufu,";
            // System.out.println(s.split(",")[1]);
            // Grammaire g = new Grammaire();
            // g.traitementReq(s);
            Racine racine = new Racine();
            // racine.createDataBase("initial");
            // select * from inscription, cours division idCours
            Bdd dataBase = racine.useDataBase("initial");
            Grammaire gram = dataBase.getGram();
            Scanner sc = new Scanner(System.in);
            boolean end = false;
            String request = new String();
            while(end == false){
                request = sc.nextLine();
                if(request.compareToIgnoreCase("quit") == 0){
                    end = true;
                } else {
                    gram.traitementReq(request);
                }
            }
            // System.out.println(dataBase.getListRelations().get(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}