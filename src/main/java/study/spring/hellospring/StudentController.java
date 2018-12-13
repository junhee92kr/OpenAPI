package study.spring.hellospring;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import study.spring.hellospring.model.Department;
import study.spring.hellospring.model.Professor;
import study.spring.hellospring.model.ProfessorDepartment;
import study.spring.hellospring.model.Student;
import study.spring.hellospring.model.StudentDepartment;
import study.spring.hellospring.service.DepartmentService;
import study.spring.hellospring.service.ProfessorService;
import study.spring.hellospring.service.StudentJoinService;
import study.spring.hellospring.service.StudentService;
import study.spring.helper.PageHelper;
import study.spring.helper.WebHelper;


@Controller
public class StudentController{
	/** log4j 객체생성 및 사용할 객체 주입받기 */
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	WebHelper web;
	
	@Autowired
	PageHelper page;
	
	//목록, 상세보기에서 사용할 서비스객체 -> department와의 join처리
	@Autowired
	StudentJoinService studentJoinService;
	
	//등록,삭제,수정에 사용
	@Autowired
	StudentService studentService;
	
	//등록,수정시에 소속학과에 대한 드롭다운 구현을 위함.
	@Autowired
	DepartmentService departmentService;
	
	@Autowired
	ProfessorService professorService;
	
