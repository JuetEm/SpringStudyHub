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
 * @desc	: �����̷�Ʈ�� �⺻���� �����ϰ� ����� ��Ʈ�ѷ�, �ִ��� �ܼ��� �����Ͽ� �н��ϰ�, Ajax �� �ʿ��� ������� ���� ������ ����.
 * 
 * ��Ʈ�ѷ� ������ ������Ʈ �� ���� ���� �ð��� �۾��� ���Ѵ�.(60~70%)
 * Pre-Considerations for Controller Development
 * 
 * URI involving Considerations
 * 1. ���� URI ��� / ��ɺ� URI
 * 2. �� URI ȣ�� ���(GET, PPOST)
 * 3. ��� ó�� �� �����̷�Ʈ ����� ������ ����
 * 4. ���� ������
 * 
 * GET/POST => URI�� � ������� ȣ���Ұ��ΰ�?
 * 	GET : ����ڰ� ���� �������� ������ ������ �� ���
 * 			'��ȸ'�� �ʿ��� ���(�Խ��� �� ���, �� �б� �� �Է� �� ��ȸ)
 * 	POST : �ܺο��� ���� ������ �Է��ϴ� ��� ���(�������󿡼� �ּ�â�� �������� �ȵǴ� ������ ���� ó��)
 * 			����ڰ� ���� �����Ͽ� '����'�� ��ȭ�� ����� ���(���� �߰�, ����, ���� �� ����ڰ� ���� ��ȭ�� ����Ű�� ���)
 * 
 * Parameter involving Considerations
 * Spring MVC �� �޼ҵ��� parameter�� return type�� �����ڰ� ��� �����ؾ� �� <= parameter�� return type�� �ſ� ������
 * 
 * 1. Spring MVC �� parameter�� ������ �ڵ����� �̷���� => ���� ���ϴ� ��ü�� parameter�� ����
 * 2. VO Ŭ���� �Ǵ� DTO Ŭ������ parameter�� ����ϴ� ���� ���� => ���ϴ� ��� Ŭ���� �� ��ü�� ��Ƽ� ��� ����
 * 3. ���ε�(Binding) : ��û(Requset)�� �ڵ� ó�� => ���������� ������ ��û(request)�� �ڵ����� parameter�� ������ Ŭ������ ��ü �Ӽ������� ó����
 * 4. Spring MVC�� Model ��ü�� �ش� �޼ҵ忡�� ��(jsp ��)�� �ʿ��� �����͸� �����ϴ� �뵵�� ���� 
 * 		=> �޼ҵ� ������ �信 ������ �����Ͱ� �ִٸ� Model�� parameter�� ������ �ִ� ���� ����
 * 
 * Spring MVC Model �� ��� �� ��� ��ü�� ������ �ڵ����� ���ִ� �������� �����԰� ���ÿ� ������ ���� ���� ���̴�
 * ����ϱⰡ ��ô ��ƴٴ�  ���� ���� �����Ѵ�.
 * 
 * Controller Return Type�� ���� ���� ���� ���� ���� ������ �ʿ伺���� ������ �����ϰ��� �Ѵ�.
 * 
 * �Ʒ� Controller�� �޼ҵ���� ���� � �޼ҵ�� ���� Ÿ���� ���� �Ǿ� �ְ�, � �޼ҵ�� ����Ÿ���� void�� ���ǵǾ� ���� �ʴ�.
 * return Type�� ���ǿ� ��� ���� jsp ������ �����ϰ� �ڵ忡 ������ ���� ��� �ش� �������� ���� �����ϴ� ����� �� �� �ִ�.
 * 
 * Controller Method�� ���� ���� ������??
 * 
 * 1. �ڵ� �߰� �� ������Ʈ
 * @ModelAttribute �� ������Ʈ(Ŀ�ǵ� ������Ʈ)
 * => @ModelAttribute�� ���̰ų�, ���� �Ͽ����� �ܼ� ������Ʈ�� �ƴ϶� Ŀ�ǵ� ����Ʈ�� ó���Ǵ� ����Ʈ�� ���
 * ��Ʈ�ѷ��� �����ϴ� model �� �ڵ� �߰��ȴ�. �⺻������ ����Ʈ �̸��� �Ķ���� Ÿ�� �̸��� ������. 
 * �����ϰ� ���� ��� @ModelAttribute("���̸�") ������� �����ϸ� �ȴ�.
 * 
 * Ex)	public void add (@ModelAttribute("user") User user)
 * 	  	public void add (@ModelAttribute User user)
 * 		public void add (User user)
 * 
 * 2. Model / Map / ModelMap �Ķ����
 * => ��Ʈ�ѷ� �޼ҵ忡 �� Ÿ���� �Ķ���͸� ����ϸ� Spring MVC ���� ������ �� �� ��ü�� ���� �߰��Ͽ� ��� ���� �� �� �ִ�.
 * �� Ÿ�� ��ü�� DispatcherServlet�� ���� �信 ���޵Ǵ� �� ��ü�� �ڵ����� �߰� �ȴ�.
 * ModelAndView�� ���� ������ �並 �����ϴ� ��쿡�� �ʿ� �߰��� ��ü�� �������� �𵨿� �߰��Ͽ� ����
 * 
 * 3. @ModelAttribute �޼ҵ尡 �����ϴ� ��ü
 * => @ModelAttribute �� ��Ʈ�ѷ� Ŭ������ �޼ҵ� �����ε� ��ü ���� ����, �޼ҵ� �������� �ο� ����
 * �޼ҵ� �������� @ModelAttribute�� ����Ͽ� ��ü�� �����ϴ� ��� @RequestMapping ������ ���ƾ� ��
 * 	=> Controller ����� �ƴ϶� ��ü�� �����Ͽ� �����ϴ� ��� ����ϱ� ����
 * 
 *  Ex)	@ModelAttribute("categories")
 *  	public void commonCategories(){
 *  		return service.getCommonCategories();
 *  	}
 *  �� ���� ���� @ModelAttribute("categories") ��ü�� �������ִ� ��� �ش� Ŭ������ �����ϴ� ��� Controller�� 
 *  ���� ��(��)�� categories��� ������ �ڵ� �߰���.
 *  ������ �˻� ����, �Խ����� ī�װ�, ���� ������ ��� �� ��Ʈ�ѷ��� ���ε� �� ���ݿ��� �ٷ�� ������ 
 *  Controller ���� ��� �޼ҵ尡 ������ ����ϱ� ���ؼ� ����Ѵ�.
 *  
 *  @ModelAttribute �±��� ���� ����� ������ ���ٸ� ���� �޼ҵ忡 �����Ͽ� ��� �� �� �ִ�.
 *  (���� ���� �޼ҵ带 ������ ���� �� ����)
 * 
 * 4. BindingResult
 * => @ModelAttribute �� ���������� �𵨿� �ڵ� �߰��ȴ�.
 * �� �ʿ� �߰� �� ���� Ű�� "org.springframework.validation.BindingResult.���̸�"
 * 	=> springframework ������ ���� �������� ���̴� ������ ������ Ȱ���Ͽ� ���������� ����ϴ� ���� ���� ���ٰ� ��
 *
 * Ex) �� �̸��� user�� ���
 * 		org.springframework.validation.BindingResult.user
 * 
 * 5. ModelAndView
 * => ModelAndView�� ��Ʈ�ѷ��� �����ؾ� �� ���� ��� �ִ� ���� ��ǥ���� Ÿ��
 * 
 * ���� Controller ��Ÿ�Ͽ����� �������� ����̾����� ���� @MVC �𵨿����� �̸� ����� ���� ���� ����� ���Ƽ�
 * ����ŭ�� ������ �ʴ� ���
 * ���� Controller ��Ÿ���� �ڵ带 @MVC ��Ÿ�Ϸ� ���� �� ��� ���
 * 
 * Ex)	������ 2.5�� �� ������ ���� ���Ǵ� Controller Ÿ���� Ŭ����
 * 		public class HelloController implements Controller {
 * 			@Override
 * 			public ModelAndView handleRequest(
 * 				HttpServletRequest request, HttpServletResponse response)throws Exception{
 * 				String name = request.getParameter("hello");
 * 				return new ModelAndView("hello.jsp").addObject("name",name);
 * 			}	
 * 		}
 * 
 * 		=> �� �ڵ带 @MVC ��Ÿ�Ϸ� ��ȯ : @Controller ����ü�̽� ���� ����, 
 * 			@Controller, @RequestMapping �ο�
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
 * 		=> ���� @MVC Controller ��� �ۼ� : HttpServletRequest ��� @RequestParam ����� �Ķ���� �� ����,
 * 			�޼ҵ� �� �ǹ�ǰ�� ������ ����, �ʿ��� ��� �ƴ϶�� throws Exception ����, Model �Ķ���� ���
 * 		@Controller
 * 		public class HelloController{
 * 			@RequestMapping("/hello")
 * 			public ModelAndView helloMethod(@RequestParam String name, Model model){
 * 				model.addAttribute("name",name);
 * 				return new ModelAndView("hello");
 * 			}
 * 		}		
 * 
 * 6. String�� ���
 * =>1. �����ϴ� ���ڿ��� ���� �̸�, 2. model ��ü�� data �� ��� ��� ���� ���ڿ��̸��� ��� ������ �Բ� ����
 * �� �� �ۼ��� ������ ���� �ڵ�� �����Ͽ� ���� ���� ��, String ���� Ÿ������ �� �̸����� �����ص� ���� ȿ�� �� �� ����
 * 	=> ���� ���������� �ڵ�
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
 * => RequestToViewNameResolver ������ ���� �ڵ� �����Ǵ� �� �̸� ���
 * URL�� �� �̸��� �ϰ��ǰ� ���� �� �� �ִٸ� ���������� Ȱ���غ����� ����
 * �� String ������ �����Ͽ� ���� �� ��� ���� ������ �ڵ� �ۼ� ����
 * 	Ex) @RequestMapping("/hello")
 * 		public void helloMethod(@RequestPara String name, Model model){
 * 			model.addAttribute("name", name);
 * 		}
 *  
 *  RequestToViewNameResolver�� �ڵ����� view �̸��� hello�� ������� (�߰� Study �ʿ�)
 *  		=> WEB-INF/view/hello.jsp ��� �ϰ� ��
 * 
 * 8. �� ������Ʈ
 * => RequestToViewNameResolver�� ����� �� �̸��� �ڵ����� �����ϰ�, �𵨿� �߰��ؾ� �ϴ� ��ü�� �ϳ� ���̶��
 * �� �Ķ���͸� �޾� �����ϴ� ��� �� ��ü�� �ٷ� �����ص� �ȴ�. 
 * �������� �����ؾ� �� ��ü�� �̸� ������ Ÿ���̳� void�� �ƴ� �ܼ� ��ü��� �̸� �� ��ü�� �ν��Ͽ� �ڵ����� �𵨿� 
 * �߰����ش�. �̶� �� �̸��� ���ϰ��� �̸��� ������
 * 		=>1 �� �׸� @ModelAttribute�� ����� ����
 * 	Ex)	@RequestMapping("/view")
 * 		public User view(@RequestParam int id){
 * 			return service.getUser(id);
 * 		}
 * 
 * �Ķ���ͷ� ���� ���̵� ���ͼ� ���� ����(����, dao ��)�� ���ؼ� User ��ü�� �����Ѵ�. 
 * ���� Ÿ������ ������ User Ÿ�� ��ü�� �ڵ����� �� ��ü�� �߰��ǰ� �� �̸��� user�� �ȴ�.
 * Ŭ�����̸��� �ٸ��� �� �̸��� ���� �����ϰ���� ��� @ModelAttribute("���̸�") ������� �����Ѵ�.
 * 
 * 9. Map / Model / ModelMap
 * => ���ϰ��� Map Ÿ������ �����ϴ� ���� �ǵ����̸� ���ϴ� ���� ����. ���� ��ü�� �ڵ� ��� �Ǵ� ������� 
 * Map �� ���� ������ �����ϴ� ���, Map userMap �̶�� ��ü�� �����Ѵٰ� �ؼ� map �̶�� �ڵ� ��� ��ü��
 * ���� �����Ǵ� ���� �ƴϱ� �����̴�. �̴� ȥ���� �ʷ��Ѵ�. model �Ķ���Ϳ� ������ map ��ü�� ��Ƽ� �ѱ�� ����
 * �ٶ����ϴ�.
 * 
 * 10.view
 * => ��Ʈ�� ���� Ÿ���� �� �̸����� �ν��Ѵ�. �̿� ����ϰ� �� ��ü�� ���ϰ����� �ѱ�� �ʹٸ� ���ϰ��� View �� �����ϰ�
 * View ��ü�� ���ϰ����� �ѱ�� �ȴ�.
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
 * => @ResponseBody�� @RequestBody�� ����� ������� �����Ѵ�. @ResponseBody�� �޼ҵ� ������ �ο��Ǹ�
 * �޼ҵ尡 �����ϴ� ��ü�� �並 ���� ����� �����ϴ� �𵨷� ���Ǵ� ���� �ƴ϶�
 * �޼��� �ܹ��͸� ���� �ٷ� HTTP ������ �޼��� �������� ��ȯ�ȴ�.
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
	 * @desc	: @RequestMapping annotation ������
	 * 				1) Ư�� URI�� �ǹ��ϴ� value �Ӽ���
	 * 				2) GET/POST �� ���۹������ �����ϴ� method �Ӽ��� ��� ��
	 * 				3) value �� method �Ӽ��� �� �� �迭��, �������� �Ӽ� �� ���� ����
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
	 * @desc	: ȭ�鿡�� �ԷµǾ� ������ �����͸� ó��
	 * 				�Ķ����
	 * 					1. �ڵ����� ��� �����͸� BoardVO�� ����
	 * 					2. ��� ������ ���� ���ɼ� �����Ƿ� Model Ŭ������ ��ü ����
	 * 
	 * 				Model Ŭ����
	 * 					Spring MVC���� �����ϴ� ������ ���޿� ��ü
	 * 					Map�� �����ϰ� 'Ű(key)'�� '��(value)'���� �����͸� �����ϴ� ����
	 * 					���� Servlet������ RequestDispatcher�� ������ �����ߵ���, Spring������ Model �̿�
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
		
		/*client���� �����ϴ� ������ ����� ���� �� Controller ���� ó���ϴ� ���
		 * JSP ���� ó���ϴ� ��İ� ���ϱ� ���� �ڵ� �ۼ�
		 * redirect ������� ó�� => ȭ�� �ܿ��� alert()�� �޼��� �����ִ� �� ����������,
		 * ȭ�� ��ȯ�� ����, alert() �޼��� ���� ȭ���� �Ѿ�� �ʴ´�. */
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
		
		/*model ��ü�� ���� ���� �� ��� GET �������� URI�� "result=SUCCESS"���ڿ��� ����
		 * ���Ӱ� ����� RedirectAttributes rttr ��ü�� ���� ���� �� ��� POST �������� URI��
		 * ���� ��Ÿ���� ����*/
