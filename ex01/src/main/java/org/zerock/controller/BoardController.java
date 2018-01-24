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
	public void regsterGET(BoardVO board, Model model) throws Exception{
		
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
		
		/*modle ��ü�� ����� ����� �����ϴ� ���� ��� 
		 *  '/WEB-INF/views/board/success.jsp' */
		return "/board/success";
	}

}
