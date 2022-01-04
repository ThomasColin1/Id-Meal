import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.*;
import java.io.*;

public class Informations extends JFrame implements ActionListener {
	
	protected JButton valider;
	protected ButtonGroup genre;
	protected ButtonGroup frAct;
	protected JComboBox poidsUt;
	protected JTextField nomUt;
	protected JComboBox tailleUt;
	protected JComboBox ageUt;
	protected ButtonGroup objf;
	protected Users utlst;

	public Informations(){
		
		//récuperer la dimension de l'écran
		Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
		int longueur = tailleMoniteur.width*1/3;
		int hauteur = tailleMoniteur.height*3/4;
		setSize(longueur, hauteur+50);
		setTitle("paramètres utilisateur");
		setResizable(false);
		setLocation(tailleMoniteur.width/2-longueur/2, tailleMoniteur.height/2-hauteur/2);
		setLayout(null);
	
		//création du conteneur principal
		
		JPanel conteneurPr = new JPanel();
		conteneurPr.setLayout(null);		
		conteneurPr.setBounds(0,0,longueur,hauteur);
		
		//création conteneur3
		JPanel conteneur3 = new JPanel();
		conteneur3.setLayout(null);
		int conthauteur = hauteur*1/4;
		conteneur3.setBounds(0,hauteur*3/4,longueur,conthauteur);
		conteneur3.setBackground(Color.orange);
		
		//choix genre
		JLabel sexe = new JLabel();
		sexe.setText("Vous êtes : ");
		sexe.setBounds(longueur*1/4, conthauteur*35/100, longueur*1/4, conthauteur*1/5);
		JRadioButton homme = new JRadioButton("un homme");
		homme.setBounds(longueur*1/2,conthauteur*15/100,longueur*1/4,conthauteur*1/5);
		JRadioButton femme = new JRadioButton("une femme");
		femme.setBounds(longueur*1/2,conthauteur*30/100,longueur*1/4, conthauteur*1/5);
		JRadioButton nonbinaire = new JRadioButton("non binaire");
		nonbinaire.setBounds(longueur*1/2,conthauteur*45/100,longueur*1/4,conthauteur*1/5);
		
		//bouton valider 
		valider = new JButton("valider");
		valider.setBounds(longueur*35/100,conthauteur*75/100 , longueur*1/4, conthauteur*1/5);	
		
		genre = new ButtonGroup();  
		genre.add(homme);
		genre.add(femme);
		genre.add(nonbinaire);
		
		
		
		//ajout dans le conteneur3
		conteneur3.add(sexe);
		conteneur3.add(homme);
		conteneur3.add(femme);
		conteneur3.add(nonbinaire);
		conteneur3.add(valider);
		
		
		//création conteneur1
		JPanel conteneur1 = new JPanel();
		conteneur1.setLayout(null);
		conteneur1.setBounds(0,0,longueur, conthauteur);
		conteneur1.setBackground(Color.cyan);
		
		
		//nom
		JLabel nom = new JLabel();
		nom.setText("Votre ptit nom :");
		nom.setBounds(longueur*1/4, conthauteur*1/9, longueur*4/11, conthauteur*1/5);
		nomUt = new JTextField();
		nomUt.setBounds(longueur*1/2, conthauteur*1/8, longueur*4/11, conthauteur*1/7);
		
		//poids
		JLabel poids = new JLabel();
		poids.setText("Poids (en kg) :");
		poids.setBounds(longueur*1/4, conthauteur*11/35, longueur*4/11, conthauteur*1/5);
		//poidsUt = new JTextField();
		//poidsUt.setBounds(240, 80, 150, 30);
		String [] listePds = {"choisir son poids","30" ,"31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160","161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176","177","178","179","180","181","182","183","184","185","186","187","188","189","190","191","192","193","194","195","196","197","198","199","200"};
		poidsUt = new JComboBox(listePds);
		poidsUt.setBounds(longueur*1/2,conthauteur*11/32,longueur*4/11,conthauteur*1/7);
		
		//taille
		JLabel taille = new JLabel();
		taille.setText("Taille (en cm) :");
		taille.setBounds(longueur*1/4, conthauteur*9/17, longueur*4/11, conthauteur*1/5);
		String [] listeTaille = {"choisir sa taille","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100","101","102","103","104","105","106","107","108","109","110","111","112","113","114","115","116","117","118","119","120","121","122","123","124","125","126","127","128","129","130","131","132","133","134","135","136","137","138","139","140","141","142","143","144","145","146","147","148","149","150","151","152","153","154","155","156","157","158","159","160","161","162","163","164","165","166","167","168","169","170","171","172","173","174","175","176","177","178","179","180","181","182","183","184","185","186","187","188","189","190","191","192","193","194","195","196","197","198","199","200","201","202","203","204","205","206","207","208","209","210","211","212","213","214","215","216","217","218","219","220","221","222","223","224","225","226","227","228","229","230","231","232","233","234","235","236","237","238","239","240","241","242","243","244","245","246","247","248","249","250"};
		tailleUt = new JComboBox(listeTaille);
		tailleUt.setBounds(longueur*1/2,conthauteur*9/16,longueur*4/11,conthauteur*1/7);
		
		//age
		JLabel age = new JLabel();
		age.setText("Age (en année): ");
		age.setBounds(longueur*1/4,conthauteur*25/34, longueur*4/11, conthauteur*1/5);
		String [] listeAge = {"choisir son age","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30" ,"31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99"};
		ageUt = new JComboBox(listeAge);
		ageUt.setBounds(longueur*1/2,conthauteur*25/32,longueur*4/11,conthauteur*1/7);
		
		// ajouts dans le conteneur1
		conteneur1.add(nom);
		conteneur1.add(nomUt);
		conteneur1.add(poids);
		conteneur1.add(poidsUt);
		conteneur1.add(taille);
		conteneur1.add(tailleUt);
		conteneur1.add(age);
		conteneur1.add(ageUt);
		
		//création conteneur2
		JPanel conteneur2 = new JPanel();
		conteneur2.setLayout(null);
		conteneur2.setBounds(0,hauteur*1/4,longueur,hauteur*1/4);
		conteneur2.setBackground(Color.green);
		
		//fréquence activité
		JLabel activite = new JLabel();
		activite.setText("Votre fréquence d'activité: ");
		activite.setBounds(longueur*2/16, conthauteur*1/2-conthauteur*1/14, longueur*4/10, conthauteur*1/7);
		JRadioButton jRB1 = new JRadioButton("sédentaire");
		jRB1.setBounds(longueur*1/2,conthauteur*1/20,longueur*4/11,conthauteur*23/100);
		JRadioButton jRB2 = new JRadioButton("légèrement actif");
		jRB2.setBounds(longueur*1/2,conthauteur*11/50,longueur*4/11,conthauteur*23/100);
		JRadioButton jRB3 = new JRadioButton("modérément actif");
		jRB3.setBounds(longueur*1/2,conthauteur*39/100,longueur*4/11,conthauteur*23/100);
		JRadioButton jRB4 = new JRadioButton("très actif");
		jRB4.setBounds(longueur*1/2,conthauteur*56/100,longueur*4/11,conthauteur*23/100);
		JRadioButton jRB5 = new JRadioButton("extrèmement actif");
		jRB5.setBounds(longueur*1/2, conthauteur*73/100,longueur*4/11,conthauteur*23/100);
		
		frAct = new ButtonGroup();  
		frAct.add(jRB1);
		frAct.add(jRB2);
		frAct.add(jRB3);
		frAct.add(jRB4);
		frAct.add(jRB5);
		
		//ajouts conteneur2
		conteneur2.add(activite);
		conteneur2.add(jRB1);
		conteneur2.add(jRB2);
		conteneur2.add(jRB3);
		conteneur2.add(jRB4);
		conteneur2.add(jRB5);
		
		//création conteneur4
		JPanel conteneur4 = new JPanel();
		conteneur4.setLayout(null);
		conteneur4.setBounds(0,hauteur*1/2,longueur,hauteur*1/4);
		conteneur4.setBackground(Color.yellow);
		
		//objEctif
		JLabel objectif = new JLabel();
		objectif.setText("Votre objectif: ");
		objectif.setBounds(longueur*1/5, conthauteur*1/2-conthauteur*1/14, longueur*4/10, conthauteur*1/7);
		JRadioButton obj1 = new JRadioButton("perte de poids");
		obj1.setBounds(longueur*1/2,conthauteur*3/20,longueur*4/11,conthauteur*2/7);
		JRadioButton obj2 = new JRadioButton("prise de masse");
		obj2.setBounds(longueur*1/2,conthauteur*7/20,longueur*4/11,conthauteur*2/7);
		JRadioButton obj3 = new JRadioButton("pas d'objectif spécial");
		obj3.setBounds(longueur*1/2,conthauteur*11/20,longueur*4/11,conthauteur*2/7);
		
		objf = new ButtonGroup();  
		objf.add(obj1);
		objf.add(obj2);
		objf.add(obj3);
		
		//ajouts conteneur2
		conteneur4.add(objectif);
		conteneur4.add(obj1);
		conteneur4.add(obj2);
		conteneur4.add(obj3);
		
		// ajouts dans le principal
		conteneurPr.add(conteneur1);
		conteneurPr.add(conteneur2);
		conteneurPr.add(conteneur3);
		conteneurPr.add(conteneur4);
		
		//ajout à fenetre1pr
		this.add(conteneurPr);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//ajout des écouteurs
		valider.addActionListener(this);
		homme.addActionListener(this);
		femme.addActionListener(this);
		nonbinaire.addActionListener(this);	
		ageUt.addActionListener(this);
		
	}
	
