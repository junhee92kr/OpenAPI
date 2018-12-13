package study.spring.hellospring;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import study.spring.hellospring.hello.Calc1;
import study.spring.hellospring.hello.Calc2;
import study.spring.hellospring.hello.Calc3;
import study.spring.hellospring.hello.Calc4;

//컨트롤러 지정
@Controller
public class CalcController{
	
	/** 객체주입 -> 자동할당 */
	@Autowired
	private Calc1 c1; //Calc1 객체를 할당한다.
	@Autowired
	private Calc2 c2;
	@Autowired
	private Calc3 c3;
	@Autowired
	private Calc4 c4;
	
	@RequestMapping(value="/calc.do", method=RequestMethod.GET)
	public String home(Locale locale, Model model) {
		//spring에 의해 자동으로 주입된 객체의  메서드 호출
		int value1 = c1.sum();
		int value2 = c2.sum();
		int value3 = c3.sum();
		int value4 = c4.sum();
		
		//변수값을 view에 전달
		 model.addAttribute("value1", value1);
		 model.addAttribute("value2", value2);
		 model.addAttribute("value3", value3);
		 model.addAttribute("value4", value4);
		
		//view폴더 안의 calc.jsp파일을 View로 지정한다.
		return "calc";
	}
}