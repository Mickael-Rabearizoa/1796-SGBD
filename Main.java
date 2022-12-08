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
            Racine racine = new Racine();
            Grammaire gram = racine.getGram();

            Scanner sc = new Scanner(System.in);
            
            boolean end = false;
            
            while(end == false){
                String req = sc.nextLine();
                Scanner scan = new Scanner(req);
                Vector request = new Vector();
                while(scan.hasNext()){
                    request.add(scan.next());
                }

                if(request.isEmpty()){

                } else {
                    String quit = String.valueOf(request.get(0)); 
                    if(quit.compareToIgnoreCase("quit") == 0){
                        end = true;
                    } else {
                        try {
                            Relation table = gram.traitementReq(request);
                            table.affiche();
                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.println(e.getMessage());
                        }
                    }
                }
            }
        } catch (Exception e) {
            // e.getMessage();
            e.printStackTrace();
        }
    }
}