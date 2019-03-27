package app.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import app.FaceX;
import app.helper.DBConnector;
import app.helper.OfflineDatabase;
import app.helper.Requests;
import app.model.PersonModel;
import app.model.PersonOfflineModel;

public class FaceVerification extends JFrame {

	private JPanel contentPane;
	public JLabel responseLabel,responseLabel1,label,titleLabel;
	/**
	 * Create the frame.
	 * @throws IOException 
	 * @throws SQLException 
	 */
	public FaceVerification() {
		
		try {
			initialiseGUI();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}
	
	public void initialiseGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		

		UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");

		setAlwaysOnTop(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState( getExtendedState()|JFrame.MAXIMIZED_BOTH );
		setBounds(100, 100, 1322, 706);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		titleLabel = new JLabel("Face Verification");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		titleLabel.setBackground(Color.LIGHT_GRAY);
		titleLabel.setBounds(0, 11, 1306, 50);
		contentPane.add(titleLabel);
		
		responseLabel = new JLabel("");
		responseLabel.setBackground(Color.WHITE);
		responseLabel.setBounds(10, 72, 636, 470);
		contentPane.add(responseLabel);
		
		responseLabel1 = new JLabel("");
		responseLabel1.setBackground(Color.WHITE);
		responseLabel1.setBounds(656, 72, 636, 470);
		contentPane.add(responseLabel1);
		
		label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Tahoma", Font.PLAIN, 25));
		label.setBackground(Color.LIGHT_GRAY);
		label.setBounds(23, 606, 1269, 50);
		contentPane.add(label);
	}
	
	public void onlineMode() throws IOException, SQLException, ParseException {
		
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
		map.put("img_1", urlParameters);
		
		PersonModel best = new PersonModel();
		double max = 0;
		HashMap<PersonModel,Double> result=DBConnector.getInstance(). getData();
		for(Entry<PersonModel, Double> entry : result.entrySet()) {
			map.remove("img_2");
    		byte[] fileContent2 = Files.readAllBytes(new File(entry.getKey().getLink()).toPath());
    		byte[] encodedBytes2 = Base64.getEncoder().encode(fileContent2);
    		String urlParameters2 = new String(encodedBytes2);
    		map.put("img_2", urlParameters2);

    		String responseString = new Requests().makePostRequest("http://www.facexapi.com/compare_faces?face_det=1", map);
    		JSONParser parser = new JSONParser(); 
    		
			JSONObject json = (JSONObject) parser.parse(responseString);
			String confidence = (String) json.get("confidence");
			entry.setValue(Double.parseDouble(confidence));
			if(entry.getValue() > max) {
				best=entry.getKey();
				max=entry.getValue();
			}	
    	
		}

		ImageIcon icon = new ImageIcon(f.toString()); 
	    Image imageIcon = icon.getImage();
	    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg);
	    responseLabel.setIcon(icon);
		
		if(max > 0.5) {
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
	
	public void offlineMode(PersonOfflineModel person) throws IOException, ParseException {
		
		File f =  new File(person.getLink()+person.getFileName()+".jpg");
		ImageIcon icon = new ImageIcon(f.toString()); 
	    Image imageIcon = icon.getImage();
	    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg);
	    responseLabel.setIcon(icon);	
		
		double[] request = stringToDoubleArray(person.getVector());
		
		double best = 1;
		boolean match = false;
		double euclid;
		double[] res;
		for(PersonOfflineModel p: OfflineDatabase.persons) {		
			res = stringToDoubleArray(p.getVector());
			euclid = euclidDistance(request, res);
			if(euclid < best && euclid < 0.6 && euclid != 0) {	
				best = euclid;
				ImageIcon icon1 = new ImageIcon(new File(p.getLink()+p.getFileName()+".jpg").toString());
			    Image imageIcon1 = icon1.getImage();
			    Image newimg1 = imageIcon1.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			    icon1 = new ImageIcon(newimg1);
			    responseLabel1.setIcon(icon1);
				label.setText("Person: "+p.getFirst_name()+ " " +p.getLast_name());
				match = true;
			}
		}
		
		if( !match ) {
			label.setText("No match found!");
			responseLabel1.setBackground(Color.BLACK);
		}

		
	}
	
	public double[] stringToDoubleArray(String input) throws ParseException {
		
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(input);
		JSONObject vector = (JSONObject) json.get("vector");
		JSONArray array = (JSONArray) vector.get("__ndarray__");
		String stringArray = array.toString();
		stringArray = stringArray.substring(1, stringArray.length()-1);
		
		String[] returnString = stringArray.split(",");
		double[] res = new double[returnString.length];
		for(int i=0; i<res.length; i++) {
			res[i] = Double.parseDouble(returnString[i]);
		}
		
		return res;
	}
	
	public static double euclidDistance(double[] array1, double[] array2){
		double Sum = 0.0;	 
		for (int i=0;i<array1.length;i++) {
			Sum = Sum+Math.pow((array1[i]-array2[i]),2.0);
		}
		return Math.sqrt(Sum);
	}
	
}
