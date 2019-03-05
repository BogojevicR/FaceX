package app.gui;

import static org.bytedeco.javacpp.opencv_core.cvFlip;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvSaveImage;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.VideoInputFrameGrabber;

public class Camera extends CanvasFrame {

	private static Camera instance= new Camera();
	public static FrameGrabber grabber = new VideoInputFrameGrabber(0); // 1 for next camera
	public static OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
	public static IplImage img;
	
	public Camera() {
		super("FaceX");
		
	}

	public static Camera getInstance() {
		initialise();
		return instance;
	}
	
	public static void initialise() {
		instance.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
	}

}
