package study.spring.hellospring.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import study.spring.hellospring.model.Student;
import study.spring.hellospring.service.StudentService;


@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	SqlSession sqlSession;
	

	@Override
	public void addStudent(Student student) throws Exception {
		try{
			int result = sqlSession.insert("StudentMapper.insertStudentItem", student);
			if(result == 0){
				throw new NullPointerException();
			}
		}catch(NullPointerException e){
			throw new Exception("저장된 데이터 없음.");
		}catch(Exception e){
			throw new Exception("데이터 저장 실패!!");
		}
	}

	@Override
	public void editStudent(Student student) throws Exception {
		try{
			int result = sqlSession.update("StudentMapper.updateStudentItem", student);
			if(result == 0){
				throw new NullPointerException();
			}
		}catch(NullPointerException e){
			throw new Exception("수정된 데이터 없음..");
		}catch(Exception e){
			throw new Exception("데이터 수정 실패!!");
		}	
	}
	@Override
	public void deleteStudent(Student student) throws Exception {
		try{
			int result = sqlSession.delete("StudentMapper.deleteStudentItem", student);
			if(result == 0){
				throw new NullPointerException();
			}
		}catch(NullPointerException e){
			throw new Exception("삭제 데이터 없음.");
		}catch(Exception e){
			throw new Exception("데이터 삭제 실패!!");
		}
	}
	@Override
	public Student getStudentItem(Student student) throws Exception {
		Student result = null;
		try{
			result = sqlSession.selectOne("StudentMapper.selectStudentItem", student);
			if(result == null){
				throw new NullPointerException();
			}
		}catch(NullPointerException e){
			throw new Exception("조회된 데이터 없음.");
		}catch(Exception e){
			throw new Exception("데이터 조회 실패!!");
		}
		return result;
	}
	@Override
	public List<Student> getStudentList(Student student) throws Exception {
		List<Student> result = null;
		try{
			result = sqlSession.selectList("StudentMapper.selectStudentList", student);
			if(result == null){
				throw new NullPointerException();
			}
		} catch(NullPointerException e){
			throw new Exception("조회된 데이터 없음.");
		} catch(Exception e){
			throw new Exception("데이터 조회 실패!!");
		}
		return result;
	}	
}