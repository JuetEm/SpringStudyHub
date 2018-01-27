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
//			service.regist(board);
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
}
