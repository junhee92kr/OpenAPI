package study.spring.hellospring.service;

import java.util.List;

import study.spring.hellospring.model.Student;

public interface StudentService {
	
	/** 학생 추가 */
	public void addStudent(Student student) throws Exception;
	
	/** 학생 수정 */
	public void editStudent(Student student) throws Exception;
	
	/** 학생 삭제 */
	public void deleteStudent(Student student) throws Exception;
	
	/** 학생 단일행 조회 */
	public Student getStudentItem(Student student) throws Exception;
	
	/** 학생 다중행 조회 */
	public List<Student> getStudentList(Student student) throws Exception;
	
}
