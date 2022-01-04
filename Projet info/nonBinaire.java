import java.util.*;

public class nonBinaire extends Users {
    public nonBinaire(String nom, int age, int taille, int poids, String facteurActivite, String objectif){
        super(nom, age, taille, poids, facteurActivite, objectif);
        apportCalo = apportCal();
    }
    
     public double apportCal(){
        double metabolisme = 0;
        double apport = 0;
        if(this.age > 12){
            metabolisme = 9.99*this.poids + 6.25*this.taille - 5*this.age -78;
            apport = metabolisme * this.facteurActivite;
         }else if (this.age >=3 && this.age<= 12){
             switch(age){
                 case 3 : apport = 1150;
                 case 4 : apport = 1250;
                 case 5 : apport = 1400;
                 case 6 : apport = 1650;
                 case 7 : apport = 1750;
                 case 8 : apport = 1900;
                 case 9 : apport = 2050;
                 default : apport = 2150;
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
        res += " et votre apport calorique journalier idéal est de : "+apportCalo+"Kcal";
        return res;
    }
}
