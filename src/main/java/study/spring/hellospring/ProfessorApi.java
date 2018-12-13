package study.spring.hellospring;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.spring.hellospring.model.Professor;
import study.spring.hellospring.service.ProfessorService;
import study.spring.helper.WebHelper;

@Controller
public class ProfessorApi{
	private static final Logger logger = LoggerFactory.getLogger(ProfessorApi.class);
	
	//객체주입 설정
	@Autowired
	WebHelper web;
	
	@Autowired
	ProfessorService professorService;
	
	/** 교수목록 Api */
	@ResponseBody
	@RequestMapping(value = "/professor_api/ProfessorSelectListApi", method = RequestMethod.GET)
	public void ProfessorSelectListApi(Locale locale, Model model, HttpServletResponse response) {
		//webhelper초기화
		web.init();
		//컨텐츠 형식 지정
		response.setContentType("application/json");
		
		List<Professor> item = null;
		try {
			item = professorService.getProfessorList(null);
		}catch(Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 교수 상세조회 Api */
	@ResponseBody
	@RequestMapping(value = "/professor_api/ProfessorSelectItemApi", method = RequestMethod.GET)
	public void ProfessorSelectItemApi(Locale locale, Model model, HttpServletResponse response) {
		//webhelper초기화
		web.init();
		//컨텐츠 형식 지정
		response.setContentType("application/json");
		
		int profno = web.getInt("profno");
		logger.debug("profno=" + profno);
		if(profno == 0) {
			web.printJsonRt("교수번호가 없습니다.");
			return;
		}
		
		Professor professor = new Professor();
		professor.setProfno(profno);
		
		Professor item = null;
		try {
			item = professorService.getProfessorItem(professor);
		}catch(Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return;
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/** 교수 정보등록 Api */
	@ResponseBody
	@RequestMapping(value = "/professor_api/ProfessorInsertApi", method = RequestMethod.POST)
	public void ProfessorInsertApi(Locale locale, Model model, HttpServletResponse response) {
		//webhelper초기화
		web.init();
		//컨텐츠 형식 지정
		response.setContentType("application/json");
		
		String name = web.getString("name");
		String userid = web.getString("userid");
		String position= web.getString("position");
		int sal = web.getInt("sal");
		String hiredate= web.getString("hiredate");
		int comm = web.getInt("comm");
		int deptno = web.getInt("deptno");
		
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("position=" + position);
		logger.debug("sal=" + sal);
		logger.debug("hiredate=" + hiredate);
		logger.debug("comm=" + comm);
		logger.debug("deptno=" + deptno);
		
		if(name == null) { web.printJsonRt("이름를 입력하세요."); return; }
		if(userid == null) { web.printJsonRt("아이디를 입력하세요."); return; }
		if(position == null) { web.printJsonRt("직위를 입력하세요."); return; }
		if(sal == 0) { web.printJsonRt("임금을 입력하세요."); return; }
		if(hiredate == null) { web.printJsonRt("입사일을 입력하세요."); return; }
		if(comm == 0) { web.printJsonRt("커미션을 입력하세요."); return; }
		if(deptno == 0) { web.printJsonRt("학과번호를 입력하세요."); return; }
		
		Professor professor = new Professor();
		professor.setName(name);
		professor.setUserid(userid);
		professor.setPosition(position);
		professor.setSal(sal);
		professor.setHiredate(hiredate);
		professor.setComm(comm);
		professor.setDeptno(deptno);
		
		try {
			professorService.addProfessor(professor);
		}catch(Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return;
		}
		
		web.printJsonRt("OK");
	}
	
	/** 교수 삭제 Api */
	@ResponseBody
	@RequestMapping(value = "/professor_api/ProfessorDeleteApi", method = RequestMethod.POST)
	public void ProfessorDeleteApi(Locale locale, Model model, HttpServletResponse response) {
		//webhelper초기화
		web.init();
		//컨텐츠 형식 지정
		response.setContentType("application/json");
		
		int profno = web.getInt("profno");
		logger.debug("profno=" + profno);
		
		if(profno == 0) {
			web.printJsonRt("삭제할 교수번호가 없습니다.");
			return;
		}
		
		Professor professor = new Professor();
		professor.setProfno(profno);
		
		try {
			professorService.deleteProfessor(professor);
		}catch(Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return;
		}
		
		web.printJsonRt("OK");
	}
	
	/** 교수 수정 Api */
	@ResponseBody
	@RequestMapping(value = "/professor_api/ProfessorEditApi", method = RequestMethod.POST)
	public void ProfessorEditApi(Locale locale, Model model, HttpServletResponse response) {
		//webhelper초기화
		web.init();
		//컨텐츠 형식 지정
		response.setContentType("application/json");
		
		int profno = web.getInt("profno");
		String name = web.getString("name");
		String userid = web.getString("userid");
		String position= web.getString("position");
		int sal = web.getInt("sal");
		String hiredate= web.getString("hiredate");
		int comm = web.getInt("comm");
		int deptno = web.getInt("deptno");
		
		System.out.println("교수번호=" + profno);
		logger.debug("profno=" + profno);
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("position=" + position);
		logger.debug("sal=" + sal);
		logger.debug("hiredate=" + hiredate);
		logger.debug("comm=" + comm);
		logger.debug("deptno=" + deptno);
		
		if(profno == 0) { web.printJsonRt("교수번호가 없습니다."); return; }
		if(name == null) { web.printJsonRt("이름를 입력하세요."); return; }
		if(userid == null) { web.printJsonRt("아이디를 입력하세요."); return; }
		if(position == null) { web.printJsonRt("직위를 입력하세요."); return; }
		if(sal == 0) { web.printJsonRt("임금을 입력하세요."); return; }
		if(hiredate == null) { web.printJsonRt("입사일을 입력하세요."); return; }
		if(comm == 0) { web.printJsonRt("커미션을 입력하세요."); return; }
		if(deptno == 0) { web.printJsonRt("학과번호를 입력하세요."); return; }
		
		Professor professor = new Professor();
		professor.setProfno(profno);
		professor.setName(name);
		professor.setUserid(userid);
		professor.setPosition(position);
		professor.setSal(sal);
		professor.setHiredate(hiredate);
		professor.setComm(comm);
		professor.setDeptno(deptno);
		
		try {
			professorService.editProfessor(professor);
		}catch(Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return;
		}
		
		web.printJsonRt("OK");
	}
}