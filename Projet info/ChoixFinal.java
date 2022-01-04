import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.util.*;

public class ChoixFinal extends JFrame {
	
	public ChoixFinal(Recette rc){
		
		
		
		//récuperer la dimension de l'écran
		Dimension tailleMoniteur = Toolkit.getDefaultToolkit().getScreenSize();
		int longueur = tailleMoniteur.width*1/2;
		int hauteur = tailleMoniteur.height*1/2;
		setSize(longueur, hauteur+50);
		setTitle("Recette choisie");
		setResizable(false);
		setLocation(tailleMoniteur.width/2-longueur/2, tailleMoniteur.height/2-hauteur/2);
		setLayout(null);
		getContentPane().setBackground(new Color(245,245,190));
		
		
		//titre
		
		JLabel titreRecette = new JLabel();
		titreRecette.setFont(new Font("Arial",Font.PLAIN,22));
		titreRecette.setText(rc.nom);
		titreRecette.setBounds(longueur*6/15,hauteur*2/16, longueur*1/2, hauteur*1/16);
		titreRecette.setHorizontalAlignment(JTextField.CENTER);
		this.add(titreRecette);
		
		//Photo recette 
		JLabel photoRecette = new JLabel(new ImageIcon(rc.image.getImage().getScaledInstance(longueur*4/15, hauteur*6/16, Image.SCALE_DEFAULT)));
		photoRecette.setBounds(longueur*1/15, hauteur*1/16, longueur*4/15, hauteur*6/16);
		this.add(photoRecette);
		
		//recette
		JTextArea text = new JTextArea (rc.texte);
		JScrollPane scroll = new JScrollPane(text);
		scroll.setBounds(longueur*6/15, hauteur*1/4, longueur*16/30, hauteur*11/16);
        text.setLineWrap(true);
        text.setEditable(false);
		this.add(scroll);
		
		//ingredients et quantités
		String ingr = "";
		int nbingr= rc.ingredients.size();
		JTextArea ingr2 = new JTextArea("");

		for (int i=0; i<nbingr ; i++){
			
			ingr2.append(rc.ingredients.get(i) +" : " + rc.quantite.get(i) +"\n");
		}
		ingr2.setEditable(false);
		JScrollPane scroll2 = new JScrollPane(ingr2);
		scroll2.setBounds(longueur*1/15, hauteur*1/2, longueur*4/15, hauteur*7/16);
		this.add(scroll2);
			
		
		this.setVisible(true);
	}
}


