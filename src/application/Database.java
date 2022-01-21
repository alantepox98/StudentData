package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java.lang.Math;
import java.lang.String;
import java.util.Optional;
import java.util.Random;



public class Database {
	
	String url;
	String username;
	String password;
	Connection connection;
	ResultSet RS;
	
	Map<Character, Integer> grades = new HashMap<Character,Integer>();
	
	//CONSTRUCTOR, ESTABLISH CONNECTION
	Database(String url, String username, String password) throws SQLException{
		this.url = url;
		this.username = username;
		this.password = password;
		this.connection = getConnection(url, username, password);
	}
	
	//METHOD USED TO CONNECT TO DATABASE, CALLED IN THE CONSTRUCTOR
	public Connection getConnection(String url, String username, String password) throws SQLException{
		Connection connection = null;		
		try {
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("connected to server");
		}
		catch(SQLException e){
			System.out.print("error");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	//INNER CLASS SCHEDULE
	class Schedule{
		String sqlTable;
		String sqlInsert;
		
		Schedule(String sqlTable, String sqlInsert){
			this.sqlTable= sqlTable;
			try {
				connection.prepareStatement(sqlTable).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sqlInsert = sqlInsert;
			
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		//insert record
		public void insert(String courseID, String sectionNo, String title, int year, 
				String semester, String instructor,String department, String program) {
			sqlInsert  = "INSERT INTO Schedule (courseID, sectionNo, title, year, semester, department)"
					+ " VALUES ( '"+courseID+"', '" + sectionNo+"' , '" + title+"' , " + year+" , '" 
					+ semester + "','" +instructor+"','"+ department+"','"+program+"');";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}		
		//Update Instructor
		public void updateInstr(String instruct, String courseID ) {
			sqlInsert = "UPDATE Schedule "
						+ "SET instructor = '" + instruct+"'"
						+ "WHERE courseID ='"+ courseID+"';";
			try {
			 	connection.prepareStatement(sqlInsert).executeUpdate(); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		//Delete record
		public void delete(String courseID ) {
			sqlInsert = "DELETE FROM Schedule "
						+ "WHERE courseID = '" + courseID+"'";

			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	class Students{
		String sqlTable;
		String sqlInsert;
		
		Students(String sqlTable){
			this.sqlTable = sqlTable;
			try {
				connection.prepareStatement(sqlTable).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		Students(String sqlTable, String sqlInsert){
			this.sqlTable= sqlTable;
			this.sqlInsert = sqlInsert;
			try {
				connection.prepareStatement(sqlTable).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sqlInsert = sqlInsert;
			
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void insert(int ID, String first, String last, String email, String gender) {
			sqlInsert  = "INSERT INTO Students (empID, firstName, lastName,email, gend)"
					+ " VALUES ( "+ID+", '" + first+"' , '" + last+"' , '" + email+"' , '" + gender + "');";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		public void update(String column, String newVal, int empID ) {
			sqlInsert = "UPDATE Students "
						+ "SET "+column+" = '" + newVal+"'"
						+ "WHERE empID ="+ empID+";";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		public void delete(int id) {
			sqlInsert = "DELETE FROM Students "
						+ "WHERE empID = " + id+"";

			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}

	}
	class Courses{
		String sqlTable;
		String sqlInsert;
		Courses(){
			sqlTable= "CREATE TABLE Courses ("
						+ "courseID CHAR(12) NOT NULL UNIQUE, "
						+ "title VARCHAR(64), "
						+ "department VARCHAR(26) "
						+ "PRIMARY KEY(courseID))";
			try {
				connection.prepareStatement(sqlTable).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sqlInsert ="INSERT INTO Courses ("
						+ "courseID, title, department) "
						+ "SELECT courseID, title, department "
						+ "FROM Schedule;";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public void insert(String courseID, String title, String department) {
			sqlInsert  = "INSERT INTO Courses(courseID, title, department)"
					+ " VALUES ('"+courseID+"', '" + title+"','" + department + "');";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		public void update(String column, String newVal, String courseID ) {
			sqlInsert = "UPDATE Courses "
						+ "SET "+column+" = '" + newVal+"'"
						+ "WHERE courseID ='"+ courseID+"';";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		public void delete(String courseid) {
			sqlInsert = "DELETE FROM Courses "
						+ "WHERE courseID = " + courseid+"";

			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	class Classes{
		String sqlTable;
		String sqlInsert;
		Classes(String sqlTable, String sqlInsert){
			this.sqlTable= sqlTable;
			this.sqlInsert = sqlInsert;
			try {
				connection.prepareStatement(sqlTable).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.sqlInsert = sqlInsert;
			
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Classes(String sqlTable){
			this.sqlTable = sqlTable;
			try {
				connection.prepareStatement(sqlTable).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		public void insert(int ID, String courseID, String sectionNo, int year, String semester, String grade) {
			sqlInsert  = "INSERT INTO Classes (empID, courseID, sectionNo, year, semester, grade)"
					+ " VALUES ( "+ID+", '" + courseID+"' , '" + sectionNo+"' , " + year+" , '" + semester + "' , '"+ grade+"');";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}                
		public void update(String column, String newVal, int empID ) {
			sqlInsert = "UPDATE Classes "
						+ "SET "+column+" = '" + newVal+"'"
						+ "WHERE empID ="+ empID+";";
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		public void delete(int id) {
			sqlInsert = "DELETE FROM Classes "
						+ "WHERE empID = " + id+"";

			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
	}
	class AggregateGrades{
		String sqlTable;
		String sqlInsert;
		//Constructor creates table and fills table with data
		AggregateGrades(String sqlTable,String sqlInsert){
			this.sqlTable = sqlTable;
			try {
				connection.prepareStatement(sqlTable).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.sqlInsert = sqlInsert;
			
			try {
				connection.prepareStatement(sqlInsert).executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		public Map<Character,Integer> getAgregateGrades(String sqlQuery){
			Map<Character,Integer> mapAgregateGrades = new HashMap<Character,Integer>();
			String sqlQueryAgrGrds = sqlQuery;
			try {
				ResultSet RS = connection.prepareStatement(sqlQueryAgrGrds).executeQuery();
				while(RS.next()) {
					mapAgregateGrades.put(RS.getString("grade").charAt(0), RS.getInt("numberStudents"));
			
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return mapAgregateGrades;
		}
	}
}
