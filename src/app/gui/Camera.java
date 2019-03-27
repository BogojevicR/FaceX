package app.gui;



import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

public class Camera extends CanvasFrame {

	private static Camera instance = new Camera();
	public static FrameGrabber grabber = new VideoInputFrameGrabber(0); // 1 for next camera
	public static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	public static IplImage img;
	
	public Camera() {
		super("FaceX");
		
	}

	public static Camera getInstance() {
		try {
			initialise();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
	}
	
	public static void initialise() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		instance.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

		UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");

	}

}