	public void actionPerformed(ActionEvent e){
		String frActivite = "";
		String objectif = "";
		String nom ="";
		int i= 0;
		int k=0;
		int j=0;
		String sex ="";
		
		for(Enumeration<AbstractButton> buttons = genre.getElements(); buttons.hasMoreElements();){
			AbstractButton button = buttons.nextElement();
			if (button.isSelected()){
				sex = button.getText();
				i++;
			}
		}
		for(Enumeration<AbstractButton> buttons2 = frAct.getElements(); buttons2.hasMoreElements();){
			AbstractButton button2 = buttons2.nextElement();
			if (button2.isSelected()){
				frActivite = button2.getText();
				k++;
			}
		}
		for(Enumeration<AbstractButton> buttons3 = objf.getElements(); buttons3.hasMoreElements();){
			AbstractButton button3 = buttons3.nextElement();
			if (button3.isSelected()){
				objectif = button3.getText();
				j++;
			}
		}
		//Si tout est bon, création d'un utilisateur sinon, apparition d'un message d'erreur
		if(	e.getSource()==valider && i!=0 && j!=0 && k!=0 && ageUt.getSelectedItem()!="choisir son age" && tailleUt.getSelectedItem()!="choisir sa taille" && poidsUt.getSelectedItem()!="choisir son poids" ){
			int pds = Integer.valueOf((String) poidsUt.getSelectedItem());
			int tlle = Integer.valueOf((String) tailleUt.getSelectedItem());
			int age = Integer.valueOf((String) ageUt.getSelectedItem());
            nom = (String) nomUt.getText();
			 //création des utilisateurs
			if (sex == "un homme"){
				utlst = new Homme (nom, age, tlle, pds, frActivite, objectif);
				JOptionPane.showMessageDialog(this, utlst.toString());
			
			}
			if (sex =="une femme"){
				utlst = new Femme (nom, age, tlle, pds, frActivite, objectif);
				JOptionPane.showMessageDialog(this, utlst.toString());
			}
			if (sex=="non binaire"){
				utlst = new nonBinaire (nom, age, tlle, pds, frActivite, objectif);
				JOptionPane.showMessageDialog(this, utlst.toString());
			}
			try{
				Refrigerateur frigo = new Refrigerateur(utlst);
			} catch(IOException f){
			}
			
			
		} else if ( e.getSource()==valider && (i==0 || j==0 || k==0 ||ageUt.getSelectedItem()=="choisir son age" || tailleUt.getSelectedItem()=="choisir sa taille" ||poidsUt.getSelectedItem()=="choisir son poids") ){
			String message = "Veuillez remplir ";
			if (ageUt.getSelectedItem()=="choisir son age"){
				message += "votre âge, ";
			}
			if (tailleUt.getSelectedItem()=="choisir sa taille"){
				message += "votre taille, ";
			}
			if (poidsUt.getSelectedItem()=="choisir son poids"){
				message += "votre poids, ";
			}
			if (i==0){
				message += "votre genre, ";
			} 
			if (k==0){
				message += "votre fréquence d'activité, ";
			} 	
			if (j==0){
				message += "votre objectif, ";
			}
		JOptionPane.showMessageDialog(this,message +" s'il vous plait!");
	}
	}
}
	
