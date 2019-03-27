package app.helper;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FaceAttributesDrawer {

	private static FaceAttributesDrawer instance = new FaceAttributesDrawer();
	private static HashMap<String,Point> attributes=new HashMap<String,Point>();

	public static FaceAttributesDrawer getInstance() {
		return instance;
	}

	public  void drawImage(File f, String jsonResponse) throws IOException, ParseException {

		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonResponse);
		JSONObject face;
		
		BufferedImage image =  ImageIO.read(f);
		Graphics2D graphics2D = image.createGraphics ();
        graphics2D.setStroke(new BasicStroke(2));      
		
		int i = 0;
		String key = "face_id_" + i;
		
		while((JSONObject)json.get(key) != null) {
			face = (JSONObject)json.get(key);
			
			drawFace(graphics2D,face);
	        
	        i = i + 1;
			key = "face_id_" + i;
		}
  
        ImageIO.write ( image, "jpg", new File ( System.getProperty("user.dir") + "\\" + "response.jpg" ) );		
	}
	
	public void drawFrame(Graphics2D graphics2D,JSONObject face) {
		
		String gender = (String) face.get("gender").toString();

		if(gender.equals("male")) {
			graphics2D.setColor(Color.BLUE);
		}else {
			graphics2D.setColor(Color.RED);
		}
		
		Object rectangle = face.get("face_rectangle");
		
		String s = rectangle.toString();
		s = s.substring(1, s.length() - 1);
		String[] points = s.split(",");

		int x1 = Integer.parseInt(points[0]);
		int y1 = Integer.parseInt(points[1]);
		int x2 = Integer.parseInt(points[2]);
		int y2 = Integer.parseInt(points[3]);
		
        graphics2D.drawLine(x1, y1, x2, y1);
        graphics2D.drawLine(x2, y1, x2, y2);
        graphics2D.drawLine(x2, y2, x1, y2);
        graphics2D.drawLine(x1, y2, x1, y1);
        
        graphics2D.setColor(Color.WHITE);
		
	}
	//TODO: KORISIT OVU FUNKCIJU UMESTO 100 FOROVA
	public void addAttributeToMap(JSONObject json,String attrName,int size) {
		int x,y;
		JSONObject object;
		Point p;
        for(int i = 0; i < size; i++) {
        	attrName = "face_endpoint_"+i;
        	object = (JSONObject) json.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);     	
        }
	}
	
	public void getAllAttributes(JSONObject face) {
		JSONObject landmarks = (JSONObject) face.get("landmarks");
        JSONObject faceLandMark = (JSONObject) landmarks.get("faceLandMark");
        
        String attrName = "face_endpoint_";
        int x,y;
        JSONObject object;
        Point p;
        for(int i = 0; i < 17; i++) {
        	if(i == 8) {
        		continue;
        	}
        	attrName = "face_endpoint_"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);     	
        }
        
        for(int i = 1; i < 9; i++) {
        	attrName = "LowerLipPoint"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
        
        for(int i = 1; i < 3; i++) {
        	attrName = "leftEyeBottom"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
        
        for(int i = 1; i < 3; i++) {
        	attrName = "leftEyeTop"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
        
        attrName = "leftEyeLeftCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "leftEyeRightCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	
    	for(int i = 1; i < 3; i++) {
        	attrName = "rightEyeBottom"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
    	
    	for(int i = 1; i < 3; i++) {
        	attrName = "rightEyeTop"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
        
        attrName = "rightEyeLeftCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "rightEyeRightCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "leftEyebrowLeftCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "leftEyebrowRightCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	for(int i = 1; i < 4; i++) {
        	attrName = "leftEyebrowMiddle"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
    	
    	attrName = "rightEyebrowLeftCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "rightEyebrowRightCorner";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	for(int i = 1; i < 4; i++) {
        	attrName = "rightEyebrowMiddle"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
    	
    	for(int i = 1; i < 3; i++) {
        	attrName = "noseLower"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
    	
    	for(int i = 1; i < 4; i++) {
        	attrName = "nosePoint"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
    	
    	for(int i = 1; i < 3; i++) {
        	attrName = "noseTop"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }
    	
    	attrName = "noseRootLeft";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "noseTip";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "noseRootRight";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "chin";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "mouthLeft";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);
    	
    	attrName = "mouthRight";
    	object = (JSONObject) faceLandMark.get(attrName);
    	x = Integer.parseInt(object.get("x").toString());
    	y = Integer.parseInt(object.get("y").toString());
    	p=new Point(x,y);
    	attributes.put(attrName, p);  	
    	
    	for(int i = 1; i < 11; i++) {
        	attrName = "upperLipPoint"+i;
        	object = (JSONObject) faceLandMark.get(attrName);
        	if(object == null) {
        		attrName = "underLipPoint"+i;
        		object = (JSONObject) faceLandMark.get(attrName);
        	}
        	attrName = "upperLipPoint"+i;
        	x = Integer.parseInt(object.get("x").toString());
        	y = Integer.parseInt(object.get("y").toString());
        	p=new Point(x,y);
        	attributes.put(attrName, p);
        }      
	}
	public void drawFace(Graphics2D graphics2D, JSONObject face) {
		getAllAttributes(face);
		drawFrame(graphics2D,face);
        drawFaceEndpoints(graphics2D);
        drawEyes(graphics2D);
        drawEyebrows(graphics2D);
        drawNose(graphics2D);
        drawLips(graphics2D);
	}
	
	public void drawFaceEndpoints(Graphics2D graphics2D) {
		int x,y,xx,yy;
        for(int i = 0; i < 17; i++){
        	if(i == 8) {
        		continue;
        	}
        	String attr = "face_endpoint_"+i;
        	x = (int) attributes.get(attr).getX();
        	y = (int) attributes.get(attr).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	if(i+1==8 || i+1==17) {
        		continue;
        	}
        	String attr1 = "face_endpoint_"+ ++i;
        	xx = (int) attributes.get(attr1).getX();
        	yy = (int) attributes.get(attr1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	i--;
        }
        
        String attr = "face_endpoint_7";
    	x = (int) attributes.get(attr).getX();
    	y = (int) attributes.get(attr).getY();
    	
    	String attr1 = "face_endpoint_9" ;
    	xx = (int) attributes.get(attr1).getX();
    	yy = (int) attributes.get(attr1).getY();
    	graphics2D.drawLine(x, y, xx, yy);
        
	}
	
	public void drawLips(Graphics2D graphics2D) {
		int x,y,xx,yy;
		String attrName,attrName1;
		for(int i = 4; i < 9; i++) {
        	attrName = "LowerLipPoint"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 8) {
        		continue;
        	}
        	attrName1 = "LowerLipPoint"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		attrName = "mouthLeft";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	graphics2D.fillOval(x-2,y-2,4,4);
    	
    	attrName1 = "LowerLipPoint8";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "mouthRight";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	graphics2D.fillOval(x-2,y-2,4,4);
    	
    	attrName1 = "LowerLipPoint4";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "mouthLeft";
    	xx = (int) attributes.get(attrName).getX();
    	yy = (int) attributes.get(attrName).getY();
    	
    	
		
    	for(int i = 1; i < 5; i++) {
        	attrName = "upperLipPoint"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 1) {
    			graphics2D.drawLine(x, y, xx, yy);
    		}
        	
        	attrName1 = "upperLipPoint"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }  
    	
    	attrName = "mouthRight";
    	xx = (int) attributes.get(attrName).getX();
    	yy = (int) attributes.get(attrName).getY();
    	graphics2D.drawLine(x, y, xx, yy);
        

	}
	
	public void drawEyes(Graphics2D graphics2D) {
		int x,y,xx,yy;
		String attrName,attrName1;
		for(int i = 1; i < 3; i++) {
        	attrName = "leftEyeBottom"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 2) {
        		continue;
        	}
        	attrName1 = "leftEyeBottom"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		for(int i = 1; i < 3; i++) {
        	attrName = "leftEyeTop"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i==2) {
        		continue;
        	}
        	attrName1 = "leftEyeTop"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		attrName = "leftEyeTop2";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
		
		
		attrName1 = "leftEyeLeftCorner";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "leftEyeBottom2";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "leftEyeTop1";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
		
		
		attrName1 = "leftEyeRightCorner";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "leftEyeBottom1";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	//RIGHT EYE
    	for(int i = 1; i < 3; i++) {
        	attrName = "rightEyeBottom"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 2) {
        		continue;
        	}
        	attrName1 = "rightEyeBottom"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		for(int i = 1; i < 3; i++) {
        	attrName = "rightEyeTop"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 2) {
        		continue;
        	}
        	attrName1 = "rightEyeTop"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		attrName = "rightEyeTop1";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
		
		
		attrName1 = "rightEyeLeftCorner";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "rightEyeBottom2";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "rightEyeTop2";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
		
		
		attrName1 = "rightEyeRightCorner";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	attrName = "rightEyeBottom1";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	
	}

	public void drawEyebrows(Graphics2D graphics2D) {
		
		int x,y,xx,yy;
		String attrName,attrName1;
		
		attrName = "rightEyebrowRightCorner";
    	xx = (int) attributes.get(attrName).getX();
    	yy = (int) attributes.get(attrName).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
		
		for(int i = 1; i < 4; i++) {
			
        	attrName = "rightEyebrowMiddle"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	if(i == 1) {
        		graphics2D.drawLine(x, y, xx, yy);
			}
        	
        	if(i == 3) {
        		continue;
        	}
        	attrName1 = "rightEyebrowMiddle"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		attrName = "rightEyebrowMiddle3";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	
		
		attrName1 = "rightEyebrowLeftCorner";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
    	graphics2D.drawLine(x, y, xx, yy);
    	
    	//Left Eyebrow
    	
    	attrName = "leftEyebrowRightCorner";
    	xx = (int) attributes.get(attrName).getX();
    	yy = (int) attributes.get(attrName).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
		
		for(int i = 1; i < 4; i++) {
			
        	attrName = "leftEyebrowMiddle"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	if(i == 1) {
        		graphics2D.drawLine(x, y, xx, yy);
			}
        	
        	if(i == 3) {
        		continue;
        	}
        	attrName1 = "leftEyebrowMiddle"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		attrName = "leftEyebrowMiddle3";
    	x = (int) attributes.get(attrName).getX();
    	y = (int) attributes.get(attrName).getY();
    	
		
		attrName1 = "leftEyebrowLeftCorner";
    	xx = (int) attributes.get(attrName1).getX();
    	yy = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(xx-2,yy-2,4,4);
    	graphics2D.drawLine(x, y, xx, yy);
    	
	}
	
	public void drawNose(Graphics2D graphics2D) {
		
		int x,y,xx,yy;
		String attrName,attrName1;
		for(int i = 1; i < 3; i++) {
        	attrName = "noseLower"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 2) {
        		continue;
        	}
        	
        	attrName1 = "noseLower"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		for(int i = 1; i < 4; i++) {
        	attrName = "nosePoint"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 3) {
        		continue;
        	}
        	
        	attrName1 = "nosePoint"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		for(int i = 1; i < 3; i++) {
        	attrName = "noseTop"+i;
        	x = (int) attributes.get(attrName).getX();
        	y = (int) attributes.get(attrName).getY();
        	graphics2D.fillOval(x-2,y-2,4,4);
        	
        	if(i == 2) {
        		continue;
        	}
        	
        	attrName1 = "noseTop"+ ++i;
        	xx = (int) attributes.get(attrName1).getX();
        	yy = (int) attributes.get(attrName1).getY();
        	graphics2D.drawLine(x, y, xx, yy);
        	
        	i--;
        }
		
		attrName1 = "noseTip";
    	x = (int) attributes.get(attrName1).getX();
    	y = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(x-2,y-2,4,4);
		
    	attrName1 = "noseRootLeft";
    	x = (int) attributes.get(attrName1).getX();
    	y = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(x-2,y-2,4,4);
    	
    	attrName1 = "noseRootRight";
    	x = (int) attributes.get(attrName1).getX();
    	y = (int) attributes.get(attrName1).getY();
    	graphics2D.fillOval(x-2,y-2,4,4);
		
		
	}
}
