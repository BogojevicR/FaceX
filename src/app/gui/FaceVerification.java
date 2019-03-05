package app.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.http.message.BasicNameValuePair;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.FaceX;
import app.helper.DBConnector;
import app.helper.Requests;
import app.model.PersonModel;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FaceVerification extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public FaceVerification() throws IOException, SQLException {
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setAlwaysOnTop(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
		setBounds(100, 100, 1322, 706);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titleLabel = new JLabel("Face Verification");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		titleLabel.setBackground(Color.LIGHT_GRAY);
		titleLabel.setBounds(0, 11, 1306, 50);
		contentPane.add(titleLabel);
		
		JLabel responseLabel = new JLabel("");
		responseLabel.setBackground(Color.WHITE);
		responseLabel.setBounds(10, 72, 636, 470);
		contentPane.add(responseLabel);
		
		JLabel responseLabel1 = new JLabel("");
		responseLabel1.setBackground(Color.WHITE);
		responseLabel1.setBounds(656, 72, 636, 470);
		contentPane.add(responseLabel1);
		
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label.setBackground(Color.LIGHT_GRAY);
		label.setBounds(23, 606, 1269, 50);
		contentPane.add(label);
		
		File f =  new File("D:\\Java Projects\\Workspace\\FaceX\\"+FaceX.getCurrentPicName());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		byte[] encodedBytes =Base64.getEncoder().encode(fileContent);
		String urlParameters=new String(encodedBytes);
		HashMap<String, String> map=new HashMap<>();
		map.put("img_1", urlParameters);
		

		HashMap<PersonModel,Double> result=DBConnector.getInstance(). getData();
		for(Entry<PersonModel, Double> entry: result.entrySet()) {
			map.remove("img_2");
    		byte[] fileContent2 = Files.readAllBytes(new File(entry.getKey().getLink()).toPath());
    		byte[] encodedBytes2 =Base64.getEncoder().encode(fileContent2);
    		String urlParameters2=new String(encodedBytes2);
    		map.put("img_2", urlParameters2);

    		String responseString=new Requests().makePostRequest("http://www.facexapi.com/compare_faces?face_det=1", map);
    		System.out.println(responseString);
    		JSONParser parser = new JSONParser(); 
    		try {
    			JSONObject json = (JSONObject) parser.parse(responseString);
    			String confidence=(String) json.get("confidence");
    			System.out.println(entry.getKey().getFirst_name()+" "+ entry.getKey().getLast_name()+": "+confidence);
    			entry.setValue(Double.parseDouble(confidence));
    		} catch (ParseException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
		}
		PersonModel best=new PersonModel();
		double max=0;
		for(Entry<PersonModel, Double> entry: result.entrySet()) {
			if(entry.getValue()>max) {
				best=entry.getKey();
				max=entry.getValue();
			}
		}
		
		ImageIcon icon = new ImageIcon(f.toString()); 
	    Image imageIcon = icon.getImage();
	    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg);
	    responseLabel.setIcon(icon);
		
		if(max>0.5) {

	    
		ImageIcon icon1 = new ImageIcon(new File(best.getLink()).toString()); 
	    Image imageIcon1 = icon.getImage();
	    Image newimg1 = imageIcon1.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg1);
	    responseLabel1.setIcon(icon1);
		label.setText("Person: "+best.getFirst_name()+ " " +best.getLast_name());
		}else {
			label.setText("No match found!");
			responseLabel1.setBackground(Color.BLACK);
		}
	}
}
