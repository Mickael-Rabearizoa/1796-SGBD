package grammaire;

import java.util.Vector;

public class Mot{
    String syntaxe;
    Mot next;
    Mot prev;
    Vector args = new Vector();
    String action;
    public Mot(){

    }
    public Mot(String syntaxe){
        this.setSyntaxe(syntaxe);
        this.checkAction(syntaxe);
    }

    public void checkAction(String syntaxe){
        if(syntaxe.compareTo("where") == 0){
            this.setAction("selection");
        }
        if(syntaxe.compareTo("prod") == 0){
            this.setAction("produit");
        }
        if(syntaxe.compareTo("union") == 0){
            this.setAction("union");
        }
        if(syntaxe.compareTo("join") == 0){
            this.setAction("jointure");
        }
        if(syntaxe.compareTo("intersect") == 0){
            this.setAction("intersection");
        }
        if(syntaxe.compareTo("soustraction") == 0){
            this.setAction("soustraction");
        }
        if(syntaxe.compareTo("division") == 0){
            this.setAction("division");
        }
    }
    public String getSyntaxe() {
        return syntaxe;
    }
    public void setSyntaxe(String syntaxe) {
        this.syntaxe = syntaxe;
    }
    public Mot getNext() {
        return next;
    }
    public void setNext(Mot next) {
        this.next = next;
    }
    public Mot getPrev() {
        return prev;
    }
    public void setPrev(Mot prev) {
        this.prev = prev;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    public Vector getArgs() {
        return args;
    }
    public void setArgs(Vector args) {
        this.args = args;
    }
    public void addArg(String arg){
        this.args.add(arg);
    }
    public String getArg(int index){
        return String.valueOf(this.args.get(index));
    }
}