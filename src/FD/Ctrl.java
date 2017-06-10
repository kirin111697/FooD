package FD;

import java.net.URL;
import java.sql.Connection;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import FD.FooD;
import FD.Menu;
import FD.Person;

public class Ctrl implements Initializable{
	
	@FXML
	private TextArea textArea;
	@FXML
	private TextArea textArea2;
	@FXML
	private TextField textField;
	@FXML
	private TextField textField2;
	@FXML
	private TextField textField3;
	@FXML
	private TextField textField4;
	@FXML
	private TextField textField4_1;
	@FXML
	private TextField textField4_2;
	@FXML
	private TextField textField4_3;
	@FXML
	private TextField textField5;
	@FXML
	private TextField todaysPeople;
	@FXML
	private TextField delDis_Name;
	@FXML
	private TextField delDis_Dis;
	@FXML
	private TextField delMenu;
	@FXML
	private TextField delUser;
	
	@FXML
	private Button Continue;
	@FXML
	private Button Finish;
	@FXML
	private Button submit;
	@FXML
	private Button submit2;
	@FXML
	private Button submit3;
	@FXML
	private Button submit4;
	@FXML
	private Button delDis_submit;
	@FXML
	private Button delMenu_submit;
	@FXML
	private Button delUser_submit;
	
	public FooD food;
	public Menu sqlMenu;
	public Person sqlPerson;
	
	
	@Override
	public void initialize(URL location, ResourceBundle res){	
		food = new FooD();
		sqlMenu = new Menu();
		sqlPerson = new Person();
	}
	
	
	@FXML
	public void listM(ActionEvent event) throws Exception{
		System.out.println("connected1");
		List <String> forMenu = new LinkedList<String>();
		forMenu = food.listMenu(sqlMenu, FooD.conn);
		String temp = "";
		for(String s: forMenu){
			temp = temp + s + "\n";
		}
		textArea.setText(temp);
	}
	
	@FXML	
	public void addDislike(ActionEvent event) {
		System.out.println("connected2");
    		if(textField.getText() != "" && textField2.getText() != ""){
			List<String> list = new LinkedList<String>();
			String tf = "'"+ textField.getText() + "'";
			String tf2 = "'" + textField2.getText() + "'";
			try {
				list = sqlPerson.getDislike(FooD.conn, tf);
			}
			catch (SQLException e1) {
			}
			Boolean boolAddDislike = true;
			for(String s: list){
				if(textField2.getText().equals(s)){
					boolAddDislike = false;
					//訊息提示
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle(""); 
					info.setHeaderText("");
					info.setContentText(s + "已存在於資料庫中");
					info.showAndWait();
					textField.setText("");
					textField2.setText("");
				}
			}
			if(boolAddDislike){
    				try {
					sqlPerson.addDislike(FooD.conn, tf, tf2);
					//訊息提示
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle(""); 
					info.setHeaderText("");
					info.setContentText("Add Dislike Success");
					info.showAndWait();
					textField.setText("");
					textField2.setText("");
    				}
				catch (SQLException e) {
					//訊息提示
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle(""); 
					info.setHeaderText("");
					info.setContentText("There's something wrong!");
					info.showAndWait();
					textField.setText("");
					textField2.setText("");
				}
			}
    		}
	}

	@FXML
	public void specialDis_btn(ActionEvent event) {
		System.out.println("connected3");
		Boolean boolAddSpecial = true;
		for(String s : food.specialDislike){
			if(textField3.getText().equals(s)){
				boolAddSpecial = false;
				//訊息提示
				Alert info = new Alert(AlertType.INFORMATION);
				info.setTitle(""); 
				info.setHeaderText("");
				info.setContentText(s + "已經輸入過了！那麼不想吃嗎？");
				info.showAndWait();
				textField3.setText("");
			}
			System.out.println(s);
		}
		System.out.println("==========");
		String spe = "'" + textField3.getText() + "'";
		if(boolAddSpecial && !spe.equals("")){
			food.specialDislike.add(spe);
			//訊息提示
			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle(""); 
			info.setHeaderText("");
			info.setContentText("今天不吃"+ spe + "!!!");
			info.showAndWait();
			textField3.setText("");
		}
		else{
			//訊息提示
			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle(""); 
			info.setHeaderText("");
			info.setContentText("輸入失敗");
			info.showAndWait();
			textField3.setText("");
		}
    }

