package study.spring.hellospring.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.taglibs.standard.tag.common.core.NullAttributeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.hellospring.model.Department;
import study.spring.hellospring.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	/** MyBatis의 Mapper를 호출하기 위한 SqlSession 객체 */
	// Spring으로 부터 주입받는다.
	// --> import org.springframework.beans.factory.annotation.Autowired;
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Department> getDepartmentList(Department department) throws Exception {
		List<Department> result = null;
		
		try {
			result = sqlSession.selectList("DepartmentMapper.selectDepartmentList", department);
			System.out.println("selectList결과 = " + result);
			if (result == null) {
				throw new NullPointerException();
			}
		}catch (NullPointerException e) {
			throw new Exception("조회된 데이터가 없습니다.");
		}catch (Exception e) {
			throw new Exception("학과 다중 데이터 조회에 실패했습니다.");
		}
		
		return result;
	}
	
	//
	@Override
	public int getDepartmentCount(Department department) throws Exception {
		int result = 0;
		
		try{
			result = sqlSession.selectOne("DepartmentMapper.selectDepartmentCount", department);
		} catch(Exception e){
			throw new Exception("학과 카운트 데이터 조회 실패");
		}
		
		return result;
	}

	@Override
	public Department selectDepartmentItem(Department department) throws Exception {
		Department result = null;
		
		try {
			result = sqlSession.selectOne("DepartmentMapper.selectDepartmentItem", department);
			if(result == null) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("단일행 조회된 데이터가 없습니다.");
		}
		catch(Exception e){
			throw new Exception("학과 단일행 데이터 조회 실패");
		}
		
		return result;
	}

	@Override
	public void addDepartment(Department department) throws Exception {
		int result = 0;
		try {
			result = sqlSession.insert("DepartmentMapper.insertDepartmentItem", department);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("저장할 데이터가 존재하지 않습니다.");
		}catch(Exception e) {
			throw new Exception("학과 데이터 입력 실패");
		}
	}

	@Override
	public void deleteDepartment(Department department) throws Exception {
		try {
			int result = sqlSession.delete("DepartmentMapper.deleteDepartmentItem", department);
			if(result == 0) {
				throw new NullPointerException();
			}
		}catch(NullPointerException e) {
			throw new Exception("삭제할 데이터가 존재하지 않습니다.");
		}catch(Exception e) {
			throw new Exception("학과 데이터 삭제 실패");
		}
		
	}

	@Override
	public void editDepartment(Department department) throws Exception {
		try {
			int result = sqlSession.update("DepartmentMapper.updateDepartmentItem", department);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			throw new Exception("학과 데이터 수정에 실패했습니다.");
		}
	}
}
