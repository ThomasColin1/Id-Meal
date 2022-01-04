import java.net.URL;
import java.net.URLConnection;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.*;
import java.util.Locale;

public class Gestion {
    
    
    //Méthode principale de tri des recettes
    public static LinkedList <Recette> RecetteBonne(String type, LinkedList<String> frigo, int laxisme, double calories) throws IOException, InterruptedException{
        System.out.println("Loading Recepies...");
        int caloriesint=(int)calories;
        LinkedList <Recette> recettesBonnes=new LinkedList <Recette>();
        LinkedList <String> recettesType=IdType(type);
        Recette recetteTest;
        for (String id: recettesType){
            recetteTest=new Recette(LireNom(id), LireTexte(id), LireIng(id), LireQnt(id), LireImage(id));
            if (recetteTest.matching(creationFrigo(frigo), laxisme)) recettesBonnes.add(recetteTest); //On ajoute les recettes qui conviennent au frigo
        }
        LinkedList <Recette> recettesBonnesOrdre = new LinkedList <Recette>();
        
        if(recettesBonnes.size()==0){ //Si aucune recette ne convient, on renvoie quand meme une recette vide identifiable par son nom pour éviter les erreurs de liste vide lors de l'affichage
            LinkedList <String> vide = new LinkedList <String>();
            vide.add("0");
            ImageIcon imgicn=new ImageIcon("boisson.jpg");
            Recette recetteVide=new Recette("vide"," ",vide,vide,imgicn);
            recettesBonnes.add(recetteVide);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            return(recettesBonnes);
        }else{
            recettesBonnesOrdre=Triage(recettesBonnes, caloriesint); //On trie les recettes
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            return (recettesBonnesOrdre);
        }
        
    }
    
    
    //Méthode renvoyant la liste des identifiants de recettes d'une catégorie de repas
    public static LinkedList<String> IdType(String type) throws IOException{
        //On traduit le type de plat
        if (type=="végétarien") type="Vegetarian";
        if (type=="dessert") type="Dessert";
        if (type=="poulet") type="Chicken";
        if (type=="boeuf") type="Beef";
        if (type=="agneau") type="Lamb";
        if (type=="porc") type="Pig";
        if (type=="fruit de mer") type="Seafood";
        if (type=="accompagnement") type="Vegetarian";
        if (type=="entrées") type="Starter";
        if (type=="vegan") type="Vegan";
        if (type=="petit déjeuner") type="Breakfast";
        if (type=="divers") type="Miscellaneous";
        
        String s = (LecteurHtml.LireHtml("https://www.themealdb.com/api/json/v1/1/filter.php?c="+type)); //On met le texte html de la page dans la variable s
        
        LinkedList < String > listeId = new LinkedList < String > (); 
        int j=0;
        
        for (int a = 1; a < 100; a++) {
            String id = "";
            int taille = 0;
            int l = 9 + s.indexOf("idMeal",j); //La position de départ de lecture de l'id
            int m = -1 + s.indexOf("},{",j); //La position de fin de lecture de l'id
            
            
            for (int i = l; i < m; i++) {
                id = id + s.charAt(i); //On ajoute chaque charactère
                taille++;
            }
            if ((taille > 3)&&(!listeId.contains(id))) {
                listeId.add(id);
            }
            j=m+5; //Pour recommencer à lire plus loin que la position précédente
        }
        return (listeId);
    }
    
    
    //Méthode renvoyant le nom d'une recette
    //Même méthode de lecture que la précédente
    public static String LireNom(String Id) throws IOException{
        String s = (LecteurHtml.LireHtml("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+Id));

        String nom = "";
        int l = 10 + s.indexOf("strMeal");
        int m = -3 + s.indexOf("strDrinkAlternate");
            
            
        for (int i = l; i < m; i++) {
            nom = nom + s.charAt(i);
            
        }
        
        return (nom);
    }
    
    
    //Méthode renvoyant l'image d'une recette
    //Même méthode de lecture que la méthode précédente
    public static ImageIcon LireImage(String Id) throws IOException{
        String s = (LecteurHtml.LireHtml("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+Id));

        String url = "";
        int l = 15 + s.indexOf("strMealThumb");
        int m = -3 + s.indexOf("strTags");
            
            
        for (int i = l; i < m; i++) {
            if(s.charAt(i)!='\\') url = url + s.charAt(i);
            
        }
        
        URL u = new URL(url);
        Image image = ImageIO.read(u);
		ImageIcon imageicon = new ImageIcon(image);
        ImageIcon imageiconred = new ImageIcon(imageicon.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)); //Redimensionner l'image
        return (imageiconred);
    }
    
    //Méthode renvoyant le texte d'instructions d'une recette
    //Même méthode de lecture que la méthode précédente
    public static String LireTexte(String Id) throws IOException{
        String s = (LecteurHtml.LireHtml("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+Id));

        String texte = "";
        int l = 18 + s.indexOf("strInstructions");
        int m = -3 + s.indexOf("strMealThumb");
            
            
        for (int i = l; i < m; i++) {
            texte = texte + s.charAt(i);
            
        }
        
        return (texte);
    }
    
    
    //Méthode renvoyant la liste d'ingrédients d'une recette
    //Même méthode de lecture que la méthode précédente
    public static LinkedList < String > LireIng(String Id) throws IOException {
        
        String s = (LecteurHtml.LireHtml("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+Id));

        LinkedList < String > listeIng = new LinkedList < String > ();
        for (int a = 1; a < 20; a++) {
            String ing = "";
            int taille = 0;
            int m=0,l=0;
            if(a<10){ //Pour les ingrédients de numéro 1 à 10, le nom le précédent est moins grand
                l = 17 + s.indexOf("strIngredient" + a + "\"");
                m = -3 + s.indexOf("strIngredient" + (a + 1) + "\"");
            }
            if(a>=10){
                l = 18 + s.indexOf("strIngredient" + a + "\"");
                m = -3 + s.indexOf("strIngredient" + (a + 1) + "\"");   
            }
            
            
            for (int i = l; i < m; i++) {
                ing = ing + s.charAt(i);
                taille++;
            }
            if (taille > 1) {
                listeIng.add(ing.toLowerCase()); //On ajoute les ingrédients
            }
        }
        return listeIng;
        

    }
    
    
    //Méthode renvoyant une liste de quantités d'une recette
    //Même méthode de lecture que la méthode précédente
    public static LinkedList < String > LireQnt(String Id) throws IOException {
        String s = (LecteurHtml.LireHtml("https://www.themealdb.com/api/json/v1/1/lookup.php?i="+Id));
        
        LinkedList < String > listeQnt = new LinkedList < String > ();
        for (int a = 1; a < 20; a++) {
            String qnt = "";
            int taille = 0;
            int m=0,l=0;
            if(a<10){
                l = 14 + s.indexOf("strMeasure" + a + "\"");
                m = -3 + s.indexOf("strMeasure" + (a + 1) + "\"");
            }
            if(a>=10){
                l = 15 + s.indexOf("strMeasure" + a + "\"");
                m = -3 + s.indexOf("strMeasure" + (a + 1) + "\"");   
            }
            
            
            for (int i = l; i < m; i++) {
                qnt = qnt + s.charAt(i);
                taille++;
            }
            if (!((taille==1)&&(qnt.contains(" ")))&&(qnt!="")){
                listeQnt.add(qnt);
            }else listeQnt.add(" "); //Pour les quantités, on ajoute aussi les quantités vides car parfois il n'y a pas de quantités liées aux ingrédients
        }
        LinkedList <String> ing=LireIng(Id);
        java.util.List <String> liste=listeQnt.subList(0,ing.size()); //On recoupe les quantités pour faire la taille de la liste des ingrédients
        LinkedList <String> listeQntFinale=new LinkedList<String>();
        listeQntFinale.addAll(liste); //On retransforme la liste découpée en LinkedList
        return listeQntFinale;
    }
    
    //Méthode convertissant toutes les quantités d'une liste de quantités en grammes
    public static LinkedList <Integer> Conversion(LinkedList <String> listeQnt)throws IOException{
        
        LinkedList <Integer> listeQntInt = new LinkedList <Integer> ();
        
        for(String qnt:listeQnt){
            
            //On convertit les quantités cas par cas
            try{
            if(qnt.contains("Cups")){
                qnt=qnt=qnt.replace(" Cups", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(236/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(236/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(236/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(236/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*236);
            }else if(qnt.contains("cups")){
                qnt=qnt=qnt.replace(" cups", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(236/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(236/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(236/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(236/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*236);
            }else if(qnt.contains("cup")){
                qnt=qnt=qnt.replace(" cup", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(236/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(236/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(236/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(236/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*236);
            }else if(qnt.contains("Cup")){
                qnt=qnt=qnt.replace(" Cup", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(236/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(236/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(236/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(236/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*236);
            }else if(qnt.contains("pound")){
                qnt=qnt=qnt.replace(" pound", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(454/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(454/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(454/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(454/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*454);
            }else if(qnt.contains("pounds")){
                qnt=qnt.replace(" pounds", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(454/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(454/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(454/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(454/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*454);
            }
            else if(qnt.contains("handful")||qnt.contains("Handful")){
                if(qnt.contains("handful"))qnt=qnt.replace("handful", "118");
                if(qnt.contains("Handful"))qnt=qnt.replace("Handful", "118");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                listeQntInt.add(Integer.parseInt(qnt));
            }else if(qnt.contains("tsp")||qnt.contains("teaspoon")||qnt.contains("teaspoons")){
                if(qnt.contains("tsp"))qnt=qnt.replace(" tsp", "");
                if(qnt.contains("teaspoons"))qnt=qnt.replace(" teaspoons", "");
                if(qnt.contains("teaspoon"))qnt=qnt.replace(" teaspoon", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(5/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(5/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(5/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(5/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*5);
            }else if(qnt.contains("tb")||qnt.contains("tablespoon")){
                if(qnt.contains("tbsp"))qnt=qnt.replace(" tbsp", "");
                if(qnt.contains("tblsps"))qnt=qnt.replace(" tblsps", "");
                if(qnt.contains("tblsp"))qnt=qnt.replace(" tblsp", "");
                if(qnt.contains("tbs"))qnt=qnt.replace(" tbs", "");
                if(qnt.contains("tablespoons"))qnt=qnt.replace(" tablespoons", "");
                if(qnt.contains("tablespoon"))qnt=qnt.replace(" tablespoon", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(15/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(15/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(15/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(15/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*15);
            }else if(qnt.contains("can")||qnt.contains("Can")){
                if(qnt.contains("cans"))qnt=qnt.replace(" cans", "");
                if(qnt.contains("Cans"))qnt=qnt.replace(" Cans", "");
                if(qnt.contains("can"))qnt=qnt.replace(" can", "");
                if(qnt.contains("Can"))qnt=qnt.replace(" Can", "");
                if(qnt.contains(" "))qnt=qnt.replace(" ","");
                if(qnt.contains("\\u00bc")||qnt.contains("1\\/4")){
                    listeQntInt.add(227/4);
                }else if(qnt.contains("\\u00bd")||qnt.contains("1\\/2")){
                    listeQntInt.add(227/2);
                }else if(qnt.contains("\\u2153")||qnt.contains("1\\/3")){
                    listeQntInt.add(227/3);
                }else if(qnt.contains("\\u215B")||qnt.contains("1\\/8")){
                    listeQntInt.add(227/8);
                }else listeQntInt.add(Integer.parseInt(qnt)*227);
            }else if(qnt.contains("oz")&&qnt.contains("\\")) {
                qnt=qnt.substring(0,(qnt.indexOf("\\")));
                if(qnt.contains("g")){
                    qnt=qnt.replace("g", "");
                    listeQntInt.add(Integer.parseInt(qnt));
                }else listeQntInt.add(0);
            }else if(qnt.contains("ml")) listeQntInt.add(Integer.parseInt(qnt.substring(0,(qnt.indexOf("ml")))));
            else if(qnt.contains("L")) listeQntInt.add(1000*Integer.parseInt(qnt.substring(0,(qnt.indexOf("L")))));
            else if(qnt.contains("0g")||qnt.contains("5g")) listeQntInt.add(Integer.parseInt(qnt.substring(0,(qnt.indexOf("g")))));
            else if(qnt.contains("large"))listeQntInt.add(250*Integer.parseInt(qnt.substring(0,(qnt.indexOf(" large")))));
            else if(qnt.contains("small"))listeQntInt.add(150*Integer.parseInt(qnt.substring(0,(qnt.indexOf(" small")))));
            else if(qnt.contains("medium"))listeQntInt.add(200*Integer.parseInt(qnt.substring(0,(qnt.indexOf(" medium")))));
            else if(nombre(qnt))listeQntInt.add(200*Integer.parseInt(qnt));
            else listeQntInt.add(0); //Si aucun type de quantité n'est identifié
            }catch(NumberFormatException e){
                listeQntInt.add(0);
            }
        }
        return (listeQntInt);
    }
    
    
    //renvoie true si la chaine de charactère est un nombre, sert dans la méthode Conversion
    public static boolean nombre(String s) {
      if (s == null){
         return false;
      }
      for (int i = 0; i < s.length(); i++) {
         if ((Character.isLetter(s.charAt(i)) == true)) {
            return false;
         }
         if (s.charAt(i)==' ') {
            return false;
         }
      }
      return true;
   }
   
   //Méthode triant les recettes par nombre de calories, en fonction de laquelle est plus proche de l'objectif de calories
    public static LinkedList <Recette> Triage(LinkedList <Recette> listeRecette, int calories){
        
        LinkedList <Recette> trie = new LinkedList <Recette>();
        trie.add(listeRecette.get(0));
        int n=0;
        for(int i=1;i<listeRecette.size();i++){
            n=trie.size();
            for(int j=0;j<n;j++){
                if ((Math.abs(listeRecette.get(i).calories-calories)<Math.abs(trie.get(j).calories-calories))&&(!trie.contains(listeRecette.get(i)))) trie.add(j,listeRecette.get(i));   
            }
            if (!trie.contains(listeRecette.get(i))) trie.add(listeRecette.get(i));   
        }
        return trie;
    }
    
    
    //Méthode traduisant les ingrédients du frigo entrés en français en ingrédients en anglais pour l'identification
    public static LinkedList < String > creationFrigo(LinkedList < String > listeCoche) throws IOException {
        LinkedList < String > listeFrigo = new LinkedList < String > ();
        BufferedReader base = new BufferedReader(new FileReader("base_donnees.txt"));
        String res = "";
        String ing = "";
        for (int a = 1; a < 10; a++) {
            String content = base.readLine();
            int l = 18 + content.indexOf("gr_ingredient 00" + a);
            int m = -1 + content.indexOf("Categorie 00" + a);
            for (int i = l; i < m; i++) {
                res = res + content.charAt(i);
            }
            if (listeCoche.contains(res) == true) {
                int d = 15 + content.indexOf("Ingredient 00" + a);
                int f = -1 + content.indexOf("FRingredient 00" + a);
                for (int i = d; i < f; i++) {
                    ing = ing + content.charAt(i);
                }
                listeFrigo.add(ing);
            }
            res = "";
            ing = "";
        }

        for (int a = 10; a < 100; a++) {
            String content = base.readLine();
            int l = 18 + content.indexOf("gr_ingredient 0" + a);
            int m = -1 + content.indexOf("Categorie 0" + a);
            for (int i = l; i < m; i++) {
                res = res + content.charAt(i);
            }
            if (listeCoche.contains(res) == true) {
                int d = 15 + content.indexOf("Ingredient 0" + a);
                int f = -1 + content.indexOf("FRingredient 0" + a);
                for (int i = d; i < f; i++) {
                    ing = ing + content.charAt(i);
                }
                listeFrigo.add(ing);
            }
            res = "";
            ing = "";
        }
        for (int a = 100; a < 570; a++) {
            String content = base.readLine();
            int l = 18 + content.indexOf("gr_ingredient " + a);
            int m = -1 + content.indexOf("Categorie " + a);
            for (int i = l; i < m; i++) {
                res = res + content.charAt(i);
            }
            if (listeCoche.contains(res) == true) {
                int d = 15 + content.indexOf("Ingredient " + a);
                int f = -1 + content.indexOf("FRingredient " + a);
                for (int i = d; i < f; i++) {
                    ing = ing + content.charAt(i);
                }
                listeFrigo.add(ing);
            }
            res = "";
            ing = "";
        }
        return listeFrigo;
    }
    
    
    //Méthode récupérant le nombre de calories de chaque ingrédient de la recette depuis la base de données
    public static LinkedList < Integer > Calories(LinkedList < String > listeIng) throws IOException {
        LinkedList < Integer > Calories = new LinkedList < Integer > ();

        String res = "";
        String cal = "";
        int orie = 0;
        for (int ind = 0; ind < listeIng.size(); ind++) {
            BufferedReader base = new BufferedReader(new FileReader("base_donnees.txt"));
            for (int a = 1; a < 10; a++) {
                String content = base.readLine();
                int l = 15 + content.indexOf("Ingredient 00" + a);
                int m = -1 + content.indexOf("FRingredient 00" + a);
                for (int i = l; i < m; i++) {
                    res = res + content.charAt(i);
                }
                if (res.toLowerCase().equals(listeIng.get(ind))){
                    int d = 12 + content.indexOf("Calorie 00" + a);
                    int f = -1 + content.indexOf("gr_ingredient 00" + a);
                    for (int i = d; i < f; i++) {
                        cal = cal + content.charAt(i);

                    }
                    orie = Integer.parseInt(cal);

                    Calories.add(orie);

                    cal = "";
                }
                res="";
            }
            
            for (int a = 10; a < 100; a++) {
               
                String content = base.readLine();
                int l = 15 + content.indexOf("Ingredient 0" + a);
                int m = -1 + content.indexOf("FRingredient 0" + a);
                for (int i = l; i < m; i++) {
                    res = res + content.charAt(i);

                }
                if (res.toLowerCase().equals(listeIng.get(ind))) {
                    int d = 12 + content.indexOf("Calorie 0" + a);
                    int f = -1 + content.indexOf("gr_ingredient 0" + a);
                    for (int i = d; i < f; i++) {
                        cal = cal + content.charAt(i);
                        orie = Integer.parseInt(cal);
                    }
                    Calories.add(orie);

                    cal = "";
                }
                res="";
            }
           
            for (int a = 100; a < 570; a++) {
                
                String content = base.readLine();
                int l = 15 + content.indexOf("Ingredient " + a);
                int m = -1 + content.indexOf("FRingredient " + a);
                for (int i = l; i < m; i++) {
                    res = res + content.charAt(i);

                } 		
                if (res.toLowerCase().equals(listeIng.get(ind))) {
                    int d = 12 + content.indexOf("Calorie " + a);
                    int f = -1 + content.indexOf("gr_ingredient " + a);
                    for (int i = d; i < f; i++) {
                        cal = cal + content.charAt(i);
                    }
                    orie = Integer.parseInt(cal);
                    Calories.add(orie);

                    cal = "";
                }
                res = "";
            } 
            if (Calories.size()<ind){
            Calories.add(0);
            }
           
        }
         Calories.add(0);
        return Calories;
    }
    
}
