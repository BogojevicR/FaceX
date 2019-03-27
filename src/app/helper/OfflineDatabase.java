package app.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import app.model.PersonOfflineModel;

public class OfflineDatabase {

	private static OfflineDatabase instance=new OfflineDatabase();
	public static List<PersonOfflineModel> persons;
	
	private OfflineDatabase() {

	}
	
	private static void initialise() throws IOException {
		persons = new ArrayList<>();
		BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\offlaneDatabase\\keys.txt"));
		
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
		
		
		
		String[] keys = jsonString.split(";");
		for(String key : keys) {
			String[] fields = key.split(",");
			if(getKey(fields[0])) {
				persons.add(new PersonOfflineModel(fields[0], fields[1], fields[2]));
			}
			
		}
		
	}

	public static OfflineDatabase  getInstance() {
		try {
			initialise();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return instance;
		
	}
	
	public static boolean getKey(String key) {
		for(PersonOfflineModel p : persons) {
			if(p.getFileName().equals(key)) {
				return false;
			}
		}
		return true;
	}

	
}
