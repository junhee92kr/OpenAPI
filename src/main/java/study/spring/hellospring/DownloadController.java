package study.spring.hellospring;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.AbstractView;


import study.spring.helper.UploadHelper;
import study.spring.helper.WebHelper;

//컨트롤러 지정
@Controller
public class DownloadController{
	@Autowired
	WebHelper web;
	@Autowired
	UploadHelper upload;
	
	@RequestMapping(value = "/download.do", method = {RequestMethod.GET} )
	public ModelAndView download(Locale locale, Model model) {
		//WebHelper를 통해 다운로드할 파일의 경로를 다운받음.
		web.init();
		String filePath = web.getString("file");
		
		//생성할 섬네일 이미지 해상도
		int width = web.getInt("width");
		int height = web.getInt("height");
		
		//크롭 여부
		String crop = web.getString("crop", "Y");
		boolean isCrop = true;
		
		if(!crop.equals("Y")) {
			isCrop = false;
		}
		
		//파라미터로 전달된 파일경로를 model에 등록
		model.addAttribute("filePath", filePath);
		
		DownloadView view = new DownloadView(filePath, null, width, height, isCrop);
		
		return new ModelAndView(view);
	}
	
	/**
	 * 다운로드를 위한 스트림 출력처리를 담당하는 가상의 view를 생성하기 위한 클래스 (inner class)
	 */
	public class DownloadView extends AbstractView {
		
		private String filePath; //다운로드할 파일의 서버상 경로
		private String originName; //원본 파일 이름
		private int width = 0; //이미지 리사이즈 할 가로크기
		private int height = 0;
		private boolean isCrop = true; //crop여부
		
		public DownloadView(String filePath, String originName, int width, int height, boolean isCrop) {
			this.filePath = filePath;
			this.originName = originName;
			this.width = width;
			this.height = height;
			this.isCrop = isCrop;
		}
		
		@Override
		protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			//파일경로명이 존재하면?
			if(filePath != null) {
				File f = new File(filePath);
				
				if(f.exists()) {
					//썸네일을 생성하기 위한 해상도가 지정되었다면?
					if(width > 0 || height > 0) {
						upload.printFileStream(filePath, width, height, isCrop);
					}else {
						upload.printFileStream(filePath, originName);
					}
				}
			}//if
		}	
	}
	//end class
}