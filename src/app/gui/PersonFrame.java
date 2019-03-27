package app.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import app.FaceX;
import app.helper.DBConnector;
import app.helper.OfflineDatabase;
import app.helper.Requests;

public class PersonFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblAddNewPerson,lblIme,lblPrezime,label;
	private JButton btnAdd,btnLink;
	private JLabel lblKey;
	private JTextField textField_3;
	private File selectedFile;

	public PersonFrame() throws IOException {
		
	}

	
	 
	public void initialiseGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		textField = new JTextField();
		textField.setBounds(454, 70, 189, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblAddNewPerson = new JLabel("Add new Person to database:");
		lblAddNewPerson.setForeground(Color.BLACK);
		lblAddNewPerson.setFont(new Font("Arial", Font.BOLD, 19));
		lblAddNewPerson.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewPerson.setBounds(10, 11, 674, 14);
		contentPane.add(lblAddNewPerson);
		
		lblIme = new JLabel("First Name:");
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIme.setForeground(Color.BLACK);
		lblIme.setBounds(362, 76, 83, 14);
		contentPane.add(lblIme);
		
		lblPrezime = new JLabel("Last Name:");
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrezime.setForeground(Color.BLACK);
		lblPrezime.setBounds(362, 117, 83, 14);
		contentPane.add(lblPrezime);
		
		btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.WHITE);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().equals("") || textField_1.equals("") || textField_2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "All fields are required!");
					return;
				}
				String statement="INSERT INTO people(first_name,last_name,link) VALUES ('"+textField.getText()+"','"+textField_1.getText()+"','"+textField_2.getText().replace("\\","\\\\")+"')";
				if(!DBConnector.getInstance().executeStatement(statement)) {
					setVisible(false); 
					dispose();
				}
				
			}
		});
		btnAdd.setBounds(543, 193, 100, 30);
		contentPane.add(btnAdd);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(454, 111, 189, 30);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setColumns(10);
		textField_2.setBounds(454, 152, 189, 30);
		contentPane.add(textField_2);
		
		label = new JLabel("");
		label.setForeground(Color.WHITE);
		label.setBackground(Color.WHITE);
		label.setVisible(true);
		label.setBounds(10, 70, 342, 276);
		contentPane.add(label);
		
		btnLink = new JButton("Chose photo");
		btnLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser =new JFileChooser(System.getProperty("user.dir"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
				int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	
			        File file = fileChooser.getSelectedFile();
		        	ImageIcon icon = new ImageIcon(file.toString()); 
		    		Image imageIcon = icon.getImage();
		    		Image newimg = imageIcon.getScaledInstance(342, 276,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		    		icon = new ImageIcon(newimg);
		    		label.setIcon(icon);
		    		textField_2.setText(file.toString());
			    } 
			    else {
			        System.out.println("File access cancelled by user.");
			    } 
			}
		});
		btnLink.setForeground(Color.BLACK);
		btnLink.setBackground(Color.WHITE);
		btnLink.setBounds(362, 152, 89, 30);
		contentPane.add(btnLink);
	}
	
	
	public void initialiseOfflaneGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
	
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 420);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		textField = new JTextField();
		textField.setBounds(454, 111, 189, 30);
		contentPane.add(textField);
		textField.setColumns(10);
		
		lblAddNewPerson = new JLabel("Add new Person to database:");
		lblAddNewPerson.setForeground(Color.BLACK);
		lblAddNewPerson.setFont(new Font("Arial", Font.BOLD, 19));
		lblAddNewPerson.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewPerson.setBounds(10, 11, 674, 14);
		contentPane.add(lblAddNewPerson);
		
		lblIme = new JLabel("First Name:");
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIme.setForeground(Color.BLACK);
		lblIme.setBounds(362, 117, 83, 14);
		contentPane.add(lblIme);
		
		lblPrezime = new JLabel("Last Name:");
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrezime.setForeground(Color.BLACK);
		lblPrezime.setBounds(362, 158, 83, 14);
		contentPane.add(lblPrezime);
		
		btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.WHITE);
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					Files.copy(Paths.get(System.getProperty("user.dir")+"\\"+selectedFile.getName()), Paths.get(System.getProperty("user.dir")+"\\offlaneDatabase\\"+textField_3.getText()+".jpg"), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				PrintWriter output;
				try {
					output = new PrintWriter(new FileWriter(System.getProperty("user.dir")+"\\offlaneDatabase\\"+"keys.txt",true));
					output.printf(";"+textField_3.getText()+","+textField.getText()+","+textField_1.getText());
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			    File f = new File(System.getProperty("user.dir")+"\\offlaneDatabase\\"+textField_3.getText()+".jpg");
			    getFaceAttributes(f);
			    getFaceVectors(f);
			   				
				OfflineDatabase.getInstance();
			    OfflineModeFrame.refreshList();;
			    
			    setVisible(false); 
				dispose();
			    
			}
		});
		btnAdd.setBounds(542, 234, 100, 30);
		contentPane.add(btnAdd);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(454, 152, 189, 30);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBackground(Color.WHITE);
		textField_2.setColumns(10);
		textField_2.setBounds(454, 193, 189, 30);
		contentPane.add(textField_2);
		
		label = new JLabel("");
		label.setForeground(Color.WHITE);
		label.setBackground(Color.WHITE);
		label.setVisible(true);
		label.setBounds(10, 70, 342, 276);
		contentPane.add(label);
		
		btnLink = new JButton("Chose photo");
		btnLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser =new JFileChooser(System.getProperty("user.dir"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
				int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			    	
			    	selectedFile = fileChooser.getSelectedFile();
		        	ImageIcon icon = new ImageIcon(selectedFile.toString()); 
		    		Image imageIcon = icon.getImage();
		    		Image newimg = imageIcon.getScaledInstance(342, 276,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		    		icon = new ImageIcon(newimg);
		    		label.setIcon(icon);
		    		textField_2.setText(selectedFile.toString());
		    		
			    } 
			    else {
			        System.out.println("File access cancelled by user.");
			    } 
			}
		});
		btnLink.setForeground(Color.BLACK);
		btnLink.setBackground(Color.WHITE);
		btnLink.setBounds(362, 193, 89, 30);
		contentPane.add(btnLink);
		
		lblKey = new JLabel("Key:");
		lblKey.setForeground(Color.BLACK);
		lblKey.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblKey.setBounds(362, 76, 83, 14);
		contentPane.add(lblKey);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(454, 70, 189, 30);
		contentPane.add(textField_3);
		
	}


	public void getFaceAttributes(File f) {
		
		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(f.toPath());
			byte[] encodedBytes = Base64.getEncoder().encode(fileContent);    
			String urlParameters = new String(encodedBytes);
			HashMap<String, String> map = new HashMap<>();
			map.put("image_attr", urlParameters);
			String responseString = new Requests().makePostRequest("http://www.facexapi.com/get_image_attr?face_det=1", map);
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(responseString);
			ObjectMapper mapper = new ObjectMapper();
			
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			
			try (PrintStream out = new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"\\offlaneDatabase\\"+textField_3.getText()+"Attribute.txt"))) {
			    out.print(indented);
			    out.close();
			}
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void getFaceVectors(File f) {
		

		byte[] fileContent;
		try {
			fileContent = Files.readAllBytes(f.toPath());
			byte[] encodedBytes = Base64.getEncoder().encode(fileContent);
			String urlParameters = new String(encodedBytes);
			HashMap<String, String> map = new HashMap<>();
			map.put("img", urlParameters);
			
			String responseString=new Requests().makePostRequest("http://www.facexapi.com/get_face_vec?face_det=1", map);

			JSONParser parser = new JSONParser(); 
			JSONObject json = (JSONObject) parser.parse(responseString);
			ObjectMapper mapper = new ObjectMapper();
			
			String indented = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
			
			try (PrintStream out = new PrintStream(new FileOutputStream(System.getProperty("user.dir")+"\\offlaneDatabase\\"+textField_3.getText()+"Vector.txt"))) {
			    out.print(indented);
			    out.close();
			}
			
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}
