import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.io.*; 
import java.util.*;

public class Refrigerateur extends JFrame implements ActionListener {
    private JButton retour;
    private JButton fruits;
    private JButton legumes;
    private JButton viandesPoissonsOeufs;
    private JButton condiments;
    private JButton produitsLaitiers;
    private JButton feculents;
    private JButton confiserie;
    private JButton liquides;
    private JButton divers;
    private JButton valider; // quand il appuie dessus, met à jour la liste frigo de l'utilisateur. 
    private JButton passerSuite; //quand il appuie dessus, ça passe au choix du type de repas, et au laxisme
    private JButton effacer;
    private JPanel panneau;
    private JPanel panneauPrincipal;
    private JPanel panneauSuivant;
    private JPanel panLegume;
    private JPanel panFruit;
    private JPanel panCondiment;
    private JPanel panConfiserie;
    private JPanel panDivers;
    private JPanel panFeculent;
    private JPanel panLaitier;
    private JPanel panViande;
    private JPanel panLiquide;
    private LinkedList<JCheckBox> liste;
    private LinkedList<String> frigoUser;
    private LinkedList <String> listeCondiment;
    private LinkedList <String> listeConfiserie;
    private LinkedList <String> listeDivers;
    private LinkedList <String> listeFeculent;
    private LinkedList <String> listeFruit;
    private LinkedList <String> listeLegume;
    private LinkedList <String> listeLaitier;
    private LinkedList <String> listeViande;
    private LinkedList <String> listeLiquide;
    private Users utilisateur;
    private JComboBox boxLaxisme;
    private ButtonGroup type;
    
