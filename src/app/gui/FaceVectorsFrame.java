package app.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.FaceX;
import app.helper.Requests;
import app.model.PersonOfflineModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;

import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import java.awt.Component;
import javax.swing.JTextPane;

public class FaceVectorsFrame extends JFrame {

	private JPanel contentPane;
	public JLabel title,label;
	public JTextPane textPane;
	public JScrollPane scrollPane;

	public FaceVectorsFrame() {	
		initialiseGUI();	
	}
	
	
	public void initialiseGUI() {
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1199, 606);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		title = new JLabel("Face Vectors");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 25));
		title.setBackground(Color.LIGHT_GRAY);
		title.setBounds(0, 11, 1184, 50);
		contentPane.add(title);
		
		label = new JLabel("");
		label.setBackground(Color.WHITE);
		label.setBounds(10, 72, 636, 470);
		contentPane.add(label);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBackground(Color.LIGHT_GRAY);
		textPane.setBounds(272, 132, 506, 468);
		
		scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(666, 72, 508, 470);
		contentPane.add(scrollPane);
	}

	public void onlineMode() throws ParseException, IOException {
		
		if(FaceX.getCurrentPicName() == null) {
			JOptionPane.showMessageDialog(null, "Must Take Picture First!");
			setVisible(false);
			this.dispose();
			throw new NullPointerException("Must Capture Picture First!");
		}
		
		
		File f =  new File(System.getProperty("user.dir")+"\\"+FaceX.getCurrentPicName());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		byte[] encodedBytes = Base64.getEncoder().encode(fileContent);
		String urlParameters = new String(encodedBytes);
		HashMap<String, String> map = new HashMap<>();
		map.put("img", urlParameters);
		
		String responseString=new Requests().makePostRequest("http://www.facexapi.com/get_face_vec?face_det=1", map);

		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(responseString);
		
			
		textPane.setText(responseString);
		
		ImageIcon icon = new ImageIcon(f.toString()); 
	    Image imageIcon = icon.getImage();
	    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg);
	    label.setIcon(icon);
	}
	
	public void offlineMode(PersonOfflineModel person) throws IOException, ParseException {
		
		File f= new File(person.getLink()+person.getFileName()+".jpg");
							
		textPane.setText(person.getVector());
		ImageIcon icon = new ImageIcon(f.toString()); 
	    Image imageIcon = icon.getImage();
	    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg);
	    label.setIcon(icon);
		
		
	}

}
