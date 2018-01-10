package org.zerock.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SampleController4 {

	String className = this.getClass().getSimpleName();
	private static final Logger logger = LoggerFactory.getLogger(SampleController4.class);
	
	@RequestMapping("/doE")
	public String doE(RedirectAttributes rttr){
		logger.info(className+"'s doE called but redirect do /doF..................");
		
		rttr.addFlashAttribute("msg", "This is the Message!!with redirected!");
		return "redirect:/doF";
	}
	
//	�Ʒ� @ModelAttribute("msg") �޾��ִ� ��� �� rttr.addFlashAttribute("msg", "This is the Message!!with redirected!");
//	�� msg �Ķ���� ���� "This is the Message!!with redirected!"�� ���� �� �ִ�.
//	@RequestMapping("/doF")
//	public void doF(@ModelAttribute("msg") String msg){
	@RequestMapping("/doF")
	public void doF(@ModelAttribute String msg){
		
		logger.info(className+"'s doF called.............."+msg);
	}
}
