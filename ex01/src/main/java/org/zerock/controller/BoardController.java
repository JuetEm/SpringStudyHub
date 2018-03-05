/**
 * 
 */
package org.zerock.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
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
 * 
 * Spring MVC Model 을 사용 할 경우 객체간 연결을 자동으로 해주는 간편함이 존재함과 동시에 구조에 대한 이해 없이는
 * 사용하기가 무척 어렵다는  난점 역시 존재한다.
 * 
 * Controller Return Type에 대한 이해 역시 위와 같은 측면의 필요성에서 간단히 정리하고자 한다.
 * 
 * 아래 Controller의 메소드들을 보면 어떤 메소드는 리던 타입이 정의 되어 있고, 어떤 메소드는 리턴타입이 void로 정의되어 있지 않다.
 * return Type의 정의와 상관 없이 jsp 파일이 존재하고 코드에 오류가 없을 경우 해당 페이지는 정상 동작하는 모습을 알 수 있다.
 * 
 * Controller Method의 리턴 값의 종류는??
 * 
 * 1. 자동 추가 모델 오브젝트
 * @ModelAttribute 모델 오브젝트(커맨드 오브젝트)
 * => @ModelAttribute를 붙이거나, 생략 하였더라도 단순 오브젝트가 아니라서 커맨드 옵젝트로 처리되는 옵젝트인 경우
 * 컨트롤러가 리던하는 model 에 자동 추가된다. 기본적으로 옵젝트 이름은 파라미터 타입 이름을 따른다. 
 * 지정하고 싶은 경우 @ModelAttribute("모델이름") 방식으로 지정하면 된다.
 * 
 * Ex)	public void add (@ModelAttribute("user") User user)
 * 	  	public void add (@ModelAttribute User user)
 * 		public void add (User user)
 * 
 * 2. Model / Map / ModelMap 파라미터
 * => 컨트롤러 메소드에 위 타입의 파라미터를 사용하면 Spring MVC 에서 생성된 모델 맵 객체에 값을 추가하여 뷰로 전달 할 수 있다.
 * 위 타입 객체는 DispatcherServlet을 통해 뷰에 전달되는 모델 객체에 자동으로 추가 된다.
 * ModelAndView를 통해 별도로 뷰를 전달하는 경우에도 맵에 추가한 객체는 빠짐없이 모델에 추가하여 전달
 * 
 * 3. @ModelAttribute 메소드가 생성하는 객체
 * => @ModelAttribute 는 컨트롤러 클래스의 메소드 단위로도 객체 생성 가능, 메소드 레벨에도 부여 가능
 * 메소드 레벨에서 @ModelAttribute를 사용하여 객체를 생성하는 경우 @RequestMapping 붙이지 말아야 함
 * 	=> Controller 기능이 아니라 객체를 생성하여 공유하는 경우 사용하기 때문
 * 
 *  Ex)	@ModelAttribute("categories")
 *  	public void commonCategories(){
 *  		return service.getCommonCategories();
 *  	}
 *  위 예와 같이 @ModelAttribute("categories") 객체를 지정해주는 경우 해당 클래스에 존재하는 모든 Controller의 
 *  리턴 값(모델)에 categories라는 변수가 자동 추가됨.
 *  예컨데 검색 조건, 게시판의 카테고리, 참조 정보의 목록 등 컨트롤러와 맵핑된 뷰 전반에서 다루는 정보를 
 *  Controller 내부 모든 메소드가 공유해 사용하기 위해서 사용한다.
 *  
 *  @ModelAttribute 태깅을 통해 사용할 정보가 많다면 여러 메소드에 매핑하여 사용 할 수 있다.
 *  (위와 같은 메소드를 여러개 만들 수 있음)
 * 
 * 4. BindingResult
 * => @ModelAttribute 와 마찬가지로 모델에 자동 추가된다.
 * 모델 맵에 추가 될 때의 키는 "org.springframework.validation.BindingResult.모델이름"
 * 	=> springframework 단위의 관리 차원에서 쓰이는 값으로 변수로 활용하여 직접적으로 사용하는 경우는 거의 없다고 함
 *
 * Ex) 모델 이름이 user인 경우
 * 		org.springframework.validation.BindingResult.user
 * 
 * 5. ModelAndView
 * => ModelAndView는 컨트롤러가 리턴해야 할 값을 담고 있는 가장 대표적인 타입
 * 
 * 기존 Controller 스타일에서는 전형적인 방식이었으나 현재 @MVC 모델에서는 이를 대신할 만한 좋은 기능이 많아서
 * 전만큼은 사용되지 않는 방식
 * 기존 Controller 스타일의 코드를 @MVC 스타일로 변경 할 경우 사용
 * 
 * Ex)	스프링 2.5나 그 이전에 자주 사용되던 Controller 타입의 클래스
 * 		public class HelloController implements Controller {
 * 			@Override
 * 			public ModelAndView handleRequest(
 * 				HttpServletRequest request, HttpServletResponse response)throws Exception{
 * 				String name = request.getParameter("hello");
 * 				return new ModelAndView("hello.jsp").addObject("name",name);
 * 			}	
 * 		}
 * 
 * 		=> 위 코드를 @MVC 스타일로 변환 : @Controller 인터체이스 구현 삭제, 
 * 			@Controller, @RequestMapping 부여
 * 
 * 		@Controller
 * 		public class HelloController{
 * 			@RequestMapping("/hello")
 * 			public ModelAndView handleRequest(HttpServletRequest request, 
 * 				HttpServletResponse response){
 * 				String name = request.getPArameter("hello");
 * 				return new ModelAndView("hello.jsp").addObject("name",name);
 * 			}
 * 		}
 * 
 * 		=> 보다 @MVC Controller 답게 작성 : HttpServletRequest 대신 @RequestParam 사용해 파라미터 값 받음,
 * 			메소드 명 의미품는 것으로 변경, 필요한 경우 아니라면 throws Exception 제거, Model 파라미터 사용
 * 		@Controller
 * 		public class HelloController{
 * 			@RequestMapping("/hello")
 * 			public ModelAndView helloMethod(@RequestParam String name, Model model){
 * 				model.addAttribute("name",name);
 * 				return new ModelAndView("hello");
 * 			}
 * 		}		
 * 
 * 6. String인 경우
 * =>1. 리턴하는 문자열이 뷰의 이름, 2. model 객체에 data 를 담는 경우 리턴 문자열이름의 뷰로 데이터 함께 전달
 * 위 에 작성한 마지막 예제 코드와 연계하여 생각 했을 때, String 리턴 타입으로 뷰 이름만을 전달해도 같은 효과 볼 수 있음
 * 	=> 보다 간결해지는 코드
 * 	Ex) @Controller
 * 		public class HelloController{
 * 			@RequestMapping("/hello")
 * 			public String helloMethod(@RequestParam String name, Model model){
 * 				model.addAttribute("name",name);
 * 				return "hello";
 * 			}
 * 		}
 * 
 * 7. void
 * => RequestToViewNameResolver 전략을 통해 자동 생성되는 뷰 이름 사용
 * URL과 뷰 이름을 일관되게 통일 할 수 있다면 적극적으로 활용해볼만한 전략
 * 위 String 예제와 연계하여 생각 할 경우 보다 간결한 코드 작성 가능
 * 	Ex) @RequestMapping("/hello")
 * 		public void helloMethod(@RequestPara String name, Model model){
 * 			model.addAttribute("name", name);
 * 		}
 *  
 *  RequestToViewNameResolver가 자동으로 view 이름을 hello로 만들어줌 (추가 Study 필요)
 *  		=> WEB-INF/view/hello.jsp 사용 하게 됨
 * 
 * 8. 모델 오브젝트
 * => RequestToViewNameResolver를 사용해 뷰 이름을 자동으로 생성하고, 모델에 추가해야 하는 객체가 하나 뿐이라면
 * 모델 파라미터를 받아 저장하는 대신 모델 객체를 바로 리턴해도 된다. 
 * 스프링은 전달해야 할 객체가 미리 지정된 타입이나 void가 아닌 단순 객체라면 이를 모델 객체로 인식하여 자동으로 모델에 
 * 추가해준다. 이때 모델 이름은 리턴값의 이름을 따른다
 * 		=>1 번 항목 @ModelAttribute의 내용과 동일
 * 	Ex)	@RequestMapping("/view")
 * 		public User view(@RequestParam int id){
 * 			return service.getUser(id);
 * 		}
 * 
 * 파라미터로 유저 아이디를 얻어와서 내부 로직(서비스, dao 등)를 통해서 User 객체를 리턴한다. 
 * 리턴 타입으로 지정된 User 타입 객체는 자동으로 모델 객체에 추가되고 모델 이름은 user가 된다.
 * 클래스이름과 다르게 모델 이름을 직접 지정하고싶은 경우 @ModelAttribute("모델이름") 방식으로 지정한다.
 * 
 * 9. Map / Model / ModelMap
 * => 리턴값을 Map 타입으로 지정하는 것은 되도록이면 피하는 것이 좋다. 단일 객체로 자동 등록 되는 방식으로 
 * Map 을 리턴 값으로 지정하는 경우, Map userMap 이라는 객체를 리턴한다고 해서 map 이라는 자동 등록 객체에
 * 값이 생성되는 것이 아니기 때문이다. 이는 혼란을 초래한다. model 파라미터에 답고싶은 map 객체를 담아서 넘기는 것이
 * 바람직하다.
 * 
 * 10.view
 * => 스트링 리턴 타입은 뷰 이름으로 인식한다. 이와 비슷하게 뷰 객체를 리턴값으로 넘기고 싶다면 리턴값을 View 로 선언하고
 * View 객체를 리턴값으로 넘기면 된다.
 * 
 * 	Ex)	public class UserController{
 * 			@AutoWired
 * 			MarshallingView userXmlView;
 * 
 * 			@RequestMapping("/user/xml")
 * 			public View view(@RequestParam int id){
 * 				...
 * 				return this.userXmlView;
 * 			}
 * 		}
 *  
 * 11. @ResponseBody
 * => @ResponseBody는 @RequestBody와 비슷한 방식으로 동작한다. @ResponseBody가 메소드 레벨에 부여되면
 * 메소드가 리턴하는 객체는 뷰를 통해 결과를 생성하는 모델로 사용되는 것이 아니라
 * 메세지 콘버터를 통해 바로 HTTP 응답의 메세지 본문으로 전환된다.
 * 
 * 
 * 
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
			service.regist(board);
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
	
	@RequestMapping(value ="/readPage", method = RequestMethod.GET)
	public void read(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri
			, Model model)throws Exception{
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
	
	@RequestMapping(value="/removePage", method=RequestMethod.POST)
	public String remove(@RequestParam("bno") int bno,
			Criteria cri,
				RedirectAttributes rttr)throws Exception{
		
		service.remove(bno);
		
		rttr.addAttribute("page",cri.getPage());
		rttr.addAttribute("perPageNum",cri.getPerPageNum());
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(int bno, Model model) throws Exception {
		model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(BoardVO board, RedirectAttributes rttr) throws Exception {
		
		logger.info("mod post...........");
		
		service.modify(board);
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/modifyPage", method = RequestMethod.GET)
	public void modifyPagingGET(@RequestParam("bno") int bno
			, @ModelAttribute("cri") Criteria cri
			, Model model)throws Exception{
		model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value="/modifyPage", method = RequestMethod.POST)
	public String modifyPagingPOST(BoardVO board,
			Criteria cri, 
				RedirectAttributes rttr)throws Exception{
		
		service.modify(board);
		
		/*JSP 파일에서 변수를 호출하는 방식 '=> cri.page' 처럼 변수를 호출 할 수도 있겠으나
		 * Controller에서는 domain 객체 Criteria 에 대하여 getter, setter 를 사용해서 접근
		 * private으로 선언 되어 있는 변수들.*/
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/listPage";
		
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 2. 26.
	 * @desc	: 해당 페이지의 리스트를 DB에서 조회하는 메서드
	 * 				
	 * 				스프링 MVC 컨트롤러는 특정 URL에 해당하는 메소드를 실행할 때, 파라미터의
	 * 				타입을 보고, 해당 객체를 자동으로 생성해 냅니다. 
	 * 				파라미터가 자동으로 수집되기 때문에, 바로 이전에 마들 Criteria라는 클래스를 그대로
	 * 				사용할 수 있습니다.
	 * 
	 * 				리스트 페이지는 기본적으로 GET방식 사용.(<= 대부분의 조회는 GET 방식)
	 * 				개발 시에는 GET 방식의 경우 URL을 조작하는 것만으로도 정상적인 동작을 확인 할 수 있음
	 * 
	 * 				위 스프링 MVC 컨트롤러의 메소드 파라미터 타입 해당 객체 자동 생성 특징과
	 * 				GET 방식의 URL 조작 동작 확인 특징이 만나면 
	 * 				Criteria cri 도메인 클래스에 선언한 타입의 객체를 URL에 변수로 붙여
	 * 				Test 가능
	 * 
	 * 				Ex) localhost:8080/board/listCri?page=3&perPageNum=20/
	 * 				위와 같은 예의 경우 Criteria 클래스의 int page 변수와 int perPageNum 변수에
	 * 				page =3, perPageNum =20의 값이 자동으로 맵핑됩
	 * 
	 * 				
	 *
	 * @param cri
	 * @param model
	 * @throws Exception
	 */
	@RequestMapping(value = "/listCri", method = RequestMethod.GET)
	public void listAll(Criteria cri, Model model) throws Exception{
		
		logger.info("show list Page with Criteria..........."+cri.getTestMessage());
		
		model.addAttribute("list",service.listCriteria(cri));
		/*model에 service dao mapper db 흐름의 처리 외의 변수를 추가로 담는 Test 진행 
		 * 아래와 같이 model.asMap().put으로 변수 담아주니 JSP 단에서 ${testMessage}로 변수 값 읽을 수 있음
		 * var testMessage = '${testMessage}' 로 JSP 변수로 받아서 사용하려고 alert() 띄우자
		 * 변수 값이 true/false 로 보여지는 것을 확인, 이 부분에 대해서는 확인 중
		 * 
		 *  위 문제상황이 생겼던 이유는 선언한 변수명이 예약어나 이미 시스템 상에서 정해진 명칭 이었기 때문인 것으로 보인다.
		 *  var testMessage 를 var testM 이나 var testMsge 로 바꾸고 변수 입력하자 출력 정상적으로 되는 것 확인됨
		 *  var testMessage 라는 변수를 새로운 값으로 초기화해 사용할 경우 계속해서 true 값 만 return 함*/
		model.asMap().put("testMessage", cri.getTestMessage());
	}
	
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(Criteria cri, Model model) throws Exception{
		
		logger.info(cri.toString());
		
		model.addAttribute("list", service.listCriteria(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
//		pageMaker.setTotalCount(131);
		pageMaker.setTotalCount(service.listCountCriteria(cri));
		
		model.addAttribute("pageMaker", pageMaker);
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
	
	@RequestMapping(value ="/templateStudy", method=RequestMethod.GET)
	public void testTemplateStudy(){
		logger.info("templateStudy JSP File just has been called");
	}
}
