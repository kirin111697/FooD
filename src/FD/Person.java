package FD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Person {
	public ArrayList getDislike(Connection conn, String name) 
			throws SQLException{
		Statement st = conn.createStatement();
		st.execute("SELECT * FROM person WHERE username LIKE '"
				+ name + "'");
		ResultSet rs = st.getResultSet();
		ArrayList <String> dislike = new ArrayList();
		while(rs.next()) {
			dislike.add(rs.getString("dislike1"));
			dislike.add(rs.getString("dislike2"));
			dislike.add(rs.getString("dislike3"));
		}
		return dislike;
	}
	public void addPerson(Connection conn, int id, String name, 
			String dislike1, String dislike2, String dislike3)
				throws SQLException{
		//remember the argument name, dislike1, dislike2, dislike3
		//cannot just pass the String
		//needed to add "''"
		//like "'Da-Jang'"
		
		Statement st = conn.createStatement();
		String update = "INSERT INTO person(userid, username, "
				+ "dislike1, dislike2, dislike3) VALUES "
				+ "(" + id + "," + name + "," + dislike1
				+ "," + dislike2 + "," + dislike3 + ")";
		st.executeUpdate(update);
	}
	public boolean addDislike(Connection conn, String name,
			String dislike) throws SQLException{
		//true  -> add successfully
		//false -> dislike columns are full
		
		//remember the argument name, dislike
		//cannot just pass the String
		//needed to add "''"
		//like "'Da-Jang'"
		Statement st = conn.createStatement();
		st.execute("SELECT * FROM person WHERE username LIKE " + name);
		ResultSet rs = st.getResultSet();
		if(rs.next()) {
			if(rs.getString("dislike1").equals("")) {
				String update = "UPDATE person SET dislike1 = " + dislike
					+ "WHERE username =" + name;
				st.executeUpdate(update);
				return true;
			}else if(rs.getString("dislike2").equals("")){
				String update = "UPDATE person SET dislike2 = " + dislike
						+ "WHERE username =" + name;
				st.executeUpdate(update);
				return true;
			}else if(rs.getString("dislike3").equals("")){
				String update = "UPDATE person SET dislike3 = " + dislike
						+ "WHERE username =" + name;
				st.executeUpdate(update);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	public int getLastID(Connection conn) throws SQLException{
		Statement st = conn.createStatement();
		st.execute("SELECT * FROM person");
		ResultSet rs = st.getResultSet();
		int size = 0;
		while(rs.next()) {
			size++;
		}
		return size;
	}
	
	public void deletePerson(Connection conn, String name) throws SQLException {
		Statement st = conn.createStatement();
		String delete = "DELETE FROM person " +
                "WHERE username = " + name;
		st.executeUpdate(delete);
	}
	
	public boolean deleteDislike(Connection conn, String name, String dislike) throws SQLException {
		Statement st = conn.createStatement();
		st.execute("SELECT * FROM person WHERE username LIKE " + name);
		ResultSet rs = st.getResultSet();
		if(rs.next()) {
			if(rs.getString("dislike1").equals(dislike)) {
				String update = "UPDATE person SET dislike1 = " + "''"
					+ "WHERE username =" + name;
				st.executeUpdate(update);
				return true;
			}else if(rs.getString("dislike2").equals(dislike)){
				String update = "UPDATE person SET dislike2 = " + "''"
						+ "WHERE username =" + name;
				st.executeUpdate(update);
				return true;
			}else if(rs.getString("dislike3").equals(dislike)){
				String update = "UPDATE person SET dislike3 = " + "''"
						+ "WHERE username =" + name;
				st.executeUpdate(update);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
} 
