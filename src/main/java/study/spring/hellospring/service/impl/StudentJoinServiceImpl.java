package study.spring.hellospring.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.hellospring.model.StudentDepartment;
import study.spring.hellospring.service.StudentJoinService;

@Service
public class StudentJoinServiceImpl implements StudentJoinService{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<StudentDepartment> getStudentJoinList(StudentDepartment student) throws Exception {
		List<StudentDepartment> result = null;
		try{
			result = sqlSession.selectList("StudentJoinMapper.selectStudentJoinList", student);
			
			if(result == null){
				System.out.println("구현체 테스트 오류=" + result);
				throw new NullPointerException();
			}
		}catch(NullPointerException e){
			throw new Exception("조회된 데이터 없습니다.");
		}catch(Exception e){
			throw new Exception("데이터 다중조회에 실패함.");
		}
		return result;
	}

	@Override
	public StudentDepartment getStudentJoinItem(StudentDepartment student) throws Exception {
		StudentDepartment result = null;
		
		try{
			result = sqlSession.selectOne("StudentJoinMapper.selectStudentJoinItem", student);
			if(result == null){
				throw new NullPointerException();
			}
		}catch(NullPointerException e){
			throw new Exception("조회된 데이터 없습니다.");
		}catch(Exception e){
			throw new Exception("데이터 단일조회에 실패함.");
		}
		return result;
	}

	@Override
	public int getStudentCount(StudentDepartment student) throws Exception {
		int result = 0;
		
		try{
			result = sqlSession.selectOne("StudentJoinMapper.selectStudentCount", student);
		} catch(Exception e){
			throw new Exception("학생 데이터 조회 실패");
		}
		
		return result;
	}
	
}