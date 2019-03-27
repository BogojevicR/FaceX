package app.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.json.simple.parser.ParseException;

import app.helper.OfflineDatabase;
import app.model.PersonOfflineModel;

public class OfflineModeFrame extends JFrame {

	private JPanel contentPane;
	private static OfflineModeFrame instance = new OfflineModeFrame();
	private static JList list;

	private OfflineModeFrame() {
		try {
			initialiseGUI();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static OfflineModeFrame getInstance() {
		return instance;
	}
	
	
	public static void refreshList() {
		ListData dataaa=new ListData();
		list.setModel(dataaa);
		
	}

	public void initialiseGUI() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		
		UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1199, 606);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JPanel panel = new JPanel();
		panel.setBounds(10, 529, 1163, 27);
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new FlowLayout());
		
		
		contentPane.add(panel);
		
		JButton faceAttrBtn = new JButton("Get Face Attribute");
		panel.add(faceAttrBtn);
		

		
		JButton faceVerBtn = new JButton("Get Face Verification");
		panel.add(faceVerBtn);
		
		JButton faceVectorBtn = new JButton("Get Face Vector");
		panel.add(faceVectorBtn);
		
		JButton addToDatabase = new JButton("Add To Database");
		panel.add(addToDatabase);
		
		
		JLabel title = new JLabel("Offline Mode");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Tahoma", Font.PLAIN, 25));
		title.setBackground(Color.LIGHT_GRAY);
		title.setBounds(10, 11, 1163, 50);
		contentPane.add(title);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 72, 514, 446);
		contentPane.add(scrollPane);
		
		JLabel label = new JLabel("");
		label.setBackground(Color.DARK_GRAY);
		label.setBounds(534, 72, 639, 446);
		contentPane.add(label);
		
		list = new JList();
		ListData dataaa=new ListData();
		list.setModel(dataaa);
		
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(list.getSelectedIndex() <0 ) {
					return;
				}
				PersonOfflineModel p = OfflineDatabase.persons.get(list.getSelectedIndex());
				
				String fileName = p.getLink()+p.getFileName()+".jpg";
				ImageIcon icon = new ImageIcon(new File(fileName).toString()); 
			    Image imageIcon = icon.getImage();
			    Image newimg = imageIcon.getScaledInstance(636, 528,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			    icon = new ImageIcon(newimg);
			    label.setIcon(icon);

			}
		});
		
		scrollPane.setViewportView(list);
		
		
		
		faceAttrBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AttributeFrame p;
				try {
					p = new AttributeFrame();
					if(list.getSelectedIndex() == -1 ) {
						JOptionPane.showMessageDialog(null, "Select item From List First!");
						return;
					}
					p.offlineMode(OfflineDatabase.persons.get(list.getSelectedIndex()));
					p.setVisible(true);
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			} 
		});
		
		faceVerBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FaceVerification fv;
				try { 
					
					if(list.getSelectedIndex() == -1 ) {
						JOptionPane.showMessageDialog(null, "Select item From List First!");
						return;
					}
					
					fv = new FaceVerification();
					fv.offlineMode(OfflineDatabase.persons.get(list.getSelectedIndex()));
					fv.setVisible(true);
				} catch (IOException | ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} 
		});
				
		faceVectorBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FaceVectorsFrame p;
				p = new FaceVectorsFrame();
				try {
					
					if(list.getSelectedIndex() == -1 ) {
						JOptionPane.showMessageDialog(null, "Select item From List First!");
						return;
					}
					
					p.offlineMode(OfflineDatabase.persons.get(list.getSelectedIndex()));
				} catch (ParseException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				p.setVisible(true);
				
			} 
		});
		
		
		addToDatabase.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PersonFrame p;
				try {
					p = new PersonFrame();
					p.initialiseOfflaneGUI();
					p.setVisible(true);
				} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}  );
		
		
	}	
}


