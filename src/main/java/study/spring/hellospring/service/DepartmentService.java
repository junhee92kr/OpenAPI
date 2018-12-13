package study.spring.hellospring.service;

import java.util.List;
import study.spring.hellospring.model.Department;

/** 학과 관리 기능을 제공하기 위한 Service 계층. */
public interface DepartmentService {
	/**
	 * 학과 목록 조회
	 * @return 조회 결과에 대한 컬렉션
	 * @throws Exception
	 */
	// -> import java.util.List;
	public List<Department> getDepartmentList(Department department) throws Exception;
	
	/**
	 * 학과 페이지 계산
	 * @param professor
	 * @throws Exception
	 */
	public int getDepartmentCount(Department department) throws Exception;
	
	/**
	 * 단일행 조회
	 * @param department
	 * @return
	 * @throws Exception
	 */
	public Department selectDepartmentItem(Department department) throws Exception;
	
	/**
	 * 학과 조회
	 * @param department
	 * @throws Exception
	 */
	public void addDepartment(Department department) throws Exception;
	
	/**
	 * 학과 삭제
	 * @param department
	 * @throws Exception
	 */
	public void deleteDepartment(Department department) throws Exception;
	
	/**
	 * 학과 수정
	 * @param department
	 * @throws Exception
	 */
	public void editDepartment(Department department) throws Exception;
}
