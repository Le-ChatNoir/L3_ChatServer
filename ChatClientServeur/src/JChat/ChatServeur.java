package JChat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.*;


public class ChatServeur {

		public static void main(String[] args) {
			ArrayList<PrintStream> liste = new ArrayList<PrintStream>();
			
			//CREATION DE LA FENETRE
			JFrame jf = new JFrame("JChat Serveur");
			JPanel panneau = new JPanel();
			JTextField text = new JTextField();
			JTextArea area = new JTextArea("--- CHAT SERVEUR");
			JScrollPane scroll = new JScrollPane(area);
			
			//REDEFINITION DE LA TAILLE
			jf.setBounds(100, 100, 400, 600);
			
			//DESACTIVER LA SAISIE FENETRE
			area.setEnabled(false);
			
			//TEXTE EN NOIR
			area.setDisabledTextColor(Color.BLACK);
			
			//AJOUT DES BORDURES
			panneau.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
			area.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			
			
			
			//TRAITEMENT DE LA TOUCHE ENTREE ET DU CLR
			text.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(text.getText().equals("cls"))
						area.setText("--- CHAT SERVEUR");
					else {
						area.setText(area.getText() + "\n" + "SERVEUR >> " + text.getText());
						for(PrintStream pstmp : liste) {
							pstmp.println(text.getText());
						}
					}
					text.setText("");
				}
				//envoi des messages
			});
			
			
			
			//AFFECTATION DU LAYER BORDURE LAYOUT AU BUREAU
			BorderLayout b1 = new BorderLayout();
			panneau.setLayout(b1);
			
			//AJOUT DU SCROLL, CHAMP DE TEXTE, ET PANNEAU
			panneau.add(scroll, BorderLayout.CENTER);
			panneau.add(text, BorderLayout.SOUTH);
			jf.getContentPane().add(panneau);
			
			//FERMETURE
			jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jf.setVisible(true);
			
			
			
			
			
			
			/*========================SERVEUR=========================*/
			
			try {
				
				ServerSocket serveur = new ServerSocket(3000);
				while(true) {
					Socket client = serveur.accept();
					Thread th = new Thread() {
						public void run() {
							try {
						
								String username;
								BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream())); //lecture
								PrintStream ps = new PrintStream(client.getOutputStream());	//ecriture
								//reception
								username = br.readLine();
								area.setText(area.getText() + "\n" + "BIENVENUE " + username + " !!");
							
								liste.add(ps);
								String line;
								
								while((line = br.readLine()) != null) {
									area.setText(area.getText() + "\n" + username + " >> " + line);
								}
								area.setText(area.getText() + "\n" + username + " s'est déconnecté...");
								
							} catch (IOException e2) {
								e2.printStackTrace();
								return;
							}
							
						}
					};
					th.start();
					//serveur.close();
					
				}
			} catch (IOException e ) {
				e.printStackTrace();
				return;	
			}
			
			
		}
}