//		model.addAttribute("result", "SUCCESS");
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		/*model ��ü�� ����� ����� �����ϴ� ���� ��� 
		 *  '/WEB-INF/views/board/success.jsp' */
//		return "/board/success";
		/*success page ���ΰ�ħ �� ���� ������ ������ ������ ���� �� �ߺ� ����� ���� ���Ͽ�
		 * redirect �����ش�.*/
		return "redirect:/board/listAll";
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 1. 24.
	 * @desc	: �����̷�Ʈ �� ��� ������ ȭ�� �� ������ ����ִ� �޼���
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
	 * @desc	: listAll ȭ�鿡�� �� ���� ��ũ Ŭ���� �Խù� ������ �����ִ� ȭ��
	 * 				
	 * 				@RequestParam Annotation�� Servlet���� request.getParameter()�� ȿ���� ����
	 * 				@RequestParam("bno")�� ���� request.getParameter("bno")ó�� �����մϴ�. Servlet��
	 * 				HttpServletRequest�� �ٸ����� ���ڿ�, ����, ��¥ ���� �� ��ȯ�� �����ϴٴ� ��
	 * 
	 * 				model.addAttribute() �޼��� ���� JSP�� ���� �ѱ� �� �̸��� ���������� ������ �ڵ����� Ŭ������ �̸��� �ҹ��ڷ� �����ؼ� ���
	 * 				read�޼����� ��� model.addAttribute(service.read(bno))�� �ұ�µ� �̶� �Ѿ�� ���� BoardVO Ŭ������
	 * 				��ü�̹Ƿ�, 'boardVO'��� �̸����� �����
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
	 * @desc	: �Խù� ���� �޼���
	 * 				�Խù� ������ȣ�� @RequestParam("bno")�� �޾Ƽ� �ش� �Խù��� ����
	 * 				���� �Ŀ��� listAll �Խù� ����Ʈ �������� redirect ��Ŵ
	 * 				RedirectAttributes rttr �� �����Ͽ� addFlashAttribute()�޼��带 �����
	 * 				rttr.addFlashAttribute("msg", "SUCCESS"); �� ����.
	 * 				RedirectAttributes�� �� ����� ����ϸ� ������ ���� ��ȸ������ ������
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
		
		/*JSP ���Ͽ��� ������ ȣ���ϴ� ��� '=> cri.page' ó�� ������ ȣ�� �� ���� �ְ�����
		 * Controller������ domain ��ü Criteria �� ���Ͽ� getter, setter �� ����ؼ� ����
		 * private���� ���� �Ǿ� �ִ� ������.*/
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addFlashAttribute("msg", "SUCCESS");
		
		return "redirect:/board/listPage";
		
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 2. 26.
	 * @desc	: �ش� �������� ����Ʈ�� DB���� ��ȸ�ϴ� �޼���
	 * 				
	 * 				������ MVC ��Ʈ�ѷ��� Ư�� URL�� �ش��ϴ� �޼ҵ带 ������ ��, �Ķ������
	 * 				Ÿ���� ����, �ش� ��ü�� �ڵ����� ������ ���ϴ�. 
	 * 				�Ķ���Ͱ� �ڵ����� �����Ǳ� ������, �ٷ� ������ ���� Criteria��� Ŭ������ �״��
	 * 				����� �� �ֽ��ϴ�.
	 * 
	 * 				����Ʈ �������� �⺻������ GET��� ���.(<= ��κ��� ��ȸ�� GET ���)
	 * 				���� �ÿ��� GET ����� ��� URL�� �����ϴ� �͸����ε� �������� ������ Ȯ�� �� �� ����
	 * 
	 * 				�� ������ MVC ��Ʈ�ѷ��� �޼ҵ� �Ķ���� Ÿ�� �ش� ��ü �ڵ� ���� Ư¡��
	 * 				GET ����� URL ���� ���� Ȯ�� Ư¡�� ������ 
	 * 				Criteria cri ������ Ŭ������ ������ Ÿ���� ��ü�� URL�� ������ �ٿ�
	 * 				Test ����
	 * 
	 * 				Ex) localhost:8080/board/listCri?page=3&perPageNum=20/
	 * 				���� ���� ���� ��� Criteria Ŭ������ int page ������ int perPageNum ������
	 * 				page =3, perPageNum =20�� ���� �ڵ����� ���ε�
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
		/*model�� service dao mapper db �帧�� ó�� ���� ������ �߰��� ��� Test ���� 
		 * �Ʒ��� ���� model.asMap().put���� ���� ����ִ� JSP �ܿ��� ${testMessage}�� ���� �� ���� �� ����
		 * var testMessage = '${testMessage}' �� JSP ������ �޾Ƽ� ����Ϸ��� alert() �����
		 * ���� ���� true/false �� �������� ���� Ȯ��, �� �κп� ���ؼ��� Ȯ�� ��
		 * 
		 *  �� ������Ȳ�� ����� ������ ������ �������� ���� �̹� �ý��� �󿡼� ������ ��Ī �̾��� ������ ������ ���δ�.
		 *  var testMessage �� var testM �̳� var testMsge �� �ٲٰ� ���� �Է����� ��� ���������� �Ǵ� �� Ȯ�ε�
		 *  var testMessage ��� ������ ���ο� ������ �ʱ�ȭ�� ����� ��� ����ؼ� true �� �� return ��*/
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
	 * @desc	: JSP ���͵� ���ؼ� ���������� ���� �޼��� �Դϴ�.
	 * 				README���� ����� å�� ���뿡�� ���ԵǾ� ���� �ʽ��ϴ�.
	 * 				NewFile() �޼���� ����Ǿ� �ִ� NewFIle.jsp ���ϰ� ���� ���� ���������� ������ ���Դϴ�.
	 * 
	 * ��ó : http://rank01.tistory.com/33?category=487064
	 * 				JSP ��ũ��Ʈ ���
	 * 				1. ���� <%! ...........%>
	 * 					- jsp ���������� �ڹ��ڵ��� ��������� �޼��带 �����ϱ� ���� ���
	 * 					- ������ ����� ����� ������ JSP ������ ������ �� �� ��� ������ �Ǳ� ������, 
	 * 						JSP �������� ��� ��ġ������ �ش� ������ �����ϴ� �� ����
	 * 					ex)
	 * 					<%!
	 * 						private String study = "JSP������";
	 * 						public String checkStr(){
	 * 							if(study==null){
	 * 								return "no";
	 * 							} else {
	 * 								return "ok";
	 * 							}
	 * 						}
	 * 					%>
	 * 
	 * 				2. ��ũ��Ʋ�� <% ........... %>
	 * 					- HTML �ڵ�� �� �κ��� �Ϲ� HTML ����ó�� ����ϰ� �ڹ� �ڵ�� �̷���� ���� �κ���
	 * 					<% ........... %> �� ǥ���Ǵ� ��ũ��Ʋ�� �±� ����Ͽ� ����
	 * 					=> out ��ü�� ������� �ʰ� ���� HTML ������ ����� �� �� ����
	 * 					-ǥ���� �±��� ������� JSP ������ �Ľ̵� �� ��� ��ü�� print() �޼ҵ带 ���� �ڵ����� ���ڿ� ��������
	 * 					��ȯ�Ǿ� ��µ�
	 * 					=> ���� ��ü�� �ϳ��� print()�޼ҵ��� ��ȣ �ȿ� ���� �ǹǷ� �����ݷ�(;)�� ����ؼ��� �ȵ�
	 * 					ex)
	 * 					<%
	 * 						Calendar c = Calendar.getInstance();
	 * 						int hour = c.get(Calendar.HOUR_OF_DAY);
	 * 						int minute = c.get(Calendar.MINUTE);
	 * 						int second = c.get(Calendar.SECOND);
	 * 					%>
	 * 				
	 * 				3. ǥ���� <%= ........... %>
	 * 					- ���� �Ǵ� ��ũ��Ʋ�� �±׿��� ����� ������ �޼ҵ��� ���ϰ�, �������� �ܺο��� ����ϱ� ���� ���
	 * 					ex)
	 * 					<body>
	 * 					<h2> ������ <%= study %> �Դϴ�</h2>
	 * 					<br>
	 * 					<h2> ���� �ð��� <%=hour%>�� <%=minute%>�� <%=second%>�� �Դϴ�.</h2>
	 * 					</body>
	 * 	
	 * ��ó : http://rank01.tistory.com/33?category=487064
	 * @throws Exception
	 */
	@RequestMapping(value="/NewFile", method=RequestMethod.GET)
	public void NewFile()throws Exception{
		logger.info("NewFile is Called ...........");
		/*���ϰ��� ���ǵǾ� ���� �ʾƵ� ȭ���� �����ϴ� ���� ������. �ݴ�� ���ϰ��� return "/board/read"; ó��
		 * �ٸ� jsp ���Ϸ� ���ν�Ű�� NewFile.jsp ȭ���� ��Ÿ���� �ʰ�, ���ε� ������ ȭ������ �ٷ� �̵���. 
		 * Controller�� ���ǵ� �޼���� �ƹ� ��� ���� ȭ���� �̾��ִ� ��ĸ����ε� ��� ������*/
//		return "/board/NewFile";
	}
	
	@RequestMapping(value ="/templateStudy", method=RequestMethod.GET)
	public void testTemplateStudy(){
		logger.info("templateStudy JSP File just has been called");
	}
}
