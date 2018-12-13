package study.spring.hellospring.service;

import java.util.List;

import study.spring.hellospring.model.StudentDepartment;

public interface StudentJoinService {
	/**
	 * 
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public StudentDepartment getStudentJoinItem(StudentDepartment student)
		throws Exception;
	
	/**
	 * 
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public List<StudentDepartment> getStudentJoinList(StudentDepartment student)
			throws Exception;
	
	/**
	 * 
	 * @param student
	 * @return
	 * @throws Exception
	 */
	public int getStudentCount(StudentDepartment student) throws Exception;

}
