/**
 * 
 */
package org.zerock.test;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

/**
 * @author	: Juet
 * @date	: 2018. 1. 24.
 * @desc	: JUnit Test를 진행하려 하는 간에 ClassNotFoundException 과 
 * log4j:WARN No appenders could be found for logger (org.springframework.test.context.junit4.SpringJUnit4ClassRunner).
 * log4j:WARN Please initialize the log4j system properly. 등 문제가 발생 하였다.
 * 
 * 정확한 해결 방법은 찾지 못했다. 구글링 결과 StackOverFlow 에서 비슷한 사례 찾아볼 수 있었으나 근본적인 해결책은 접하지 못함.
 * 
 * 프로그램 껐다가 켜니 해결;;;
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardServiceTest {
	
	@Inject
	private BoardService service;
	
	private static Logger logger = LoggerFactory.getLogger(BoardServiceTest.class);
	
	@Test
	public void svcRegist() throws Exception{
		BoardVO vo = new BoardVO();
		vo.setTitle("서비스 테스트 타이틀입니다.");
		vo.setContent("서비스 테스트 내용입니다.");
		vo.setWriter("서비스 테스트 글쓴이 입니다.");
		service.regist(vo);
	}
	
	@Test
	public void svcListAll() throws Exception{
			logger.info("서비스 테스트 리스트 \r\n"+service.listAll().toString());
	}

}
