import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Graphics ;
import java.util.*;

public class FenetreChoixRecette extends JFrame implements ActionListener{
	
	public LinkedList <Recette> listeRecette;
	private JButton [] lesBoutons = new JButton [5];//{monBouton1,monBouton2,monBouton3,monBouton4,monBouton5,monBouton6,monBouton7,monBouton8,monBouton9,monBouton10};
	private JPanel [] lesBoites = new JPanel [5];//{maBoiteRecette1,maBoiteRecette2,maBoiteRecette3,maBoiteRecette4,maBoiteRecette5,maBoiteRecette6,maBoiteRecette7,maBoiteRecette8,maBoiteRecette9,maBoiteRecette10};
	private JLabel [] lesPhotos = new JLabel [5];
	private JLabel [] lesTitres = new JLabel [5];
	private JLabel [] lesApports = new JLabel [5];
	private JLabel [] lesCalories = new JLabel [5];
    private JLabel erreurRecettes;
    private JPanel boiteErreurRecettes;
	private JButton PSuivante;
	private JButton PPrecedente;
	private JButton FPrecedente;
	private int a;
	private int b;
	private int longueur;
	private int hauteur;
	private int caloriesJournaliere;
	
	public FenetreChoixRecette(LinkedList <Recette> recettes, int calories){
		caloriesJournaliere=calories;
		Dimension tailleMoniteur= Toolkit.getDefaultToolkit().getScreenSize();
		longueur = tailleMoniteur.width*1/2;
		hauteur = tailleMoniteur.height*3/4;
		int longueurB = longueur*1/4;
		int hauteurB = hauteur*1/13;
		a=0;
		b=5;
        listeRecette=recettes;
		
		setTitle("Recettes possibles selon vos critères");
		// dimensionnement et affichage de la fenêtre
		setSize(longueur,hauteur);
		setLocation(tailleMoniteur.width/2-longueur/2, tailleMoniteur.height/2-hauteur/2);
		// quitter le programme lorsqu'on ferme la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
        
		PSuivante=new JButton ("Page Suivante");
		PSuivante.setBounds (longueur*17/24,hauteur*81/96,longueurB,hauteurB);
		PSuivante.addActionListener(this);
		add(PSuivante);
		PPrecedente=new JButton ("Page Precedente");
		PPrecedente.setBounds (longueur*9/24,hauteur*81/96,longueurB,hauteurB);
		PPrecedente.addActionListener(this);
		add(PPrecedente);
		FPrecedente=new JButton ("Fenetre Precedente");
		FPrecedente.setBounds (longueur*1/24,hauteur*81/96,longueurB,hauteurB);
		FPrecedente.addActionListener(this);
		add(FPrecedente);
		verifB();
		creationBox();
        if(recettes.get(0).nom=="vide"){
            boiteErreurRecettes=new JPanel();
            boiteErreurRecettes.setLayout(null);
            //boiteErreurRecettes.setBounds(20,20,this.getContentPane().getWidth()-40,this.getContentPane().getHeight()-40);
            boiteErreurRecettes.setBounds(this.getWidth()/8,40,3*this.getWidth()/4,this.getHeight()/2);
            boiteErreurRecettes.setBackground(new Color (197,41,41));
            erreurRecettes=new JLabel();
            erreurRecettes.setText("<html> <p style='text-align:center'> Aucune recette ne remplit les conditions ! <br> Essayez avec un laxisme plus grand ! </p> </html>");
            erreurRecettes.setLayout(null);
            erreurRecettes.setBounds(20,20,boiteErreurRecettes.getWidth()-40,boiteErreurRecettes.getHeight()-40);
            erreurRecettes.setOpaque(true);
            erreurRecettes.setBackground(new Color (40,150,150));
            erreurRecettes.setForeground(Color.black);
            erreurRecettes.setFont(new Font("Serif", Font.BOLD, 20));
            erreurRecettes.setHorizontalAlignment(SwingConstants.CENTER);
            boiteErreurRecettes.add(erreurRecettes);
            this.add(boiteErreurRecettes);
        }
		
		setVisible(true);
	}
    
