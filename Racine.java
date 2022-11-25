package dataBase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

public class Racine{
    Vector listBdd = new Vector();
    public Racine() throws Exception {
        try {
            initListBdd();
        } catch (Exception e) {
            throw e;
        }
        
    }
    public Vector getListBdd() {
        return listBdd;
    }
    public void setListBdd(Vector listBdd) {
        this.listBdd = listBdd;
    }
    public void initListBdd() throws Exception{
        File fichier = new File("dataBase/listBdd.txt");
        try(BufferedReader lecteur = new BufferedReader(new FileReader(fichier)))
        {

            String ligne = lecteur.readLine();
            
            while(ligne != null) {
                listBdd.add(ligne);
                ligne = lecteur.readLine();
                // System.out.println(ligne);
            }
            // System.out.println(ligne);
        }
        catch(Exception a)
        {
            a.printStackTrace();
            System.err.println(a);
            throw a;
        }
    }
    public void createDataBase(String dataBaseName) throws Exception {
        try {
            File fichier = new File("dataBase/listBdd.txt");
            FileWriter lier_fichier = new FileWriter(fichier , true);
            BufferedWriter ecrivain = new BufferedWriter(lier_fichier);
            ecrivain.write(dataBaseName);
            ecrivain.newLine();
            ecrivain.close();
            lier_fichier.close();
            this.initListBdd();
        } catch (Exception e) {
            // e.printStackTrace();
            throw e;
        }
    }
    public boolean checkBDExistence(String baseName){
        for(int i=0;i<this.listBdd.size();i++){
            if(baseName.compareToIgnoreCase(String.valueOf(this.listBdd.get(i))) == 0){
                return true;
            }
        }
        return false;
    }
    public Bdd useDataBase(String baseName) throws Exception {
        if(checkBDExistence(baseName) == false){
            throw new Exception("Base de donnees inexistante");
        }
        Bdd dataBase = new Bdd(baseName);
        return dataBase;
    }
    
}