package study.spring.hellospring;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sun.media.jfxmedia.logging.Logger;

@Controller
public class CookieController{
	/**
	 * 쿠키 저장을 위한 페이지
	 * 이 페이지를 /cookie/write.do URL에 GET방식으로 노출시킴.
	 */
	@RequestMapping(value="/cookie/write.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response,
			@CookieValue(value="my_cookie", defaultValue="") String myCookie) {
		
		/** 쿠키값 처리 */
		//컨트롤러 메서드의 파라미터로 전달받을 경우 디! 코딩이 별도로 필요함
		try {
			myCookie = URLDecoder.decode(myCookie, "utf-8");
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		/** 추출한 값을 view에 전달 */
		model.addAttribute("my_cookie", myCookie);
		
		return "cookie/write";
	}
	
	@RequestMapping(value="/cookie/save.do", method = RequestMethod.POST)
	public String cookieSave(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value="memo", defaultValue="") String memo) {
		
		if(!memo.equals("")) {
			try {
				memo = URLEncoder.encode(memo, "utf-8");
			}catch(UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		Cookie cookie = new Cookie("my_cookie", memo); //저장할 쿠키 객체 생성
		cookie.setPath("/"); //쿠키의 유효경로 -> 사이트 전역에 대한 설정
		
		if(memo.equals("")) {
			cookie.setMaxAge(0);
		}else {
			cookie.setMaxAge(5); //60초 동안 쿠키 저장
		}
		
		response.addCookie(cookie); //쿠키 저장
		
		/** Spring 방식의 페이지 이동 */
		//Servlet의 response.sendRedirect(url) 방식과 동일
		// -> "/"부터 시작할 경우 ContextPath는 자동으로 앞에 추가된다.
		String url = "/cookie/write.do";
		return "redirect:" + url;
	}
}