	/** 학생 목록 페이지 */
	@RequestMapping(value = "/student/stud_list.do", method = RequestMethod.GET)
	public ModelAndView StudList(Locale locale, Model model) {
		
		/** 1. WebHelper초기화 및 파라미터 처리 */
		web.init();
		
		//파라미터를 저장할 beans
		StudentDepartment student = new StudentDepartment();
		//검색어 파라미터 받기 + beans 설정
		String keyword = web.getString("keyword", "");
		student.setName(keyword);
		
		//현재 페이지번호 파라미터 받기
		int nowPage = web.getInt("page", 1);
		
		/** 2. 페이지 번호 구현 */
		//전체 데이터수 조회
		int totalCount = 0;

		try {
			totalCount = studentJoinService.getStudentCount(student);
			System.out.println("카운트=" + totalCount);
		}catch(Exception e) {
			
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		//페이지 번호에 대한 연산수행 후, 조회조건 값 지정을 beans에 추가
		page.pageProcess(nowPage, totalCount, 10, 5);
		student.setLimitStart(page.getLimitStart());
		student.setListCount(page.getListCount());
		System.out.println("학생페이지번호 검사=" + student);
		
		/** 3. service를 통한 sql수행 */
		//조회결과를 저장하기 위함
		
		List<StudentDepartment> list = null;
		//List<Student> list = null;
		System.out.println("리스트 바깥에");
		try {
			list = studentJoinService.getStudentJoinList(student);
			//list = studentService.getStudentList(student);
			System.out.println("리스트 try문 안에 = " + list);
		}catch(Exception e) {
			web.redirect(null, e.getLocalizedMessage());
		}
		System.out.println("리스트 오류구문 뒤" + list);
		/** 4. view 처리하기 */
		model.addAttribute("list", list);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		
		return new ModelAndView("student/stud_list");
	}
	
	/** 학생정보 상세보기 페이지 */
	@RequestMapping(value = "/student/stud_view.do", method = RequestMethod.GET)
	public ModelAndView ProfView(Locale locale, Model model) {
		web.init();
		
		int studno = web.getInt("studno");
		logger.debug("studno=" + studno);

		if(studno == 0) {
			return web.redirect(null, "학생번호가 없습니다.");
		}

		
		StudentDepartment student = new StudentDepartment();
		student.setStudno(studno);
		
		StudentDepartment item = null;
		try {
			item = studentJoinService.getStudentJoinItem(student);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("item", item);
		
		return new ModelAndView("student/stud_view");
	
	}
	
	/** 학생 등록 페이지 */
	@RequestMapping(value = "/student/stud_add.do", method = RequestMethod.GET)
	public ModelAndView StudAdd(Locale locale, Model model) {
		web.init();
		
		//조회결과 저장
		List<Department> deptList = null;
		//List<StudentDepartment> deptList = null;
		List<Professor> profList = null;
		//List<StudentDepartment> studProfList = null;
		try {
			deptList = departmentService.getDepartmentList(null);
			//deptList = studentJoinService.getStudentJoinList(null);
			profList = professorService.getProfessorList(null);
			
			System.out.println("교수목록=" + profList);
			
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("profList", profList);
		return new ModelAndView("student/stud_add");
	}
	
	/** 학생 등록 처리 페이지(action 페이지로 사용된다.) */
	@RequestMapping(value = "/student/stud_add_ok.do", method = RequestMethod.POST)
	public ModelAndView StudAddOk(Locale locale, Model model) {
		web.init();
		
		// input 태그의 name속성에 명시된 값을 사용한다.
		String name = web.getString("name");
		String userid = web.getString("userid");
		int grade = web.getInt("grade");
		String idnum = web.getString("idnum");
		String birthdate = web.getString("birthdate");
		String tel = web.getString("tel");
		int height = web.getInt("height");
		int weight = web.getInt("weight");
		int deptno = web.getInt("deptno");
		int profno = web.getInt("profno");
		System.out.println("교수번호는=" + profno);
		// 전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("grade=" + grade);
		logger.debug("idnum=" + idnum);
		logger.debug("birthdate=" + birthdate);
		logger.debug("tel=" + tel);
		logger.debug("deptno=" + deptno);
		logger.debug("테스트profno=" + profno);
		
		
		/** 2) 필수항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
		if (name == null) 		{ return web.redirect(null, "이름을 입력하세요."); }
		if (userid == null) 	{ return web.redirect(null, "아이디를 입력하세요."); }
		if (grade == 0) 	{ return web.redirect(null, "학년을 입력하세요."); }
		if (idnum == null) 			{ return web.redirect(null, "학번을 입력하세요."); } 
		if (birthdate == null) 	{ return web.redirect(null, "생년월일을 입력하세요."); }
		if (tel == null) 		{ return web.redirect(null, "전화번호를 입력하세요."); }
		if (height == 0) 		{ return web.redirect(null, "키를 입력하세요."); }
		if (weight == 0) 		{ return web.redirect(null, "몸무게를 입력하세요."); }
		if (deptno == 0) 		{ return web.redirect(null, "학과번호를 입력하세요."); }
		if (profno == 0) 		{ return web.redirect(null, "교수를 입력하세요."); }
		
		/** 3) 저장을 위한 JavaBeans 구성하기 */
		// --> study.jsp.myschool.model.Professor
		Student student = new Student();
		
		student.setName(name);
		student.setUserid(userid);
		student.setGrade(grade);
		student.setIdnum(idnum);
		student.setBirthdate(birthdate);
		student.setTel(tel);
		student.setHeight(height);
		student.setWeight(weight);
		student.setDeptno(deptno);
		student.setProfno(profno);
		
		System.out.println("학생리스트 테스트"+student);
		
		try {
			studentService.addStudent(student);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		String url = web.getRootPath() + "/student/stud_view.do?studno=" + student.getStudno();
		//+ "&profno=" + student.getProfno();
		return web.redirect(url, "저장되었습니다.");
	}
	
	/** 학생정보 삭제 페이지 */
	@RequestMapping(value = "/student/stud_delete.do", method = RequestMethod.GET)
	public ModelAndView StudDelete(Locale locale, Model model) {
		web.init();
		
		int studno = web.getInt("studno");
		logger.debug("studno=" + studno);
		
		if(studno == 0) {
			return web.redirect(null, "학생번호가 없습니다.");
		}
		
		Student student = new Student();
		student.setStudno(studno);
		
		try {
			studentService.deleteStudent(student); 
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		return web.redirect(web.getRootPath() + "/student/stud_list.do", "삭제되었습니다.");
	}
	
	/** 교수정보 수정 페이지 */
	@RequestMapping(value = "/student/stud_edit.do", method = RequestMethod.GET)
	public ModelAndView StudEdit(Locale locale, Model model) {
		web.init();
		
		int studno = web.getInt("studno");
		logger.debug("studno=" + studno);
		
		if(studno == 0) {
			return web.redirect(null, "학생번호가 없습니다.");
		}
		
		StudentDepartment student = new StudentDepartment();
		student.setStudno(studno);
		
		//조회결과를 저장하기 위한 객체
		StudentDepartment item = null;
		List<Department> deptList = null;
		
		try {
			item = studentJoinService.getStudentJoinItem(student);
			deptList = departmentService.getDepartmentList(null);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		System.out.println("아이템=" + item);
		model.addAttribute("item", item);
		model.addAttribute("deptList", deptList);
		
		return new ModelAndView("student/stud_edit");
	}
	
	/** 교수정보 수정 처리 페이지(action 페이지로 사용) */
	@RequestMapping(value = "/student/stud_edit_ok.do", method = RequestMethod.POST)
	public ModelAndView StudEditOk(Locale locale, Model model) {
		web.init();
		
		// input 태그의 name속성에 명시된 값을 사용한다.
		int studno = web.getInt("studno");
		String name = web.getString("name");
		String userid = web.getString("userid");
		int grade = web.getInt("grade");
		String idnum = web.getString("idnum");
		String birthdate = web.getString("birthdate");
		String tel = web.getString("tel");
		int height = web.getInt("height");
		int weight = web.getInt("weight");
		int deptno = web.getInt("deptno");
		int profno = web.getInt("profno");
		
		// 전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("studno=" + studno);
		logger.debug("name=" + name);
		logger.debug("userid=" + userid);
		logger.debug("grade=" + grade);
		logger.debug("idnum=" + idnum);
		logger.debug("birthdate=" + birthdate);
		logger.debug("tel=" + tel);
		logger.debug("deptno=" + deptno);
		logger.debug("profno=" + profno);
		
		
		/** 2) 필수항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
		if (name == null) 		{ return web.redirect(null, "이름을 입력하세요."); }
		if (userid == null) 	{ return web.redirect(null, "아이디를 입력하세요."); }
		if (grade == 0) 	{ return web.redirect(null, "학년을 입력하세요."); }
		if (idnum == null) 			{ return web.redirect(null, "학번을 입력하세요."); } 
		if (birthdate == null) 	{ return web.redirect(null, "생년월일을 입력하세요."); }
		if (tel == null) 		{ return web.redirect(null, "전화번호를 입력하세요."); }
		if (height == 0) 		{ return web.redirect(null, "키를 입력하세요."); }
		if (weight == 0) 		{ return web.redirect(null, "몸무게를 입력하세요."); }
		if (deptno == 0) 		{ return web.redirect(null, "(수정)학과번호를 입력하세요."); }
		
		/** 3) 저장을 위한 JavaBeans 구성하기 */
		// --> study.jsp.myschool.model.Professor
		Student student = new Student();
		student.setStudno(studno);
		student.setName(name);
		student.setUserid(userid);
		student.setGrade(grade);
		student.setIdnum(idnum);
		student.setBirthdate(birthdate);
		student.setTel(tel);
		
		student.setHeight(height);
		student.setWeight(weight);
		student.setDeptno(deptno);
		student.setProfno(profno);
		try {
			studentService.editStudent(student);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		String url = web.getRootPath() + "/student/stud_view.do?studno=" + student.getStudno();
		return web.redirect(url, "수정되었습니다.");
	}
}