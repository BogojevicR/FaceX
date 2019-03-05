package app.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

import app.FaceX;

public class ButtonPanel extends JPanel {
	
	private static ButtonPanel instance= new ButtonPanel();
	private static JButton btn=new JButton("Take a Picture");
	private static JButton btn1=new JButton("Face Attributes");
	private static JButton btn2=new JButton("Face Recognition");
	private static JButton btn3=new JButton("Add Person");
	private static JButton btn4=new JButton("Face Vectors");
	
	public ButtonPanel() {
		
	}
	
	public static ButtonPanel getInstance() {
		initialiseGUI();
		return instance;
	}

	public static void initialiseGUI() {
		instance.setBackground(Color.LIGHT_GRAY);
		instance.setLayout(new FlowLayout());
		btn.setBackground(Color.WHITE);
		btn1.setBackground(Color.WHITE);
		btn2.setBackground(Color.WHITE);
		btn3.setBackground(Color.WHITE);
		btn4.setBackground(Color.WHITE);
		instance.add(btn);
		instance.add(btn1);
		instance.add(btn2);
		instance.add(btn3);
		instance.add(btn4);
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FaceX.clicked=true;
			} 
		});
		
		
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AttributeFrame p;
				try {
					p = new AttributeFrame();
					p.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} 
		});
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FaceVerification fv;
				try {
					fv = new FaceVerification();
					fv.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} 
		});
		
		
		btn3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonFrame p=new PersonFrame();
				p.setVisible(true);
			} 
		});
		
		
		btn4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FaceVectorsFrame p;
				try {
					p = new FaceVectorsFrame();
					p.setVisible(true);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			} 
		});
		
	}
	
}
