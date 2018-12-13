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
import study.spring.hellospring.service.DepartmentService;
import study.spring.hellospring.service.ProfessorJoinService;
import study.spring.hellospring.service.ProfessorService;
import study.spring.helper.PageHelper;
import study.spring.helper.WebHelper;

@Controller
public class DepartmentController{
	/** log4j 객체생성 및 사용할 객체 주입받기 */
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	WebHelper web;
	@Autowired
	PageHelper page;
	
	//목록, 상세보기에서 사용할 서비스객체
	@Autowired
	ProfessorJoinService professorJoinService;
	
	//등록,삭제,수정에 사용
	@Autowired
	ProfessorService professorService;
	
	//등록,수정시에 소속학과에 대한 드롭다운 구현을 위함.
	@Autowired
	DepartmentService departmentService;
	
	/** 교수목록 페이지 */
	@RequestMapping(value = "/department/dept_list.do", method = RequestMethod.GET)
	public ModelAndView DeptList(Locale locale, Model model) {
		/** 1. WebHelper초기화 및 파라미터 처리 */
		web.init();
		
		//파라미터를 저장할 beans
		Department department = new Department();
		//검색어 파라미터 받기 + beans 설정
		String keyword = web.getString("keyword", "");
		department.setDname(keyword);
		
		//현재 페이지번호 파라미터 받기
		int nowPage = web.getInt("page", 1);
		
		/** 2. 페이지 번호 구현 */
		//전체 데이터수 조회
		int totalCount = 0;
		try {
			System.out.println("테스트용");
			totalCount = departmentService.getDepartmentCount(department);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		//페이지 번호에 대한 연산수행 후, 조회조건 값 지정을 beans에 추가
		page.pageProcess(nowPage, totalCount, 10, 5);
		department.setLimitStart(page.getLimitStart());
		department.setListCount(page.getListCount());
		
		/** 3. service를 통한 sql수행 */
		//조회결과를 저장하기 위함
		List<Department> deptList = null;
		try {
			deptList = departmentService.getDepartmentList(department);
		}catch(Exception e) {
			web.redirect(null, e.getLocalizedMessage());
		}
		
		/** 4. view 처리하기 */
		model.addAttribute("deptList", deptList);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		
		return new ModelAndView("department/dept_list");
	}


	/** 교수정보 상세보기 페이지 */
	@RequestMapping(value = "/department/dept_view.do", method = RequestMethod.GET)
	public ModelAndView DeptView(Locale locale, Model model) {
		web.init();
		
		int deptno = web.getInt("deptno");
		logger.debug("deptno=" + deptno);
		
		if(deptno == 0) {
			return web.redirect(null, "학과번호가 없습니다.");
		}
		
		Department department = new Department();
		department.setDeptno(deptno);
		
		Department item = null;
		try {
			item = departmentService.selectDepartmentItem(department);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("item", item);
		
		return new ModelAndView("department/dept_view");
	
	}
	
	/** 교수 등록 페이지 */
	@RequestMapping(value = "/department/dept_add.do", method = RequestMethod.GET)
	public ModelAndView DeptAdd(Locale locale, Model model) {
		web.init();
		
		//조회결과 저장
//		List<Department> deptList = null;
//		try {
//			deptList = departmentService.getDepartmentList(null);
//		}catch(Exception e) {
//			return web.redirect(null, e.getLocalizedMessage());
//		}
		
		//model.addAttribute("deptList", deptList);
		
		return new ModelAndView("department/dept_add");
	}
	
	/** 교수 등록 처리 페이지(action 페이지로 사용된다.) */
	@RequestMapping(value = "/department/dept_add_ok.do", method = RequestMethod.POST)
	public ModelAndView DeptAddOk(Locale locale, Model model) {
		web.init();
		
		// input 태그의 name속성에 명시된 값을 사용한다.
		String dname = web.getString("dname");
		String loc = web.getString("loc");
				
		// 전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("dname=" + dname);
		logger.debug("loc=" + loc);
		
		/** 2) 필수항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
		if (dname == null) 		{ return web.redirect(null, "학과이름을 입력하세요."); }
		if (loc == null) 	{ return web.redirect(null, "학과 위치를 입력하세요."); }	
		
		/** 3) 저장을 위한 JavaBeans 구성하기 */
		Department department = new Department();
		department.setDname(dname);
		department.setLoc(loc);
		
		System.out.println("학과번호출력" + department);
		
		try {
			departmentService.addDepartment(department);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		String url = web.getRootPath() + "/department/dept_view.do?deptno=" + department.getDeptno();
		return web.redirect(url, "저장되었습니다.");
	}
	
	/** 교수정보 삭제 페이지 */
	@RequestMapping(value = "/department/dept_delete.do", method = RequestMethod.GET)
	public ModelAndView ProfDelete(Locale locale, Model model) {
		web.init();
		
		int deptno = web.getInt("deptno");
		logger.debug("deptno=" + deptno);
		
		if(deptno == 0) {
			return web.redirect(null, "학과번호가 없습니다.");
		}
		
		Department department = new Department();
		department.setDeptno(deptno);
		
		try {
			departmentService.deleteDepartment(department); 
			System.out.println("학과는 = " + department);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		return web.redirect(web.getRootPath() + "/department/dept_list.do", "삭제되었습니다.");
	}
	
	/** 교수정보 수정 페이지 */
	@RequestMapping(value = "/department/dept_edit.do", method = RequestMethod.GET)
	public ModelAndView ProfEdit(Locale locale, Model model) {
		web.init();
		
		int deptno = web.getInt("deptno");
		logger.debug("deptno=" + deptno);
		
		if(deptno == 0) {
			return web.redirect(null, "학과번호가 없습니다.");
		}
		
		Department department = new Department();
		department.setDeptno(deptno);
		
		//조회결과를 저장하기 위한 객체
		Department item = null;
		//List<Department> deptList = null;
		
		try {
			item = departmentService.selectDepartmentItem(department);
			//deptList = departmentService.getDepartmentList(null);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("item", item);
		//model.addAttribute("deptList", deptList);
		
		return new ModelAndView("department/dept_edit");
	}
	
	/** 교수정보 수정 처리 페이지(action 페이지로 사용) */
	@RequestMapping(value = "/department/dept_edit_ok.do", method = RequestMethod.POST)
	public ModelAndView ProfEditOk(Locale locale, Model model) {
		web.init();
		
		// input 태그의 name속성에 명시된 값을 사용한다.
		int deptno = web.getInt("deptno");
		String dname = web.getString("dname");
		String loc = web.getString("loc");
		
		
		// 전달 받은 파라미터는 로그로 값을 확인하는 것이 좋다.
		logger.debug("deptno=" + deptno);
		logger.debug("dname=" + dname);
		logger.debug("loc=" + loc);
		
		/** 2) 필수항목에 대한 입력 여부 검사하기 */
		// RegexHelper를 사용하여 입력값의 형식을 검사할 수 도 있다. (여기서는 생략)
		if (deptno == 0) 		{ return web.redirect(null, "학과 번호가 없습니다."); }
		if (dname == null) 		{ return web.redirect(null, "학과 이름을 입력하세요."); }
		if (loc == null) 	{ return web.redirect(null, "학과 위치를 입력하세요."); }

		
		/** 3) 저장을 위한 JavaBeans 구성하기 */
		Department department = new Department();
		department.setDeptno(deptno);
		department.setDname(dname);
		department.setLoc(loc);
		
		try {
			departmentService.editDepartment(department);
		}catch(Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		String url = web.getRootPath() + "/department/dept_view.do?deptno=" + department.getDeptno();
		return web.redirect(url, "수정되었습니다.");
	}

}