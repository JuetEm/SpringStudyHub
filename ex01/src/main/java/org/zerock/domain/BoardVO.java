package org.zerock.domain;

import java.util.Date;

/**
 * @author	: Juet
 * @date	: 2018. 1. 22.
 * @desc	: DB 작업을 우선 진행, 1)단순한 DB 형태이고 2)개발에 익숙하지 않은 경우 복잡한 로직이나 화면 구성이 필요한 Controller 단에서 시작하기 보다는 
 * VO작업을 먼저 시작하는 것이 좋다..는 저자의 의견. 
 * 
 * VO 작성 후에는 Mybatis의 DAO, XML Mapper 설정해주어야 함
 * 
 */
public class BoardVO {
	
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int viewcnt;
	
	/*MVC 모델 변수 타입 객체 반환 도중 변수 임의 추가를 위한 Test*/
	private String testMessage;
	
	public Integer getBno() {
		return bno;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getWriter() {
		return writer;
	}
	public Date getRegdate() {
		return regdate;
	}
	
	public int getViewcnt() {
		return viewcnt;
	}
	
	public void setBno(Integer bno) {
		this.bno = bno;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public void setWriter(String writer) {
		this.writer = writer;
	}
	
	public void setViewcnt(int viewcnt) {
		this.viewcnt = viewcnt;
	}
	
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

}