	@FXML
	public void addUser(ActionEvent event) {
		System.out.println("connected4");
    		try {
    			if(!textField4.getText().equals("")){
    				int id;
    				id = sqlPerson.getLastID(FooD.conn)+1;
    				String tf4 = "'" + textField4.getText() + "'";
    				String tf4_1 = "'" + textField4_1.getText() + "'";
    				String tf4_2 = "'" + textField4_2.getText() + "'";
    				String tf4_3 = "'" + textField4_3.getText() + "'";
    				sqlPerson.addPerson(FooD.conn, id, tf4, tf4_1, tf4_2, tf4_3);
    				//訊息提示
    				Alert info = new Alert(AlertType.INFORMATION);
    				info.setTitle(""); 
    				info.setHeaderText("");
    				info.setContentText("成功新增使用者\n使用者: " + textField4.getText() + "dislike 1: "+ textField4_1.getText() + "dislike 2: "+ textField4_2.getText() + "dislike 3: "+ textField4_3.getText());
    				info.showAndWait();
    				textField4.setText("");
    				textField4_1.setText("");
    				textField4_2.setText("");
    				textField4_3.setText("");
    			}
    		}
    		catch (Exception e) {
    			//訊息提示
    			Alert info = new Alert(AlertType.INFORMATION);
    			info.setTitle(""); 
    			info.setHeaderText("");
    			info.setContentText("There's something wrong!");
    			info.showAndWait();
    			textField4.setText("");
			textField4_1.setText("");
			textField4_2.setText("");
			textField4_3.setText("");
		}
    }
	
	@FXML
	public void addChoice(ActionEvent event){
		System.out.println("connecte5");
		try {
    			int id;
    			id = sqlMenu.getLastID(FooD.conn)+1;
    			if(!textField5.getText().equals("")){
    				String tf5 = "'" + textField5.getText() + "'";
    				sqlMenu.addMenu(FooD.conn, id, tf5);
    				//訊息提示
    				Alert info = new Alert(AlertType.INFORMATION);
    				info.setTitle(""); 
    				info.setHeaderText("");
    				info.setContentText("成功增加menu選項");
    				info.showAndWait();
    				textField5.setText("");
    			}
		}
		catch (Exception e) {
			//訊息提示
			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle(""); 
			info.setHeaderText("");
			info.setContentText("There's something wrong!");
			info.showAndWait();
			textField5.setText("");
		}
	}
	
	@FXML
	public void continues(ActionEvent event){
		System.out.println("connected6");
		textArea2.setText(textArea2.getText() + " " + todaysPeople.getText());
        todaysPeople.setText("");
	}
	
