package relation;
import java.util.Vector;

import relation.Ligne;
public class Relation{
    Vector lignes = new Vector();
    public Relation() {

    }
    public void add(Object ligne) {
        this.lignes.add(ligne);
    }
    public Ligne get(int index) {
        return (Ligne)(this.lignes.get(index));
    }
    public Vector getAll() {
        return this.lignes;
    }
    public int size() {
        return this.lignes.size();
    }
    public void setElemetAt(Ligne line ,int index){
        this.lignes.setElementAt(line, index);
    }
    public void affiche(){
        this.get(0).afficheNomCol();
        for(int i=0;i<this.lignes.size();i++){
            this.get(i).afficheValue();
        }
    }
}