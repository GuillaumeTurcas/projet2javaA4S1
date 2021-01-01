import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;

@ManagedBean()
public class StudentManager {
	List<Student> listStudents;
	StudentDbUtil util;
	
	public StudentManager() throws Exception{
		listStudents = new ArrayList<Student>();
		util = new StudentDbUtil();
	}
	
	public List<Student> getList() throws Exception {
		listStudents = util.getStudents();
		return listStudents;
	}
	
	public void setList(ArrayList<Student> students) {
		 listStudents = students;
	}
	
	public void loadStudent() throws Exception{
		listStudents = util.getStudents(); 
	}

	public String addStudent(Student stu) {
		util.addStudent(stu);
		return "List-student";
	}
	
	public String loadStudent(int ids) {
		util.fetchStudent(ids);
		return "Edit-student";
	}
	
	public String updatesStudent(Student stu) {
		util.updateStudent(stu);
		return "List-student";
	}
	
	public String deleteStudent(Student s) {
		util.deleteStudent(s);
		return "List-student";
	}
	
	
	

}
