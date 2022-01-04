import java.util.*;

public abstract class Users {
    protected String nom;
    protected int age;
    protected int taille;
    protected int poids;
    protected double facteurActivite;
    protected String objectif;
    protected LinkedList <String> listeFrigo;
    protected double apportCalo ;
    
    
    public Users (String nom, int age,  int taille, int poids, String facteurAct, String objectif){
        this.nom = nom;
        this.age = age;
        this.taille = taille;
        this.poids = poids;
        if(facteurAct == "sédentaire"){
            this.facteurActivite = 1.2;
        } else if(facteurAct == "légèrement actif"){
            this.facteurActivite = 1.4;
        } else if(facteurAct == "modérément actif"){
            this.facteurActivite = 1.6;
        } else if(facteurAct == "très actif"){
            this.facteurActivite = 1.7;
        } else if(facteurAct == "extrèmement actif"){
            this.facteurActivite = 1.9;
        }
        this.objectif = objectif;
        listeFrigo = new LinkedList<String>();
        
    }
    
    public double imc(){
        return (this.poids/(Math.pow(this.taille, 2)/10000));
    }
    
    public void modifierListeFrigo(LinkedList<String> nouvelleListe){
        listeFrigo = nouvelleListe;
        
    }
    
    public String toString(){
        String res = this.nom+" : vous avez un IMC de : "+((int)this.imc());
        return res;
    }
    
    public abstract double apportCal();
    
    
    
}
