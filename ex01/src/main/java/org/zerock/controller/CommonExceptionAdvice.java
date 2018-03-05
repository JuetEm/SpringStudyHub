/**
 * 
 */
package org.zerock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author	: Juet
 * @date	: 2018. 1. 29.
 * @desc	: @ControllerAdvice�� �̿��� ����ó�� Class
 * 				Exception ó�� ���� ��ü�� ����ϴ� ���
 * 				MVC���� �����ϴ� @ControllerAdvice�� �߻��ϴ� ��� Exception ó��
 * 					1. Ŭ������ @ControllerAdvice ������̼� ó��
 * 					2. �� �޼��忡 @ExceptionHandler �̿��� ������ Exception ó��
 * 
 * 				@ControllerAdvice Ŭ������ �޼ҵ�� �߻��� Exception ��ü�� Ÿ�Ը��� �Ķ���ͷ� ��� ����
 * 				=> �Ϲ� Controlleró�� Model�� ��� �� �� ����
 * 				���� Spring MVC ���� ����ߴ� ModelAndView Ÿ���� �̿�,
 * 				=> ModelAndView�� �ϳ��� ��ü�� Model �����Ϳ� View �� ó�� ���ÿ� �� �� �ִ� ��ü
 * 					MVC������ @ControllerAdvice Ŭ������ ���� ���� �� �ִ� �Ķ���Ͱ� �������ִ� ��� 
 * 					�ַ� ���
 */
@ControllerAdvice
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public ModelAndView common(Exception e){
		
		/*�Ʒ��� ���� ó���ϴ� ��� @ControllerAdvice Ŭ������ Ư��(Exception ��ü�� �Ķ���ͷ� ����)��
		 * ������ ó���� �Ұ��� �ϱ� ������ ModelAndView ��ü �����ؼ� ó��*/
//		logger.info(e.toString());
//		
//		return "error_common";
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/error_common");
		modelAndView.addObject("exception", e);
		
		return modelAndView;
	}
}
