package grammaire;

import java.util.Vector;

import relation.Relation;
import requete.FunctReq;

public class Requete{
    Vector mots;
    String action;
    public Requete(){
        this.mots=new Vector();
    }
    public Vector getMots() {
        return mots;
    }
    public Mot getMot(int index){
        return (Mot)(mots.get(index));
    }
    public Mot getMot(String syntaxe){
        for(int i=0;i<mots.size();i++){
            Mot mot = (Mot)(this.mots.get(i));
            if(syntaxe.compareToIgnoreCase(mot.getSyntaxe()) == 0){
                return mot;
            }
        }
        return null;
    }
    public void setMots(Vector mots) {
        this.mots = mots;
    }
    public void addMot(Mot mot){
        this.mots.add(mot);
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public int initializing(){
        for(int i=0;i<this.mots.size();i++){
            if(this.getMot(i).getAction() != null){
                return i;
            }
        }
        return -1;
    }
    public void initAction(){
        int index = this.initializing();
        this.action = this.getMot(index).getAction();
        for(int i=index+1;i<this.mots.size();i++){
            if(this.getMot(i).getAction() != null){
                this.action = this.action+" "+this.getMot(i).getAction();
            }
        }
    }
    public String[] intoString(Object[] list){
        String[] lString = new String[list.length];
        for(int i=0;i<list.length;i++){
            lString[i] = String.valueOf(list[i]);
        }
        return lString;
    } 
    public Relation query(Relation[] tableReq) throws Exception {
        FunctReq funct = new FunctReq();
        Relation result = new Relation();
        boolean niditra = false;
        try {
            if(this.action.contains("produit") == true){
                result = funct.produitCartesien(tableReq[0],tableReq[1]);
                niditra = true;
            }
            if(this.action.contains("intersection") == true){
                result = funct.intersection(tableReq[0],tableReq[1]);
                niditra = true;
            }
            if(this.action.contains("soustraction") == true){
                result = funct.soustraction(tableReq[0],tableReq[1]);
                niditra = true;
            }
            if(this.action.contains("union") == true){
                result = funct.union(tableReq[0],tableReq[1]);
                niditra = true;
            }
            if(this.action.contains("jointure") == true){
                Mot on = this.getMot("on");
                result = funct.jointure(tableReq[0],tableReq[1], on.getArg(0));
                niditra = true;
            }
            if(this.action.contains("division") == true){
                Mot division = this.getMot("division");
                result = funct.division(tableReq[0], tableReq[1], division.getArg(0));
            }
            if(this.action.contains("selection") == true){
                String nomCol = this.getMot("where").getArg(0);
                Mot option = new Mot();
                if(this.getMot("=") != null){
                    option = this.getMot("=");
                }
                if(this.getMot("like") != null){
                    option = this.getMot("like");
                }
                String filtre = this.getMot(option.getSyntaxe()).getArg(0);
                result = funct.selection(result, nomCol, filtre, option.getSyntaxe());
                niditra = true;
            }
            // System.out.println(niditra);
            if(niditra == true){
                // System.out.println(this.action);
                if(this.action.contains("projection") == true){
                    Mot select = this.getMot("select");
                    String[] nomCol = this.intoString(select.getArgs().toArray());
                    result = funct.projection(result, nomCol);
                    // System.out.println();
                }
            } else {  
                if(this.action.contains("projection") == true){
                    
                    Mot select = this.getMot("select");
                    String[] nomCol = this.intoString(select.getArgs().toArray());
                    // System.out.println(tableReq[0].get(0).get("nom"));
                    result = funct.projection(tableReq[0], nomCol);
                } else {
                    // System.out.println("allllllll");
                    result = tableReq[0];
                }
                
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw(e);
        }
        
    }
}