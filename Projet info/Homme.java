import java.util.*;
import javax.swing.*;

public class Homme extends Users{
    
    public Homme (String nom, int age, int taille, int poids, String facteurActivite, String objectif){
    super(nom, age, taille, poids, facteurActivite, objectif);
    apportCalo = apportCal();
    }
    
    
    
    public double apportCal(){
        double metabolisme = 0;
        double apport = 0;
        if(this.age > 12){
            metabolisme = 9.99*this.poids + 6.25*this.taille - 5*this.age + 5;
            apport = metabolisme * this.facteurActivite;
         }else if (this.age >=3 && this.age<= 12){
             switch(age){
                 case 3 : apport = 1200;
                 case 4 : apport = 1300;
                 case 5 : apport = 1400;
                 case 6 : apport = 1700;
                 case 7 : apport = 1900;
                 case 8 : apport = 2000;
                 case 9 : apport = 2100;
                 default : apport = 2200;
             }
 
        }
       if (objectif == "perte de poids"){
            apport = apport * 0.90; // représente une diminution de 10% de l'apport calorifique normal
        } else if(objectif == "prise de masse"){
            apport = apport * 1.1; //représente une augmentation de 10% de l'apport calorifique normal
        }
        return apport;
    }
    
    public String toString(){
        String res = super.toString();
        res += " et votre apport calorique journalier idéal est de : "+this.apportCalo+"Kcal";
        return res;
    }
}
