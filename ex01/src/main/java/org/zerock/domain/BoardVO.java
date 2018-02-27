package org.zerock.domain;

import java.util.Date;

/**
 * @author	: Juet
 * @date	: 2018. 1. 22.
 * @desc	: DB �۾��� �켱 ����, 1)�ܼ��� DB �����̰� 2)���߿� �ͼ����� ���� ��� ������ �����̳� ȭ�� ������ �ʿ��� Controller �ܿ��� �����ϱ� ���ٴ� 
 * VO�۾��� ���� �����ϴ� ���� ����..�� ������ �ǰ�. 
 * 
 * VO �ۼ� �Ŀ��� Mybatis�� DAO, XML Mapper �������־�� ��
 * 
 */
public class BoardVO {
	
	private Integer bno;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	private int viewcnt;
	
	/*MVC �� ���� Ÿ�� ��ü ��ȯ ���� ���� ���� �߰��� ���� Test*/
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
