package JChat;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {
	public static void main(String[] args) {
		
		/*====================CLIENT====================*/
		try {
			
			Socket client = new Socket("127.0.0.1", 3000);
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintStream ps = new PrintStream(client.getOutputStream());
			String username = "Chat_Noir";
						
			
			
			//CREATION DE LA FENETRE
			JFrame jf = new JFrame("JChat Client");
			JPanel panneau = new JPanel();
			JTextField text = new JTextField();
			JTextArea area = new JTextArea("--- CHAT CLIENT");
			area.setText(area.getText() + "\nBienvenue " + username + "...");
			JScrollPane scroll = new JScrollPane(area);
			
			ps.println(username);
			
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
						area.setText("--- CHAT CLIENT");
					else {
						area.setText(area.getText() + "\n" + username + " >> " + text.getText());
						ps.println(text.getText());
					}
					text.setText("");
				}
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
			
			//LECTURE DES MESSAGES
			String line;
			
			while((line = br.readLine()) != null) {
				area.setText(area.getText() + "\n" + "SERVEUR >> " + line);
			}
			
			//client.close();
		
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		
	}
}
