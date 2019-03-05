package app.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import app.FaceX;
import app.helper.Requests;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
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

	public FaceVectorsFrame() throws IOException {
		setAlwaysOnTop(true);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1199, 606);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Face Vectors");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 25));
		title.setBackground(Color.LIGHT_GRAY);
		title.setBounds(0, 11, 1184, 50);
		contentPane.add(title);
		
		JLabel label = new JLabel("");
		label.setBackground(Color.WHITE);
		label.setBounds(10, 72, 636, 470);
		contentPane.add(label);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBackground(Color.LIGHT_GRAY);
		textPane.setBounds(272, 132, 506, 468);
		
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setBounds(666, 72, 508, 470);
		contentPane.add(scrollPane);
		
		File f =  new File("D:\\Java Projects\\Workspace\\FaceX\\"+FaceX.getCurrentPicName());
		byte[] fileContent = Files.readAllBytes(f.toPath());
		byte[] encodedBytes =Base64.getEncoder().encode(fileContent);
		String urlParameters=new String(encodedBytes);
		HashMap<String, String> map=new HashMap<>();
		map.put("img", urlParameters);
		
		String responseString=new Requests().makePostRequest("http://www.facexapi.com/get_face_vec?face_det=1", map);
		textPane.setText(responseString);
		
		ImageIcon icon = new ImageIcon(f.toString()); 
	    Image imageIcon = icon.getImage();
	    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
	    icon = new ImageIcon(newimg);
	    label.setIcon(icon);
		
	}
}
