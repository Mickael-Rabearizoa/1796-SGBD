package dataBase;
import java.util.Vector;

import grammaire.Grammaire;
import relation.Ligne;
import relation.Relation;

import java.io.*;
public class Bdd{
    String baseName;
    Vector listRelations = new Vector();
    Grammaire gram;
    public Bdd(String baseName) {
        this.baseName=baseName;
        setListRelations();
        initGrammaire();
    }
    // public String[] getRelation(){
    //     File fichier = new File("dataBase/list");
    // }
    public void initGrammaire(){
        this.gram = new Grammaire(this);
    } 
    public Grammaire getGram() {
        return gram;
    }
    public void setGram(Grammaire gram) {
        this.gram = gram;
    }
    public String getBaseName() {
        return baseName;
    }
    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }
    public Vector getListRelations() {
        return listRelations;
    }
    public void setListRelations(){
        File fichier = new File("dataBase/"+baseName);
        File[] listFile = fichier.listFiles();
        for(int i=0;i<listFile.length;i++){
            String replaced = listFile[i].getName().replace(".",":");
            String[] splited = replaced.split(":");
            listRelations.add(splited[0]);
            // System.out.println(listRelations.get(i));
        }
    }
    public Ligne getLigne(String ligne){
        String[] listAtr = ligne.split(",");
        Vector nomCol = new Vector();
        Vector valeur = new Vector();
        Vector type = new Vector();
        for(int i=0;i<listAtr.length;i++) {
            String[] atr = listAtr[i].split(":");
            nomCol.add(atr[0]);
            valeur.add(atr[1]);
            type.add(atr[2]);
        }
        Ligne line = new Ligne(nomCol, valeur, type);
        return line;
    }

    public Relation getRelation(String relationName) throws Exception {
        File fichier = new File("dataBase/"+baseName+"/"+relationName+".txt");
        
        try(BufferedReader lecteur = new BufferedReader(new FileReader(fichier)))
        {
            Relation table = new Relation();

            String ligne = lecteur.readLine();
            
            while(ligne != null) {
                table.add(this.getLigne(ligne));
                ligne = lecteur.readLine();
                // System.out.println(ligne);
            }
            // System.out.println(ligne);
            return table;
        }
        catch(Exception a)
        {
            a.printStackTrace();
            System.err.println(a);
            throw a;
        }
    }

}