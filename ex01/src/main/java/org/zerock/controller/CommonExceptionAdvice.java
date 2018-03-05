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
 * @desc	: @ControllerAdvice를 이용한 예외처리 Class
 * 				Exception 처리 전용 객체를 사용하는 방식
 * 				MVC에서 제공하는 @ControllerAdvice는 발생하는 모든 Exception 처리
 * 					1. 클래스에 @ControllerAdvice 어노테이션 처리
 * 					2. 각 메서드에 @ExceptionHandler 이용해 적절한 Exception 처리
 * 
 * 				@ControllerAdvice 클래스의 메소드는 발생한 Exception 객체의 타입만을 파라미터로 사용 가능
 * 				=> 일반 Controller처럼 Model을 사용 할 수 없음
 * 				과거 Spring MVC 에서 사용했던 ModelAndView 타입을 이용,
 * 				=> ModelAndView는 하나의 객체에 Model 데이터와 View 의 처리 동시에 할 수 있는 객체
 * 					MVC에서는 @ControllerAdvice 클래스와 같이 받을 수 있는 파라미터가 정해져있는 경우 
 * 					주로 사용
 */
@ControllerAdvice
public class CommonExceptionAdvice {

	private static final Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);
	
	@ExceptionHandler(Exception.class)
	public ModelAndView common(Exception e){
		
		/*아래와 같이 처리하는 경우 @ControllerAdvice 클래스의 특성(Exception 객체만 파라미터로 받음)상
		 * 데이터 처리가 불가능 하기 때문에 ModelAndView 객체 생성해서 처리*/
//		logger.info(e.toString());
//		
//		return "error_common";
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/error_common");
		modelAndView.addObject("exception", e);
		
		return modelAndView;
	}
}
