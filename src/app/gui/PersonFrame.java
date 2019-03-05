package app.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import app.FaceX;
import app.helper.DBConnector;

public class PersonFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public PersonFrame() {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setAlwaysOnTop(true);

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
		
		JLabel lblAddNewPerson = new JLabel("Add new Person to database:");
		lblAddNewPerson.setForeground(Color.BLACK);
		lblAddNewPerson.setFont(new Font("Arial", Font.BOLD, 19));
		lblAddNewPerson.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddNewPerson.setBounds(10, 11, 674, 14);
		contentPane.add(lblAddNewPerson);
		
		JLabel lblIme = new JLabel("First Name:");
		lblIme.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblIme.setForeground(Color.BLACK);
		lblIme.setBounds(362, 76, 83, 14);
		contentPane.add(lblIme);
		
		JLabel lblPrezime = new JLabel("Last Name:");
		lblPrezime.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrezime.setForeground(Color.BLACK);
		lblPrezime.setBounds(362, 117, 83, 14);
		contentPane.add(lblPrezime);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBackground(Color.WHITE);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		
		JLabel label = new JLabel("");
		label.setForeground(Color.WHITE);
		label.setBackground(Color.WHITE);
		label.setVisible(true);
		label.setBounds(10, 70, 342, 276);
		contentPane.add(label);
		
		JButton LinkBtn = new JButton("Chose photo");
		LinkBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser =new JFileChooser(System.getProperty("user.dir"));
				fileChooser.setAcceptAllFileFilterUsed(false);
				fileChooser.setFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
				int returnVal = fileChooser.showOpenDialog((Component)e.getSource());
			    if (returnVal == JFileChooser.APPROVE_OPTION) {
			        File file = fileChooser.getSelectedFile();
			        try {
			        	ImageIcon icon = new ImageIcon(file.toString()); 
			    		Image imageIcon = icon.getImage();
			    		Image newimg = imageIcon.getScaledInstance(342, 276,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			    		icon = new ImageIcon(newimg);
			    		label.setIcon(icon);
			    		textField_2.setText(file.toString());
			        } catch (Exception ex) {
			          System.out.println("problem accessing file"+file.getAbsolutePath());
			        }
			    } 
			    else {
			        System.out.println("File access cancelled by user.");
			    } 
			}
		});
		LinkBtn.setForeground(Color.BLACK);
		LinkBtn.setBackground(Color.WHITE);
		LinkBtn.setBounds(362, 152, 89, 30);
		contentPane.add(LinkBtn);
		
	}
}