	@FXML
	public void finish(ActionEvent event){
		System.out.println("connected7");
		Scanner allPeople = new Scanner(textArea2.getText());
    		String name;
    		List<String> list = new LinkedList<String>();
    		List<String> rough = new LinkedList<String>();
    		List<String> menu = new LinkedList<String>();
    		List<String> resultList = new LinkedList<String>();
    		while(allPeople.hasNext()){
    			name = allPeople.next();
    			list.add(name);
    		}
    		for(String s: list){
    			try {
    				List<String> temp = new LinkedList<String>();
				temp = sqlPerson.getDislike(FooD.conn, s);
				for(String roughDis: temp){
					rough.add(roughDis);
				}
			}
    			catch (SQLException e) {
    				//訊息提示
    				Alert info = new Alert(AlertType.INFORMATION);
    				info.setTitle(""); 
    				info.setHeaderText("");
    				info.setContentText("There's something wrong!");
    				info.showAndWait();
    				textArea2.setText("");
			}
    		}
    		rough = FooD.sortRough(rough);
    		try {
			menu = sqlMenu.getMenu(FooD.conn);
		}
    		catch (SQLException e) {
    			//訊息提示
    			Alert info = new Alert(AlertType.INFORMATION);
    			info.setTitle(""); 
    			info.setHeaderText("");
    			info.setContentText("There's something wrong!");
    			info.showAndWait();
    			textArea2.setText("");
		}
    	
    		resultList = FooD.Compare(menu, FooD.sortRough(rough));
    		resultList = FooD.Compare(resultList, food.specialDislike);
    		textArea2.setText("Result for eating :\n" + FooD.randFood(resultList));
	}
	
	@FXML
	public void delDislike(ActionEvent event) throws SQLException{
		try{
			if((!delDis_Name.getText().equals("") ) && (!delDis_Dis.getText().equals(""))){
				boolean delORnot = false;
				try{
					List<String> disList = new LinkedList<String>();
					disList = sqlPerson.getDislike(FooD.conn, delDis_Name.getText());
					for(String s: disList){
						if(delDis_Dis.getText().equals(s))
							delORnot = true;
					}
				}catch(SQLException exc){
					//訊息提示
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle(""); 
					info.setHeaderText("");
					info.setContentText("There's something wrong!");
					info.showAndWait();
					delDis_Name.setText("");
					delDis_Dis.setText("");
				}
				
				if(delORnot == true){
					String name = "'" + delDis_Name.getText() + "'";
					String dis = "'" + delDis_Dis.getText() + "'";
					sqlPerson.deleteDislike(FooD.conn, name, dis);
					//訊息提示
					Alert info = new Alert(AlertType.INFORMATION);
					info.setTitle(""); 
					info.setHeaderText("");
					info.setContentText("成功從" + name + "的資料庫中刪除" + dis);
					info.showAndWait();
					delDis_Name.setText("");
					delDis_Dis.setText("");
				}
			}
		}catch(SQLException e){
			//訊息提示
			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle(""); 
			info.setHeaderText("");
			info.setContentText("There's something wrong!");
			info.showAndWait();
			delDis_Name.setText("");
			delDis_Dis.setText("");
		}
	}
	
	@FXML
	public void delUser(ActionEvent event) throws SQLException{
		try{
			if(!delUser.getText().equals("")){
				String name = "'" + delUser.getText() + "'";
				sqlPerson.deletePerson(FooD.conn, name);
				//訊息提示
				Alert info = new Alert(AlertType.INFORMATION);
				info.setTitle(""); 
				info.setHeaderText("");
				info.setContentText("成功刪除" + name + "的資料");
				info.showAndWait();
				delUser.setText("");
			}
		}catch(SQLException e){
			//訊息提示
			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle(""); 
			info.setHeaderText("");
			info.setContentText("There's something wrong!");
			info.showAndWait();
			delUser.setText("");
		}
	}
	
	@FXML
	public void delMenu(ActionEvent event) throws SQLException{
		try{
			if(!delMenu.getText().equals("")){
				String menu = "'" + delMenu.getText() + "'";
				sqlMenu.deleteMenu(FooD.conn, menu);
				//訊息提示
				Alert info = new Alert(AlertType.INFORMATION);
				info.setTitle(""); 
				info.setHeaderText("");
				info.setContentText("成功刪除" + menu);
				info.showAndWait();
				delMenu.setText("");
			}
		}catch(SQLException e){
			//訊息提示
			Alert info = new Alert(AlertType.INFORMATION);
			info.setTitle(""); 
			info.setHeaderText("");
			info.setContentText("There's something wrong!");
			info.showAndWait();
			delMenu.setText("");
		}
	}
}
	