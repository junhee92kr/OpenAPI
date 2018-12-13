package study.spring.hellospring;

import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import study.spring.hellospring.hello.Calc1;
import study.spring.hellospring.hello.Calc2;
import study.spring.hellospring.hello.Calc3;
import study.spring.hellospring.hello.Calc4;
import study.spring.helper.MailHelper;
import study.spring.helper.RegexHelper;
import study.spring.helper.WebHelper;

//컨트롤러 지정
@Controller
public class MailController{
	
	@Autowired
	WebHelper web;
	@Autowired
	MailHelper mail;
	@Autowired
	RegexHelper regex;
	
	/** 메일 작성 폼 */
	@RequestMapping(value = "/mail/write.do", method = RequestMethod.GET)
	public ModelAndView write(Locale locale, Model model) {
		web.init();
		return new ModelAndView("mail/write");
	}
	
	/** 메일 발송처리를 구현할 컨트롤러 */
	@RequestMapping(value = "mail/mail_ok.do", method = RequestMethod.POST)
	public ModelAndView mailOk(Locale locale, Model model) {
		web.init();
		//파라미터 받기
		String sender = web.getString("sender");
		String receiver = web.getString("receiver");
		String subject = web.getString("subject");
		String content = web.getString("content");
		
		if(!regex.isEmail(sender)) {
			return web.redirect(null, "보내는 사람의 주소가 맞지 않습니다.");
		}
		if(!regex.isEmail(receiver)) {
			return web.redirect(null, "받는 사람의 주소가 맞지 않습니다.");
		}
		if(!regex.isValue(subject)) {
			return web.redirect(null, "메일 제목을 입력하세요");
		}
		if(!regex.isValue(content)) {
			return web.redirect(null, "메일의 내용을 입력하세요.");
		}
		
		/** 메일 발송 처리 */
		try {
			mail.sendMail(sender, receiver, subject, content);
		}catch(MessagingException e) {
			return web.redirect("write.do", e.getLocalizedMessage());
		}
		
		return web.redirect("write.do", "메일이 발송되었습니다.");
	}
	
}