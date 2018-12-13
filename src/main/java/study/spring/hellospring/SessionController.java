package study.spring.hellospring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class SessionController {
	
	@RequestMapping(value="/session/write.do", method=RequestMethod.GET)
	public String home(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String mySession = (String) session.getAttribute("my_session");
		if(mySession == null) {
			mySession = "";
		}
		
		model.addAttribute("my_session_value", mySession);
		
		return "session/write";
	}
	
	@RequestMapping(value="/session/save.do", method=RequestMethod.POST)
	public String sessionSave(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="memo", defaultValue="") String memo) {
		
		HttpSession session = request.getSession();
		
		if(memo.equals("")) {
			//입력내용이 없는경우 세션 삭제처리
			session.removeAttribute("my_session");
		}else {
			session.setAttribute("my_session", memo);
		}
		
		/** Spring 방식의 페이지 이동 */
		String url = "/session/write.do";	
		return "redirect:" + url;
	}
}
