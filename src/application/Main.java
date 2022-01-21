package application;
	
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.lang.IllegalStateException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.lang.Math;
import java.nio.file.Paths;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;






public class Main extends Application {

	    @Override
	    public void start(Stage primaryStage) {
			//Declare necessary variables
	    	int canvaswidth = 600;
	    	int canvasheight = 600;
	       	int radius= canvasheight/4;
	    	MyPoint center = new MyPoint(canvaswidth/2,canvasheight/2,MyColor.BLACK);	
	    	
	    	Group root = new Group();
	    	Canvas canvas = new Canvas(canvaswidth, canvasheight);
	        GraphicsContext gc = canvas.getGraphicsContext2D();
	        
	    	String url= "jdbc:sqlserver://DESKTOP-SIO509H\\MSSQLSERVER:1433;databaseName=Student";
	    	
	    	
	    	try {
	    		//INSTANTIATE DATABASE CLASS/ CONNECT.
				Database DB = new Database(url, "sa","data");	
				
				//****SCHEDULE TABLE****
				//SQL statement to create table
				String sqlTable = "CREATE TABLE Schedule("
	    								+ "courseID CHAR(12) NOT NULL UNIQUE, "
	    								+ "sectionNo VARCHAR(8) NOT NULL UNIQUE, "
	    								+ "title VARCHAR(64),"
	    								+ "year INT,"
	    								+ "semester CHAR(6),"
	    								+ "instructor VARCHAR(40),"
	    								+ "department VARCHAR(26),"
	    								+ "program VARCHAR(70) "
	    								+ "PRIMARY KEY(courseID, sectionNo))";
				//SQL Insert statement
				String table = "Schedule";
				String filename = "\"C:\\Users\\alant\\Desktop\\sql\\ScheduleSpring2021-Copy.txt\"";
				String sqlInsert = "BULK INSERT " + table + " FROM " +filename +
									" WITH (FIELDTERMINATOR = '\t', ROWTERMINATOR = '\n', FIRSTROW = 2)";
				
				//Schedule instantiation, creates table and populates it with txt file
				Database.Schedule schedule = DB.new Schedule(sqlTable, sqlInsert);
				
				//****STUDENTS TABLE****
				//Create and Insert statement for Students table
				sqlTable = "CREATE TABLE Students("
							+"empID INT NOT NULL UNIQUE, "
							+"firstName VARCHAR(25) NOT NULL, "
							+ "lastName VARCHAR(30) NOT NULL, "
							+ "email VARCHAR(45),"
							+ "gend CHAR(1) "
							+ "PRIMARY KEY (empID))";
				table = "Students";
				filename = "\"C:\\Users\\alant\\Desktop\\sql\\students2.txt\"";
				sqlInsert = "BULK INSERT " + table + " FROM " +filename +
						" WITH (FIELDTERMINATOR = '\t', ROWTERMINATOR = '\r', FIRSTROW = 1)";			
				Database.Students students = DB.new Students(sqlTable, sqlInsert);   //Instantiation
				
				//****COURSES TABLE****
				//Courses utilizes Schedule table, necessary SQL statements are declared in the constructor
				Database.Courses courses = DB.new Courses();
				
				//****CLASSES TABLE****
				table = "Classes";
				sqlTable = "CREATE TABLE Classes("
							+ "empID INT, "
							+ "courseID CHAR(12) , "
							+ "sectionNo VARCHAR(8), "
							+ "year INT, "
							+ "semester CHAR(6), "
							+ "grade CHAR(1) "
							+ "PRIMARY KEY(empID, courseID))";
				
				Database.Classes classes = DB.new Classes(sqlTable);
				//INSERTING SAMPLE DATA TO CLASSES TABLE
				classes.insert(5,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(6,"22100 R", "32150", 2021, "Spring", "A");
				classes.insert(7,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(8,"22100 R", "32150", 2021, "Spring", "A");
				classes.insert(9,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(10,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(11,"22100 R", "32150", 2021, "Spring", "C");
				classes.insert(12,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(13,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(14,"22100 R", "32150", 2021, "Spring", "C");
				classes.insert(15,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(16,"22100 R", "32150", 2021, "Spring", "C");
				classes.insert(17,"22100 R", "32150", 2021, "Spring", "F");
				classes.insert(18,"22100 R", "32150", 2021, "Spring", "A");
				classes.insert(19,"22100 R", "32150", 2021, "Spring", "D");
				classes.insert(20,"22100 R", "32150", 2021, "Spring", "A");
				classes.insert(21,"22100 R", "32150", 2021, "Spring", "B");
				classes.insert(22,"22100 R", "32150", 2021, "Spring", "W");
				
				classes.delete(17);					//Example using update and delete methods
				classes.update("grade", "A", 21);
				
				//****TABLE AGGREGATEGRADES****
				sqlTable = "CREATE TABLE AggregateGrades("
							+ "grade CHAR PRIMARY KEY,"
							+ "numberStudents INT)";
				sqlInsert = "INSERT INTO AggregateGrades "
							+ "SELECT grade, count(grade) "
							+ "FROM Classes "
							+ "WHERE courseID = '22100 R'"
							+ "GROUP BY grade";
				Database.AggregateGrades cs221 = DB.new AggregateGrades(sqlTable, sqlInsert);
				String sqlQuery = "SELECT * FROM AggregateGrades";
				//getAggregateGrades returns a map, Now we can use this to instantiate Histogram class
				HistogramAlphabet mapgrades = new HistogramAlphabet(cs221.getAgregateGrades(sqlQuery));
				//drawing pie chart		
				HistogramAlphabet.MyPieChart piegrades = mapgrades.new MyPieChart(6,center,radius,0);
				piegrades.draw(gc);
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("error");
				e.printStackTrace();
			}	    	
	    	//Displaying canvas
	        root.getChildren().add(canvas);  
	        primaryStage.setScene(new Scene(root));
	        primaryStage.show();	        
	    }
	    public static void main(String[] args) {
	        //launch canvas
	    	launch(args);	         		    	
	    }
}
