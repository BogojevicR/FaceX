package app;

import static org.bytedeco.javacpp.opencv_core.cvFlip;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

import java.awt.BorderLayout;
import java.util.UUID;

import javax.swing.JOptionPane;

import org.bytedeco.javacv.Frame;

import app.gui.ButtonPanel;
import app.gui.Camera;

public class FaceX {
	public static Camera canvas=Camera.getInstance();
	private static ButtonPanel bp=ButtonPanel.getInstance();
	public static boolean clicked=false;
	private static String currentPicName;
	public static void main(String[] args) {
		
		canvas.add(bp,BorderLayout.SOUTH);

        try {
        	Camera.grabber.start();
            while (true) {
                Frame frame = Camera.grabber.grab();
                Camera.img = Camera.converter.convert(frame);            
                cvFlip(Camera.img, Camera.img, 1);//the grabbed frame will be flipped, re-flip to make it right  l-r = 90_degrees_steps_anti_clockwise
                canvas.showImage(Camera.converter.convert(Camera.img));
                if(clicked==true) {   
                	setCurrentPicName(UUID.randomUUID().toString().replace("-", "").substring(0, 5)+".jpg");;
                    cvSaveImage(currentPicName, Camera.img);
                    canvas.showImage(Camera.converter.convert(Camera.img));
                    JOptionPane.showMessageDialog(null, "Picture Captured!");

                    clicked=false;
                }
                
            }
        } catch (Exception e) {
            e.printStackTrace();

	}
	}
	
	
	public static boolean isClicked() {
		return clicked;
	}


	public static void setClicked(boolean clicked) {
		FaceX.clicked = clicked;
	}


	public static String getCurrentPicName() {
		return currentPicName;
	}


	public static void setCurrentPicName(String currentPicName) {
		FaceX.currentPicName = currentPicName;
	}


}