package FD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Menu {
	public ArrayList getMenu(Connection conn) throws SQLException{
		Statement st = conn.createStatement();
		st.execute("SELECT * FROM shop");
		ResultSet rs = st.getResultSet();
		ArrayList <String> menu = new ArrayList();
		while(rs.next()) {
			menu.add(rs.getString("shopname"));
		}
		return menu;
	}
	
	public void addMenu(Connection conn, int id, String shopname) 
			throws SQLException{
		//remember the argument shopname
		//cannot just pass the String
		//needed to add "''"
		//like "'Da-Jang'"
		Statement st = conn.createStatement();
		String update = "INSERT INTO shop" + 
		"(id, shopname) VALUES" + 
		"(" + id + "," + shopname + ")";
		System.out.println(update);
		st.executeUpdate(update);
	}
	
	public int getLastID(Connection conn) throws SQLException{
		Statement st = conn.createStatement();
		st.execute("SELECT * FROM shop");
		ResultSet rs = st.getResultSet();
		int size = 0;
		while(rs.next()) {
			size++;
		}
		return size;
	}
	public void deleteMenu(Connection conn, String shopname) throws SQLException {
		Statement st = conn.createStatement();
		String delete = "DELETE FROM shop " +
                "WHERE shopname = " + shopname;
		st.executeUpdate(delete);

	}
}
