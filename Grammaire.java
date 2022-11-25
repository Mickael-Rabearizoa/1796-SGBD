package grammaire;

import java.io.File;
import java.rmi.server.ExportException;
import java.util.Vector;

import grammaire.Mot;
import grammaire.Requete;
import dataBase.Bdd;
import relation.Relation;

public class Grammaire{
    Bdd dataBase;
    String[] vocabulaire = new String[13];
    Requete requete;
    Relation[] tableReq;
    public Grammaire(Bdd dataBase){
        this.vocabulaire[0]="select";
        this.vocabulaire[1]="*";
        this.vocabulaire[2]="from";
        this.vocabulaire[3]="prod";
        this.vocabulaire[4]="join";
        this.vocabulaire[5]="union";
        this.vocabulaire[6]="intersect";
        this.vocabulaire[7]="soustraction";
        this.vocabulaire[8]="division";
        this.vocabulaire[9]="on";
        this.vocabulaire[10]="where";
        this.vocabulaire[11]="=";
        this.vocabulaire[12]="like";
        this.dataBase = dataBase;
    }
    public String[] getVocabulaire() {
        return vocabulaire;
    }
    public Bdd getData() {
        return dataBase;
    }
    public void setData(Bdd data) {
        this.dataBase = data;
    }
    public Requete getRequete() {
        return requete;
    }
    public void setRequete(Requete requete) {
        this.requete = requete;
    }
    public Relation[] getTableReq() {
        return tableReq;
    }
    public void setTableReq(Relation[] tableReq) {
        this.tableReq = tableReq;
    }
    public void initTableReq(Vector nomTable) throws Exception {
        this.tableReq = new Relation[nomTable.size()];
        for(int i=0;i<nomTable.size();i++){
            try {
                this.tableReq[i] = this.dataBase.getRelation(String.valueOf(nomTable.get(i)));
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            
        }
    }
    // public intitVocabulaire(){
    //     this.vocabulaire = new Mot[];
    // }
    public void addlistInVector(Vector splitedNomCol,String[] list){
        for(int i=0;i<list.length;i++){
            splitedNomCol.add(list[i]);
        }
    }
    public void trimer(String[] list){
        for(int i=0;i<list.length;i++){
            // System.out.println(list[i]);
            list[i] = list[i].trim();
        }
    }
    public Vector dashSplit(String list){
        Vector splitedNomCol = new Vector();
        String[] splited = list.split(",");
        this.trimer(splited);
        this.addlistInVector(splitedNomCol,splited);
        return splitedNomCol;
    }

    public void addColName(Vector allColName,String[] colName){
        for(int i=0;i<colName.length;i++){
            allColName.add(colName[i]);
        }
    }

    public Vector getAllColName(){
        Vector allColName = new Vector();
        for(int i=0;i<this.tableReq.length;i++){
            this.addColName(allColName, this.tableReq[i].get(0).getAllNomCol());
        }
        return allColName;
    }

    public boolean checkNomCol(String nomCol){
        Vector allColName = this.getAllColName();
        for(int i=0;i<allColName.size();i++){
            if(nomCol.compareToIgnoreCase(String.valueOf(allColName.get(i))) == 0){
                return true;
            }
        }
        return false;
    }
    public boolean checkListNomCol(Vector argsNomCol) throws Exception {
        for(int i=0;i<argsNomCol.size();i++){
            // System.out.println(argsNomCol.get(i));
            if(this.checkNomCol(String.valueOf(argsNomCol.get(i))) == false){
                throw new Exception("colonne '"+ String.valueOf(argsNomCol.get(i))+"' inexistante"); 
            }
        }
        return true;
    }
    
    public boolean checkVocabulaire(String mot){
        for(int i=0;i<this.vocabulaire.length;i++){
            if(mot.compareToIgnoreCase(this.vocabulaire[i]) == 0){
                return true;
            }
        }
        return false;
    }
    public boolean checkTableName(String tableName){
        for(int i=0;i<this.dataBase.getListRelations().size();i++){
            if(tableName.compareToIgnoreCase(String.valueOf(this.dataBase.getListRelations().get(i))) == 0){
                return true;
            }
        }
        return false;
    }
    public Vector getTableName()throws Exception{
        if(this.getRequete().getMot(0).getSyntaxe().compareToIgnoreCase("select") == 0){
            Vector nomTable = new Vector();
            Mot from = this.getRequete().getMot("from");
            if(from == null){
                throw new Exception("syntaxe manquante: 'from'");
            } else {
                if(from.getArgs().isEmpty() == false){
                    // System.out.println("niditra");
                    for(int i=0;i<from.getArgs().size();i++){
                        if(this.checkTableName(from.getArg(i)) == true){
                            nomTable.add(from.getArg(i));
                        } else {
                            throw new Exception("table '"+from.getArg(i)+"' inexistante");
                        }
                    }
                } else {
                    throw new Exception("requete incomplete: nom de table manquant");
                }
                return nomTable; 
            } 
        }
        return null;
    }
    public boolean checkSelectionValue(Mot like) throws Exception {
        if(like.getArgs().isEmpty() == true){
            throw new Exception("arguments manquantes pour la selection");
        } else {
            return true;
        }
    }
    public boolean checkWhereNext(Mot where) throws Exception {
        if(where.getArgs().isEmpty() == true){
            throw new Exception("nom de colonne manquante pour la selection");
        } else {
            if(where.getArgs().size() > 1){
                throw new Exception("trop d'arguments pour la selection");
            } else {
                if(this.checkNomCol(where.getArg(0)) == false){
                    throw new Exception("nom de colonne innexistante pour la selection");
                } else {
                    if(where.getNext().getSyntaxe().compareToIgnoreCase("=") != 0 && where.getNext().getSyntaxe().compareToIgnoreCase("like") != 0){
                        throw new Exception("Syntaxe manquante: 'like' ou '='");
                    } else {
                        return this.checkSelectionValue(where.getNext());
                    }    
                }    
            }
        }
    }
    public boolean checkJoinNext(Mot join) throws Exception {
        if(join.getNext().getSyntaxe().compareToIgnoreCase("on") != 0){
            throw new Exception("Syntaxe manquante: 'on'");
        }
        Mot on = join.getNext();
        if(on.getArgs().isEmpty() == true){
            throw new Exception("nom de colonne absente pour la jointure");
        } else {
            if(on.getArgs().size() > 1){
                throw new Exception("trop d'arguments pour la jointure");
            }
            else if(this.checkNomCol(on.getArg(0)) == false){
                throw new Exception("nom de colonne '"+on.getArg(0)+"' innexistante pour la jointure");
            } else {
                if(on.getNext() != null){
                    if(on.getNext().getSyntaxe().compareToIgnoreCase("where") != 0){
                        throw new Exception("placement incorrect de '"+on.getNext().getSyntaxe()+"'");
                    } else {
                        return this.checkWhereNext(on.getNext());
                    }
                }
                return true;
            }
        }
    }
    public boolean checkDivisionNext(Mot division) throws Exception {
        if(division.getArgs().isEmpty() == true){
            throw new Exception("nom de colonne absente pour la division");
        } else {
            if(division.getArgs().size() > 1){
                throw new Exception("trop d'argument pour la division");
            }
            else if(this.checkNomCol(division.getArg(0)) == false){
                throw new Exception("nom de colonne '"+division.getArg(0)+"' innexistante pour la division");
            } else {
                if(division.getNext() != null){
                    if(division.getNext().getSyntaxe().compareToIgnoreCase("where") != 0){
                        throw new Exception("placement incorrect de '"+division.getNext().getSyntaxe()+"'");
                    } else {
                        return this.checkWhereNext(division.getNext());
                    }
                }
                return true;
            }
        }
    }
    public boolean checkFunction(Mot fonction) throws Exception {
        if(fonction.getSyntaxe().compareToIgnoreCase("prod") != 0 && fonction.getSyntaxe().compareToIgnoreCase("union") != 0 && fonction.getSyntaxe().compareToIgnoreCase("intersect") != 0 && fonction.getSyntaxe().compareToIgnoreCase("soustraction") != 0){
            return false;
        }
        Mot from = fonction.getPrev();
        if(from.getArgs().size() != 2){
            throw new Exception("nombres de tables invalides pour la fonction:'"+fonction.getSyntaxe()+"'");
        }
        return true;
    }
    public boolean checkFromNext(Mot from) throws Exception {
        if(from.getNext() != null){
            if(from.getNext().getSyntaxe().compareToIgnoreCase("where") == 0){
                return this.checkWhereNext(from.getNext());
            }
            else if(from.getNext().getSyntaxe().compareToIgnoreCase("join") == 0){
                return this.checkJoinNext(from.getNext());
            } 
            else if(from.getNext().getSyntaxe().compareToIgnoreCase("division") == 0){
                return this.checkDivisionNext(from.getNext());
            }
            else if(this.checkFunction(from.getNext()) == true ){
                return true;
            } else {
                throw new Exception("placement incorrect de '"+from.getNext().getSyntaxe()+"'");
            }
        } else {
            if(from.getArgs().size() > 1){
                throw new Exception("trop d'argument pour 'from'");
            }
            from.setAction("getRelation");
            return true;
        }
        
    }
    public boolean checkUntilFrom() throws Exception {
        Mot select = this.requete.getMot(0);
            Mot from;
            if(select.getArgs().isEmpty() == false){
                // System.out.println("oh nooooo!");
                for(int i=0;i<select.getArgs().size();i++){
                    try {
                        this.checkListNomCol(select.getArgs());
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                }
                if(select.getNext().getSyntaxe().compareTo("from") != 0){
                    throw new Exception("syntaxe manquante: 'from'");
                }
                select.setAction("projection");
                from = select.getNext();
                // this.requete.setAction("projection");
                return this.checkFromNext(from);
            } else {
                Mot all = select.getNext();
                if(all.getSyntaxe().compareToIgnoreCase("*") != 0){
                    throw new Exception("placement incorrect de '"+all.getSyntaxe()+"'");
                }
                if(all.getArgs().isEmpty() == false){
                    throw new Exception("'from' attendue a la place de '"+all.getArg(0)+"'");
                }
                if(all.getNext().getSyntaxe().compareToIgnoreCase("from") != 0){
                    throw new Exception("syntaxe manquante: 'from'");
                }
                from = all.getNext();
                // this.requete.setAction("*");
                return this.checkFromNext(from);
            }
    }
    public boolean checkRequest() throws Exception {
        if(this.requete.getMot(0).getSyntaxe().compareToIgnoreCase("select") == 0){
            return this.checkUntilFrom();
        }
        return false;
    }
    //  affiche les actions
    public void checkAction(){
        for(int i=0;i<this.requete.getMots().size();i++){
            if(this.requete.getMot(i).getAction() != null){
                System.out.println(this.requete.getMot(i).getAction());
            }
        }
    }
    public void traitementReq(String request) throws Exception {
        // Vector splitedRequest = new Vector();
        // if(request.contains("from") == true){
        //     String[] firstSplit = request.split("from");
        //     String firstPart = firstSplit[0];
        //     String secondPart = firstSplit[1];

        //     String[] secondSplit = firstPart.split(" "); 
        //     String 
        //     // if(secondSplit[1].trim().compareToIgnoreCase("*") != 0){
        //     //     for(int i=1;i<secondSplit){

        //     //     }
        //     // }
        //     // secondSplit[]
        //     System.out.println(secondSplit[1]);
        // }
        this.requete=new Requete();
        String[] splited = request.split(" ");
        int nbMot = 0;
        for(int i=0;i<splited.length;i++) {
            boolean check = this.checkVocabulaire(splited[i]);
            if(check == true){
                Mot mot = new Mot(splited[i]);
                if(i != 0){
                    mot.setPrev(this.requete.getMot(nbMot-1));
                    this.requete.getMot(nbMot-1).setNext(mot);
                }
                this.requete.addMot(mot); 
                nbMot++;
            } else {
                // System.out.println(splited[i]);
                Vector args = this.dashSplit(splited[i]);
                if(i-1 >= 0){
                    for(int j=0;j<args.size();j++){
                        this.requete.getMot(nbMot-1).addArg(String.valueOf(args.get(j)));
                    }
                } else {
                    throw new Exception("syntaxe inconnue: '"+splited[i]+"'");
                }
            }
        }
        // Vector l = this.requete.getMot(0).getArgs();
        // for(int i=0;i<l.size();i++){
        //     System.out.println(l.get(i));
        // }
        Vector tableName = this.getTableName();
        if(tableName == null){
            throw new Exception("not select");
        }
        this.initTableReq(tableName);
        this.checkRequest();
        // this.checkAction();
        this.requete.initAction();
        // System.out.println(this.requete.getAction());
        Relation result = this.requete.query(tableReq);
        result.affiche();
        // System.out.println(result.get(0).get("nom"));
        System.out.println("ok");
    }
}