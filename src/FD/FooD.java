package FD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FooD extends Application{
	
	public static Connection conn = null;
	public List<String> specialDislike = new LinkedList<String>();
	
	public static void main(String[] args){
		//launch(args);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			String datasource = "jdbc:mysql://localhost/FoodDecider?user=root&password=58A5D64D";
            conn = DriverManager.getConnection(datasource);
            launch(args);
		}catch(ClassNotFoundException e){ 
            System.out.println("DriverClassNotFound :"+e.toString()); 
        }//有可能會產生sqlexception 
        catch(SQLException x) { 
            System.out.println("Exception :"+x.toString()); 
        }
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		primaryStage.setTitle("Food Decider");
		
		FXMLLoader ld = new FXMLLoader(getClass().getResource("/FD/FooDFX.fxml"));
		Parent root = ld.load();
		primaryStage.setScene(new Scene(root));
        primaryStage.show();
	}
	
	public List <String> listMenu(Menu menu, Connection conn) throws Exception{
		List <String> menuList = new LinkedList<String>();
		menuList = menu.getMenu(conn);
		return menuList;
	}
	
	public static List<String> sortRough(List<String> rough){
		List<String> allDislike = new LinkedList<String>();
		Boolean add = true;
		for(String food: rough){
			for(String dllist: allDislike){
				add = true;
				if(dllist.equals(food) || dllist.equals("")){
					add = false;
					break;
				}
			}
			if(add){
				allDislike.add(food);
			}
		}
		return allDislike;
	}
	
	public static List<String> Compare(List<String> menu, List<String> dlList){
		List<String> result = new LinkedList<String>();
		Boolean add = true;
		for(String menuFood: menu){
			add = true;
			for(String dlFood: dlList){
				if(dlFood.equals(menuFood)){
					add = false;
					break;
				}
			}
			if(add)
				result.add(menuFood);
		}
		return result;
	}
	
	public static String randFood(List<String> resultList){
		String result = "nothing";
		Random index = new Random();
		if(resultList.isEmpty())
			return result;
		else
        	result = resultList.get(index.nextInt(resultList.size()));
		return result;
	}
}
