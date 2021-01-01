import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import javax.faces.bean.ManagedBean;



@ManagedBean()
public class StudentDbUtil {
	
private static DataSource dataSource;
	


	
	public DataSource getDataSource() throws NamingException{
		String jndi="java:comp/env/jdbc/studentdb" ;
		Context context = new InitialContext();
		DataSource dataSource = (DataSource) 
	    context.lookup(jndi);
		return dataSource;
	}
	
	public StudentDbUtil()throws NamingException{  
		dataSource = getDataSource();
	}

	
	public List<Student> getStudents() throws Exception {
		List<Student> students= new ArrayList<Student>();
		Connection myConn=null;
		Statement myStmt = null;
		ResultSet myRs= null;
		try {
			myConn =dataSource.getConnection();
			myStmt= myConn.createStatement();
			String sql= "select * from student order by last_name";
			myRs = myStmt.executeQuery(sql);
			while(myRs.next()){
			int id = myRs.getInt("id");
			String firstName=myRs.getString("first_name");
			String lastName=myRs.getString("last_name");
			String email = myRs.getString("email");
			Student tempStudent= new Student(id,firstName,lastName,email);
			students.add(tempStudent);
		}
		return students;
		} finally{
			close(myConn,myStmt,myRs);
		}
	}
	
	public void addStudent(Student s) {
		Connection myConn = null;
		Statement myStmt = null;
		try {
			myConn =  dataSource.getConnection();
			myStmt =  myConn.createStatement();
			String sql = "INSERT INTO student (first_name,last_name,email)values('"+s.firstName+"','"+s.lastName+"','"+s.email+"');";
			myStmt.executeUpdate(sql);
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			close(myConn, myStmt, null);
		}
	}
	
	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try{
			if(myStmt!=null)
				myStmt.close();
			if(myRs!=null)
				myRs.close();
			if(myConn!=null)
				myConn.close();
		}catch(Exception e){
		System.out.println(e.getMessage());
		}
	}
	
	public Student fetchStudent(int id) {
		Connection myConn=null;
		Statement myStmt = null;
		ResultSet myRs= null;
		Student student=null;
		try {
			myConn = dataSource.getConnection();
			myStmt=  myConn.createStatement();
			String sql= "select * from student where id="+id;
			myRs =  myStmt.executeQuery(sql);
		while(myRs.next()){
			String firstName=myRs.getString("first_name");
			String lastName=myRs.getString("last_name");
			String email = myRs.getString("email");
			student = new Student(id,firstName,lastName,email);
		}
		return student;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return null;
		} finally{
			close(myConn,myStmt,myRs);
		}
		}

	public void updateStudent(Student student) {
		Connection myConn=null;
		PreparedStatement myStmt = null;
		try {
			myConn =  dataSource.getConnection();
			String sql = "update student set first_name=?, last_name=?,email=? where id=?";
			myStmt = myConn.prepareStatement(sql);
			myStmt.setString(1, student.getFirstName());
			myStmt.setString(2, student.getLastName());
			myStmt.setString(3, student.getEmail());
			myStmt.setInt(4,student.getId());
			myStmt.execute();
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		finally{
			close(myConn, myStmt,null);
		}
	}

	
	public void deleteStudent(Student s)  {
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			myConn = dataSource.getConnection();
			String sql = "delete from student where id="+s.id+";";
			myStmt = myConn.prepareStatement(sql);
			myStmt.execute();
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		finally {
			close(myConn, myStmt, null);
		}
	}
	

}
