package app.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.FaceX;
import app.helper.Requests;


public class AttributeFrame extends JFrame {

	private JPanel contentPane;
	public JLabel image_label;
	public JTextPane responseText;
	
	public AttributeFrame() throws IOException {
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setAlwaysOnTop(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1200, 650);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Face Attributes");
		lblNewLabel.setBackground(Color.LIGHT_GRAY);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblNewLabel.setBounds(10, 11, 1164, 50);
		contentPane.add(lblNewLabel);
		
		image_label = new JLabel("");
		image_label.setBackground(Color.WHITE);
		image_label.setBounds(20, 72, 636, 470);
		contentPane.add(image_label);
		
		JLabel age = new JLabel("Age: ");
		age.setFont(new Font("Tahoma", Font.PLAIN, 15));
		age.setBounds(20, 553, 206, 47);
		contentPane.add(age);
		
		JLabel gender = new JLabel("Gender: ");
		gender.setFont(new Font("Tahoma", Font.PLAIN, 15));
		gender.setBounds(244, 553, 206, 47);
		contentPane.add(gender);
		
		JLabel gender_confidence = new JLabel("Gender confidence: ");
		gender_confidence.setFont(new Font("Tahoma", Font.PLAIN, 15));
		gender_confidence.setBounds(450, 553, 206, 47);
		contentPane.add(gender_confidence);
		
		responseText = new JTextPane();
		responseText.setEditable(false);
		responseText.setBackground(Color.LIGHT_GRAY);
		JScrollPane jsp=new JScrollPane(responseText);
		jsp.setBounds(666, 72, 508, 470);
		contentPane.add(jsp);
		
		File f =  new File("D:\\Java Projects\\Workspace\\FaceX\\"+FaceX.getCurrentPicName());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		byte[] encodedBytes =Base64.getEncoder().encode(fileContent);    
		String urlParameters=new String(encodedBytes);
		HashMap<String, String> map=new HashMap<>();
		map.put("image_attr", urlParameters);
		String responseString=new Requests().makePostRequest("http://www.facexapi.com/get_image_attr?face_det=1", map);
		responseText.setText(responseString); 
		
		ImageIcon icon = new ImageIcon(f.toString()); 
	    Image imageIcon = icon.getImage();
	    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg);
	    image_label.setIcon(icon);
		
		JSONParser parser = new JSONParser(); 
		try {
			
			JSONObject json = (JSONObject) parser.parse(responseString);
			JSONObject face=(JSONObject) json.get("face_id_0");
			age.setText(gender.getText()+face.get("age"));
			gender.setText(gender.getText()+face.get("gender"));
			gender_confidence.setText(gender_confidence.getText()+face.get("gender_confidence"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}



