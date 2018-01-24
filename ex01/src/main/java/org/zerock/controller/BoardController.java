/**
 * 
 */
package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

/**
 * @author	: Juet
 * @date	: 2018. 1. 24.
 * @desc	: 리다이렉트를 기본으로 전제하고 만드는 컨트롤러, 최대한 단순히 시작하여 학습하고, Ajax 등 필요한 기술들은 차차 적용해 간다.
 * 
 * 컨트롤러 개발이 프로젝트 간 가장 많은 시간과 작업을 요한다.(60~70%)
 * Pre-Considerations for Controller Development
 * 
 * URI involving Considerations
 * 1. 공통 URI 경로 / 기능별 URI
 * 2. 각 URI 호출 방식(GET, PPOST)
 * 3. 결과 처리 및 리다이렉트 방식의 페이지 결정
 * 4. 예외 페이지
 * 
 * GET/POST => URI를 어떤 방식으로 호출할건인가?
 * 	GET : 사용자가 직접 브라우저에 접근이 가능할 때 사용
 * 			'조회'가 필요한 경우(게시판 글 목록, 글 읽기 등 입력 및 조회)
 * 	POST : 외부에서 많은 정보를 입력하는 경우 사용(브라우저상에서 주소창에 보여지면 안되는 정보를 전송 처리)
 * 			사용자가 직접 결정하여 '내용'의 변화가 생기는 경우(글의 추가, 수정, 삭제 등 사용자가 내용 변화를 일으키는 경우)
 * 
 * Parameter involving Considerations
 * Spring MVC 는 메소드의 parameter와 return type을 개발자가 모두 결정해야 함 <= parameter와 return type이 매우 유연함
 * 
 * 1. Spring MVC 는 parameter의 수집이 자동으로 이루어짐 => 수집 원하는 객체를 parameter로 선언
 * 2. VO 클래스 또는 DTO 클래스를 parameter로 사용하는 것이 편리함 => 원하는 경우 클래스 외 객체에 담아서 사용 가능
 * 3. 바인딩(Binding) : 요청(Requset)의 자동 처리 => 브라우저에서 들어오는 요청(request)이 자동으로 parameter로 지정한 클래스의 객체 속성값으로 처리됨
 * 4. Spring MVC의 Model 객체는 해당 메소드에서 뷰(jsp 등)에 필요한 데이터를 전달하는 용도로 사용됨 
 * 		=> 메소드 내에서 뷰에 전달할 데이터가 있다면 Model을 parameter로 선언해 주는 것이 편리함
 */

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 24.
	 * @desc	: @RequestMapping annotation 설정은
	 * 				1) 특정 URI를 의미하는 value 속성과
	 * 				2) GET/POST 등 전송방식으로 결정하는 method 속성이 사용 됨
	 * 				3) value 와 method 속성은 둘 다 배열로, 여러가지 속성 값 지정 가능
	 * 	EX) @RequestMapping(value = "/register" , method = {RequestMethod.GET, RequestMethod.POST})
	 *
	 * @param board
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void regsterGET(BoardVO board, Model model) throws Exception{
		
		logger.info("register get ...........");
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 24.
	 * @desc	: 화면에서 입력되어 들어오는 데이터를 처리
	 * 				파라미터
	 * 					1. 자동으로 모든 데이터를 BoardVO로 수집
	 * 					2. 뷰로 데이터 전달 가능성 있으므로 Model 클래스의 객체 받음
	 * 
	 * 				Model 클래스
	 * 					Spring MVC에서 제공하는 데이터 전달용 객체
	 * 					Map과 유사하게 '키(key)'와 '값(value)'으로 데이터를 저장하는 역할
	 * 					과거 Servlet에서는 RequestDispatcher에 데이터 저장했듯이, Spring에서는 Model 이용
	 *
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, Model model) throws Exception {
		
		logger.info("register post ...........");
		logger.info(board.toString());
		
		service.regist(board);
		
		model.addAttribute("result", "success");
		
		/*modle 객체에 저장된 결과를 리턴하는 실제 경로 
		 *  '/WEB-INF/views/board/success.jsp' */
		return "/board/success";
	}

}
