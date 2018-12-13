package study.spring.hellospring;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import study.spring.helper.FileInfo;
import study.spring.helper.UploadHelper;
import study.spring.helper.WebHelper;

//컨트롤러 지정
@Controller
public class UploadController{
	//객체 주입
	@Autowired
	WebHelper web;
	@Autowired
	UploadHelper upload;
	
	@RequestMapping(value="/upload/upload.do", method=RequestMethod.GET)
	public String upload(Locale locale, Model model) {
		// HTML만 표시할 것이므로 특별한 처리를 하지 않는다.
		return "upload/upload";
	}
	
	@RequestMapping(value="/upload/upload_ok.do", method=RequestMethod.POST)
	public ModelAndView uploadOk(Locale locale, Model model) {
		web.init();
		
		try {
			upload.multipartRequest();
		}catch(Exception e) {
			e.printStackTrace();
			return web.redirect(null, "업로드 된 파일 저장에 실패했습니다.");
		}
		
		List<FileInfo> fileList = upload.getFileList();
		Map<String, String> paramMap = upload.getParamMap();
		
		//컬렉션에서 파라미터 추출하기
		String memo = paramMap.get("memo");
		
		//view에게 업로드 결과 전달
		model.addAttribute("memo", memo);
		model.addAttribute("fileList", fileList);
		
		//html만 표시할 것이므로 특별한 처리를 하지 않는다.
		return new ModelAndView("upload/upload_ok");
	}
}