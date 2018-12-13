package study.spring.hellospring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import study.spring.helper.WebHelper;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ParamController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	//WebHelper 객체 의존성 주입
	@Autowired
	WebHelper web;
	
	/**
	 * 페이지를 구성하는 메서드
 	 * @param locale : 국가설정
	 * @param model : view에게 전달할 데이터의 참조객체
	 * @return String : View의 파일이름
	 */
	// value -> 이 메서드의 URL, method -> 이 메서드가 응답할 요청방식
	@RequestMapping(value = "/param/home.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		//로그 출력
		logger.debug("home is running...");	
		//view 파일이름을 리턴
		return "param/home";
	}
	
	@RequestMapping(value = "/param/get.do", method = RequestMethod.GET)
	//public String get(Locale locale, Model model,
			//@RequestParam(value="answer", defaultValue="0") int answer) {
	public ModelAndView get(Locale locale, Model model) {
		logger.debug("get is running...");
		//모든 컨트롤러 메서드에 초기 1회 호출
		web.init();
		
		int answer =web.getInt("answer");
		if(answer == 0) {
			return web.redirect(null, "답변이 없습니다.");
		}
		String result = null;
		
		if(answer == 300) {
			result = "정답입니다.";
		} else {
			result = "오답입니다.";
		}
		
		//파라미터값을 view에 전달
		model.addAttribute("answer", answer);
		model.addAttribute("result", result);
		
		//view 파일이름을 리턴
		return new ModelAndView("param/get");
	}
	
	@RequestMapping(value = "/param/post.do", method = RequestMethod.POST)
	//public String post(Locale locale, Model model,
			//@RequestParam(value="user_name", defaultValue="") String name,
			//@RequestParam(value="user_age", defaultValue="0") int age) {
	public ModelAndView post(Locale locale, Model model) {
		logger.debug("post is running...");
		web.init();
		String userName = web.getString("user_name");
		int userAge = web.getInt("user_age");
		
		if(userName == null) {
			return web.redirect(null, "이름을 입력하세요.");
		}
		if(userAge == 0) {
			return web.redirect(null, "나이를 숫자로 입력하세요.");
		}
		
		//view에 전달
		model.addAttribute("name", userName);
		model.addAttribute("age", userAge);
				
		//view 파일이름을 리턴
		return new ModelAndView("param/post");
	}
	
}
