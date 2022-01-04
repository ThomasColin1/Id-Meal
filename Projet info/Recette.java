import java.util.*;
import javax.swing.*;
import java.io.*;

public class Recette{
    public LinkedList <String> quantite;
    public LinkedList <String> ingredients;
    public ImageIcon image;
    public String nom;
    public String texte;
    public double calories;
    
    public Recette(String nom, String texte, LinkedList <String> ingredients, LinkedList <String> quantite, ImageIcon image)throws IOException{
        this.nom = nom;
        this.texte = texte;
        this.ingredients = ingredients;
        this.quantite = quantite;
        this.image = image;
        
        LinkedList <Integer> listeCalories = Gestion.Calories(ingredients);
        
        calories = 0;
        LinkedList<Integer> quantiteInt = Gestion.Conversion(quantite);
        for(int i = 0 ; i < ingredients.size() ; i++){
            calories+= listeCalories.get(i)* quantiteInt.get(i)/100;
        }
        
    
    }
    
  
    
    public boolean matching(LinkedList <String> listeFrigo, int laxisme){
        boolean match = false;
        int i = 0;
        for(String s : ingredients){
            for(String f : listeFrigo){
                if(f.equalsIgnoreCase(s)){
                    i++;
                }
            }
        }
        if(i + laxisme >= ingredients.size()){
            match = true;
        }
        return match;
    }
}
