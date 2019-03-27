package app.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class PersonOfflineModel {
	private String fileName;
	private String first_name;
	private String last_name;
	private String link;
	private String vector;
	private String attribute;
	
	public PersonOfflineModel() {
		super();
	}

	
	public PersonOfflineModel(String fileName, String first_name, String last_name) throws IOException {
		super();
		this.fileName = fileName;
		this.first_name = first_name;
		this.last_name = last_name;
		this.link = System.getProperty("user.dir")+"\\"+"offlaneDatabase\\";
		try {
			setVector();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAttribute();
	}

	

	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	public String getVector() {
		return vector;
	}

	public void setVector( ) throws  ParseException, IOException {
		
		File f =  new File(link+this.fileName+".jpg");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(link+this.fileName+"Vector.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return;

		}

		BufferedReader reader = new BufferedReader(new FileReader(link+this.fileName+"Vector.txt"));
		
		String jsonString = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    jsonString = sb.toString();
		} finally {
		    reader.close();
		}
		this.vector = jsonString;

	}
	

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute( ) throws IOException {
		
		File f =  new File(link+this.fileName+".jpg");
		BufferedReader reader = new BufferedReader(new FileReader(link+this.fileName+"Attribute.txt"));
		String jsonString = "";
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = reader.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append("\n");
		        line = reader.readLine();
		    }
		    jsonString = sb.toString();
		} finally {
		    reader.close();
		}
		this.attribute = jsonString;
	}

	
}
