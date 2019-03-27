package app.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import app.model.PersonModel;

public class DBConnector {
	
	private static DBConnector instance=new DBConnector();
	private static Connection con;
	private static Statement statement;
	
	
	
	
	private  DBConnector() {
		try {
			setInstance();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static DBConnector getInstance() {
		return instance;
	}
	public static void setInstance() throws SQLException {

		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/facex","root","admin");
		statement= con.createStatement();	
		
	}
	
	public boolean executeStatement(String statement) {
		boolean ret=true;
		try {
			
			ret= DBConnector.statement.execute(statement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public HashMap<PersonModel, Double> getData() throws SQLException {
		
		PreparedStatement ps;
			ps = con.prepareStatement("select * from people");
			 ResultSet rs=ps.executeQuery();
			 HashMap<PersonModel,Double> person_list=new HashMap<PersonModel,Double>();
			 while(rs.next()){
				PersonModel p=new PersonModel(Long.parseLong(rs.getString(1)),rs.getString(2),rs.getString(3),rs.getString(4));
		        person_list.put(p, (double) 0);
		     }
			 return person_list;
	}

}
