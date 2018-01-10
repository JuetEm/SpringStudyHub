package org.zerock.web;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.domain.ProductVO;

@Controller
public class SampleController3 {

	String className = this.getClass().getSimpleName();
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SampleController3.class);
	
	@RequestMapping("/doD")
	public String doD(Model model){
		
		//make sample data
		ProductVO productVo = new ProductVO("Sample Product", 10000);
		
		logger.info(className+"'s doD called..........................");
		
		model.addAttribute(productVo);
		
		return "productDetail";
	}
}
