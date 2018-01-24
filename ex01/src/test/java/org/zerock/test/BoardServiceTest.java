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
 * @desc	: JUnit Test�� �����Ϸ� �ϴ� ���� ClassNotFoundException �� 
 * log4j:WARN No appenders could be found for logger (org.springframework.test.context.junit4.SpringJUnit4ClassRunner).
 * log4j:WARN Please initialize the log4j system properly. �� ������ �߻� �Ͽ���.
 * 
 * ��Ȯ�� �ذ� ����� ã�� ���ߴ�. ���۸� ��� StackOverFlow ���� ����� ��� ã�ƺ� �� �־����� �ٺ����� �ذ�å�� ������ ����.
 * 
 * ���α׷� ���ٰ� �Ѵ� �ذ�;;;
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
		vo.setTitle("���� �׽�Ʈ Ÿ��Ʋ�Դϴ�.");
		vo.setContent("���� �׽�Ʈ �����Դϴ�.");
		vo.setWriter("���� �׽�Ʈ �۾��� �Դϴ�.");
		service.regist(vo);
	}
	
	@Test
	public void svcListAll() throws Exception{
			logger.info("���� �׽�Ʈ ����Ʈ \r\n"+service.listAll().toString());
	}

}