	public void verifB (){
		if (b>listeRecette.size()){
            b=listeRecette.size();
		}else {
			b=b;
        }
	}
	public void AetBsuivant (){
		if ((a+5)<listeRecette.size()){
            a=a+5;
            b=b+5;
            //System.out.print("ca marche aussi"+a+"  "+b);
            verifB();
        }
	}
	public void AetBprecedent (){
        //System.out.println(a);
		if (a!=0){
            a=a-5;
            b=a+5;
		}
	}
	public void creationBox (){
		for (int i=a;i<b;i++){
			Recette recette = listeRecette.get(i);
            //System.out.println(i%5);
			lesBoites[i%5]=new JPanel ();
			lesBoites[i%5].setLayout(null);
			lesBoites[i%5].setBounds(10,i*hauteur*1/6+10,longueur*13/14,hauteur*1/6-10);
			lesBoites[i%5].setBackground(new Color (40,150,150));
			lesBoutons[i%5]=new JButton ("choisir");
			lesBoutons[i%5].setBounds((longueur)*3/4,(hauteur*1/6-10)*3/4,(longueur)*1/6,(hauteur*1/6-10)*1/4);
			lesBoutons[i%5].addActionListener(this);
			lesBoites[i%5].add(lesBoutons[i%5]);	
			ImageIcon icone = new ImageIcon(recette.image.getImage().getScaledInstance(hauteur*1/6-10,hauteur*1/6-10,Image.SCALE_DEFAULT));
            lesPhotos[i%5]=new JLabel (icone);
			lesPhotos[i%5].setBounds(0,0,hauteur*1/6-10,hauteur*1/6-10);
			lesBoites[i%5].add(lesPhotos[i%5]);
			lesTitres[i%5]=new JLabel (listeRecette.get(i).nom);
			lesTitres[i%5].setBounds (hauteur*1/6,0,longueur-hauteur*1/6,hauteur*1/24);
			lesBoites[i%5].add(lesTitres[i%5]);
			//System.out.print(i);
			apportJournalier(i);
			lesApports[i%5].setBounds(hauteur*1/6,10,(longueur-20)*3/5,hauteur*1/6);
			lesBoites[i%5].add(lesApports[i%5]);
			lesCalories[i%5].setBounds(hauteur*1/6,0,(longueur-20)*3/5,hauteur*1/6);
			lesBoites[i%5].add(lesCalories[i%5]);
			if(listeRecette.get(0).nom!="vide"){
                add(lesBoites[i%5]);
            }
            
        }
    }
    public void modifierBox(){
        //System.out.println("b :"+b);
        for (int i=a;i<b;i++){
			Recette recette = listeRecette.get(i);
            //System.out.println(i%5);
            ImageIcon icone = new ImageIcon(recette.image.getImage().getScaledInstance(hauteur*1/6-10,hauteur*1/6-10,Image.SCALE_DEFAULT));
            lesPhotos[i%5].setIcon(icone);
			lesTitres[i%5].setText(listeRecette.get(i).nom);
			apportJournalier(i);
			
        }
        if(b%5 != 0){
            for(int i = b%5; i < 5 ; i++){
                lesBoites[i].setVisible(false);
            }
        } else {
            for(int i=0 ; i < 5 ; i++){
                lesBoites[i].setVisible(true);
            }
        }
    }
    public void apportJournalier (int ind){
        Recette recetteChoisie =listeRecette.get(ind);
        double reste=(double)recetteChoisie.calories/(double)caloriesJournaliere*100;
        int res = (int)reste;
        String str="soit "+res+"% de votre apport journalier";
        //System.out.print (str);
        lesApports[ind%5]=new JLabel(str);
        lesCalories[ind%5]=new JLabel ("cette recette représente "+listeRecette.get(ind).calories+" Kcal");
        //System.out.print ("cette recette représente "+listeRecette.get(ind).calories);
    }
    public void actionPerformed(ActionEvent e){
        int ind = a;
        if (e.getSource()== PSuivante) {
            AetBsuivant ();
            modifierBox ();
        }else if (e.getSource()==PPrecedente){
            AetBprecedent();
            modifierBox();
        }else if (e.getSource()==FPrecedente){
            a=0;
            b=5;
            verifB();
            dispose();
        }else if (e.getSource()==lesBoutons[0]){
            ChoixFinal vf = new ChoixFinal(listeRecette.get(ind));
            //System.out.println(listeRecette.get(ind).nom);
        }else if (e.getSource()==lesBoutons[1]){
            ind+=1;
            ChoixFinal vf = new ChoixFinal(listeRecette.get(ind));
        }else if (e.getSource()==lesBoutons[2]){
            ind+=2;
            ChoixFinal vf = new ChoixFinal(listeRecette.get(ind));
        }else if (e.getSource()==lesBoutons[3]){
            ind+=3;
            ChoixFinal vf = new ChoixFinal(listeRecette.get(ind));
        }else if (e.getSource()==lesBoutons[4]){
            ind+=4;
            ChoixFinal vf = new ChoixFinal(listeRecette.get(ind));
        }
    }
}
