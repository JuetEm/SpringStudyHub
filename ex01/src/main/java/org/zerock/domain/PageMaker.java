/**
 * 
 */
package org.zerock.domain;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author	: Juet
 * @date	: 2018. 2. 27.
 * @desc	: ����¡ ó���� Ŭ����
 * 				�ܺο��� �ԷµǴ� �����Ϳ� DB�� �����͸� ���, ��� ���� ����¡ ó��
 * 				
 * 				Math.ceil() : �ø�
 * 				Math.round() : �ݿø�
 * 				Math.floor() : ����
 * 				Math.abs() : ���밪
 * 
 *  			����¡ ó���� ���ؼ� �ʿ��� ������
 *  			Criteria.class
 *  			page : ���� ��ȸ�ϴ� �������� ��ȣ
 *  			perPageNum : �� �������� ����ϴ� �������� ����
 *  			
 *  			DB
 *  			totalCount : SQL ���� ���� �������� ��ü ����
 *  		
 *  			PageMaker.class <= ��� ���� �� ����
 *  			startPage
 *  			endPage
 *  			prev
 *  			next
 *  
 *  			����¡ ó�� ������ �ٽ��� ��ü ������ �����ְ��� �ϴ� ������ ���� �� ������ ����
 *  			endPage�� ���� ã�Ƴ��� ��
 * 				
 */

public class PageMaker {

	private int totalCount;
	private int startPage;
	private int endPage;
	private boolean prev;
	private boolean next;
	private boolean test = true;
	private boolean what = false;
	
	private int displayPageNum = 10;
	
	private Criteria cri;
	
	/**
	 * 
	 */
	public PageMaker() {
		// TODO Auto-generated constructor stub
	}
	
	public void setCri(Criteria cri){
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount){
		this.totalCount = totalCount;
		
		calcData();
	}

	/**
	 * @author	: Juet
	 * @date	: 2018. 2. 27.
	 * @desc	: ����¡ ó���� ǥ���� �������� ������ ��, ù ������ ��ȣ, �� ������ ��ȣ
	 * 				����, ���� ��ư �����ֱ� ���� ó���ϴ� �޼���
	 * 				
	 *
	 */
	private void calcData(){
		
		endPage = (int) (Math.ceil(cri.getPage() / (double) displayPageNum) * displayPageNum);
		
		startPage = (endPage - displayPageNum) + 1;
		
		int tempEndPage = (int)(Math.ceil(totalCount / (double) cri.getPerPageNum()));
		
		if(endPage > tempEndPage){
			endPage = tempEndPage;
		}
		
		prev = startPage == 1? false : true;
		
		next = endPage * cri.getPerPageNum() >= totalCount? false : true;
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 3. 3.
	 * @desc	: Get ������� �̷������ Paging ó��
	 * 				������ �̵� ���� �����͸� �ű�� ���� �ڵ尡 ������� ���� ����
	 * 				����¡ �� ������ ��ȸ�� Uri�� �����ؼ� Ÿ�ο��� �����Ͽ� �������� ���� ���� �� �� �ִ� �������
	 * 				�����ϴ� ���� ���� Ȱ��� ������ �����ϱ� ������ Uri�� ���� ��� ���� ����
	 * 				�Ʒ� UriComponents ��ü�� �����Ͽ� ������ �̵� �� Uri�� �������� ���� �����Ͽ� 
	 * 				��Ƶд�.
	 *
	 * @param page
	 * @return
	 */
	public String makeQuery(int page){
		
		UriComponents uriComponents =
				UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", cri.getPerPageNum())
				.build();
		return uriComponents.toString();
	}
	
	/**
	 * @param displayPageNum the displayPageNum to set
	 */
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	
	/**
	 * @param endPage the endPage to set
	 */
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	
	/**
	 * @param next the next to set
	 */
	public void setNext(boolean next) {
		this.next = next;
	}
	
	/**
	 * @param prev the prev to set
	 */
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	
	/**
	 * @param startPage the startPage to set
	 */
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	
	/**
	 * @return the cri
	 */
	public Criteria getCri() {
		return cri;
	}
	
	/**
	 * @return the displayPageNum
	 */
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	
	/**
	 * @return the endPage
	 */
	public int getEndPage() {
		return endPage;
	}
	
	/**
	 * @return the startPage
	 */
	public int getStartPage() {
		return startPage;
	}
	
	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}
	
	/**
	 * @author	: Juet
	 * @date	: 2018. 3. 1.
	 * @desc	: JSP ���Ͽ��� pageMaker.prev�� �дµ� ������ �ڲ� �߻���
	 * 				javax.el.PropertyNotFoundException: Property [prev] not readable on type
	 * 				�� Exception�� ������ ����ؼ� ������ ���� ����
	 * 
	 * 				���۸��� ���� �˾ƺ� ��� ��ǥ���� ���ε��� �־���
	 * 				1. ������ ���� �� quotation �ȿ� ������ �����ϴ� ���
	 * 					Ex) <c:if test=" ${pageMaker.prev}"> 
	 * 				2. ��ҹ��� �� ���� ���� ��Ȯ�� �������� �� �Ͽ��� ��
	 * 					Ex) <c:if test=" ${pageMaker.Prev}"> 
	 * 				3. �����ϰ��� �ϴ� class�� public���� ������� ���� ���
	 * 					Ex) class PageMaker{
	 * 						
	 * 						}
	 * 				�ռ� �ٷ� ������ ���� ��� Ȯ���ϰ� �ٽ� �����غ������� �ذ���� ����
	 * 				
	 * 				4. getter, setter, toString �� override method�� ��� ������� ���� ���
	 * 				4�� �׸� ������ Ȯ�� �ϴ� �����ϰ��� �ϴ� ������ ���� getter�� ����Ǿ� ���� ���� 
	 * 				�Ʒ� �ΰ� �޼ҵ� �������� jsp ���Ͽ��� ���������� ���� ���� ��
	 * 
	 * 				8BOKNOTE���� ��α׿��� ���ΰ� �ذ� ��� ã�� �� �־���
	 * ��ó: http://8boknote.tistory.com/14 [8BOKNOTE]
	 *
	 * @return
	 */
	public boolean getPrev(){
		return prev;
	}
	
	public boolean getNext(){
		return next;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	
}
