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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	public void registerGET(BoardVO board, Model model) throws Exception{
		
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
	 *
	 * @param board
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
//		public String registPOST(BoardVO board, Model model) throws Exception {
		
		logger.info("register post ...........");
		logger.info(board.toString());
		
		/*client에서 전송하는 데이터 방식을 서버 단 Controller 에서 처리하는 방식
		 * JSP 에서 처리하는 방식과 비교하기 위해 코드 작성
		 * redirect 방식으로 처리 => 화면 단에서 alert()로 메세지 보여주는 것 가능하지만,
		 * 화면 전환이 많고, alert() 메세지 이후 화면이 넘어가지 않는다. */
//		String nullCheck = service.nullCheck(board);
//		logger.info("nullCheck is called!!! ===> "+nullCheck);
//		if(nullCheck.equals("")){
//			service.regist(board);
//			rttr.addFlashAttribute("msg", "SUCCESS");
//			return "redirect:/board/listAll";
//		}else{
//			rttr.addFlashAttribute("msg", nullCheck);
//			return "redirect:/board/register";
//		}
		
		/*model 객체로 값을 전달 할 경우 GET 형식으로 URI에 "result=SUCCESS"문자열이 남음
		 * 새롭게 언언한 RedirectAttributes rttr 객체로 값을 전달 할 경우 POST 형식으로 URI에
		 * 값이 나타나지 않음*/
//		model.addAttribute("result", "SUCCESS");
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		/*model 객체에 저장된 결과를 리턴하는 실제 경로 
		 *  '/WEB-INF/views/board/success.jsp' */
//		return "/board/success";
		/*success page 새로고침 시 같은 데이터 재전송 등으로 인한 글 중복 등록을 막기 위하여
		 * redirect 시켜준다.*/
		return "redirect:/board/listAll";
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 24.
	 * @desc	: 리다이렉트 된 경우 보여질 화면 및 로직을 담고있는 메서드
	 *
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public void listAll(Model model)throws Exception {
		
		logger.info("show all list ...........");
		model.addAttribute("list", service.listAll());
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 26.
	 * @desc	: listAll 화면에서 글 제목 링크 클릭시 게시물 내용을 보여주는 화면
	 * 				
	 * 				@RequestParam Annotation은 Servlet에서 request.getParameter()의 효과와 유사
	 * 				@RequestParam("bno")는 과거 request.getParameter("bno")처럼 동작합니다. Servlet의
	 * 				HttpServletRequest와 다른점은 문자열, 숫자, 날짜 등의 형 변환이 가능하다는 점
	 * 
	 * 				model.addAttribute() 메서드 통해 JSP에 값을 넘길 때 이름은 지정해주지 않으면 자동으로 클래스의 이름을 소문자로 시작해서 사용
	 * 				read메서드의 경우 model.addAttribute(service.read(bno))를 넙기는데 이때 넘어가는 값이 BoardVO 클래스의
	 * 				객체이므로, 'boardVO'라는 이름으로 저장됨
	 *
	 * @param bno
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, Model model) throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 26.
	 * @desc	: 게시물 삭제 메서드
	 * 				게시물 고유번호를 @RequestParam("bno")로 받아서 해당 게시물을 삭제
	 * 				삭제 후에는 listAll 게시물 리스트 페이지로 redirect 시킴
	 * 				RedirectAttributes rttr 을 선언하여 addFlashAttribute()메서드를 사용해
	 * 				rttr.addFlashAttribute("msg", "SUCCESS"); 로 전달.
	 * 				RedirectAttributes의 위 방식을 사용하면 전달한 값이 일회적으로 생성되
	 *
	 * @param bno
	 * @param rttr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/remove", method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno, RedirectAttributes rttr) throws Exception {
//		public String remove(@RequestParam("bno") int bno, Model model) throws Exception {
		service.remove(bno);
		
		rttr.addFlashAttribute("msg", "SUCCESS");
//		model.addAttribute("msg", "SUCCESS");
		
		return "redirect:/board/listAll";
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 26.
	 * @desc	: JSP 스터디 위해서 임의적으로 만든 메서드 입니다.
	 * 				README에서 언급한 책의 내용에는 포함되어 있지 않습니다.
	 * 				NewFile() 메서드와 연결되어 있는 NewFIle.jsp 파일과 내용 역시 임의적으로 생성한 것입니다.
	 * 
	 * 출처 : http://rank01.tistory.com/33?category=487064
	 * 				JSP 스크립트 요소
	 * 				1. 선언문 <%! ...........%>
	 * 					- jsp 페이지에서 자바코드의 멤버변수와 메서드를 선언하기 위해 사용
	 * 					- 선언문을 사용해 선언된 변수는 JSP 파일이 컴파일 될 때 멤버 변수가 되기 때문에, 
	 * 						JSP 페이지의 어느 위치에서도 해당 변수를 참조하는 것 가능
	 * 					ex)
	 * 					<%!
	 * 						private String study = "JSP공부중";
	 * 						public String checkStr(){
	 * 							if(study==null){
	 * 								return "no";
	 * 							} else {
	 * 								return "ok";
	 * 							}
	 * 						}
	 * 					%>
	 * 
	 * 				2. 스크립틀릿 <% ........... %>
	 * 					- HTML 코드로 된 부분은 일반 HTML 파일처럼 사용하고 자바 코드로 이루어진 로직 부분은
	 * 					<% ........... %> 로 표현되는 스크립틀릿 태그 사용하여 구분
	 * 					=> out 개체를 사용하지 않고 쉽게 HTML 응답을 만들어 낼 수 있음
	 * 					-표현식 태그의 결과값은 JSP 파일이 파싱될 때 출력 객체의 print() 메소드를 통해 자동으로 문자열 형식으로
	 * 					변환되어 출력됨
	 * 					=> 구문 전체가 하나의 print()메소드의 괄호 안에 들어가게 되므로 세미콜론(;)을 사용해서는 안됨
	 * 					ex)
	 * 					<%
	 * 						Calendar c = Calendar.getInstance();
	 * 						int hour = c.get(Calendar.HOUR_OF_DAY);
	 * 						int minute = c.get(Calendar.MINUTE);
	 * 						int second = c.get(Calendar.SECOND);
	 * 					%>
	 * 				
	 * 				3. 표현식 <%= ........... %>
	 * 					- 선언문 또는 스크립틀릿 태그에서 선언된 변수나 메소드의 리턴값, 변수식을 외부에서 출력하기 위해 사용
	 * 					ex)
	 * 					<body>
	 * 					<h2> 지금은 <%= study %> 입니다</h2>
	 * 					<br>
	 * 					<h2> 현재 시간은 <%=hour%>시 <%=minute%>분 <%=second%>초 입니다.</h2>
	 * 					</body>
	 * 	
	 * 출처 : http://rank01.tistory.com/33?category=487064
	 * @throws Exception
	 */
	@RequestMapping(value="/NewFile", method=RequestMethod.GET)
	public void NewFile()throws Exception{
		logger.info("NewFile is Called ...........");
		/*리턴값이 정의되어 있지 않아도 화면을 연결하는 것은 가능함. 반대로 리턴값을 return "/board/read"; 처럼
		 * 다른 jsp 파일로 맵핑시키면 NewFile.jsp 화면은 나타나지 않고, 맵핑된 파일의 화면으로 바로 이동함. 
		 * Controller에 정의된 메서드는 아무 기능 없이 화면을 이어주는 방식만으로도 사용 가능함*/
//		return "/board/NewFile";
	}
}