    public Refrigerateur(Users utilisateur)throws IOException  {
        this.utilisateur = utilisateur;
        
        //Récupérer la dimension de l'écran
        Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
        int longueur = tailleMoniteur.width*3/5;
        int hauteur = tailleMoniteur.height*5/6;
        
        //Mise en place de la fenêtre
		setSize(longueur,hauteur);
        setLocation(tailleMoniteur.width/2-longueur/2, tailleMoniteur.height/2-hauteur/2);
        setTitle("Réfrigérateur de "+utilisateur.nom);
        setResizable(false);
        setLayout(null);
        panneauPrincipal = new JPanel(); //Panneau principal contenant le panneau des boutons aliments, et les boutons valider, retour, passé à la suite
        panneauPrincipal.setBounds(0,0, getWidth(),getHeight());
        //System.out.println(getInsets().bottom);
        panneauPrincipal.setLayout(null);
        this.add(panneauPrincipal);
        panneauPrincipal.setBackground(new Color(245,245,190));
        this.setVisible(true);
        
        //mise en place panneau suivant 
        panneauSuivant = new JPanel();
        panneauSuivant.setBounds(0,0,getWidth(),getHeight()*86/100);
        panneauPrincipal.add(panneauSuivant);
        panneauSuivant.setLayout(null);
        panneauSuivant.setBackground(new Color(245, 245, 190));
        panneauSuivant.setVisible(false);
        
        JTextField effort = new JTextField("Encore un dernier effort "+utilisateur.nom+"...");
        effort.setFont(new Font("Arial", Font.BOLD, 18));
        effort.setHorizontalAlignment(SwingConstants.CENTER); 
        effort.setBounds(getWidth()/10, getHeight()*9/100, getWidth()*80/100, 20);
        effort.setBackground(new Color(245,245,190));
        effort.setBorder(null);
        panneauSuivant.add(effort);
        
        JTextField choisirLaxisme = new JTextField("A combien d'ingrédients près voulez-vous cuisiner la recette :");
        choisirLaxisme.setFont(new Font("Arial", Font.PLAIN, 18));
        choisirLaxisme.setHorizontalAlignment(SwingConstants.CENTER); 
        choisirLaxisme.setBounds(getWidth()/10, getHeight()*21/100, getWidth()*80/100, 20);
        choisirLaxisme.setBackground(new Color(245,245,190));
        choisirLaxisme.setBorder(null);
        panneauSuivant.add(choisirLaxisme);
        String[] tableauLaxisme = {"0","1","2","3","4","5", "6", "7", "8", "9"};
        boxLaxisme = new JComboBox(tableauLaxisme);
        boxLaxisme.setBounds(getWidth()*3/8,getHeight()*29/100,getWidth()/4,30);
        panneauSuivant.add(boxLaxisme);
        
        //sélection du type de plat
        JTextField choisirCategorie = new JTextField("Quelle catégorie de plat vous fait envie :");
        choisirCategorie.setFont(new Font("Arial",Font.PLAIN,18));
        choisirCategorie.setHorizontalAlignment(SwingConstants.CENTER); 
        choisirCategorie.setBounds(getWidth()/4,getHeight()*45/100,getWidth()/2,20);
        choisirCategorie.setBackground(new Color(245,245,190));
        choisirCategorie.setBorder(null);
        panneauSuivant.add(choisirCategorie);
        

        JRadioButton vegetarien = new JRadioButton("végétarien");
        vegetarien.setBounds(getWidth()*70/100,getHeight()*54/100,getWidth()*20/100,20);
        vegetarien.setBackground(new Color(245,245,190));
        vegetarien.setFont(new Font("Arial", Font.BOLD, 12));
        JRadioButton dessert = new JRadioButton("dessert");
        dessert.setBounds(getWidth()*70/100,getHeight()*68/100,getWidth()*20/100,20);
        dessert.setBackground(new Color(245,245,190));
        JRadioButton poulet = new JRadioButton("poulet");
        poulet.setBounds(getWidth()*45/100,getHeight()*75/100,getWidth()*20/100,20);
        poulet.setBackground(new Color(245,245,190));
        JRadioButton boeuf = new JRadioButton("boeuf");
        boeuf.setBounds(getWidth()*45/100,getHeight()*61/100,getWidth()*20/100,20);
        boeuf.setBackground(new Color(245,245,190));
        JRadioButton agneau = new JRadioButton("agneau");
        agneau.setBounds(getWidth()*45/100,getHeight()*54/100,getWidth()*20/100,20);
        agneau.setBackground(new Color(245,245,190));
        JRadioButton porc = new JRadioButton("porc");
        porc.setBounds(getWidth()*45/100,getHeight()*68/100,getWidth()*20/100,20);
        porc.setBackground(new Color(245,245,190));
        JRadioButton fruitDeMer = new JRadioButton("fruit de mer");
        fruitDeMer.setBounds(getWidth()*16/100,getHeight()*75/100,getWidth()*20/100,20);
        fruitDeMer.setBackground(new Color(245,245,190));
        JRadioButton accompagnement = new JRadioButton("accompagnement");
        accompagnement.setBounds(getWidth()*16/100,getHeight()*68/100,getWidth()*20/100,20);
        accompagnement.setBackground(new Color(245,245,190));
        JRadioButton entrees = new JRadioButton("entrées");
        entrees.setBounds(getWidth()*16/100,getHeight()*61/100,getWidth()*20/100,20);
        entrees.setBackground(new Color(245,245,190));
        JRadioButton vegan = new JRadioButton("vegan");
        vegan.setBounds(getWidth()*70/100,getHeight()*61/100,getWidth()*20/100,20);
        vegan.setBackground(new Color(245,245,190));
        JRadioButton petitDejeuner = new JRadioButton("petit déjeuner");
        petitDejeuner.setBounds(getWidth()*16/100,getHeight()*54/100,getWidth()*20/100,20);
        petitDejeuner.setBackground(new Color(245,245,190));
        JRadioButton dive = new JRadioButton("divers");
        dive.setBounds(getWidth()*70/100,getHeight()*75/100,getWidth()*20/100,20);
        dive.setBackground(new Color(245,245,190));
        
        type = new ButtonGroup();
        type.add(vegetarien);
        type.add(dessert);
        type.add(poulet);
        type.add(boeuf);
        type.add(agneau);
        type.add(porc);
        type.add(fruitDeMer);
        type.add(accompagnement);
        type.add(entrees);
        type.add(vegan);
        type.add(petitDejeuner);
        type.add(dive);
        panneauSuivant.add(vegetarien);
        panneauSuivant.add(dessert);
        panneauSuivant.add(poulet);
        panneauSuivant.add(boeuf);
        panneauSuivant.add(agneau);
        panneauSuivant.add(porc);
        panneauSuivant.add(fruitDeMer);
        panneauSuivant.add(accompagnement);
        panneauSuivant.add(entrees);
        panneauSuivant.add(vegan);
        panneauSuivant.add(petitDejeuner);
        panneauSuivant.add(dive);

        
        
        //création des listes d'aliments;
        listeAliments();
        //Mise en place panneaux catégories et des boutons et images des aliments
        panneau = new JPanel(); //panneau contenant les boutons des aliments 
        panneau.setBounds(0,0,getWidth(),getHeight()*86/100);
        panneau.setBackground(new Color(245,245,190));
        panneau.setLayout(null);
        panneauPrincipal.add(panneau);

        
        legumes = new JButton ("Légumes");
        legumes.setBounds(getWidth()*37/100,getHeight()*22/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(legumes);
        legumes.addActionListener(this);
        ImageIcon imageLeg = new ImageIcon("légumes.jpg");
        JLabel leg = new JLabel(new ImageIcon(imageLeg.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        leg.setBounds(getWidth()*40/100,getHeight()*3/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(leg);
        
        
        viandesPoissonsOeufs = new JButton("Viandes / Poissons / Oeufs");
        viandesPoissonsOeufs.setBounds(getWidth()*7/100,getHeight()*50/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(viandesPoissonsOeufs);
        viandesPoissonsOeufs.addActionListener(this);
        ImageIcon imageVia = new ImageIcon("viande.jpg");
        JLabel via = new JLabel(new ImageIcon(imageVia.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        via.setBounds(getWidth()*10/100,getHeight()*31/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(via);
        
        fruits = new JButton ("Fruits");
        fruits.setBounds(getWidth()*7/100,getHeight()*22/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(fruits);
        fruits.addActionListener(this);
        ImageIcon imageFru = new ImageIcon("fruits.jpg");
        JLabel fru = new JLabel(new ImageIcon(imageFru.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        fru.setBounds(getWidth()*10/100,getHeight()*3/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(fru);
       
        condiments = new JButton ("Condiments");
        condiments.setBounds(getWidth()*37/100,getHeight()*77/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(condiments);
        condiments.addActionListener(this);
        ImageIcon imageCond = new ImageIcon("condiments.jpg");
        JLabel cond = new JLabel(new ImageIcon(imageCond.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        cond.setBounds(getWidth()*40/100,getHeight()*58/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(cond);
        
        produitsLaitiers = new JButton("Produits laitiers");
        produitsLaitiers.setBounds(getWidth()*37/100,getHeight()*50/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(produitsLaitiers);
        produitsLaitiers.addActionListener(this);
        ImageIcon imagePro = new ImageIcon("laitier.jpg");
        JLabel pro = new JLabel(new ImageIcon(imagePro.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        pro.setBounds(getWidth()*40/100,getHeight()*31/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(pro);
        
        feculents = new JButton("Féculents");
        feculents.setBounds(getWidth()*67/100,getHeight()*22/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(feculents);
        feculents.addActionListener(this);
        ImageIcon imageFec = new ImageIcon("féculents.jpg");
        JLabel fec = new JLabel(new ImageIcon(imageFec.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        fec.setBounds(getWidth()*70/100,getHeight()*3/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(fec);
        
        confiserie = new JButton("Confiseries");
        confiserie.setBounds(getWidth()*67/100,getHeight()*50/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(confiserie);
        confiserie.addActionListener(this);
        ImageIcon imageConf = new ImageIcon("confiseries.jpg");
        JLabel conf = new JLabel(new ImageIcon(imageConf.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        conf.setBounds(getWidth()*70/100,getHeight()*31/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(conf);
        
        liquides = new JButton("Liquides");
        liquides.setBounds(getWidth()*7/100,getHeight()*77/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(liquides);
        liquides.addActionListener(this);
        ImageIcon imageLiq = new ImageIcon("boisson.jpg");
        JLabel liq = new JLabel(new ImageIcon(imageLiq.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        liq.setBounds(getWidth()*10/100,getHeight()*58/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(liq);
        
        divers = new JButton("D'hivers");
        divers.setBounds(getWidth()*67/100,getHeight()*77/100,getWidth()*25/100, getHeight()*5/100);
        panneau.add(divers);
        divers.addActionListener(this);   
        ImageIcon imageDiv = new ImageIcon("divers.jpg");
        JLabel div = new JLabel(new ImageIcon(imageDiv.getImage().getScaledInstance(getWidth()*20/100, getHeight()*19/100, Image.SCALE_DEFAULT)));
        div.setBounds(getWidth()*70/100,getHeight()*58/100,getWidth()*20/100, getHeight()*19/100);
        panneau.add(div);     
        
        
        //Création panneaux des aliments et tous les JCheckBox 
        panLegume = new JPanel();
        panLegume.setBackground(new Color(245,245,190));
        panLegume.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeLegume = new LinkedList<JCheckBox>();
        for(String s : listeLegume){
            listeDeLegume.add(new JCheckBox(s));
        }
        panLegume.setLayout(null);
        panLegume = positionnerAliments(listeDeLegume, panLegume);
        panneauPrincipal.add(panLegume);
        panLegume.setVisible(false);
        
        panFruit = new JPanel();
        panFruit.setBackground(new Color(245,245,190));
        panFruit.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeFruit = new LinkedList<JCheckBox>();
        for(String s : listeFruit){
            listeDeFruit.add(new JCheckBox(s));
        }
        
        panFruit.setLayout(null);
        panFruit = positionnerAliments(listeDeFruit, panFruit);
        panneauPrincipal.add(panFruit);
        panFruit.setVisible(false);
        
        panCondiment = new JPanel();
        panCondiment.setBackground(new Color(245,245,190));
        panCondiment.setBounds(longueur*1/50, 0, longueur, hauteur*6/7);
        LinkedList <JCheckBox> listeDeCondiment = new LinkedList<JCheckBox>();
        for(String s : listeCondiment){
            listeDeCondiment.add(new JCheckBox(s));
        }
        panCondiment.setLayout(null);
        panCondiment = positionnerAliments(listeDeCondiment, panCondiment);
        panneauPrincipal.add(panCondiment);
        panCondiment.setVisible(false);
        
        panConfiserie = new JPanel();
        panConfiserie.setBackground(new Color(245,245,190));
        panConfiserie.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeConfiserie = new LinkedList<JCheckBox>();
        for(String s : listeConfiserie){
            listeDeConfiserie.add(new JCheckBox(s));
        }
        panConfiserie.setLayout(null);
        panConfiserie = positionnerAliments(listeDeConfiserie, panConfiserie);
        panneauPrincipal.add(panConfiserie);
        panConfiserie.setVisible(false);
        
        panViande = new JPanel();
        panViande.setBackground(new Color(245,245,190));
        panViande.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeViande = new LinkedList<JCheckBox>();
        for(String s : listeViande){
            listeDeViande.add(new JCheckBox(s));
        }
        panViande.setLayout(null);
        panViande = positionnerAliments(listeDeViande, panViande);
        panneauPrincipal.add(panViande);
        panViande.setVisible(false);
        
        panLiquide = new JPanel();
        panLiquide.setBackground(new Color(245,245,190));
        panLiquide.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeLiquide = new LinkedList<JCheckBox>();
        for(String s : listeLiquide){
            listeDeLiquide.add(new JCheckBox(s));
        }
        panLiquide.setLayout(null);
        panLiquide = positionnerAliments(listeDeLiquide, panLiquide);
        panneauPrincipal.add(panLiquide);
        panLiquide.setVisible(false);
        
        panLaitier = new JPanel();
        panLaitier.setBackground(new Color(245,245,190));
        panLaitier.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeLaitier = new LinkedList<JCheckBox>();
        for(String s : listeLaitier){
            listeDeLaitier.add(new JCheckBox(s));
        }
        panLaitier.setLayout(null);
        panLaitier = positionnerAliments(listeDeLaitier, panLaitier);
        panneauPrincipal.add(panLaitier);
        panLaitier.setVisible(false);
        
        panDivers = new JPanel();
        panDivers.setBackground(new Color(245,245,190));
        panDivers.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeDivers = new LinkedList<JCheckBox>();
        for(String s : listeDivers){
            listeDeDivers.add(new JCheckBox(s));
        }
        panDivers.setLayout(null);
        panDivers = positionnerAliments(listeDeDivers, panDivers);
        panneauPrincipal.add(panDivers);
        panDivers.setVisible(false);
        
        panFeculent = new JPanel();
        panFeculent.setBackground(new Color(245,245,190));
        panFeculent.setBounds(longueur*1/20, 0, longueur*9/10, hauteur*5/6);
        LinkedList <JCheckBox> listeDeFeculent = new LinkedList<JCheckBox>();
        for(String s : listeFeculent){
            listeDeFeculent.add(new JCheckBox(s));
        }
        panFeculent.setLayout(null);
        panFeculent = positionnerAliments(listeDeFeculent, panFeculent);
        panneauPrincipal.add(panFeculent);
        panFeculent.setVisible(false);
        
        //Bouton retour, valider, passer suite, vider frigo et liste de tous les Checkbox
        retour = new JButton(" << Retour");
        retour.setFont(retour.getFont().deriveFont(Font.BOLD));
        retour.setBounds(getWidth()*60/1000,getHeight()*87/100, getWidth()*4/20, getHeight()*6/100);
        panneauPrincipal.add(retour);
        retour.addActionListener(this);
        valider = new JButton("Valider");
        valider.setBounds(getWidth()*520/1000, getHeight()*87/100, getWidth()*4/20, getHeight()*6/100);
        panneauPrincipal.add(valider);
        valider.addActionListener(this);
        passerSuite = new JButton("Etape suivante >>");
        passerSuite.setBounds(getWidth()*750/1000, getHeight()*87/100, getWidth()*4/20, getHeight()*6/100);
        panneauPrincipal.add(passerSuite);
        passerSuite.addActionListener(this);
        effacer = new JButton ("Effacer la sélection");
        effacer.setBounds(getWidth()*29/100,getHeight()*87/100, getWidth()*4/20, getHeight()*6/100);
        panneauPrincipal.add(effacer);
        effacer.addActionListener(this);
        
        liste = new LinkedList<JCheckBox>(); //liste avec tous les JCheckBox
        liste.addAll(listeDeCondiment);
        liste.addAll(listeDeConfiserie);
        liste.addAll(listeDeDivers);
        liste.addAll(listeDeFeculent);
        liste.addAll(listeDeFruit);
        liste.addAll(listeDeLegume);
        liste.addAll(listeDeLaitier);
        liste.addAll(listeDeViande);
        liste.addAll(listeDeLiquide);
       
        frigoUser = new LinkedList<String>();
        frigoUser.add("eau");
        
    }
    
    
    //proposer le type de plat voulu, et le laxisme demandé
            //demander à gestion de créer les recettes en fonction du type de plat et du laxisme
            // créér la troisième fenêtre graphique en lui donnant une liste d'objet recettes
    public void actionPerformed (ActionEvent e){
        if (e.getSource()== legumes) {
            panLegume.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== fruits){
            panFruit.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== feculents){
            panFeculent.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== condiments){
            panCondiment.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== confiserie){
            panConfiserie.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== viandesPoissonsOeufs){
            panViande.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== produitsLaitiers){
            panLaitier.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== divers){
            panDivers.setVisible(true);
            panneau.setVisible(false);
        }
        if(e.getSource()== liquides){
            panLiquide.setVisible(true);
            panneau.setVisible(false);
        }
        
        if(e.getSource()==retour){
            panneau.setVisible(true);
            panLegume.setVisible(false);
            panCondiment.setVisible(false);
            panFeculent.setVisible(false);
            panFruit.setVisible(false);
            panLaitier.setVisible(false);
            panLiquide.setVisible(false);
            panViande.setVisible(false);
            panConfiserie.setVisible(false);
            panDivers.setVisible(false);
            valider.setVisible(true);
            effacer.setVisible(true);
            panneauSuivant.setVisible(false);
        }
        
        if(e.getSource()==valider){
            for(JCheckBox c : liste){
                if(c.isSelected()==true && !frigoUser.contains(c.getText())){ //ajoute à la liste frigo les aliments sélectionnés
                   frigoUser.add(c.getText());
               }
               if(c.isSelected() == false && frigoUser.contains(c.getText())){ //enlève de la liste frigo les aliments désélectionnés
                   frigoUser.remove(c.getText());
               }
            }
            utilisateur.modifierListeFrigo(frigoUser);
            
            //Retour au menu principal
            panneau.setVisible(true);
            panLegume.setVisible(false);
            panCondiment.setVisible(false);
            panFeculent.setVisible(false);
            panFruit.setVisible(false);
            panLaitier.setVisible(false);
            panLiquide.setVisible(false);
            panViande.setVisible(false);
            panConfiserie.setVisible(false);
            panDivers.setVisible(false);
        }
        
        if(e.getSource()==effacer){
            for(JCheckBox c : liste){
                c.setSelected(false);
            }
            frigoUser.clear();
            frigoUser.add("eau");
            utilisateur.modifierListeFrigo(frigoUser);
        }
        
        if(e.getSource()==passerSuite){
            if(panneauSuivant.isVisible()==false){ //passer à l'étape suivante pour demander le laxisme et la catégorie de plat
                panneau.setVisible(false);
                panLegume.setVisible(false);
                panCondiment.setVisible(false);
                panFeculent.setVisible(false);
                panFruit.setVisible(false);
                panLaitier.setVisible(false);
                panLiquide.setVisible(false);
                panViande.setVisible(false);
                panConfiserie.setVisible(false);
                panDivers.setVisible(false);
                valider.setVisible(false);
                effacer.setVisible(false);
                panneauSuivant.setVisible(true);
                                 
            } else if(panneauSuivant.isVisible()==true){ // passer à l'étape suivante d'appeler méthodes de gestion et créer la troisième fenêtre
                // récupérer la catégorie de plat sélectionné
                int i = 0;
                String categorie = "";
                for(Enumeration <AbstractButton> buttons = type.getElements(); buttons.hasMoreElements();){
                    AbstractButton button = buttons.nextElement();
                    if (button.isSelected()) { 
                        categorie = button.getText(); 
                        i++;
                    } 
                }
                if(i==0){ //si aucune catégorie de plat n'a été sélectionné
                    JOptionPane.showMessageDialog(panneauSuivant,"Veuillez choisir une catégorie de plat");
                } else {
                    int laxisme = Integer.valueOf((String) boxLaxisme.getSelectedItem());
                    try{
                    LinkedList<Recette> recettesTriees = Gestion.RecetteBonne(categorie, utilisateur.listeFrigo, laxisme, utilisateur.apportCalo); 
                    FenetreChoixRecette fenetreListeRecette = new FenetreChoixRecette(recettesTriees, (int)utilisateur.apportCalo);
                    }catch (IOException f){
                    }
                  
                }  
            }
        }
    }
    
    public JPanel positionnerAliments(LinkedList<JCheckBox> aliments, JPanel pan) throws IOException{
        int i = 0;
        int j =0;
        int p =0;
        for(JCheckBox c : aliments){
            c.setBackground(new Color(245,245,190));
            c.setBounds(i, j, pan.getWidth()/4,20);
            pan.add(c);
            j+=c.getHeight();
            p++;
            if(p>= aliments.size()/4+1){
                i+= pan.getWidth()/4;
                j = 0;
                p=0;
            }
        }
        return pan;
    }

    public void listeAliments() throws IOException {
        listeCondiment= new LinkedList <String>();
        listeConfiserie= new LinkedList <String>();
        listeDivers= new LinkedList <String>();
        listeFeculent= new LinkedList <String>();
        listeFruit= new LinkedList <String>();
        listeLegume= new LinkedList <String>();
        listeLaitier= new LinkedList <String>();
        listeViande= new LinkedList <String>();
        listeLiquide= new LinkedList <String>();
        BufferedReader br = new BufferedReader(new FileReader("base_donnees.txt"));
        String res="";
        //String content =br.readLine();
        for (int a=1;a<10;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient 00"+a);
            int m =-1+content.indexOf("Categorie 00"+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
            }
			if (listeCondiment.contains(res)==false){
                listeCondiment.add(res);
            }
            res="";
        }
			//System.out.print(listeIng.toString());
	 
        for (int a=10;a<100;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient 0"+a);
            int m =-1+content.indexOf("Categorie 0"+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
            }
			if (listeCondiment.contains(res)==false){
                listeCondiment.add(res);
            }
            res="";
        }
			//System.out.print(listeIng.toString());
        for (int a=100;a<171;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeCondiment.contains(res)==false){
                listeCondiment.add(res);
            }
			res="";
        }
        
        for (int a=171;a<226;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeConfiserie.contains(res)==false){
                listeConfiserie.add(res);
            }
            res="";
        }
			
        for (int a=226;a<234;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeDivers.contains(res)==false){
                listeDivers.add(res);
            }
            res="";
        }
			
        for (int a=234;a<310;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeFeculent.contains(res)==false){
                listeFeculent.add(res);
            }
			res="";
        }
			
        for (int a=310;a<345;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeFruit.contains(res)==false){
                listeFruit.add(res);
            }
			res="";
        }
			
        for (int a=345;a<428;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeLegume.contains(res)==false){
                listeLegume.add(res);
            }
            res="";
        }
			
        for (int a=428;a<451;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeLiquide.contains(res)==false){
                listeLiquide.add(res);
            }
            res="";
        }
			
        for (int a=451;a<495;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeLaitier.contains(res)==false){
                listeLaitier.add(res);
            }
            res="";
        }
			
        for (int a=495;a<570;a++){
            String content =br.readLine();
            int l =18+content.indexOf("gr_ingredient "+a);
            int m =-1+content.indexOf("Categorie "+a);
            for(int i=l;i<m;i++){
                //System.out.print(new Character(content.charAt(i)));
                res=res+content.charAt(i);
			}
			if (listeViande.contains(res)==false){
                listeViande.add(res);
            }
            res="";
        }
    }
}


